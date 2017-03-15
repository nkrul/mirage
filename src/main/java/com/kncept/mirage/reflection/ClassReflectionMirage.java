package com.kncept.mirage.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.kncept.mirage.Mirage;
import com.kncept.mirage.MirageAnnotation;
import com.kncept.mirage.MirageField;
import com.kncept.mirage.MirageMethod;

public class ClassReflectionMirage implements Mirage {
	
	final Class<?> source;

	public ClassReflectionMirage(Class<?> source) {
		this.source = source;
	}
	
	@Override
	public String getName() {
		return source.getName();
	}
	
	@Override
	public String getSuperclassName() {
		return source.getSuperclass().getName();
	}
	
	@Override
	public List<MirageAnnotation> getAnnotations() {
		List<MirageAnnotation> annotations = new ArrayList<>();
		if (source.getAnnotations() != null)
			for (Annotation annotation: source.getAnnotations())
				annotations.add(new ClassReflectionMirageAnnotation(annotation));
		return annotations;
	}
	
	@Override
	public List<String> getImplementedInterfaces() {
		List<String> implementedInterfaces = new ArrayList<String>();
		for(Class<?> iFace: source.getInterfaces()) {
			implementedInterfaces.add(iFace.getName());
		}
		return implementedInterfaces;
	}
	
	@Override
	public List<MirageField> getFields() {
		List<MirageField> fields = new ArrayList<MirageField>();
		for(Field field: source.getDeclaredFields()) {
			fields.add(new ClassReflectionMirageField(this, field));
		}
		return fields;
	}
	
	@Override
	public List<MirageMethod> getMethods() {
		List<MirageMethod> methods = new ArrayList<>();
		for(Method method: source.getDeclaredMethods()) {
			methods.add(new ClassReflectionMirageMethod(this, method));
		}
		return methods;
	}
	
}
