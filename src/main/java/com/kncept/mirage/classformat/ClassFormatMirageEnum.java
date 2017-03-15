package com.kncept.mirage.classformat;

import com.kncept.mirage.MirageEnum;
import com.kncept.mirage.MirageType;

public class ClassFormatMirageEnum implements MirageEnum {

	private final MirageType baseType;
	private final String name;
	
	public ClassFormatMirageEnum(MirageType baseType, String name) {
		this.baseType = baseType;
		this.name = name;
	}
	
	@Override
	public MirageType getBaseType() {
		return baseType;
	}
	
	public String name() {
		return name;
	};
	
}
