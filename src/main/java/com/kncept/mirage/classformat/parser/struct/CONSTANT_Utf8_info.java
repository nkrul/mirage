package com.kncept.mirage.classformat.parser.struct;

import java.io.IOException;
import java.nio.charset.Charset;

import com.kncept.mirage.classformat.parser.SimpleDataTypesStream;

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
public class CONSTANT_Utf8_info extends cp_info {
	public static final Charset utf8 = Charset.forName("UTF-8");
	public int length;
	public byte[] bytes;
	
	public CONSTANT_Utf8_info(byte tag) {
		super(tag);
	}
	
	@Override
	public void parse(SimpleDataTypesStream in) throws IOException {
		length = in.u2();
		bytes = in.bytes(length);
	}
	
	public String value() {
		return new String(bytes, utf8);
	}
}
