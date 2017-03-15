package com.kncept.mirage;

import static com.kncept.junit.dataprovider.testfactory.TestFactoryCallback.instanceProvider;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import com.kncept.junit.dataprovider.ParameterSource;
import com.kncept.junit.dataprovider.ParameterisedTest;
import com.kncept.mirage.util.MirageProvider;
import com.kncept.mirage.util.annotation.AnnotationWithEnum;
import com.kncept.mirage.util.enumeration.SimpleEnum;

public class MirageAnnotationsTest {

	@TestFactory
	public Collection<DynamicTest> testFactory() {
		return instanceProvider(this);
	}
	
	@ParameterSource(name="allTypes")
	public static Object[][] allTypes() throws IOException {
		return MirageProvider.ParamsProviders.allTypesAsParameters();
	}
	
	@ParameterisedTest(source="allTypes")
	public void canFindSingleMethodAnnotations(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		MirageMethod method = getMethod("canFindSingleMethodAnnotations", mirage.getMethods());
		assertEquals(1, method.getAnnotations().size());
		MirageAnnotation annotation = method.getAnnotations().get(0);
		assertEquals(ParameterisedTest.class.getName(), annotation.annotationType().getClassName());
	}
	
	@ParameterisedTest(source="allTypes")
	@DisplayName("canFindmultipleMethodAnnotations")
	public void canFindmultipleMethodAnnotations(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		MirageMethod method = getMethod("canFindmultipleMethodAnnotations", mirage.getMethods());
		assertEquals(2, method.getAnnotations().size());
		assertEquals(ParameterisedTest.class.getName(), method.getAnnotations().get(0).annotationType().getClassName());
		assertEquals(DisplayName.class.getName(), method.getAnnotations().get(1).annotationType().getClassName());
	}
	
	@ParameterisedTest(source="allTypes")
	public void catGetSimpleAnnotationValues(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		MirageMethod method = getMethod("catGetSimpleAnnotationValues", mirage.getMethods());
		
		MirageAnnotation annotation = method.getAnnotations().get(0);
		assertTrue(annotation.annotationValues().containsKey("source"));
		assertEquals("allTypes", annotation.annotationValues().get("source"));
	}
	
	@AnnotationWithEnum(enumValue=SimpleEnum.FIRST)
	private void methodWithEnumAnnotation() {}
	@ParameterisedTest(source="allTypes")
	public void canGetEnumValues(MirageProvider provider) {
		//need our own test annotations here
		Mirage mirage = provider.mirage(getClass());
		MirageMethod method = getMethod("methodWithEnumAnnotation", mirage.getMethods());
		MirageAnnotation annotationWithEnum = method.getAnnotations().get(0);
		assertEquals(AnnotationWithEnum.class.getName(), annotationWithEnum.annotationType().getClassName());
		System.out.println(provider.getClass().getSimpleName() + " " + annotationWithEnum.annotationValues());
		MirageEnum enumeration = (MirageEnum)annotationWithEnum.annotationValues().get("enumValue");
		assertEquals(SimpleEnum.class.getName(), enumeration.getBaseType().getClassName());
		assertEquals("FIRST", enumeration.name());
		
		//TODO - this requires classpath awareness
		//defaults on enums...  
//		enumeration = (MirageEnum)annotationWithEnum.annotationValues().get("defaultValue");
//		Assertions.assertNotNull(enumeration);
//		assertEquals(SimpleEnum.class.getName(), enumeration.getBaseType().getClassName());
//		assertEquals("SECOND", enumeration.name());
	}
	
	
	
	private MirageMethod getMethod(String name, List<MirageMethod> methods) {
		for(MirageMethod method: methods)
			if (method.getName().equals(name))
				return method;
		return null;
	}
}




