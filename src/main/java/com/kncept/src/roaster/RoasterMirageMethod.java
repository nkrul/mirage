package com.kncept.src.roaster;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.jboss.forge.roaster.model.Annotation;
import org.jboss.forge.roaster.model.Method;
import org.jboss.forge.roaster.model.Parameter;

import com.kncept.mirage.Mirage;
import com.kncept.mirage.MirageAnnotation;
import com.kncept.mirage.MirageMethod;
import com.kncept.mirage.MirageType;

public class RoasterMirageMethod implements MirageMethod {
	
	private final RoasterMirage parent;
	private final Method<?, ?> method;

	public RoasterMirageMethod(RoasterMirage parent, Method<?, ?> method) {
		this.parent = parent;
		this.method = method;
	}
	
	@Override
	public Mirage getDeclaredBy() {
		// TODO Auto-generated method stub
		return parent;
	}

	@Override
	public String getName() {
		return method.getName();
	}

	@Override
	public int getModifiers() {
		int modifiers = 0;
		if (method.isPublic()) modifiers |= Modifier.PUBLIC;
		if (method.isPrivate()) modifiers |= Modifier.PRIVATE;
		if (method.isProtected()) modifiers |= Modifier.PROTECTED;
		if (method.isStatic()) modifiers |= Modifier.STATIC;
		if (method.isFinal()) modifiers |= Modifier.FINAL;
		if (method.isSynchronized()) modifiers |= Modifier.SYNCHRONIZED;
		if (method.isNative()) modifiers |= Modifier.NATIVE;
		return modifiers;
	}

	@Override
	public MirageType getReturnType() {
//		if (method.isReturnTypeVoid())
//			return new ClassReflectionMirageType(void.class, null);
		return new RoasterMirageType(method.getReturnType());
	}

	@Override
	public List<MirageType> getParameterTypes() {
		List<MirageType> params = new ArrayList<>();
		for(Parameter p: method.getParameters()) 
			params.add(new RoasterMirageType(p.getType()));
		return params;
	}

	@Override
	public List<MirageAnnotation> getAnnotations() {
		List<MirageAnnotation> mirageAnnotations = new ArrayList<>();
		List<? extends Annotation<?>> roasterAnnotations = method.getAnnotations();
		for(Annotation a: roasterAnnotations)
			mirageAnnotations.add(new RoasterMirageAnnotation(a));
		return mirageAnnotations;
	}

}
