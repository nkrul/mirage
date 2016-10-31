package com.kncept.mirage.classformat.parser.struct;

import java.io.IOException;

import com.kncept.mirage.classformat.parser.DataTypesParser;
import com.kncept.mirage.classformat.parser.Parsable;

/**
 * 
<pre>

attribute_info {
	u2 attribute_name_index;
	u4 attribute_length;
	u1 info[attribute_length];
}

The attributes defined by this specification as appearing in the attributes table of a field_info structure are ConstantValue, Synthetic, Signature, Deprecated, RuntimeVisibleAnnotations and RuntimeInvisibleAnnotations.

A Java Virtual Machine implementation must recognize and correctly read ConstantValue attributes found in the attributes table of a field_info structure. If a Java Virtual Machine implementation recognizes class files whose version number is 49.0 or above, it must recognize and correctly read Signature, RuntimeVisibleAnnotations and RuntimeInvisibleAnnotations attributes found in the attributes table of a field_info structure of a class file whose version number is 49.0 or above.
</pre>
 * 
 * 
 * @author nick
 *
 */
public class attribute_info implements Parsable {
	public int attribute_name_index; //constant pool ref for a CONSTANT_Utf8_info type
	public int attribute_length;
	public byte[] info;
	
	@Override
	public void parse(DataTypesParser in) throws IOException {
		attribute_name_index = in.u2();
		attribute_length = in.u4();
		
		//this is contextual on the name
		info = in.bytes(attribute_length);
	}
	
	//marker interface
	/**
	 * Marker interface.<br>
	 * Use AttributesMapper to look up the correct value
	 * @author nick
	 *
	 */
	public static interface attribute_info_struct extends Parsable {
	}

}
