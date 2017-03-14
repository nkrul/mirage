package com.kncept.mirage.classformat.parser.struct.attributes;

import java.io.IOException;

import com.kncept.mirage.classformat.parser.SimpleDataTypesStream;
import com.kncept.mirage.classformat.parser.struct.attribute_info;
import com.kncept.mirage.classformat.parser.struct.cp_info;
import com.kncept.mirage.classformat.parser.struct.attributes.RuntimeVisibleAnnotations_attribute.element_value;

/**
 * 
<pre>
AnnotationDefault_attribute {
    u2            attribute_name_index;
    u4            attribute_length;
    element_value default_value;
}
</pre>
 * 
 * @author nick
 *
 */
public class AnnotationDefault_attribute extends attribute_info {
	
	element_value default_value;
	
	public AnnotationDefault_attribute(
			int attribute_name_index,
			int attribute_length,
			SimpleDataTypesStream in,
			cp_info[] zeroPaddedConstantPool
			) throws IOException {
		super(attribute_name_index, attribute_length, in, zeroPaddedConstantPool);
		default_value = new element_value(in, zeroPaddedConstantPool);
	}
	

}
