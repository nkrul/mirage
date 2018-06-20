package com.kncept.src.roaster;

import java.util.LinkedHashMap;
import java.util.Map;

import org.jboss.forge.roaster.model.Annotation;
import org.jboss.forge.roaster.model.ValuePair;

import com.kncept.mirage.MirageAnnotation;
import com.kncept.mirage.MirageType;

public class RoasterMirageAnnotation implements MirageAnnotation {

	private final Annotation<?> type;
	
	public RoasterMirageAnnotation(Annotation<?> type) {
		this.type = type;
	}
	
	@Override
	public MirageType getBaseType() {
		return new RoasterMirageAnnotationType(type);
	}

	@Override
	public Map<String, Object> getAnnotationValues() {
		Map<String, Object> map = new LinkedHashMap<>();
		for(ValuePair pair: type.getValues()) {
			map.put(pair.getName(), pair.getStringValue());
		}
		return map;
	}

	@Override
	public boolean isDefaultsIncluded() {
		return false;
	}

}
