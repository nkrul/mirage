package com.kncept.mirage.reflection;

import com.kncept.mirage.Mirage;

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

}
