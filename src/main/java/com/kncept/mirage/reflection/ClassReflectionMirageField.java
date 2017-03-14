package com.kncept.mirage.reflection;

import java.lang.reflect.Field;

import com.kncept.mirage.Mirage;
import com.kncept.mirage.MirageField;
import com.kncept.mirage.MirageType;

public class ClassReflectionMirageField implements MirageField {

	private Mirage parent;
	private final Field source;
	
	public ClassReflectionMirageField(Mirage parent, Field source) {
		this.parent = parent;
		this.source = source;
	}
	
	@Override
	public Mirage getDeclaredBy() {
		return parent;
	}
	
	@Override
	public String getName() {
		return source.getName();
	}
	
	@Override
	public MirageType getMirageType() {
		return new ClassReflectionMirageType(source.getType(), source.getGenericType());
	}
	
}
