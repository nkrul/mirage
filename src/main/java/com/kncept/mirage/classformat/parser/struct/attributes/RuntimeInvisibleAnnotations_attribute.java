package com.kncept.mirage.classformat.parser.struct.attributes;

import java.io.IOException;

import com.kncept.mirage.classformat.parser.SimpleDataTypesStream;
import com.kncept.mirage.classformat.parser.struct.attribute_info;
import com.kncept.mirage.classformat.parser.struct.cp_info;
import com.kncept.mirage.classformat.parser.struct.attributes.RuntimeVisibleAnnotations_attribute.annotation;

/**
 * 
<pre>
RuntimeInvisibleAnnotations_attribute {
    u2         attribute_name_index;
    u4         attribute_length;
    u2         num_annotations;
    annotation annotations[num_annotations];
}
</pre>
 * 
 * @author nick
 *
 */
public class RuntimeInvisibleAnnotations_attribute extends attribute_info {
	
	public int num_annotations;
	public annotation annotations[];
	
	public RuntimeInvisibleAnnotations_attribute(
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
	

}
