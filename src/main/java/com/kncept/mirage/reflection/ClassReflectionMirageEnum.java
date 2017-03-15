package com.kncept.mirage.reflection;

import com.kncept.mirage.MirageEnum;
import com.kncept.mirage.MirageType;

public class ClassReflectionMirageEnum implements MirageEnum {

	private final Enum<?> enumeration;
	
	public ClassReflectionMirageEnum(Enum<?> enumeration) {
		this.enumeration = enumeration;
	}
	
	@Override
	public MirageType getBaseType() {
		return new ClassReflectionMirageType(enumeration.getClass(), null);
	}

	@Override
	public String name() {
		return enumeration.name();
	}

}
