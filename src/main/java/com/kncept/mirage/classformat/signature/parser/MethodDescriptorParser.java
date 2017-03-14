package com.kncept.mirage.classformat.signature.parser;

import static java.util.Optional.empty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import com.kncept.mirage.MirageType;
import com.kncept.mirage.classformat.signature.parser.MethodDescriptorParser.MethodDescriptorResult;
import com.kncept.mirage.classformat.signature.parser.PartialSignatureParser.PartialSignatureReqeust;
import com.kncept.mirage.classformat.signature.parser.PartialSignatureParser.PartialSignatureResponse;

/**
 * 
<pre>
https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.3.3

MethodDescriptor:
    ( {ParameterDescriptor} ) ReturnDescriptor
    
ParameterDescriptor:
    FieldType
    
ReturnDescriptor:
    FieldType 
    VoidDescriptor
    
VoidDescriptor:
    V

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
public class MethodDescriptorParser implements DescriptorParser<MethodDescriptorResult> {

	private static final String startMethodParameters= "(";
	private static final String endMethodParameters = ")";
	
	private Collection<PartialSignatureParser> parameterParsers = new HashSet<>();
	private Collection<PartialSignatureParser> returnTypeParsers = new HashSet<>();
	
	public MethodDescriptorParser() {
		parameterParsers.addAll(PartialSignatureParser.Factory.primitiveTypeSupport());
		parameterParsers.add(PartialSignatureParser.Factory.arraySupport());
		parameterParsers.add(PartialSignatureParser.Factory.classNameSupport(false));
		
		returnTypeParsers.addAll(parameterParsers);
		returnTypeParsers.add(PartialSignatureParser.Factory.voidSupport());
	}
	
	public MethodDescriptorResult parse(final String descriptor) {
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
					return new MethodDescriptorResult(sig.get().mirageType, params);
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
	
	public static class MethodDescriptorResult {
		public final MirageType returnType;
		public final List<MirageType> params;
		public MethodDescriptorResult(MirageType returnType, List<MirageType> params) {
			this.returnType = returnType;
			this.params = params;
		}
	}
	
}
