package com.kncept.mirage.classformat.parser.struct;

import java.io.IOException;

import com.kncept.mirage.classformat.parser.DataTypesParser;
import com.kncept.mirage.classformat.parser.struct.cp_info.cp_info_tag_struct;

/**
 * 
<pre>
CONSTANT_Double_info {
	u1 tag;
	u4 high_bytes;
	u4 low_bytes;
}
</pre>
 * 
 * @author nick
 *
 */
public class CONSTANT_Double_info implements cp_info_tag_struct {
	int high_bytes;
	int low_bytes;
	
	@Override
	public void parse(DataTypesParser in) throws IOException {
		high_bytes = in.u4();
		low_bytes = in.u4();
	}
	
	@Override
	public int tag() {
		return 6;
	}
}
