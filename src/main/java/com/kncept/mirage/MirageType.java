package com.kncept.mirage;

import static java.util.Collections.emptyList;

import java.util.List;

public interface MirageType {
	
	/**
	 * The fully qualified name of the class this mirage represents.<br>
	 * 
	 * A class instance may be obtained by calling {@code Class.forName}
	 * @return class name
	 */
	public String getClassName();
	
	/**
	 * ArrayDepth will be zero for non-arrays.
	 * @return array depth
	 */
	public int getArrayDepth();
	
	/**
	 * A list of the generic types that are embedded within this mirage type.<br>
	 * 
	 * If there are no generics, an empty list is returned
	 * @return generic types
	 */
	public List<MirageType> getGenerics();
	
	public static class SimpleMirageType implements MirageType {
		private final String className;
		private final int arrayDepth;
		private final List<MirageType> generics;
		public SimpleMirageType(String baseType) {
			this(baseType, 0);
		}
		public SimpleMirageType(String className, int arrayDepth) {
			this(className, arrayDepth, emptyList());
		}
		public SimpleMirageType(String className, int arrayDepth, List<MirageType> generics) {
			this.className = className;
			this.arrayDepth = arrayDepth;
			this.generics = generics;
		}
		@Override
		public String getClassName() {
			return className;
		}
		@Override
		public int getArrayDepth() {
			return arrayDepth;
		}
		@Override
		public List<MirageType> getGenerics() {
			return generics;
		}
	}
	
}
