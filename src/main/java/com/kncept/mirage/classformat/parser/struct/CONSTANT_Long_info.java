package com.kncept.mirage.classformat.parser.struct;

import java.io.IOException;

import com.kncept.mirage.classformat.parser.SimpleDataTypesStream;

/**
 * 
<pre>
CONSTANT_Long_info {
	u1 tag;
	u4 high_bytes;
	u4 low_bytes;
}
</pre>
 * 
 * @author nick
 *
 */
public class CONSTANT_Long_info extends cp_info {
	public int high_bytes;
	public int low_bytes;
	
	public CONSTANT_Long_info(byte tag) {
		super(tag);
	}
	
	@Override
	public void parse(SimpleDataTypesStream in) throws IOException {
		high_bytes = in.u4();
		low_bytes = in.u4();
	}
	
	public long toLong() {
		return (((long) high_bytes) << 32) | (low_bytes & 0xffffffffL);
	}
	
}
