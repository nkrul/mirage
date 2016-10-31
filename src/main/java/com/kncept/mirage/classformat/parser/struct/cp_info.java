package com.kncept.mirage.classformat.parser.struct;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.kncept.mirage.classformat.parser.SimpleDataTypesStream;
import com.kncept.mirage.classformat.parser.ClassFileByteParser;

/**
 * 
<pre>
cp_info {
	u1 tag;
	u1 info[];
}


Constant pool tags:
CONSTANT_Class 7
CONSTANT_Fieldref 9
CONSTANT_Methodref 10
CONSTANT_InterfaceMethodref 11
CONSTANT_String 8
CONSTANT_Integer 3
CONSTANT_Float 4
CONSTANT_Long 5
CONSTANT_Double 6
CONSTANT_NameAndType 12
CONSTANT_Utf8 1
</pre>
 * 
 * @author nick
 *
 */
public abstract class cp_info implements ClassFileByteParser {
	public byte tag;
	
	public cp_info(byte tag) {
		this.tag = tag;
	}
	
	public static cp_info getStruct(SimpleDataTypesStream in) throws IOException {
		byte tag = in.u1();
		
		Class<? extends cp_info> tagMappingType = tagMapping().get(tag);
		
		if (tagMappingType == null)
			throw new RuntimeException("Unknown tag type: " + tag);
		
		try {
			Constructor<? extends cp_info> constructor = tagMappingType.getConstructor(byte.class);
			return constructor.newInstance(tag);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Map<Byte, Class<? extends cp_info>> tagMapping() {
		Map<Byte, Class<? extends cp_info>> tags = new HashMap<Byte, Class<? extends cp_info>>();
		tags.put((byte)1, CONSTANT_Utf8_info.class);
		
		tags.put((byte)3, CONSTANT_Integer_info.class);
		tags.put((byte)4, CONSTANT_Float_info.class);
		
		tags.put((byte)5, CONSTANT_Long_info.class);
		tags.put((byte)6, CONSTANT_Double_info.class);
		
		tags.put((byte)7, CONSTANT_Class_info.class);
		
		tags.put((byte)8, CONSTANT_String_info.class);
		
		tags.put((byte)9, CONSTANT_Fieldref_info.class);
		tags.put((byte)10, CONSTANT_Methodref_info.class);
		tags.put((byte)11, CONSTANT_InterfaceMethodref_info.class);
		
		tags.put((byte)12,  CONSTANT_NameAndType_info.class);
		return tags;
	}
	
}
