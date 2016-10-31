package com.kncept.mirage.classformat;

import java.util.ArrayList;
import java.util.List;

import com.kncept.mirage.MirageType;
import com.kncept.mirage.classformat.parser.descriptor.FieldTypeDescriptor;

public class InputStreamMirageType implements MirageType {
	
	private FieldTypeDescriptor type;
	
	public InputStreamMirageType(FieldTypeDescriptor type) {
		this.type = type;
	}
	
	@Override
	public String getBaseType() {
		return type.type();
	}
	
	@Override
	public List<MirageType> getGenerics() {
		// TODO Auto-generated method stub
		return new ArrayList<MirageType>();
	}

}
