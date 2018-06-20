package com.kncept.src.roaster;

import java.util.Collections;
import java.util.List;

import org.jboss.forge.roaster.model.Annotation;

import com.kncept.mirage.MirageType;

public class RoasterMirageAnnotationType implements MirageType {
	private final Annotation<?> type;
	
	public RoasterMirageAnnotationType(Annotation<?> type) {
		this.type = type;
	}

	@Override
	public String getClassName() {
		return type.getQualifiedName();
	}

	@Override
	public int getArrayDepth() {
		return 0;
	}

	@Override
	public List<MirageType> getGenerics() {
		return Collections.emptyList();
	}

}
