package com.kncept.mirage.classformat.signature.parser;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.kncept.mirage.classformat.signature.parser.PartialSignatureParser.PartialSignatureReqeust;
import com.kncept.mirage.classformat.signature.parser.PartialSignatureParser.PartialSignatureResponse;

public class PrimitivePartialSignatureParserTest {

	private final PrimitivePartialSignatureParser parser = new PrimitivePartialSignatureParser("B", byte.class);
	
	@Test
	public void absentWhenStringDoesNotStartWithPrefix() {
		Optional<PartialSignatureResponse> signature = parse("X");
		assertFalse(signature.isPresent());
	}
	
	@Test
	public void remainderStringIsCorrectlyCalculatedOnMatch() {
		Optional<PartialSignatureResponse> signature = parse("B");
		assertEquals("", signature.get().remainingSignature);
		
		signature = parse("BXX");
		assertEquals("XX", signature.get().remainingSignature);
	}
	
	@Test
	public void mirageTypeIsSetOnMatch() {
		Optional<PartialSignatureResponse> signature = parse("B");
		assertEquals("byte", signature.get().mirageType.getBaseType());
	}
	
	private Optional<PartialSignatureResponse> parse(String descriptor) {
		return parser.parse(new PartialSignatureReqeust(descriptor, asList(parser)));
	}
}
