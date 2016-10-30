package com.kncept.mirage.reflection;

import java.util.ArrayList;
import java.util.List;

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
	
	@Override
	public List<String> getImplementedInterfaces() {
		List<String> implementedInterfaces = new ArrayList<>();
		for(Class iFace: source.getInterfaces()) {
			implementedInterfaces.add(iFace.getName());
		}
		return implementedInterfaces;
	}

}
