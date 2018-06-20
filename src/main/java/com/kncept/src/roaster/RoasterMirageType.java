package com.kncept.src.roaster;

import java.util.ArrayList;
import java.util.List;

import org.jboss.forge.roaster.model.GenericCapable;
import org.jboss.forge.roaster.model.Type;
import org.jboss.forge.roaster.model.TypeVariable;

import com.kncept.mirage.MirageType;

public class RoasterMirageType implements MirageType {
	private final String startsWithToRemove = "? extends ";
	private final Type<?> type;
	
	public RoasterMirageType(Type<?> type) {
		this.type = type;
	}

	@Override
	public String getClassName() {
		if (type.isPrimitive())
			return type.getName();
		String name = type.getQualifiedName();
		if (name.equals("?"))
			return "java.lang.Object";
		if (name.startsWith(startsWithToRemove))
			name = name.substring(startsWithToRemove.length());
		return name;
	}

	@Override
	public int getArrayDepth() {
		return type.getArrayDimensions();
	}

	@Override
	public List<MirageType> getGenerics() {
		List<MirageType> generics = new ArrayList<>();
		if (type instanceof GenericCapable) {
			List<? extends TypeVariable<?>> typeVariables = ((GenericCapable)type).getTypeVariables();
			for(TypeVariable t: typeVariables) {
				List<Type> bounds = t.getBounds();
				if (bounds.size() == 1) {
					generics.add(new RoasterMirageType(bounds.get(0)));
				} else {
					throw new RuntimeException("Can't deal with this yet: bounds size = " + bounds.size());
				}
			}
		} else {
			List<?> typeArgs = type.getTypeArguments();
			for(Type t: (List<Type>)typeArgs) {
				generics.add(new RoasterMirageType(t));
			}
		}
		return generics;
	}

}
