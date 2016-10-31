package com.kncept.mirage;

import java.util.List;

public interface MirageType {

	public String getBaseType();
	
	public List<MirageType> getGenerics();
	
}
