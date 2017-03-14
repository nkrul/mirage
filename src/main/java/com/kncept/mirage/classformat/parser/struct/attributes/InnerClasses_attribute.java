package com.kncept.mirage.classformat.parser.struct.attributes;

import java.io.IOException;

import com.kncept.mirage.classformat.parser.SimpleDataTypesStream;
import com.kncept.mirage.classformat.parser.struct.attribute_info;
import com.kncept.mirage.classformat.parser.struct.cp_info;

/**
 * 
<pre>
InnerClasses_attribute {
    u2 attribute_name_index;
    u4 attribute_length;
    u2 number_of_classes;
    {   u2 inner_class_info_index;
        u2 outer_class_info_index;
        u2 inner_name_index;
        u2 inner_class_access_flags;
    } classes[number_of_classes];
}

Inner class access flags

Flag Name	Value	Interpretation
ACC_PUBLIC	0x0001	Marked or implicitly public in source.
ACC_PRIVATE	0x0002	Marked private in source.
ACC_PROTECTED	0x0004	Marked protected in source.
ACC_STATIC	0x0008	Marked or implicitly static in source.
ACC_FINAL	0x0010	Marked final in source.
ACC_INTERFACE	0x0200	Was an interface in source.
ACC_ABSTRACT	0x0400	Marked or implicitly abstract in source.
ACC_SYNTHETIC	0x1000	Declared synthetic; not present in the source code.
ACC_ANNOTATION	0x2000	Declared as an annotation type.
ACC_ENUM	0x4000	Declared as an enum type.
</pre>
 * 
 * @author nick
 *
 */
public class InnerClasses_attribute extends attribute_info {
	
	public int number_of_classes;
	public classes exception_index_table[];
	
	public InnerClasses_attribute(
			int attribute_name_index,
			int attribute_length,
			SimpleDataTypesStream in,
			cp_info[] zeroPaddedConstantPool
			) throws IOException {
		super(attribute_name_index, attribute_length, in, zeroPaddedConstantPool);
		number_of_classes = in.u2();
		exception_index_table = new classes[number_of_classes];
		for(int i = 0; i < number_of_classes; i++)
			exception_index_table[i] = new classes(in);
	}
	
	public static class classes {
		public final int inner_class_info_index;
		public final int outer_class_info_index;
		public final int inner_name_index;
		public final int inner_class_access_flags;
		public classes(SimpleDataTypesStream in) throws IOException {
			inner_class_info_index = in.u2();
			outer_class_info_index = in.u2();
			inner_name_index = in.u2();
			inner_class_access_flags = in.u2();
		}
	}
}
