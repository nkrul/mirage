package com.kncept.mirage;

import java.util.Map;

public interface MirageAnnotation {

	MirageType annotationType();
	
	public Map<String, Object> annotationValues();
	
	
}
