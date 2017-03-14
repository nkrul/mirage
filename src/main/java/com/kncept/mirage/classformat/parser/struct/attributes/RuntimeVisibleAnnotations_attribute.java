package com.kncept.mirage.classformat.parser.struct.attributes;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.kncept.mirage.MirageType;
import com.kncept.mirage.classformat.parser.SimpleDataTypesStream;
import com.kncept.mirage.classformat.parser.struct.CONSTANT_Double_info;
import com.kncept.mirage.classformat.parser.struct.CONSTANT_Float_info;
import com.kncept.mirage.classformat.parser.struct.CONSTANT_Integer_info;
import com.kncept.mirage.classformat.parser.struct.CONSTANT_Long_info;
import com.kncept.mirage.classformat.parser.struct.CONSTANT_Utf8_info;
import com.kncept.mirage.classformat.parser.struct.attribute_info;
import com.kncept.mirage.classformat.parser.struct.cp_info;
import com.kncept.mirage.classformat.signature.parser.FieldDescriptorParser;

/**
 * 
<pre>
RuntimeVisibleAnnotations_attribute {
    u2         attribute_name_index;
    u4         attribute_length;
    u2         num_annotations;
    annotation annotations[num_annotations];
}

annotation {
    u2 type_index;
    u2 num_element_value_pairs;
    {   u2            element_name_index;
        element_value value;
    } element_value_pairs[num_element_value_pairs];
}

element_value {
    u1 tag;
    union {
        u2 const_value_index;

        {   u2 type_name_index;
            u2 const_name_index;
        } enum_const_value;

        u2 class_info_index;

        annotation annotation_value;

        {   u2            num_values;
            element_value values[num_values];
        } array_value;
    } value;
}

 Interpretation of tag values as types

tag Item	Type	value Item	Constant Type
B	byte	const_value_index	CONSTANT_Integer
C	char	const_value_index	CONSTANT_Integer
D	double	const_value_index	CONSTANT_Double
F	float	const_value_index	CONSTANT_Float
I	int	const_value_index	CONSTANT_Integer
J	long	const_value_index	CONSTANT_Long
S	short	const_value_index	CONSTANT_Integer
Z	boolean	const_value_index	CONSTANT_Integer
s	String	const_value_index	CONSTANT_Utf8
e	Enum type	enum_const_value	Not applicable
c	Class	class_info_index	Not applicable
@	Annotation type	annotation_value	Not applicable
[	Array type	array_value	Not applicable

</pre>
 * 
 * @author nick
 *
 */
public class RuntimeVisibleAnnotations_attribute extends attribute_info {
	
	public final int num_annotations;
	public final annotation annotations[];
	
	public RuntimeVisibleAnnotations_attribute(
			int attribute_name_index,
			int attribute_length,
			SimpleDataTypesStream in,
			cp_info[] zeroPaddedConstantPool
			) throws IOException {
		super(attribute_name_index, attribute_length, in, zeroPaddedConstantPool);
		num_annotations = in.u2();
		annotations = new annotation[num_annotations];
		for(int i = 0; i < num_annotations; i++)
			annotations[i] = new annotation(in, zeroPaddedConstantPool);
	}
	
	
	public static class annotation {
		private final cp_info[] zeroPaddedConstantPool;
		public final int type_index;
		public final int num_element_value_pairs;
		public final element_value_pairs element_value_pairs[];
		public annotation(SimpleDataTypesStream in, cp_info[] zeroPaddedConstantPool) throws IOException {
			this.zeroPaddedConstantPool= zeroPaddedConstantPool;
			type_index = in.u2();
			num_element_value_pairs = in.u2();
			element_value_pairs = new element_value_pairs[num_element_value_pairs];
			for(int i = 0; i < num_element_value_pairs; i++)
				element_value_pairs[i] = new element_value_pairs(in, zeroPaddedConstantPool);
		}
		
		private String typeDescriptor() {
			return ((CONSTANT_Utf8_info)zeroPaddedConstantPool[type_index]).value();
		}
		
		public MirageType type() {
			return new FieldDescriptorParser().parse(typeDescriptor());
		}
		
		public Map<String, Object> values() {
			Map<String, Object> values = new LinkedHashMap<>();
			for(element_value_pairs valuePair: element_value_pairs) {
				values.put(valuePair.name(), valuePair.value());
			}
			return values;
		}
		
	}
	
	public static class element_value_pairs {
		private final cp_info[] zeroPaddedConstantPool;
		public final int element_name_index;
		public final element_value value;
		public element_value_pairs(SimpleDataTypesStream in, cp_info[] zeroPaddedConstantPool) throws IOException {
			this.zeroPaddedConstantPool = zeroPaddedConstantPool;
			element_name_index = in.u2();
			value = new element_value(in, zeroPaddedConstantPool);
		}
		
		public String name() {
			return ((CONSTANT_Utf8_info)zeroPaddedConstantPool[element_name_index]).value();
		}
		
		public Object value() {
			return value.value();
		}
	}
	
	public static class element_value {
		private final cp_info[] zeroPaddedConstantPool;
		byte tag;
		//and ONE of the following types
		
		int const_value_index;
		
		//enum_const_value:
		int type_name_index;
		int const_name_index;
		
		int class_info_index;
		
		annotation annotation_value;
		
		//array_value:
		int num_values;
		element_value values[];
		
		public element_value(SimpleDataTypesStream in, cp_info[] zeroPaddedConstantPool) throws IOException {
			this.zeroPaddedConstantPool = zeroPaddedConstantPool;
			
			tag = in.u1();
			
			switch(tag) {
			case 'B':
			case 'C':
			case 'D':
			case 'F':
			case 'I':
			case 'J':
			case 'S':
			case 'Z':
			case 's':
				const_value_index = in.u2();
				break;
			case 'e':
				type_name_index = in.u2();
				const_name_index = in.u2();
				break;
			case 'c':
				class_info_index = in.u2();
				break;
			case '@':
				annotation_value = new annotation(in, zeroPaddedConstantPool);
				break;
			case '[':
				num_values = in.u2();
				values = new element_value[num_values];
				for(int i = 0; i < num_values; i++)
					values[i] = new element_value(in, zeroPaddedConstantPool);
				break;
			default:
				throw new RuntimeException("Unable to parse: unknown element value tag: " + tag);
			}
		}
		
		public Object value() {
			switch(tag) {
			case 'B':
				return (byte)((CONSTANT_Integer_info)zeroPaddedConstantPool[const_value_index]).bytes;
			case 'C':
				return (char)((CONSTANT_Integer_info)zeroPaddedConstantPool[const_value_index]).bytes;
			case 'D':
				return ((CONSTANT_Double_info)zeroPaddedConstantPool[const_value_index]).toDouble();
			case 'F':
				return ((CONSTANT_Float_info)zeroPaddedConstantPool[const_value_index]).toFloat();
			case 'I':
				return ((CONSTANT_Integer_info)zeroPaddedConstantPool[const_value_index]).bytes; //native int value
			case 'J':
				return ((CONSTANT_Long_info)zeroPaddedConstantPool[const_value_index]).toLong();
			case 'S':
				return (short)((CONSTANT_Integer_info)zeroPaddedConstantPool[const_value_index]).bytes;
			case 'Z':
				return ((CONSTANT_Integer_info)zeroPaddedConstantPool[const_value_index]).bytes != 0;
			case 's':
				return ((CONSTANT_Utf8_info)zeroPaddedConstantPool[const_value_index]).value();
			case 'e':
				//enum_const_value
				break;
			case 'c':
//				//class_info_index
			case '@':
//				annotation_value
			case '[':
//				array_value
			}
			throw new RuntimeException("Unable to parse: unknown element value tag: " + tag + " " + ((char)tag));
		}
		
	}

}
