package com.kncept.mirage.classformat.parser.struct.attributes;

import java.io.IOException;

import com.kncept.mirage.classformat.parser.SimpleDataTypesStream;
import com.kncept.mirage.classformat.parser.struct.attribute_info;
import com.kncept.mirage.classformat.parser.struct.cp_info;

/**
 * 
<pre>
ConstantValue_attribute {
    u2 attribute_name_index;
    u4 attribute_length;
    u2 constantvalue_index;
}

Constant value attribute types:

Field Type	Entry Type
long	CONSTANT_Long
float	CONSTANT_Float
double	CONSTANT_Double
int, short, char, byte, boolean	CONSTANT_Integer
String	CONSTANT_String
</pre>
 * 
 * @author nick
 *
 */
public class ConstantValue_attribute extends attribute_info {
	
	public int constantvalue_index;
	
	public ConstantValue_attribute(
			int attribute_name_index,
			int attribute_length,
			SimpleDataTypesStream in,
			cp_info[] zeroPaddedConstantPool
			) throws IOException {
		super(attribute_name_index, attribute_length, in, zeroPaddedConstantPool);
		constantvalue_index = in.u2();
	}
	
	public cp_info constant() {
		return zeroPaddedConstantPool[constantvalue_index];
	}

}
