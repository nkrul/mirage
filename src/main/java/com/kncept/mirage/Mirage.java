package com.kncept.mirage;

import java.util.List;

//
// This is a roadmap, not an endorsement...
// jdk 7 and 6 legacy downloads from here:
// https://github.com/alexkasko/openjdk-unofficial-builds
//

/**
 * This interface represents the 'mirage' of a class<br>
 * If provides an API similar to the reflection API, but it does not require classes to be loaded into the JVM<br>
 * <br>
 * Use one of the following to create a new class mirage
<pre>
    Mirage loadedClassMirage = new ClassReflectionMirage(classReference);
    Mirage unloadedClassMirage = new ClassFormatMirage(inputStreamSource);
</pre>
 * @author ebola
 *
 */
public interface Mirage {

	public String getClassName();
	public String getSuperclassName();
	
//	public MirageType getMirageType(); //classname and OTHER STUFF..

	public List<MirageAnnotation> getAnnotations();
	
	//TODO: turn this into a List<MirageType> return type
	public List<String> getImplementedInterfaces();
	
	public List<MirageField> getFields();
	
	public List<MirageMethod> getMethods();
	
}
