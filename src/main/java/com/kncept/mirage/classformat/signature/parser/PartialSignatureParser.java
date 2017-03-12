package com.kncept.mirage.classformat.signature.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.kncept.mirage.MirageType;

public interface PartialSignatureParser {
	
	Optional<PartialSignatureResponse> parse(PartialSignatureReqeust signature);
	
	public static class PartialSignatureReqeust {
		public final String remainingSignature;
		public final Collection<PartialSignatureParser> parsers;
		public PartialSignatureReqeust(String remainingSignature, Collection<PartialSignatureParser> parsers) {
			this.remainingSignature = remainingSignature;
			this.parsers = parsers;
		}
		
	}
	
	public static class PartialSignatureResponse {
		public final MirageType mirageType;
		public final String remainingSignature; //AFTER the input string has been parsed
		public PartialSignatureResponse(MirageType mirageType, String remainingSignature) {
			this.mirageType = mirageType;
			this.remainingSignature = remainingSignature;
			
		}
	}
	
	public static class Factory {
		public static List<PartialSignatureParser> primitiveTypeSupport() {
			List<PartialSignatureParser> parsers = new ArrayList<>();
			parsers.add(new PrimitivePartialSignatureParser("B", byte.class));
			parsers.add(new PrimitivePartialSignatureParser("C", char.class));
			parsers.add(new PrimitivePartialSignatureParser("D", double.class));
			parsers.add(new PrimitivePartialSignatureParser("F", float.class));
			parsers.add(new PrimitivePartialSignatureParser("I", int.class));
			parsers.add(new PrimitivePartialSignatureParser("J", long.class));
			parsers.add(new PrimitivePartialSignatureParser("S", short.class));
			parsers.add(new PrimitivePartialSignatureParser("Z", boolean.class));
			return parsers;
		}
		public static PartialSignatureParser arraySupport() {
			return new ArrayPartialSignatureParser();
		}
		public static PartialSignatureParser classNameSupport(boolean includeGenericsSupport) {
			return new ClassNamePartialSignatureParser(includeGenericsSupport);
		}
	}
}
