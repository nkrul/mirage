package com.kncept.mirage.classformat.parser.struct.attributes;

import java.io.IOException;

import com.kncept.mirage.classformat.parser.SimpleDataTypesStream;
import com.kncept.mirage.classformat.parser.struct.attribute_info;
import com.kncept.mirage.classformat.parser.struct.cp_info;

/**
 * 
<pre>
MethodParameters_attribute {
    u2 attribute_name_index;
    u4 attribute_length;
    u1 parameters_count;
    {   u2 name_index;
        u2 access_flags;
    } parameters[parameters_count];
}
</pre>
 * 
 * @author nick
 *
 */
public class MethodParameters_attribute extends attribute_info {
	
	public byte parameters_count;
	public parameters parameters[];
	
	public MethodParameters_attribute(
			int attribute_name_index,
			int attribute_length,
			SimpleDataTypesStream in,
			cp_info[] zeroPaddedConstantPool
			) throws IOException {
		super(attribute_name_index, attribute_length, in, zeroPaddedConstantPool);
		parameters_count = in.u1();
		parameters = new parameters[parameters_count];
		for(int i = 0; i < parameters_count; i++)
			parameters[i] = new parameters(in);
	}
	
	public static class parameters {
		public final int name_index;
		public final int access_flags;
		public parameters(SimpleDataTypesStream in) throws IOException {
			name_index = in.u2();
			access_flags = in.u2();
		}
	}

}
