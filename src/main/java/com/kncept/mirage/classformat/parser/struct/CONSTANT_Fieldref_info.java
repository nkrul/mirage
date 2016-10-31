package com.kncept.mirage.classformat.parser.struct;

import java.io.IOException;

import com.kncept.mirage.classformat.parser.SimpleDataTypesStream;

/**
 * 
<pre>
CONSTANT_Fieldref_info {
	u1 tag;
	u2 class_index;
	u2 name_and_type_index;
}
</pre>
 * 
 * @author nick
 *
 */
public class CONSTANT_Fieldref_info extends cp_info {
	int class_index;
	int name_and_type_index;
	
	public CONSTANT_Fieldref_info(byte tag) {
		super(tag);
	}
	
	@Override
	public void parse(SimpleDataTypesStream in) throws IOException {
		class_index = in.u2();
		name_and_type_index = in.u2();
	}

}
