package com.kncept.mirage.classformat;

import com.kncept.mirage.MirageField;
import com.kncept.mirage.MirageType;
import com.kncept.mirage.classformat.parser.descriptor.FieldTypeDescriptor;

public class InputStreamMirageField implements MirageField {
	
	private final String name;
	private final FieldTypeDescriptor type;
	
	public InputStreamMirageField(String name, FieldTypeDescriptor type) {
		this.name = name;
		this.type = type;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public MirageType getType() {
		return new InputStreamMirageType(type);
	}

}
