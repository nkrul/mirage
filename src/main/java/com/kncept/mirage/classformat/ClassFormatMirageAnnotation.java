package com.kncept.mirage.classformat;

import java.util.Map;

import com.kncept.mirage.MirageAnnotation;
import com.kncept.mirage.MirageType;
import com.kncept.mirage.classformat.parser.struct.attributes.RuntimeVisibleAnnotations_attribute.annotation;

public class ClassFormatMirageAnnotation implements MirageAnnotation {

	annotation methodAnnotation;
	
	public ClassFormatMirageAnnotation(annotation methodAnnotation) {
		this.methodAnnotation = methodAnnotation;
	}
	
	@Override
	public MirageType getBaseType() {
		return methodAnnotation.type();
	}
	
	@Override
	public Map<String, Object> getAnnotationValues() {
		return methodAnnotation.values();
	}
	
	@Override
	public boolean isDefaultsIncluded() {
		return false;
	}

}
