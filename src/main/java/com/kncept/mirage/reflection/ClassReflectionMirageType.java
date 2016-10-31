package com.kncept.mirage.reflection;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.kncept.mirage.MirageType;

public class ClassReflectionMirageType implements MirageType {

	private final Type source;
	
	public ClassReflectionMirageType(Type source) {
		this.source = source;
	}
	
	
	@Override
	public String getBaseType() {
		if (source instanceof ParameterizedType) {
			ParameterizedType parameterized = (ParameterizedType)source;
			return scrubToClassOnly(parameterized.getRawType().toString());
		} else {
			return scrubToClassOnly(source.toString());
		}
	}
	
	@Override
	public List<MirageType> getGenerics() {
		List<MirageType> generics = new ArrayList<MirageType>();
		if (source instanceof ParameterizedType) {
			ParameterizedType parameterized = (ParameterizedType)source;
			for(Type type: parameterized.getActualTypeArguments()) {
				generics.add(new ClassReflectionMirageType(type));
			}
		}
		return generics;
	}
	
	private String scrubToClassOnly(String s) {
		if (s.startsWith("class "))
			return s.substring(6);
		if (s.startsWith("interface "))
			return s.substring(10);
		return s;
	}
	
}
