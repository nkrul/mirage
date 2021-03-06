package com.kncept.mirage.classformat.parser.struct.attributes;

import java.io.IOException;

import com.kncept.mirage.classformat.parser.SimpleDataTypesStream;
import com.kncept.mirage.classformat.parser.struct.CONSTANT_Utf8_info;
import com.kncept.mirage.classformat.parser.struct.attribute_info;
import com.kncept.mirage.classformat.parser.struct.cp_info;

/**
 * 
<pre>
Signature_attribute {
	u2 attribute_name_index;
	u4 attribute_length;
	u2 signature_index;
}
</pre>
 * 
 * @author nick
 *
 */
public class Signature_attribute extends attribute_info {
	
	public int signature_index;
	
	public Signature_attribute(
			int attribute_name_index,
			int attribute_length,
			SimpleDataTypesStream in,
			cp_info[] zeroPaddedConstantPool
		) throws IOException {
		super(attribute_name_index, attribute_length, in, zeroPaddedConstantPool);
		signature_index = in.u2();
	}
	
	public String signature() {
		return ((CONSTANT_Utf8_info)zeroPaddedConstantPool[signature_index]).value();
	}

}
