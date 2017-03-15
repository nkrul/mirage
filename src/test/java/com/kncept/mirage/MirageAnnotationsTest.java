package com.kncept.mirage;

import static com.kncept.junit.dataprovider.testfactory.TestFactoryCallback.instanceProvider;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import com.kncept.junit.dataprovider.ParameterSource;
import com.kncept.junit.dataprovider.ParameterisedTest;
import com.kncept.mirage.util.MirageProvider;
import com.kncept.mirage.util.annotation.AnnotationWithAnnotation;
import com.kncept.mirage.util.annotation.AnnotationWithEnum;
import com.kncept.mirage.util.annotation.AnnotationWithString;
import com.kncept.mirage.util.annotation.MethodAnnotation;
import com.kncept.mirage.util.annotation.SimpleAnnotation;
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
	
	@MethodAnnotation
	public void methodWithSingleAnnotation(){}
	@ParameterisedTest(source="allTypes")
	public void canFindSingleMethodAnnotations(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		MirageMethod method = getMethod("methodWithSingleAnnotation", mirage.getMethods());
		assertEquals(1, method.getAnnotations().size());
		MirageAnnotation annotation = method.getAnnotations().get(0);
		assertEquals(MethodAnnotation.class.getName(), annotation.getBaseType().getClassName());
	}
	
	@MethodAnnotation
	@SimpleAnnotation
	public void methodWithMultipleAnnotations(){}
	@ParameterisedTest(source="allTypes")
	public void canFindmultipleMethodAnnotations(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		MirageMethod method = getMethod("methodWithMultipleAnnotations", mirage.getMethods());
		assertEquals(2, method.getAnnotations().size());
		assertEquals(MethodAnnotation.class.getName(), method.getAnnotations().get(0).getBaseType().getClassName());
		assertEquals(SimpleAnnotation.class.getName(), method.getAnnotations().get(1).getBaseType().getClassName());
	}
	
	@AnnotationWithString("stringVal")
	public void methodWithAnnotationWithValue(){}
	@ParameterisedTest(source="allTypes")
	public void catGetSimpleAnnotationValues(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		MirageMethod method = getMethod("methodWithAnnotationWithValue", mirage.getMethods());
		MirageAnnotation annotation = method.getAnnotations().get(0);
		assertTrue(annotation.getAnnotationValues().containsKey("value"));
		assertEquals("stringVal", annotation.getAnnotationValues().get("value"));
	}
	
	@AnnotationWithEnum(enumValue=SimpleEnum.FIRST)
	private void methodWithEnumAnnotation() {}
	@ParameterisedTest(source="allTypes")
	public void canGetEnumValues(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		MirageMethod method = getMethod("methodWithEnumAnnotation", mirage.getMethods());
		MirageAnnotation annotationWithEnum = method.getAnnotations().get(0);
		assertEquals(AnnotationWithEnum.class.getName(), annotationWithEnum.getBaseType().getClassName());
		MirageEnum enumeration = (MirageEnum)annotationWithEnum.getAnnotationValues().get("enumValue");
		assertEquals(SimpleEnum.class.getName(), enumeration.getBaseType().getClassName());
		assertEquals("FIRST", enumeration.name());
		
		//defaults on enums require a class lookup of the source enum type  
		if (annotationWithEnum.isDefaultsIncluded()) {
			enumeration = (MirageEnum)annotationWithEnum.getAnnotationValues().get("defaultValue");
			assertNotNull(enumeration);
			assertEquals(SimpleEnum.class.getName(), enumeration.getBaseType().getClassName());
			assertEquals("SECOND", enumeration.name());
		}
	}
	
	@AnnotationWithAnnotation(nested=@SimpleAnnotation)
	private void methodWithNestedAnnotations() {}
	@ParameterisedTest(source="allTypes")
	public void canGetNestedAnnotationValues(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		MirageMethod method = getMethod("methodWithNestedAnnotations", mirage.getMethods());
		MirageAnnotation annotationWithAnnotation = method.getAnnotations().get(0);
		assertEquals(AnnotationWithAnnotation.class.getName(), annotationWithAnnotation.getBaseType().getClassName());
		MirageAnnotation nested = (MirageAnnotation)annotationWithAnnotation.getAnnotationValues().get("nested");
		assertNotNull(nested);
		assertEquals(AnnotationWithAnnotation.class.getName(), annotationWithAnnotation.getBaseType().getClassName());
		
	}
	
	@SimpleAnnotation
	public static class classWithAnnotation {}
	
	
	
	private MirageMethod getMethod(String name, List<MirageMethod> methods) {
		for(MirageMethod method: methods)
			if (method.getName().equals(name))
				return method;
		return null;
	}
}




