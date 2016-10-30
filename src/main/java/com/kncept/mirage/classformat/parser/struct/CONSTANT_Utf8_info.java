package com.kncept.mirage.classformat.parser.struct;

import java.io.IOException;
import java.nio.charset.Charset;

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
	public static final Charset utf8 = Charset.forName("UTF-8");
	int length;
	byte[] bytes;
	
	@Override
	public void parse(DataTypesParser in) throws IOException {
		length = in.u2();
		bytes = in.bytes(length);
	}
	
	@Override
	public int tag() {
		return 1;
	}
	
	public String value() {
		return new String(bytes, utf8);
	}
}
