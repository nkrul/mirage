package com.kncept.mirage.classformat.parser.struct;

import java.io.IOException;

import com.kncept.mirage.classformat.parser.SimpleDataTypesStream;

/**
 * 
<pre>
CONSTANT_String_info {
	u1 tag;
	u2 string_index;
}
</pre>
 * 
 * @author nick
 *
 */
public class CONSTANT_String_info extends cp_info {
	public int string_index;
	
	public CONSTANT_String_info(byte tag) {
		super(tag);
	}
	
	@Override
	public void parse(SimpleDataTypesStream in) throws IOException {
		string_index = in.u2();
	}

}
