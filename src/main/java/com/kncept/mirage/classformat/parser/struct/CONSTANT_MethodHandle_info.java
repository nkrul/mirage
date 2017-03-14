package com.kncept.mirage.classformat.parser.struct;

import java.io.IOException;

import com.kncept.mirage.classformat.parser.SimpleDataTypesStream;

/**
 * 
<pre>
CONSTANT_MethodHandle_info {
    u1 tag;
    u1 reference_kind;
    u2 reference_index;
}
</pre>
 * 
 * @author nick
 *
 */
public class CONSTANT_MethodHandle_info extends cp_info {
	public int reference_kind;
	public int reference_index;
	
	public CONSTANT_MethodHandle_info(byte tag) {
		super(tag);
	}
	
	@Override
	public void parse(SimpleDataTypesStream in) throws IOException {
		reference_kind = in.u2();
		reference_index = in.u2();
	}

}
