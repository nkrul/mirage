package com.kncept.mirage.classformat.parser.struct;

import java.io.IOException;

import com.kncept.mirage.classformat.parser.ClassFileConstantPoolByteParser;
import com.kncept.mirage.classformat.parser.SimpleDataTypesStream;

/**
 * 
<pre>
method_info {
	u2 access_flags;
	u2 name_index;
	u2 descriptor_index;
	u2 attributes_count;
	attribute_info attributes[attributes_count];
}
</pre>
 * 
 * 
 * @author nick
 *
 */
public class method_info implements ClassFileConstantPoolByteParser {

	public int access_flags;
	public int name_index;
	public int descriptor_index;
	public int attributes_count;
	public attribute_info[] attributes;
	
	@Override
	public void parse(SimpleDataTypesStream in, cp_info[] zeroPaddedConstantPool) throws IOException {
		access_flags = in.u2();
		name_index = in.u2();
		descriptor_index = in.u2();
		attributes_count = in.u2();
		attributes = new attribute_info[attributes_count];
		
		attribute_info.Factory factory = new attribute_info.Factory();
		for(int i = 0; i < attributes_count; i++) {
			attributes[i] = factory.getStruct(in, zeroPaddedConstantPool);;
		}
	}

}
