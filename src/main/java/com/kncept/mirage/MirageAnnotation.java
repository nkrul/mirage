package com.kncept.mirage;

import java.util.Map;

public interface MirageAnnotation {

	public MirageType getBaseType();
	
	public Map<String, Object> getAnnotationValues();
	
	/**
	 * Default values for annotations are stored in the annotation class<br>
	 * Only supplied values are visible in the embedded annotation<br>
	 * In order to resolve them, classloader logic is required.<br>
	 * @return
	 */
	public boolean isDefaultsIncluded();
	
	
}
