package com.kncept.mirage.util;

import java.io.InputStream;

import com.kncept.mirage.Mirage;
import com.kncept.mirage.classformat.InputStreamMirage;
import com.kncept.mirage.reflection.ClassReflectionMirage;

public interface MirageProvider {

	public Mirage mirage(Class<?> type);
	
	public static class ParamsProviders{
		public static Object[][] allTypesAsParameters() {
			return new Object[][]{
				{new ClassReflectionMirageProvider()},
				{new InputStreamMirageProvider()}
			};
		}
	}
	
	public static class ClassReflectionMirageProvider implements MirageProvider {
		@Override
		public Mirage mirage(Class<?> type) {
			return new ClassReflectionMirage(type);
		}
	}
	
	public static class InputStreamMirageProvider implements MirageProvider {
		@Override
		public Mirage mirage(Class<?> type) {
			return new InputStreamMirage(classInputStream(type));
		}
		
		private static InputStream classInputStream(Class<?> type) {
			String classResourceName = type.getName().replaceAll("\\.", "/") + ".class";
			return type.getClassLoader().getResourceAsStream(classResourceName);
		}
	}
	
	
}
