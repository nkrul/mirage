package com.kncept.mirage.reflection;

import java.lang.reflect.Field;

import com.kncept.mirage.MirageField;
import com.kncept.mirage.MirageType;

public class ClassReflectionMirageField implements MirageField {

	private final Field source;
	
	public ClassReflectionMirageField(Field source) {
		this.source = source;
	}
	
	@Override
	public String getName() {
		return source.getName();
	}
	
	@Override
	public MirageType getMirageType() {
		//getGenericSignature ?
		return new ClassReflectionMirageType(source.getType(), source.getGenericType());
	}
	
}
