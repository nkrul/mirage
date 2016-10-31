package com.kncept.mirage.classformat;

import com.kncept.mirage.MirageField;
import com.kncept.mirage.MirageType;
import com.kncept.mirage.classformat.parser.descriptor.FieldTypeDescriptor;
import com.kncept.mirage.classformat.parser.struct.CONSTANT_Utf8_info;
import com.kncept.mirage.classformat.parser.struct.ClassFile;
import com.kncept.mirage.classformat.parser.struct.attribute_info;
import com.kncept.mirage.classformat.parser.struct.field_info;
import com.kncept.mirage.classformat.parser.struct.attributes.Signature_attribute;

public class InputStreamMirageField implements MirageField {
	
	private final ClassFile cf;
	private final field_info field;
	
	public InputStreamMirageField(ClassFile cf, field_info field) {
		this.cf = cf;
		this.field = field;
	}
	
	@Override
	public String getName() {
		return ((CONSTANT_Utf8_info)cf.constant_pool[field.name_index]).value();
	}
	
	@Override
	public MirageType getType() {
		
		System.out.println("\n\n" + getName());
		for(attribute_info attr: field.attributes) {
			System.out.println(attr.getClass().getName());
			
			if (attr instanceof Signature_attribute) {
				Signature_attribute sAttr = (Signature_attribute)attr;
				
				System.out.println(((CONSTANT_Utf8_info)cf.constant_pool[sAttr.signature_index]).value());
				
			}
		}
		
		
		return new InputStreamMirageType(new FieldTypeDescriptor(((CONSTANT_Utf8_info)cf.constant_pool[field.descriptor_index]).value()));
	}

}
