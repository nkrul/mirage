package com.kncept.mirage.classformat.parser.struct;

import java.io.IOException;

import com.kncept.mirage.classformat.parser.DataTypesParser;
import com.kncept.mirage.classformat.parser.Parsable;

/**
 * 
<pre>

attribute_info {
	u2 attribute_name_index;
	u4 attribute_length;
	u1 info[attribute_length];
}
</pre>
 * 
 * 
 * @author nick
 *
 */
public class attribute_info implements Parsable {
	public int attribute_name_index; //constant pool ref for a CONSTANT_Utf8_info type
	public int attribute_length;
	public Object info;
	
	@Override
	public void parse(DataTypesParser in) throws IOException {
		attribute_name_index = in.u2();
		attribute_length = in.u4();
		
		//uh....
		info = in.bytes(attribute_length);
	}

}
