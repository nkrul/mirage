package com.kncept.mirage.classformat.parser.struct;

import java.io.IOException;

import com.kncept.mirage.classformat.parser.DataTypesParser;
import com.kncept.mirage.classformat.parser.Parsable;

/**
 * 
<pre>
field_info {
	u2 access_flags;
	u2 name_index;
	u2 descriptor_index;
	u2 attributes_count;
	attribute_info attributes[attributes_count];
}


Flag Name Value Interpretation
ACC_PUBLIC 0x0001 Declared public; may be accessed from outside its package.
ACC_PRIVATE 0x0002 Declared private; usable only within the defining class.
ACC_PROTECTED 0x0004 Declared protected; may be accessed within subclasses.
ACC_STATIC 0x0008 Declared static.
ACC_FINAL 0x0010 Declared final; no further assignment after initialization.
ACC_VOLATILE 0x0040 Declared volatile; cannot be cached.
ACC_TRANSIENT 0x0080 Declared transient; not written or read by a persistent object manager.
ACC_SYNTHETIC 0x1000 Declared synthetic; Not present in the source code.
ACC_ENUM 0x4000 Declared as an element of an enum.

</pre>
 * 
 * @author nick
 *
 */
public class field_info implements Parsable {
	public int access_flags;
	public int name_index;
	public int descriptor_index;
	public int attributes_count;
	public attribute_info[] attributes;

	@Override
	public void parse(DataTypesParser in) throws IOException {
		access_flags = in.u2();
		name_index = in.u2();
		descriptor_index = in.u2();
		attributes_count = in.u2();
		attributes = new attribute_info[attributes_count];
		for(int i = 0; i < attributes_count; i++) {
			attributes[i] = new attribute_info();
			attributes[i].parse(in);
		}
	}

}
