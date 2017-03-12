package com.kncept.mirage.reflection;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.List;

import com.kncept.mirage.MirageType;

public class ClassReflectionMirageType implements MirageType {

	private final Class<?> type;
	private final Type genericInfo;

	public ClassReflectionMirageType(Class<?> type, Type genericInfo) {
		this.type = type;
		this.genericInfo = genericInfo;
	}

	@Override
	public String getBaseType() {
		Class<?> type = this.type;
		while(type.isArray()) {
			type = type.getComponentType();
		}
		return type.getName();
	}
	
	@Override
	public int getArrayDepth() {
		Class<?> type = this.type;
		int arrayDepth = 0;
		while(type.isArray()) {
			type = type.getComponentType();
			arrayDepth++;
		}
		return arrayDepth;
	}

	@Override
	public List<MirageType> getGenerics() {
		List<MirageType> generics = new ArrayList<MirageType>();
		if (genericInfo == null || genericInfo instanceof Class<?>) {
			// no generics
		} else if (genericInfo instanceof ParameterizedType) {
			ParameterizedType parameterized = (ParameterizedType) genericInfo;

//			 System.out.println(parameterized.getRawType().getClass().getName());
//			 System.out.println(((Class)parameterized.getRawType()).getName());
//			 System.out.println(parameterized.getActualTypeArguments().length);

			for (Type type : parameterized.getActualTypeArguments()) {
				if (type instanceof WildcardType) {
					WildcardType wildcardType = (WildcardType) type;

					int lowerBound = len(wildcardType.getLowerBounds()); //1 or 0
					int upperBound = len(wildcardType.getUpperBounds());

					// T extends ConcreteType
					if (lowerBound == 0 && upperBound == 1) {
						generics.add(new ClassReflectionMirageType((Class) wildcardType.getUpperBounds()[0], null));
						
					// T super ConcreteType
					} else if (lowerBound == 1 && upperBound == 1) {
						generics.add(new ClassReflectionMirageType((Class) wildcardType.getLowerBounds()[0], null));
					} else {
						for(int i = 0; i < wildcardType.getLowerBounds().length; i++) {
							if (wildcardType.getLowerBounds()[i] instanceof Class) {
								System.out.println(
										"((Class)wildcardType.getLowerBounds()[" + i + "]).getName() "
										+ ((Class) wildcardType.getLowerBounds()[i]).getName());	
							} else {
								System.out.println(
										"wildcardType.getLowerBounds()[" + i + "].getClass().getName() "
										+ wildcardType.getLowerBounds()[i].getClass().getName());	
							}
						}
						
						for(int i = 0; i < wildcardType.getUpperBounds().length; i++) {
							if (wildcardType.getUpperBounds()[i] instanceof Class) {
								System.out.println(
										"((Class)wildcardType.getUpperBounds()[" + i + "]).getName() "
										+ ((Class) wildcardType.getUpperBounds()[i]).getName());
							} else {
								System.out.println(
										"wildcardType.getUpperBounds()[" + i + "].getClass().getName() "
										+ wildcardType.getUpperBounds()[i].getClass().getName());
							}
						}

						System.out.println("Unable to deal with these generics yet...");
						throw new RuntimeException("Unable to deal with these generics yet...");
					}
				} else if (type instanceof Class) {
					generics.add(new ClassReflectionMirageType((Class) type, null));
				} else if (type instanceof ParameterizedType) {
					//needs to be recursive here...
					
					generics.add(new ClassReflectionMirageType((Class)((ParameterizedType) type).getRawType(), type));
					
				} else {
					System.out.println("type.getClass().getName() " + type.getClass().getName());
					throw new RuntimeException("Unable to deal with type " + type.getClass().getName());
				}
			}
		}
		return generics;
	}

	private int len(Type[] types) {
		if (types == null)
			return 0;
		return types.length;
	}
}
