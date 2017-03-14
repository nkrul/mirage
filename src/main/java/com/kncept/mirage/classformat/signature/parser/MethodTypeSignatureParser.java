package com.kncept.mirage.classformat.signature.parser;

import static java.util.Optional.empty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import com.kncept.mirage.MirageType;
import com.kncept.mirage.classformat.signature.parser.MethodDescriptorParser.MethodDescriptorResult;
import com.kncept.mirage.classformat.signature.parser.MethodTypeSignatureParser.MethodSignatureResult;
import com.kncept.mirage.classformat.signature.parser.PartialSignatureParser.PartialSignatureReqeust;
import com.kncept.mirage.classformat.signature.parser.PartialSignatureParser.PartialSignatureResponse;

/**
 * 
<pre>

MethodSignature:
    [TypeParameters] ( {JavaTypeSignature} ) Result {ThrowsSignature}
Result:
    JavaTypeSignature 
    VoidDescriptor
ThrowsSignature:
    ClassTypeSignature 
    TypeVariableSignature
















FieldTypeSignature:
    ClassTypeSignature	
    ArrayTypeSignature
    TypeVariableSignature
    
    
    
ClassTypeSignature:
    L PackageSpecifieropt SimpleClassTypeSignature ClassTypeSignatureSuffix* ;

PackageSpecifier:
    Identifier / PackageSpecifier*

SimpleClassTypeSignature:
    Identifier TypeArgumentsopt

ClassTypeSignatureSuffix:
    . SimpleClassTypeSignature

TypeVariableSignature:
    T Identifier ;

TypeArguments:
   &lt; TypeArgument+ &gt;

TypeArgument:
    WildcardIndicatoropt FieldTypeSignature
    *

WildcardIndicator:
    +
    -

ArrayTypeSignature:
    [ TypeSignature

TypeSignature:
    FieldTypeSignature
    BaseType
</pre>
 * @author nick
 *
 */
public class MethodTypeSignatureParser implements DescriptorParser<MethodSignatureResult> {
	private static final String startMethodParameters= "(";
	private static final String endMethodParameters = ")";
	
	private Collection<PartialSignatureParser> parameterParsers = new HashSet<>();
	private Collection<PartialSignatureParser> returnTypeParsers = new HashSet<>();
	
	public MethodTypeSignatureParser() {
		parameterParsers.addAll(PartialSignatureParser.Factory.primitiveTypeSupport());
		parameterParsers.add(PartialSignatureParser.Factory.arraySupport());
		parameterParsers.add(PartialSignatureParser.Factory.classNameSupport(true));
		
		returnTypeParsers.addAll(parameterParsers);
		returnTypeParsers.add(PartialSignatureParser.Factory.voidSupport());
	}
	
	public MethodSignatureResult parse(final String descriptor) {
		if (descriptor.startsWith(startMethodParameters)) {
			List<MirageType> params = new ArrayList<>();
			String remainingDescriptor = descriptor.substring(startMethodParameters.length());
			
			while (!remainingDescriptor.isEmpty() && !remainingDescriptor.startsWith(endMethodParameters)) {
				Optional<PartialSignatureResponse> sig = subParse(new PartialSignatureReqeust(remainingDescriptor, parameterParsers));
				if (sig.isPresent()) {
					params.add(sig.get().mirageType);
					remainingDescriptor = sig.get().remainingSignature;
				}
			}
			
			
			if (remainingDescriptor.startsWith(endMethodParameters)) {
				remainingDescriptor = remainingDescriptor.substring(endMethodParameters.length());
				
				Optional<PartialSignatureResponse> sig = subParse(new PartialSignatureReqeust(remainingDescriptor, returnTypeParsers));
				if (sig.isPresent() && sig.get().remainingSignature.isEmpty()) {
					return new MethodSignatureResult(sig.get().mirageType, params);
				}
			}
			
		}
		throw new RuntimeException("Unable to parse descriptor " + descriptor);
	}
	
	private Optional<PartialSignatureResponse> subParse(PartialSignatureReqeust request) {
		for(PartialSignatureParser parser: request.parsers) {
			Optional<PartialSignatureResponse> sig = parser.parse(request);
			if (sig.isPresent()) {
				return sig;
			}
		}
		return empty();
	}
	
	public static class MethodSignatureResult {
		public final MirageType returnType;
		public final List<MirageType> params;
		public MethodSignatureResult(MirageType returnType, List<MirageType> params) {
			this.returnType = returnType;
			this.params = params;
		}
	}
	
}
