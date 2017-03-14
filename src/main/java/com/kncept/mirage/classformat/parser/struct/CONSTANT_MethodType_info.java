package com.kncept.mirage.classformat.parser.struct;

import java.io.IOException;

import com.kncept.mirage.classformat.parser.SimpleDataTypesStream;

/**
 * 
<pre>
CONSTANT_MethodType_info {
    u1 tag;
    u2 descriptor_index;
}
</pre>
 * 
 * @author nick
 *
 */
public class CONSTANT_MethodType_info extends cp_info {
	public int descriptor_index;
	
	public CONSTANT_MethodType_info(byte tag) {
		super(tag);
	}
	
	@Override
	public void parse(SimpleDataTypesStream in) throws IOException {
		descriptor_index = in.u2();
	}

}
