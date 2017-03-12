package com.kncept.mirage.util;

import java.io.InputStream;

import com.kncept.mirage.Mirage;
import com.kncept.mirage.classformat.ClassFormatMirage;
import com.kncept.mirage.reflection.ClassReflectionMirage;

public interface MirageProvider {

	public Mirage mirage(Class<?> type);
	
	public static class ParamsProviders{
		public static Object[][] allTypesAsParameters() {
			return new Object[][]{
				{new ClassReflectionMirageProvider()},
				{new ClassFormatMirageProvider()}
			};
		}
	}
	
	public static class ClassReflectionMirageProvider implements MirageProvider {
		@Override
		public Mirage mirage(Class<?> type) {
			return new ClassReflectionMirage(type);
		}
		@Override
		public String toString() {
			return getClass().getSimpleName();
		}
	}
	
	public static class ClassFormatMirageProvider implements MirageProvider {
		@Override
		public Mirage mirage(Class<?> type) {
			return new ClassFormatMirage(classInputStream(type));
		}
		
		private static InputStream classInputStream(Class<?> type) {
			String classResourceName = type.getName().replaceAll("\\.", "/") + ".class";
			return type.getClassLoader().getResourceAsStream(classResourceName);
		}
		@Override
		public String toString() {
			return getClass().getSimpleName();
		}
	}
}
