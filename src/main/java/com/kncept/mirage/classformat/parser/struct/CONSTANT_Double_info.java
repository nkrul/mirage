package com.kncept.mirage.classformat.parser.struct;

import java.io.IOException;

import com.kncept.mirage.classformat.parser.SimpleDataTypesStream;

/**
 * 
<pre>
CONSTANT_Double_info {
	u1 tag;
	u4 high_bytes;
	u4 low_bytes;
}
</pre>
 * 
 * @author nick
 *
 */
public class CONSTANT_Double_info extends cp_info {
	int high_bytes;
	int low_bytes;
	
	public CONSTANT_Double_info(byte tag) {
		super(tag);
	}
	
	@Override
	public void parse(SimpleDataTypesStream in) throws IOException {
		high_bytes = in.u4();
		low_bytes = in.u4();
	}
}
