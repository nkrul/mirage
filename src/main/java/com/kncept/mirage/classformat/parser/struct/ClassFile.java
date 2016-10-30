package com.kncept.mirage.classformat.parser.struct;

import java.io.IOException;

import com.kncept.mirage.classformat.parser.DataTypesParser;
import com.kncept.mirage.classformat.parser.Parsable;

/**
 *
<pre>
ClassFile {
	u4 magic;
	u2 minor_version;
	u2 major_version;
	u2 constant_pool_count;
	cp_info constant_pool[constant_pool_count-1];
	u2 access_flags;
	u2 this_class;
	u2 super_class;
	u2 interfaces_count;
	u2 interfaces[interfaces_count];
	u2 fields_count;
	field_info fields[fields_count];
	u2 methods_count;
	method_info methods[methods_count];
	u2 attributes_count;
	attribute_info attributes[attributes_count];
}

Access Flags:
Flag Name	Value	Interpretation
ACC_PUBLIC	0x0001	Declared public; may be accessed from outside its package.
ACC_FINAL	0x0010	Declared final; no subclasses allowed.
ACC_SUPER	0x0020	Treat superclass methods specially when invoked by the invokespecial instruction.
ACC_INTERFACE	0x0200	Is an interface, not a class.
ACC_ABSTRACT	0x0400	Declared abstract; must not be instantiated.
ACC_SYNTHETIC	0x1000	Declared synthetic; not present in the source code.
ACC_ANNOTATION	0x2000	Declared as an annotation type.
ACC_ENUM	0x4000	Declared as an enum type.
</pre>
 * 
 * @author nick
 *
 */
/*
 * Useful references:
 * https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html
 * https://jcp.org/aboutJava/communityprocess/maintenance/jsr924/JVMS-SE5.0-Ch4-ClassFile.pdf
 */
public class ClassFile implements Parsable {

	public int magic;
	public int minor_version;
	public int major_version;
	public int constant_pool_count;
	// The constant_pool table is indexed from 1 to constant_pool_count - 1
	// for convinience, the java representation contains a null-pad at index zero
	public cp_info[] constant_pool;
	public int access_flags;
	public int this_class;
	public int super_class;
	
	@Override
	public void parse(DataTypesParser in) throws IOException {
		magic = in.u4();
		minor_version = in.u2();
		major_version = in.u2();
		constant_pool_count = in.u2();
		constant_pool = new cp_info[constant_pool_count - 1];
		for(int i = 1; i < constant_pool.length; i++) { //1 indexed as well!
			constant_pool[i] = new cp_info();
			constant_pool[i].parse(in);
		}
		access_flags = in.u2();
		this_class = in.u2(); //CONSTANT_Class_info offset
		super_class = in.u2();
	}
	
	public String className() {
		CONSTANT_Class_info classInfo = (CONSTANT_Class_info)constant_pool[this_class].info;
		CONSTANT_Utf8_info className = (CONSTANT_Utf8_info)constant_pool[classInfo.name_index].info;
		return className.value();
	}
	
}
