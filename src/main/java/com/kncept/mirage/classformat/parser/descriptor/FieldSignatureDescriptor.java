package com.kncept.mirage.classformat.parser.descriptor;

import java.util.HashMap;
import java.util.Map;

import com.kncept.mirage.classformat.InputStreamMirage;

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
    < TypeArgument+ >

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
public class FieldSignatureDescriptor {
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
	
	public FieldSignatureDescriptor(String description) {
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
