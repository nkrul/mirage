package com.kncept.mirage.classformat.parser.descriptor;

import java.io.IOException;

import com.kncept.mirage.classformat.parser.DataTypesParser;
import com.kncept.mirage.classformat.parser.struct.attribute_info.attribute_info_struct;

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
public class Signature_attribute implements attribute_info_struct {
	
//	public int attribute_name_index;
//	public int attribute_length;
	public int signature_index;
	
	@Override
	public void parse(DataTypesParser in) throws IOException {
		signature_index = in.u2();
	}

}
