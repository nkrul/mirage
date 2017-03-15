package com.kncept.mirage.classformat;

import java.util.ArrayList;
import java.util.List;

import com.kncept.mirage.Mirage;
import com.kncept.mirage.MirageAnnotation;
import com.kncept.mirage.MirageField;
import com.kncept.mirage.MirageType;
import com.kncept.mirage.classformat.parser.struct.CONSTANT_Utf8_info;
import com.kncept.mirage.classformat.parser.struct.ClassFile;
import com.kncept.mirage.classformat.parser.struct.attribute_info;
import com.kncept.mirage.classformat.parser.struct.field_info;
import com.kncept.mirage.classformat.parser.struct.attributes.RuntimeVisibleAnnotations_attribute;
import com.kncept.mirage.classformat.parser.struct.attributes.RuntimeVisibleAnnotations_attribute.annotation;
import com.kncept.mirage.classformat.parser.struct.attributes.Signature_attribute;
import com.kncept.mirage.classformat.signature.parser.FieldDescriptorParser;
import com.kncept.mirage.classformat.signature.parser.FieldTypeSignatureParser;

public class ClassFormatMirageField implements MirageField {
	
	private final Mirage parent;
	private final ClassFile cf;
	private final field_info field;
	
	public ClassFormatMirageField(Mirage parent, ClassFile cf, field_info field) {
		this.parent = parent;
		this.cf = cf;
		this.field = field;
	}
	
	@Override
	public Mirage getDeclaredBy() {
		return parent;
	}
	
	@Override
	public String getName() {
		return ((CONSTANT_Utf8_info)cf.constant_pool[field.name_index]).value();
	}
	
	@Override
	public MirageType getMirageType() {
		for(attribute_info attr: field.attributes) {
			if (attr instanceof Signature_attribute) {
				return new FieldTypeSignatureParser().parse(((Signature_attribute)attr).signature());
			}
		}
		String descriptor = ((CONSTANT_Utf8_info)cf.constant_pool[field.descriptor_index]).value();
		return new FieldDescriptorParser().parse(descriptor);
	}
	
	@Override
	public List<MirageAnnotation> getAnnotations() {
		List<MirageAnnotation> annotations = new ArrayList<>();
		for(attribute_info attr: field.attributes) {
			if (attr instanceof RuntimeVisibleAnnotations_attribute) {
				RuntimeVisibleAnnotations_attribute annotationAttribute = (RuntimeVisibleAnnotations_attribute)attr;
				for(annotation annotation: annotationAttribute.annotations) {
					annotations.add(new ClassFormatMirageAnnotation(annotation));
				}
			}
		}
		return annotations;
	}

}
