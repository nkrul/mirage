package com.kncept.mirage.classformat.parser.struct;

import java.io.IOException;

import com.kncept.mirage.classformat.parser.DataTypesParser;
import com.kncept.mirage.classformat.parser.struct.cp_info.cp_info_tag_struct;

/**
 * 
<pre>
CONSTANT_Float_info {
	u1 tag;
	u4 bytes;
}
</pre>
 * 
 * @author nick
 *
 */
public class CONSTANT_Float_info implements cp_info_tag_struct {
	int bytes;
	
	@Override
	public void parse(DataTypesParser in) throws IOException {
		bytes = in.u4();
	}
	
	@Override
	public int tag() {
		return 4;
	}
}
