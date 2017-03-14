package com.kncept.mirage.classformat.parser.struct.attributes;

import java.io.IOException;

import com.kncept.mirage.classformat.parser.SimpleDataTypesStream;
import com.kncept.mirage.classformat.parser.struct.attribute_info;
import com.kncept.mirage.classformat.parser.struct.cp_info;

/**
 * 
<pre>
Code_attribute {
    u2 attribute_name_index;
    u4 attribute_length;
    u2 max_stack;
    u2 max_locals;
    u4 code_length;
    u1 code[code_length];
    u2 exception_table_length;
    {   u2 start_pc;
        u2 end_pc;
        u2 handler_pc;
        u2 catch_type;
    } exception_table[exception_table_length];
    u2 attributes_count;
    attribute_info attributes[attributes_count];
}
</pre>
 * 
 * @author nick
 *
 */
public class Code_attribute extends attribute_info {
	
	public int max_stack;
	public int max_locals;
	public int code_length;
	public byte[] code;
	public int exception_table_length;
	public exception_table[] exception_table;
	public int attributes_count;
	public attribute_info[] attributes;
	
	public Code_attribute(
			int attribute_name_index,
			int attribute_length,
			SimpleDataTypesStream in,
			cp_info[] zeroPaddedConstantPool
			) throws IOException {
		super(attribute_name_index, attribute_length, in, zeroPaddedConstantPool);
		
		max_stack = in.u2();
		max_locals = in.u2();
		code_length = in.u4();
		code = in.bytes(code_length);
		exception_table_length = in.u2();
		exception_table = new exception_table[exception_table_length];
		for(int i = 0; i < exception_table_length; i++)
			exception_table[i] = new exception_table(in);
		attributes_count = in.u2();
		attributes = new attribute_info[attributes_count];
		attribute_info.Factory factory = new attribute_info.Factory();
		for(int i = 0; i < attributes_count; i++)
			attributes[i] = factory.getStruct(in, zeroPaddedConstantPool);
	}
	
	public static class exception_table {
		public int start_pc;
		public int end_pc;
		public int handler_pc;
		public int catch_type;
		private exception_table (SimpleDataTypesStream in) throws IOException {
        	start_pc = in.u2();
            end_pc = in.u2();
            handler_pc = in.u2();
            catch_type = in.u2();
		}
	}

}
