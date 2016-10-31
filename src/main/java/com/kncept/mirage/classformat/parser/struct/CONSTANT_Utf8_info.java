package com.kncept.mirage.classformat.parser.struct;

import java.io.IOException;

import com.kncept.mirage.classformat.parser.DataTypesParser;
import com.kncept.mirage.classformat.parser.struct.cp_info.cp_info_tag_struct;

/**
 * 
<pre>
CONSTANT_Utf8_info {
	u1 tag;
	u2 length;
	u1 bytes[length];
}
</pre>
 * 
 * @author nick
 *
 */
public class CONSTANT_Utf8_info implements cp_info_tag_struct {
	public int length;
	public byte[] bytes;
	
	@Override
	public void parse(DataTypesParser in) throws IOException {
		length = in.u2();
		bytes = in.bytes(length);
	}
	
	@Override
	public int tag() {
		return 1;
	}
}
