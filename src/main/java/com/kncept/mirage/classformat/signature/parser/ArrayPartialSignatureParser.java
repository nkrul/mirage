package com.kncept.mirage.classformat.signature.parser;

import static java.util.Optional.empty;
import static java.util.Optional.of;

import java.util.Optional;

import com.kncept.mirage.MirageType;

public class ArrayPartialSignatureParser implements PartialSignatureParser {
	private static final String arrayTypeCharacter = "[";
	
	@Override
	public Optional<PartialSignatureResponse> parse(PartialSignatureReqeust signature){
		if (signature.remainingSignature.startsWith(arrayTypeCharacter)) {
			String remainingSignature = signature.remainingSignature.substring(arrayTypeCharacter.length());
			if (!remainingSignature.isEmpty()) {
				Optional<PartialSignatureResponse> remainingResponse = subParse(
						new PartialSignatureReqeust(remainingSignature, signature.parsers));
				if (remainingResponse.isPresent()) {
					return of(new PartialSignatureResponse(
							new MirageType.SimpleMirageType(
									remainingResponse.get().mirageType.getClassName(),
									remainingResponse.get().mirageType.getArrayDepth() + 1,
									remainingResponse.get().mirageType.getGenerics()),
							remainingResponse.get().remainingSignature));
				}
			}
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
}
