package com.kncept.mirage.classformat.parser.struct;

import java.io.IOException;

import com.kncept.mirage.classformat.parser.SimpleDataTypesStream;

/**
 * 
<pre>
CONSTANT_InvokeDynamic_info {
    u1 tag;
    u2 bootstrap_method_attr_index;
    u2 name_and_type_index;
}
</pre>
 * 
 * @author nick
 *
 */
public class CONSTANT_InvokeDynamic_info extends cp_info {
	public int bootstrap_method_attr_index;
	public int name_and_type_index;
	
	public CONSTANT_InvokeDynamic_info(byte tag) {
		super(tag);
	}
	
	@Override
	public void parse(SimpleDataTypesStream in) throws IOException {
		bootstrap_method_attr_index = in.u2();
		name_and_type_index = in.u2();
	}

}
