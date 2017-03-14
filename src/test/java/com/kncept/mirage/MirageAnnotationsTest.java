package com.kncept.mirage;

import static com.kncept.junit.dataprovider.testfactory.TestFactoryCallback.instanceProvider;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import com.kncept.junit.dataprovider.ParameterSource;
import com.kncept.junit.dataprovider.ParameterisedTest;
import com.kncept.mirage.util.MirageProvider;

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
		assertEquals(ParameterisedTest.class.getName(), annotation.annotationType().getBaseType());
	}
	
	@ParameterisedTest(source="allTypes")
	@DisplayName("canFindmultipleMethodAnnotations")
	public void canFindmultipleMethodAnnotations(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		MirageMethod method = getMethod("canFindmultipleMethodAnnotations", mirage.getMethods());
		assertEquals(2, method.getAnnotations().size());
		assertEquals(ParameterisedTest.class.getName(), method.getAnnotations().get(0).annotationType().getBaseType());
		assertEquals(DisplayName.class.getName(), method.getAnnotations().get(1).annotationType().getBaseType());
	}
	
	@ParameterisedTest(source="allTypes")
	public void catGetSimpleAnnotationValues(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		MirageMethod method = getMethod("catGetSimpleAnnotationValues", mirage.getMethods());
		
		MirageAnnotation annotation = method.getAnnotations().get(0);
		assertTrue(annotation.annotationValues().containsKey("source"));
		assertEquals("allTypes", annotation.annotationValues().get("source"));
	}
	
	
	
	private MirageMethod getMethod(String name, List<MirageMethod> methods) {
		for(MirageMethod method: methods)
			if (method.getName().equals(name))
				return method;
		return null;
	}
}




