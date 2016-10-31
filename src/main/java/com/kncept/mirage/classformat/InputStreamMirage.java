package com.kncept.mirage.classformat;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.kncept.mirage.Mirage;
import com.kncept.mirage.MirageField;
import com.kncept.mirage.classformat.parser.SimpleDataTypesStream;
import com.kncept.mirage.classformat.parser.descriptor.FieldTypeDescriptor;
import com.kncept.mirage.classformat.parser.struct.CONSTANT_Class_info;
import com.kncept.mirage.classformat.parser.struct.CONSTANT_Utf8_info;
import com.kncept.mirage.classformat.parser.struct.ClassFile;
import com.kncept.mirage.classformat.parser.struct.attribute_info;
import com.kncept.mirage.classformat.parser.struct.field_info;

public class InputStreamMirage implements Mirage {
	
	private final ClassFile cf;
	
	public InputStreamMirage(InputStream in) {
		try {
			cf = new ClassFile();
			cf.parse(new SimpleDataTypesStream(in));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String jvmClassnameToJavaClassname(String name) {
		return name.replaceAll("\\/", ".");
	}
	
	private String constantPoolUTF8(int offset) {
		CONSTANT_Utf8_info utf8Info = (CONSTANT_Utf8_info)cf.constant_pool[offset];
		return utf8Info.value();
	}
	
	private String constantPoolClassInfo(int offset) {
		CONSTANT_Class_info classInfo = (CONSTANT_Class_info)cf.constant_pool[offset];
		return constantPoolUTF8(classInfo.name_index);
	}
	
	
	@Override
	public String getName() {
		return jvmClassnameToJavaClassname(constantPoolClassInfo(cf.this_class));
	}
	
	@Override
	public String getSuperclassName() {
		return jvmClassnameToJavaClassname(constantPoolClassInfo(cf.super_class));
	}
	
	@Override
	public List<String> getImplementedInterfaces() {
		List<String> implementedInterfaces = new ArrayList<String>(cf.interfaces_count);
		for(int offset: cf.interfaces) {
			implementedInterfaces.add(jvmClassnameToJavaClassname(constantPoolClassInfo(offset)));
		}
		return implementedInterfaces;
	}
	
	@Override
	public List<MirageField> getFields() {
		List<MirageField> fields = new ArrayList<MirageField>(cf.fields_count);
		for(field_info field: cf.fields) {
			FieldTypeDescriptor typeDescriptor = new FieldTypeDescriptor(constantPoolUTF8(field.descriptor_index));
			
			fields.add(new InputStreamMirageField(constantPoolUTF8(field.name_index), typeDescriptor));
			
			System.out.println("--------");
			System.out.println("field name: " + constantPoolUTF8(field.name_index));
			
			for(int i = 0; i < field.attributes_count; i++) {
				attribute_info info = field.attributes[i];
				System.out.println("Attribute Name: " + constantPoolUTF8(info.attribute_name_index));
			}
			
			
//			System.out.println(name);
//			System.out.println(
//					constantPoolUTF8(field.descriptor_index)
//					);
		}
		return fields;
	}
}
