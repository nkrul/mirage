package com.kncept.mirage.classformat.parser.struct;

import java.io.IOException;

import com.kncept.mirage.classformat.parser.DataTypesParser;
import com.kncept.mirage.classformat.parser.struct.cp_info.cp_info_tag_struct;

/**
 * 
<pre>
CONSTANT_NameAndType_info {
	u1 tag;
	u2 name_index;
	u2 descriptor_index;
}
</pre>
 * 
 * @author nick
 *
 */
public class CONSTANT_NameAndType_info implements cp_info_tag_struct {
	int name_index;
	int descriptor_index;
	
	@Override
	public void parse(DataTypesParser in) throws IOException {
		name_index = in.u2();
		descriptor_index = in.u2();
	}
	
	@Override
	public int tag() {
		return 12;
	}
}