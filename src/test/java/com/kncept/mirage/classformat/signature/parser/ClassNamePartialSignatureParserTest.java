package com.kncept.mirage.classformat.signature.parser;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.kncept.mirage.MirageType;
import com.kncept.mirage.classformat.signature.parser.PartialSignatureParser.PartialSignatureReqeust;
import com.kncept.mirage.classformat.signature.parser.PartialSignatureParser.PartialSignatureResponse;

public class ClassNamePartialSignatureParserTest {
	
	@Test
	public void canParseSimpleDescriptor() {
		Optional<PartialSignatureResponse> sig = parse("Ljava/lang/Class;", false);
		assertTrue(sig.isPresent());
		assertEquals("java.lang.Class", sig.get().mirageType.getClassName());
	}

	@Test
	public void remainderIsEmptyAfterParsing() {
		Optional<PartialSignatureResponse> sig = parse("Ljava/lang/Class;", false);
		assertTrue(sig.isPresent());
		assertEquals("", sig.get().remainingSignature);
	}
	
	@Test
	public void canParseASimpleGenericsParam() {
		Optional<PartialSignatureResponse> sig = parse("Ljava/lang/Class<+Ljava/lang/Class;>;", true);
		assertTrue(sig.isPresent());
		assertEquals("", sig.get().remainingSignature);
		List<MirageType> generics = sig.get().mirageType.getGenerics();
		assertEquals(1, generics.size());
		MirageType generic = generics.get(0);
		assertEquals("java.lang.Class", generic.getClassName());
	}
	
	@Test
	public void parsingANestedGenericsParam() {
		Optional<PartialSignatureResponse> sig = parse("Ljava/util/Map<Ljava/lang/String;Ljava/util/List<Ljava/lang/String;>;>;", true);
		assertTrue(sig.isPresent());
		assertEquals("", sig.get().remainingSignature);
		List<MirageType> generics = sig.get().mirageType.getGenerics();
		assertEquals(2, generics.size());
	}
	
	private Optional<PartialSignatureResponse> parse(String descriptor, boolean includeGenericsSupport) {
		ClassNamePartialSignatureParser parser = new ClassNamePartialSignatureParser(includeGenericsSupport);
		return parser.parse(new PartialSignatureReqeust(descriptor, asList(parser)));
	}
	
}
