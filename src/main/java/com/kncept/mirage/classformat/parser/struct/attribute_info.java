package com.kncept.mirage.classformat.parser.struct;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.kncept.mirage.classformat.parser.SimpleDataTypesStream;
import com.kncept.mirage.classformat.parser.struct.attributes.AnnotationDefault_attribute;
import com.kncept.mirage.classformat.parser.struct.attributes.Code_attribute;
import com.kncept.mirage.classformat.parser.struct.attributes.ConstantValue_attribute;
import com.kncept.mirage.classformat.parser.struct.attributes.Deprecated_attribute;
import com.kncept.mirage.classformat.parser.struct.attributes.EnclosingMethod_attribute;
import com.kncept.mirage.classformat.parser.struct.attributes.Exceptions_attribute;
import com.kncept.mirage.classformat.parser.struct.attributes.InnerClasses_attribute;
import com.kncept.mirage.classformat.parser.struct.attributes.MethodParameters_attribute;
import com.kncept.mirage.classformat.parser.struct.attributes.RuntimeInvisibleAnnotations_attribute;
import com.kncept.mirage.classformat.parser.struct.attributes.RuntimeInvisibleTypeAnnotations_attribute;
import com.kncept.mirage.classformat.parser.struct.attributes.RuntimeVisibleAnnotations_attribute;
import com.kncept.mirage.classformat.parser.struct.attributes.RuntimeVisibleTypeAnnotations_attribute;
import com.kncept.mirage.classformat.parser.struct.attributes.Signature_attribute;
import com.kncept.mirage.classformat.parser.struct.attributes.SourceFile_attribute;
import com.kncept.mirage.classformat.parser.struct.attributes.Synthetic_attribute;
import com.kncept.mirage.classformat.parser.struct.attributes.UnknownAttributeInfo;

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
public abstract class attribute_info {
	public final int attribute_name_index; //constant pool ref for a CONSTANT_Utf8_info type
	public final int attribute_length;
	public final cp_info[] zeroPaddedConstantPool;
	
	public attribute_info(int attribute_name_index, int attribute_length, SimpleDataTypesStream in, cp_info[] zeroPaddedConstantPool) {
		this.attribute_name_index = attribute_name_index;
		this.attribute_length = attribute_length;
		this.zeroPaddedConstantPool = zeroPaddedConstantPool;
	}
	
	public String name() {
		return ((CONSTANT_Utf8_info)zeroPaddedConstantPool[attribute_name_index]).value();
	}
	
	public static class Factory {
		public final Map<String, AttributeInfoCreator> attributeTypes = defaultMappings();
		
		public attribute_info getStruct(SimpleDataTypesStream in, cp_info[] zeroPaddedConstantPool) throws IOException {
			int attribute_name_index = in.u2();
			int attribute_length = in.u4();
			
			String attributeName = ((CONSTANT_Utf8_info)zeroPaddedConstantPool[attribute_name_index]).value();
			AttributeInfoCreator attrSrc = attributeTypes.get(attributeName);
			
			//validation has been added as a few of these were slightly out when they were created
			if (attrSrc != null) {
				long initialReadIndex = in.read();
				attribute_info value = attrSrc.create(attribute_name_index, attribute_length, in, zeroPaddedConstantPool);
				if (attribute_length != (in.read() - initialReadIndex))
					throw new RuntimeException("Parsing " + value.getClass().getSimpleName() + " error - off by " + (in.read() - initialReadIndex) + " bytes");
				return value;
			}
			//default - use 'unknown'
			return new UnknownAttributeInfo(attribute_name_index, attribute_length, in, zeroPaddedConstantPool);
		}
		
		public static Map<String, AttributeInfoCreator> defaultMappings() {
			Map<String, AttributeInfoCreator> attributes = new HashMap<>();
			attributes.put("ConstantValue", (nameIndex, len, in, cp) -> new ConstantValue_attribute(nameIndex, len, in, cp));
			attributes.put("Code", (nameIndex, len, in, cp) -> new Code_attribute(nameIndex, len, in, cp));
//			attributes.put("StackMapTable", (nameIndex, len, in, cp) -> new UnknownAttributeInfo(nameIndex, len, in, cp));
			attributes.put("Exceptions", (nameIndex, len, in, cp) -> new Exceptions_attribute(nameIndex, len, in, cp));
			attributes.put("InnerClasses", (nameIndex, len, in, cp) -> new InnerClasses_attribute(nameIndex, len, in, cp));
			attributes.put("EnclosingMethod", (nameIndex, len, in, cp) -> new EnclosingMethod_attribute(nameIndex, len, in, cp));
			attributes.put("Synthetic", (nameIndex, len, in, cp) -> new Synthetic_attribute(nameIndex, len, in, cp));
			attributes.put("Signature", (nameIndex, len, in, cp) -> new Signature_attribute(nameIndex, len, in, cp));
			attributes.put("SourceFile", (nameIndex, len, in, cp) -> new SourceFile_attribute(nameIndex, len, in, cp));
//			attributes.put("SourceDebugExtension", (nameIndex, len, in, cp) -> new UnknownAttributeInfo(nameIndex, len, in, cp));
//			attributes.put("LineNumberTable", (nameIndex, len, in, cp) -> new UnknownAttributeInfo(nameIndex, len, in, cp));
//			attributes.put("LocalVariableTable", (nameIndex, len, in, cp) -> new UnknownAttributeInfo(nameIndex, len, in, cp));
//			attributes.put("LocalVariableTypeTable", (nameIndex, len, in, cp) -> new UnknownAttributeInfo(nameIndex, len, in, cp));
			attributes.put("Deprecated", (nameIndex, len, in, cp) -> new Deprecated_attribute(nameIndex, len, in, cp));
			attributes.put("RuntimeVisibleAnnotations", (nameIndex, len, in, cp) -> new RuntimeVisibleAnnotations_attribute(nameIndex, len, in, cp));
			attributes.put("RuntimeInvisibleAnnotations", (nameIndex, len, in, cp) -> new RuntimeInvisibleAnnotations_attribute(nameIndex, len, in, cp));
//			attributes.put("RuntimeVisibleParameterAnnotations", (nameIndex, len, in, cp) -> new UnknownAttributeInfo(nameIndex, len, in, cp));
//			attributes.put("RuntimeInvisibleParameterAnnotations", (nameIndex, len, in, cp) -> new UnknownAttributeInfo(nameIndex, len, in, cp));
			attributes.put("RuntimeVisibleTypeAnnotations", (nameIndex, len, in, cp) -> new RuntimeVisibleTypeAnnotations_attribute(nameIndex, len, in, cp));
			attributes.put("RuntimeInvisibleTypeAnnotations", (nameIndex, len, in, cp) -> new RuntimeInvisibleTypeAnnotations_attribute(nameIndex, len, in, cp));
			attributes.put("AnnotationDefault", (nameIndex, len, in, cp) -> new AnnotationDefault_attribute(nameIndex, len, in, cp));
//			attributes.put("BootstrapMethods", (nameIndex, len, in, cp) -> new UnknownAttributeInfo(nameIndex, len, in, cp));
			attributes.put("MethodParameters", (nameIndex, len, in, cp) -> new MethodParameters_attribute(nameIndex, len, in, cp));
			return attributes;
		}
	}
	
	public static interface AttributeInfoCreator {
		public attribute_info create(int attribute_name_index, int attribute_length, SimpleDataTypesStream in, cp_info[] zeroPaddedConstantPool) throws IOException;
	}
}
