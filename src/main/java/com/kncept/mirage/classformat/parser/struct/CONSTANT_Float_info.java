package com.kncept.mirage.classformat.parser.struct;

import java.io.IOException;

import com.kncept.mirage.classformat.parser.SimpleDataTypesStream;

/**
 * 
<pre>
CONSTANT_Float_info {
	u1 tag;
	u4 bytes;
}
</pre>
 * 
 * @author nick
 *
 */
public class CONSTANT_Float_info extends cp_info {
	public int bytes;
	
	public CONSTANT_Float_info(byte tag) {
		super(tag);
	}
	
	@Override
	public void parse(SimpleDataTypesStream in) throws IOException {
		bytes = in.u4();
	}
	
	public float toFloat() {
		return Float.intBitsToFloat(bytes);
	}
}
