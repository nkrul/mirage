package com.kncept.mirage.classformat.parser.struct;

import java.io.IOException;

import com.kncept.mirage.classformat.parser.DataTypesParser;
import com.kncept.mirage.classformat.parser.struct.cp_info.cp_info_tag_struct;

/**
 * 
<pre>
CONSTANT_InterfaceMethodref_info {
	u1 tag;
	u2 class_index;
	u2 name_and_type_index;
}
</pre>
 * 
 * @author nick
 *
 */
public class CONSTANT_InterfaceMethodref_info implements cp_info_tag_struct {
	int class_index;
	int name_and_type_index;
	
	@Override
	public void parse(DataTypesParser in) throws IOException {
		class_index = in.u2();
		name_and_type_index = in.u2();
	}
	
	@Override
	public int tag() {
		return 11;
	}
}
