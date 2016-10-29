package com.kncept.mirage.reflection;

import com.kncept.mirage.Mirage;

public class ClassMirage implements Mirage {
	
	final Class<?> source;

	public ClassMirage(Class<?> source) {
		this.source = source;
	}
	
	@Override
	public Object source() {
		return source;
	}
	
	@Override
	public String getName() {
		return source.getName();
	}

}
