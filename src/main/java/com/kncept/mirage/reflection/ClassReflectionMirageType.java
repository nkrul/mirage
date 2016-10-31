package com.kncept.mirage.reflection;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.List;

import com.kncept.mirage.MirageType;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class ClassReflectionMirageType implements MirageType {

	private final Class<?> type;
	private final Type genericInfo;
	
	public ClassReflectionMirageType(Class<?> type, Type genericInfo) {
		this.type = type;
		this.genericInfo = genericInfo;
	}
	
	
	@Override
	public String getBaseType() {
		return type.getName();
	}
	
	@Override
	public List<MirageType> getGenerics() {
		List<MirageType> generics = new ArrayList<MirageType>();
		if (genericInfo == null) System.out.println("genericInfo is null");
		else System.out.println("genericInfo is " + genericInfo.getClass().getName());
		if (genericInfo instanceof Class<?>) {
			//no generics
		} else if (genericInfo instanceof ParameterizedType) {
			ParameterizedType parameterized = (ParameterizedType)genericInfo;
			
//			System.out.println("parameterized.getRawType().getClass().getName() " + parameterized.getRawType().getClass().getName());
//			System.out.println("((Class)parameterized.getRawType()).getName() " + ((Class)parameterized.getRawType()).getName());
//			System.out.println("parameterized.getActualTypeArguments().length " + parameterized.getActualTypeArguments().length);
			
			
			for(Type type: parameterized.getActualTypeArguments()) {
				if (type instanceof WildcardType) {
					WildcardType wildcardType = (WildcardType)type;
					
					int lowerBound = len(wildcardType.getLowerBounds());
					int upperBound = len(wildcardType.getUpperBounds());
					
					if (lowerBound == 0 && upperBound == 1) {
						generics.add(new ClassReflectionMirageType((Class)wildcardType.getUpperBounds()[0], null));
						
					} else {
						System.out.println("wildcardType.getLowerBounds().length " + wildcardType.getLowerBounds().length);
						System.out.println("wildcardType.getUpperBounds().length " + wildcardType.getUpperBounds().length);
						
						System.out.println("wildcardType.getUpperBounds()[0].getClass().getName() " + wildcardType.getUpperBounds()[0].getClass().getName());
						System.out.println("((Class)wildcardType.getUpperBounds()[0]).getName() " + ((Class)wildcardType.getUpperBounds()[0]).getName());
						
						System.out.println("Unable to deal with these generics yet...");
						throw new NotImplementedException();
					}
				} else {
					System.out.println("type.getClass().getName() " + type.getClass().getName());
					throw new NotImplementedException();
				}
			}
		}
		return generics;
	}
	
	private int len(Type[] types) {
		if(types == null)
			return 0;
		return types.length;
	}
}
