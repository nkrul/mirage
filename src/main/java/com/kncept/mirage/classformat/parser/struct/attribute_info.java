package com.kncept.mirage.classformat.parser.struct;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import com.kncept.mirage.classformat.parser.ClassFileByteParser;
import com.kncept.mirage.classformat.parser.SimpleDataTypesStream;
import com.kncept.mirage.classformat.parser.struct.attributes.Signature_attribute;

/**
 * 
<pre>
attribute_info {
	u2 attribute_name_index;
	u4 attribute_length;
	u1 info[attribute_length];
}
</pre>

ClassFile attributes:<pre>
ConstantValue
Code
StackMapTable
Exceptions
InnerClasses
EnclosingMethod
Synthetic
Signature
SourceFile
SourceDebugExtension
LineNumberTable
LocalVariableTable
LocalVariableTypeTable
Deprecated
RuntimeVisibleAnnotations
RuntimeInvisibleAnnotations
RuntimeVisibleParameterAnnotations
RuntimeInvisibleParameterAnnotations
AnnotationDefault
BootstrapMethods
</pre>

 * 
 * 
 * @author nick
 *
 */
public abstract class attribute_info implements ClassFileByteParser {
	public int attribute_name_index; //constant pool ref for a CONSTANT_Utf8_info type
	public int attribute_length;
	
	public attribute_info(int attribute_name_index, int attribute_length) {
		this.attribute_name_index = attribute_name_index;
		this.attribute_length = attribute_length;
	}
	
	public static attribute_info getStruct(SimpleDataTypesStream in, cp_info[] zeroPaddedConstantPool) throws IOException {
		int attribute_name_index = in.u2();
		int attribute_length = in.u4();
		
		String name = ((CONSTANT_Utf8_info)zeroPaddedConstantPool[attribute_name_index]).value();
		Class<? extends attribute_info> attributeType = attributeMapping().get(name);
		
		if (attributeType == null)
			return new UnknownAttributeInfo(attribute_name_index, attribute_length);
		
		try {
			Constructor<? extends attribute_info> constructor = attributeType.getConstructor(int.class, int.class);
			return constructor.newInstance(attribute_name_index, attribute_length);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Map<String, Class<? extends attribute_info>> attributeMapping() {
		Map<String, Class<? extends attribute_info>> attributes = new HashMap<String, Class<? extends attribute_info>>();
		
		attributes.put("Signature", Signature_attribute.class);
		
		return attributes;
	}
	
	
	public static class UnknownAttributeInfo extends attribute_info {
		
		public byte[] data;
		
		public UnknownAttributeInfo(int attribute_name_index, int attribute_length) {
			super(attribute_name_index, attribute_length);
		}
		
		@Override
		public void parse(SimpleDataTypesStream in) throws IOException {
			data = in.bytes(attribute_length);
		}
	}
	
}
