package com.kncept.mirage.classformat.parser.struct.attributes;

import java.io.IOException;

import com.kncept.mirage.classformat.parser.SimpleDataTypesStream;
import com.kncept.mirage.classformat.parser.struct.attribute_info;

/**
 * 
Signature_attribute {
	u2 attribute_name_index;
	u4 attribute_length;
	u2 signature_index;
}
 * 
 * @author nick
 *
 */
public class Signature_attribute extends attribute_info {
	
	public int signature_index;
	
	public Signature_attribute(int attribute_name_index, int attribute_length) {
		super(attribute_name_index, attribute_length);
	}
	
	@Override
	public void parse(SimpleDataTypesStream in) throws IOException {
		signature_index = in.u2();
	}

}
