package com.kncept.mirage.util;

import static com.kncept.mirage.util.MirageProvider.getClassFile;
import static com.kncept.mirage.util.MirageProvider.getSourceFile;

import java.io.InputStream;
import java.util.stream.Stream;

import com.kncept.mirage.Mirage;
import com.kncept.mirage.classformat.ClassFormatMirage;
import com.kncept.mirage.reflection.ClassReflectionMirage;
import com.kncept.src.roaster.RoasterMirage;

/*
 * A Mirage Provider...
 * Does that make this an oasis?
 */
public interface MirageProvider {

	public Mirage mirage(Class<?> type);
	
	public static InputStream getClassFile(Class<?> type) {
		String classResourceName = type.getName().replaceAll("\\.", "/") + ".class";
		return type.getClassLoader().getResourceAsStream(classResourceName);
	}
	public static InputStream getSourceFile(Class<?> type) {
		String classResourceName = type.getName().replaceAll("\\.", "/") + ".java";
		return type.getClassLoader().getResourceAsStream(classResourceName);
	}
	
	public static Stream<MirageProvider> allTypesAsParameters() {
		return Stream.of(
			new ClassReflectionMirageProvider(),
			new ClassFormatMirageProvider()
//			new SourceFileRoasterMirageProvider()
		);
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
			return new ClassFormatMirage(getClassFile(type));
		}
		@Override
		public String toString() {
			return getClass().getSimpleName();
		}
	}
	
	public static class SourceFileRoasterMirageProvider implements MirageProvider {
		@Override
		public Mirage mirage(Class<?> type) {
			return new RoasterMirage(getSourceFile(type));
		}
		
		@Override
		public String toString() {
			return getClass().getSimpleName();
		}
	}
}
