package com.kncept.mirage;

import java.util.List;

//
// This is a roadmap, not an endorsement...
// jdk 7 and 6 legacy downloads from here:
// https://github.com/alexkasko/openjdk-unofficial-builds
//

public interface Mirage {

	public String getName();
	
	public String getSuperclassName();
	
	public List<String> getImplementedInterfaces();
	
	public List<MirageField> getFields();
	
}
