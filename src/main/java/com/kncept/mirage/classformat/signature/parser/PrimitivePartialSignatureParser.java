package com.kncept.mirage.classformat.signature.parser;

import static java.util.Optional.empty;
import static java.util.Optional.of;

import java.util.Optional;

import com.kncept.mirage.MirageType;

public class PrimitivePartialSignatureParser implements PartialSignatureParser {

	private final String typeCharacter;
	private final Class<?> type;
	
	public PrimitivePartialSignatureParser(String typeCharacter, Class<?> type) {
		this.typeCharacter = typeCharacter;
		this.type = type;
	}
	
	@Override
	public Optional<PartialSignatureResponse> parse(PartialSignatureReqeust signature){
		if (signature.remainingSignature.startsWith(typeCharacter)) {
			return of(new PartialSignatureResponse(
					new MirageType.SimpleMirageType(type.getCanonicalName()),
					signature.remainingSignature.substring(typeCharacter.length())));
		}
		return empty();
	}
}
