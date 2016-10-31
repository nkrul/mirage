package com.kncept.mirage.classformat.parser.descriptor;

import java.util.HashMap;
import java.util.Map;

import com.kncept.mirage.classformat.InputStreamMirage;

/**
 * 
<pre>
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
L Classname; reference an instance of class <classname>
S short signed short
Z boolean true or false
[ reference one array dimension
</pre>
 * @author nick
 *
 */
public class FieldTypeDescriptor {
	int arrayDepth = 0;
	String type;
	
	public static final Map<String, String> primitiveTypeMap;
	static {
		primitiveTypeMap = new HashMap<String, String>();
		primitiveTypeMap.put("B", "byte");
		primitiveTypeMap.put("C", "char");
		primitiveTypeMap.put("D", "double");
		primitiveTypeMap.put("F", "float");
		primitiveTypeMap.put("I", "int");
		primitiveTypeMap.put("J", "long");
		primitiveTypeMap.put("S", "short");
		primitiveTypeMap.put("Z", "boolean");
	}
	
	public FieldTypeDescriptor(String description) {
		String remaining = description;
		while(remaining.startsWith("[")) {
			arrayDepth++;
			remaining = remaining.substring(1);
		}
		
		if (remaining.startsWith("L") && remaining.endsWith(";")) {
			type = remaining.substring(1,  remaining.length() - 1); //trim the trailing semicolon as well
		} else {
			type = primitiveTypeMap.get(remaining);
		}
		
		if (type == null) throw new RuntimeException("Unknown type descripton: " + description);
		
	}
	
	public String type() {
		return InputStreamMirage.jvmClassnameToJavaClassname(type);
	}
	
	public int arrayDepth() {
		return arrayDepth;
	}
	
}
