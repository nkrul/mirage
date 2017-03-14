package com.kncept.mirage.classformat.parser.struct.attributes;

import java.io.IOException;

import com.kncept.mirage.classformat.parser.SimpleDataTypesStream;
import com.kncept.mirage.classformat.parser.struct.attribute_info;
import com.kncept.mirage.classformat.parser.struct.cp_info;

/**
 * 
<pre>
Exceptions_attribute {
    u2 attribute_name_index;
    u4 attribute_length;
    u2 number_of_exceptions;
    u2 exception_index_table[number_of_exceptions];
}
</pre>
 * 
 * @author nick
 *
 */
public class Exceptions_attribute extends attribute_info {
	
	public int number_of_exceptions;
	public int exception_index_table[];
	
	public Exceptions_attribute(
			int attribute_name_index,
			int attribute_length,
			SimpleDataTypesStream in,
			cp_info[] zeroPaddedConstantPool
			) throws IOException {
		super(attribute_name_index, attribute_length, in, zeroPaddedConstantPool);
		number_of_exceptions = in.u2();
		exception_index_table = new int[number_of_exceptions];
		for(int i = 0; i < number_of_exceptions; i++)
			exception_index_table[i] = in.u2();
	}
	

}
