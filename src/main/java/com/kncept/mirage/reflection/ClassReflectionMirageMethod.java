package com.kncept.mirage.reflection;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.kncept.mirage.Mirage;
import com.kncept.mirage.MirageMethod;
import com.kncept.mirage.MirageType;

public class ClassReflectionMirageMethod implements MirageMethod {

	private final Mirage parent;
	private final Method method;
	
	public ClassReflectionMirageMethod(Mirage parent, Method method) {
		this.parent = parent;
		this.method = method;
	}
	
	@Override
	public Mirage getDeclaredBy() {
		return parent;
	}
	
	@Override
	public String getName() {
		return method.getName();
	}
	
	@Override
	public MirageType getReturnType() {
		return new ClassReflectionMirageType(method.getReturnType(), method.getGenericReturnType());
	}
	
	@Override
	public int getModifiers() {
		return method.getModifiers();
	}
	
	@Override
	public List<MirageType> getParameterTypes() {
		List<MirageType> parameterTypes = new ArrayList<>();
		Class[] paramTypes = method.getParameterTypes();
		Type[] genericTypes = method.getGenericParameterTypes();
		for(int i = 0; i < paramTypes.length; i++) {
			parameterTypes.add(new ClassReflectionMirageType(paramTypes[i], genericTypes[i]));
		}
		return parameterTypes;
	}
	
}
