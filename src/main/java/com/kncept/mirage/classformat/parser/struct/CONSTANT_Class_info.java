package com.kncept.mirage.classformat.parser.struct;

import java.io.IOException;

import com.kncept.mirage.classformat.parser.SimpleDataTypesStream;

/**
 * 
<pre>
CONSTANT_Class_info {
	u1 tag;
	u2 name_index;
}
</pre>
 * 
 * @author nick
 *
 */
public class CONSTANT_Class_info extends cp_info {
	public int name_index;
	
	public CONSTANT_Class_info(byte tag) {
		super(tag);
	}
	
	@Override
	public void parse(SimpleDataTypesStream in) throws IOException {
		name_index = in.u2();
	}

}
