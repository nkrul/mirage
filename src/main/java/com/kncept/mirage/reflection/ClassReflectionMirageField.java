package com.kncept.mirage.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.kncept.mirage.Mirage;
import com.kncept.mirage.MirageAnnotation;
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
	
	@Override
	public List<MirageAnnotation> getAnnotations() {
		List<MirageAnnotation> annotations = new ArrayList<>();
		Annotation[] methodAnnotations = source.getAnnotations();
		if (methodAnnotations != null)
			for(Annotation a: methodAnnotations)
				annotations.add(new ClassReflectionMirageAnnotation(a));
		return annotations;
	}
}
