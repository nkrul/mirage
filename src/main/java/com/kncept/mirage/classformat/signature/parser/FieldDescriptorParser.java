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
https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.3.2

FieldDescriptor:
    FieldType

FieldType:
    BaseType
    ObjectType
    ArrayType	

BaseType:
    B
    C
    D
    F
    I
    J
    S
    Z

ObjectType:
    L ClassName ;

ArrayType:
    [ ComponentType

ComponentType:
    FieldType
    
    
    
    

BaseType Character Type Interpretation
B byte signed byte
C char Unicode character
D double double-precision floating-point value
F float single-precision floating-point value
I int integer
J long long integer
L Classname; reference an instance of class {@code classname}
S short signed short
Z boolean true or false
[ reference one array dimension
</pre>
 *
 */
public class FieldDescriptorParser implements DescriptorParser {

	private Collection<PartialSignatureParser> parsers = new HashSet<>();
	
	public FieldDescriptorParser() {
		parsers.addAll(PartialSignatureParser.Factory.primitiveTypeSupport());
		parsers.add(PartialSignatureParser.Factory.arraySupport());
		parsers.add(PartialSignatureParser.Factory.classNameSupport(false));
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
