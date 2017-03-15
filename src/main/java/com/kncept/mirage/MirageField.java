package com.kncept.mirage;

import java.util.List;

public interface MirageField {
	
	public Mirage getDeclaredBy();
	
	public String getName();
	
	public MirageType getMirageType();
	
	public List<MirageAnnotation> getAnnotations();

}
