package com.kncept.mirage.classformat.signature.parser;

import static java.util.Optional.empty;
import static java.util.Optional.of;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.kncept.mirage.MirageType;
import com.kncept.mirage.classformat.ClassFormatMirage;

public class ClassNamePartialSignatureParser implements PartialSignatureParser {
	private static final String classTypeCharacter = "L";
	private static final String classTypeEndCharacter = ";";
	
	private static final String genericsStartCharacter = "<";
	private static final String genericsEndCharacter = ">";
	
	private final boolean includeGenericsSupport;
	
	public ClassNamePartialSignatureParser(boolean includeGenericsSupport) {
		this.includeGenericsSupport = includeGenericsSupport;
	}
	
	@Override
	public Optional<PartialSignatureResponse> parse(PartialSignatureReqeust signature){
		if (signature.remainingSignature.startsWith(classTypeCharacter)) {
			if (includeGenericsSupport) {
				int classEndIndex = signature.remainingSignature.indexOf(classTypeEndCharacter);
				int genericsStartIndex = signature.remainingSignature.indexOf(genericsStartCharacter);
				
				if (genericsStartIndex == -1 || classEndIndex < genericsStartIndex) {
					return extractNonGenericsResponse(signature);
				}
				return extractGenericsResponse(signature);
				
			} else {
				return extractNonGenericsResponse(signature);
			}
		}
		return empty();
	}
	
	public Optional<PartialSignatureResponse> extractNonGenericsResponse(PartialSignatureReqeust signature) {
		int classEndIndex = signature.remainingSignature.indexOf(classTypeEndCharacter);
		String className = signature.remainingSignature.substring(classTypeCharacter.length(), classEndIndex);
		className = ClassFormatMirage.jvmClassnameToJavaClassname(className);
		return of(
				new PartialSignatureResponse(
						new MirageType.SimpleMirageType(className),
						signature.remainingSignature.substring(classEndIndex + classTypeEndCharacter.length())));
	}
	
	public Optional<PartialSignatureResponse> extractGenericsResponse(PartialSignatureReqeust signature) {
		int genericsStartIndex = signature.remainingSignature.indexOf(genericsStartCharacter);
		String className = signature.remainingSignature.substring(genericsStartCharacter.length(), genericsStartIndex);
		className = ClassFormatMirage.jvmClassnameToJavaClassname(className);
		
		Optional<GenericInfo> genericParams = extractGenericParams(
				new PartialSignatureReqeust(
						signature.remainingSignature.substring(genericsStartIndex),
						signature.parsers));
		
		if (!genericParams.isPresent() || !genericParams.get().remainingSignature.startsWith(classTypeEndCharacter))
			return empty();
		
		String remainder = genericParams.get().remainingSignature.substring(classTypeEndCharacter.length());
		return of(
				new PartialSignatureResponse(
						new MirageType.SimpleMirageType(
								className,
								0,
								genericParams.get().mirageTypes
								),
						remainder));
	}
	
	public Optional<GenericInfo> extractGenericParams(PartialSignatureReqeust signature) {
		String remainingSignature = signature.remainingSignature;
		if (!remainingSignature.startsWith(genericsStartCharacter))
			return empty();
		remainingSignature = remainingSignature.substring(genericsStartCharacter.length());
		
		List<MirageType> mirageTypes = new ArrayList<>();
		while(!remainingSignature.isEmpty() && !remainingSignature.startsWith(genericsEndCharacter)) {
			//could be just a *
			//otherwise its a class name - POSSIBLY prefixed with a + or a -
			
			if (remainingSignature.startsWith("*")) { //logically equal to +Ljava/lang/Object; ?
				remainingSignature = remainingSignature.substring(1);
				mirageTypes.add(new MirageType.SimpleMirageType(Object.class.getName()));
				continue;
			}
			
			if (remainingSignature.startsWith("+") || remainingSignature.startsWith("-")) {
				remainingSignature = remainingSignature.substring(1);
			}
			
			Optional<PartialSignatureResponse> genericType = subParse(new PartialSignatureReqeust(remainingSignature, signature.parsers));
			if (!genericType.isPresent())
				return empty();
			remainingSignature = genericType.get().remainingSignature;
			mirageTypes.add(genericType.get().mirageType);
			
		}
		if (remainingSignature.startsWith(genericsEndCharacter)) {
			remainingSignature = remainingSignature.substring(genericsEndCharacter.length());
			return of(new GenericInfo(mirageTypes, remainingSignature));
		}

		return empty();
	}
	
	public Optional<PartialSignatureResponse> subParse(PartialSignatureReqeust signature) {
		for(PartialSignatureParser parser: signature.parsers){
			Optional<PartialSignatureResponse> sig = parser.parse(signature);
			if (sig.isPresent())
				return sig;
		}
		return empty();
	}

	private static class GenericInfo {
		final List<MirageType> mirageTypes;
		final String remainingSignature;
		public GenericInfo(List<MirageType> mirageTypes, String remainingSignature) {
			this.mirageTypes = mirageTypes;
			this.remainingSignature = remainingSignature;
		}
	}
	
}
