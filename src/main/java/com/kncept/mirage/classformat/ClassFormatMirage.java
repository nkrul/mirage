package com.kncept.mirage.classformat;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.kncept.mirage.Mirage;
import com.kncept.mirage.MirageAnnotation;
import com.kncept.mirage.MirageField;
import com.kncept.mirage.MirageMethod;
import com.kncept.mirage.classformat.parser.SimpleDataTypesStream;
import com.kncept.mirage.classformat.parser.struct.CONSTANT_Class_info;
import com.kncept.mirage.classformat.parser.struct.CONSTANT_Utf8_info;
import com.kncept.mirage.classformat.parser.struct.ClassFile;
import com.kncept.mirage.classformat.parser.struct.attribute_info;
import com.kncept.mirage.classformat.parser.struct.field_info;
import com.kncept.mirage.classformat.parser.struct.method_info;
import com.kncept.mirage.classformat.parser.struct.attributes.RuntimeVisibleAnnotations_attribute;
import com.kncept.mirage.classformat.parser.struct.attributes.RuntimeVisibleAnnotations_attribute.annotation;

public class ClassFormatMirage implements Mirage {
	
	private final ClassFile cf;
	
	public ClassFormatMirage(InputStream in) {
		try {
			cf = new ClassFile();
			cf.parse(new SimpleDataTypesStream(in));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public ClassFormatMirage(ClassFile cf) {
		this.cf = cf;
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
	public String getClassName() {
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
	public List<MirageAnnotation> getAnnotations() {
		List<MirageAnnotation> annotations = new ArrayList<>();
		for(attribute_info attr: cf.attributes) {
			if (attr instanceof RuntimeVisibleAnnotations_attribute) {
				RuntimeVisibleAnnotations_attribute annotationAttribute = (RuntimeVisibleAnnotations_attribute)attr;
				for(annotation annotation: annotationAttribute.annotations) {
					annotations.add(new ClassFormatMirageAnnotation(annotation));
				}
			}
		}
		return annotations;
	}
	
	
	@Override
	public List<MirageField> getFields() {
		List<MirageField> fields = new ArrayList<MirageField>(cf.fields_count);
		for(field_info field: cf.fields) {
			fields.add(new ClassFormatMirageField(this, cf, field));
		}
		return fields;
	}
	
	@Override
	public List<MirageMethod> getMethods() {
		List<MirageMethod> methods = new ArrayList<>(cf.methods_count);
		for(method_info method: cf.methods)
			methods.add(new ClassFormatMirageMethod(this, cf, method));
		return methods;
	}
}
