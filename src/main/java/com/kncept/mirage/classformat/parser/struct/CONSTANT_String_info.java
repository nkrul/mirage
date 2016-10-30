package com.kncept.mirage.classformat.parser.struct;

import java.io.IOException;

import com.kncept.mirage.classformat.parser.DataTypesParser;
import com.kncept.mirage.classformat.parser.struct.cp_info.cp_info_tag_struct;

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
public class CONSTANT_String_info implements cp_info_tag_struct {
	int string_index;
	
	@Override
	public void parse(DataTypesParser in) throws IOException {
		string_index = in.u2();
	}
	
	@Override
	public int tag() {
		return 8;
	}
}
