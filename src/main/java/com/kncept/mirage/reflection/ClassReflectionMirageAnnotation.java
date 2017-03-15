package com.kncept.mirage.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import com.kncept.mirage.MirageAnnotation;
import com.kncept.mirage.MirageType;

public class ClassReflectionMirageAnnotation implements MirageAnnotation {

	private final Annotation src;
	
	public ClassReflectionMirageAnnotation(Annotation src) {
		this.src = src;
	}
	
	@Override
	public MirageType annotationType() {
		return new ClassReflectionMirageType(src.annotationType(), null);
	}
	
	@Override
	public Map<String, Object> annotationValues() {
		Map<String, Object> values = new LinkedHashMap<>();
		try {
			for(Method m: src.annotationType().getDeclaredMethods())
				values.put(m.getName(), ensureTypeIsCorrect(m.invoke(src)));
		} catch (Exception e) {
			throw new RuntimeException();
		}
		
		return values;
	}

	//todo - have an enum type wrapper...
	private Object ensureTypeIsCorrect(Object type) {
		if (type instanceof Enum) {
			return new ClassReflectionMirageEnum((Enum<?>)type);
		}
		
		return type;
	}
	
}
