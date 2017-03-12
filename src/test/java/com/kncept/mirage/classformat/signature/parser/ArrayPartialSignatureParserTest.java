package com.kncept.mirage.classformat.signature.parser;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.kncept.mirage.classformat.signature.parser.PartialSignatureParser.PartialSignatureReqeust;
import com.kncept.mirage.classformat.signature.parser.PartialSignatureParser.PartialSignatureResponse;

public class ArrayPartialSignatureParserTest {
	
	private final ArrayPartialSignatureParser parser = new ArrayPartialSignatureParser();
	private final PartialSignatureParser otherType = new PrimitivePartialSignatureParser("I", int.class);
	
	@Test
	public void doesNotFindNonArrayTypes() {
		Optional<PartialSignatureResponse> sig = parse("I");
		assertFalse(sig.isPresent());
	}
	
	@Test
	public void cannotFindArrayWithoutAType() {
		Optional<PartialSignatureResponse> sig = parse("[");
		assertFalse(sig.isPresent());
	}
	
	@Test
	public void oneDimensionalArray() {
		Optional<PartialSignatureResponse> sig = parse("[I");
		assertTrue(sig.isPresent());
		Assertions.assertEquals("int",  sig.get().mirageType.getBaseType());
		Assertions.assertEquals(1,  sig.get().mirageType.getArrayDepth());
	}
	
	@Test
	public void multiDimensionalArray() {
		Optional<PartialSignatureResponse> sig = parse("[[[I");
		assertTrue(sig.isPresent());
		Assertions.assertEquals("int",  sig.get().mirageType.getBaseType());
		Assertions.assertEquals(3,  sig.get().mirageType.getArrayDepth());
	}

	private Optional<PartialSignatureResponse> parse(String descriptor) {
		return parser.parse(new PartialSignatureReqeust(descriptor, Arrays.asList(parser, otherType)));
	}
	
}
