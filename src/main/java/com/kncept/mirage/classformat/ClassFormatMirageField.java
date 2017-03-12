package com.kncept.mirage.classformat;

import com.kncept.mirage.MirageField;
import com.kncept.mirage.MirageType;
import com.kncept.mirage.classformat.parser.struct.CONSTANT_Utf8_info;
import com.kncept.mirage.classformat.parser.struct.ClassFile;
import com.kncept.mirage.classformat.parser.struct.attribute_info;
import com.kncept.mirage.classformat.parser.struct.field_info;
import com.kncept.mirage.classformat.parser.struct.attributes.Signature_attribute;
import com.kncept.mirage.classformat.signature.parser.FieldDescriptorParser;
import com.kncept.mirage.classformat.signature.parser.FieldTypeSignatureParser;

public class ClassFormatMirageField implements MirageField {
	
	private final ClassFile cf;
	private final field_info field;
	
	public ClassFormatMirageField(ClassFile cf, field_info field) {
		this.cf = cf;
		this.field = field;
	}
	
	@Override
	public String getName() {
		return ((CONSTANT_Utf8_info)cf.constant_pool[field.name_index]).value();
	}
	
	@Override
	public MirageType getMirageType() {
		for(attribute_info attr: field.attributes) {
			if (attr instanceof Signature_attribute) {
				Signature_attribute sAttr = (Signature_attribute)attr;
				String signatureDescriptor = ((CONSTANT_Utf8_info)cf.constant_pool[sAttr.signature_index]).value();
				return new FieldTypeSignatureParser().parse(signatureDescriptor);
			}
		}
		String descriptor = ((CONSTANT_Utf8_info)cf.constant_pool[field.descriptor_index]).value();
		return new FieldDescriptorParser().parse(descriptor);
	}

}
