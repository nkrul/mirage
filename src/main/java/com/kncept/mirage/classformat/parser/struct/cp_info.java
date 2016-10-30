package com.kncept.mirage.classformat.parser.struct;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.kncept.mirage.classformat.parser.DataTypesParser;
import com.kncept.mirage.classformat.parser.Parsable;

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
public class cp_info implements Parsable {
	
	public byte tag;
	public cp_info_tag_struct info;
	
	@Override
	public void parse(DataTypesParser in) throws IOException {
		tag = in.u1();
		Class<? extends cp_info_tag_struct> type = tagMapping().get(tag);
		if (type == null)
			throw new RuntimeException("No cp_info mapping for " + tag);
		try {
			info = type.newInstance();
			if (tag != (byte)info.tag())
				throw new RuntimeException("mismatch:: " + type.getName());
			info.parse(in);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Map<Byte, Class<? extends cp_info_tag_struct>> tagMapping() {
		Map<Byte, Class<? extends cp_info_tag_struct>> tags = new HashMap<>();
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
	
	public static interface cp_info_tag_struct extends Parsable {
		public int tag();
	}
	
}
