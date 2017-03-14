package com.kncept.mirage.classformat.signature.parser;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

import com.kncept.mirage.MirageType;
import com.kncept.mirage.classformat.signature.parser.PartialSignatureParser.PartialSignatureReqeust;
import com.kncept.mirage.classformat.signature.parser.PartialSignatureParser.PartialSignatureResponse;

/**
 * 
<pre>

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
public class FieldTypeSignatureParser implements DescriptorParser {
	
	private Collection<PartialSignatureParser> parsers = new HashSet<>();
	
	public FieldTypeSignatureParser() {
		parsers.addAll(PartialSignatureParser.Factory.primitiveTypeSupport());
		parsers.add(PartialSignatureParser.Factory.arraySupport());
		parsers.add(PartialSignatureParser.Factory.classNameSupport(true));
	}
	
	public MirageType parse(String descriptor) {
		PartialSignatureReqeust request = new PartialSignatureReqeust(descriptor, parsers);
		for(PartialSignatureParser parser: parsers) {
			Optional<PartialSignatureResponse> sig = parser.parse(request);
			if (sig.isPresent() && sig.get().remainingSignature.isEmpty()) {
				return sig.get().mirageType;
			}
		}
		throw new RuntimeException("Unable to parse descriptor " + descriptor);
	}
	
}
