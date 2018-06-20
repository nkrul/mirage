package com.kncept.src.roaster;

import java.util.ArrayList;
import java.util.List;

import org.jboss.forge.roaster.model.Annotation;
import org.jboss.forge.roaster.model.Field;

import com.kncept.mirage.Mirage;
import com.kncept.mirage.MirageAnnotation;
import com.kncept.mirage.MirageField;
import com.kncept.mirage.MirageType;

public class RoasterField implements MirageField {
	
	private final RoasterMirage parent;
	private final Field<?> field;
	
	public RoasterField(RoasterMirage parent, Field<?> field) {
		this.parent = parent;
		this.field = field;
	}

	@Override
	public Mirage getDeclaredBy() {
		return parent;
	}

	@Override
	public String getName() {
		return field.getName();
	}

	@Override
	public MirageType getMirageType() {
		return new RoasterMirageType(field.getType());
	}

	@Override
	public List<MirageAnnotation> getAnnotations() {
		List<MirageAnnotation> mirageAnnotations = new ArrayList<>();
		List<? extends Annotation<?>> roasterAnnotations = field.getAnnotations();
		for(Annotation a: roasterAnnotations)
			mirageAnnotations.add(new RoasterMirageAnnotation(a));
		return mirageAnnotations;
	}

}
