package com.kncept.mirage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.kncept.mirage.annotationstest.ClassWithAnnotation;
import com.kncept.mirage.annotationstest.InterfaceWithAnnotationAndTypeAnnotation;
import com.kncept.mirage.util.MirageProvider;
import com.kncept.mirage.util.annotation.AnnotationWithAnnotation;
import com.kncept.mirage.util.annotation.AnnotationWithClass;
import com.kncept.mirage.util.annotation.AnnotationWithEnum;
import com.kncept.mirage.util.annotation.AnnotationWithString;
import com.kncept.mirage.util.annotation.AnnotationWithStringArray;
import com.kncept.mirage.util.annotation.MethodAnnotation;
import com.kncept.mirage.util.annotation.SimpleAnnotation;
import com.kncept.mirage.util.enumeration.SimpleEnum;

public class MirageAnnotationsTest {


	public static Stream<MirageProvider> allTypes() throws IOException {
		return MirageProvider.allTypesAsParameters();
	}
	
	@MethodAnnotation
	public void methodWithSingleAnnotation(){}
	@ParameterizedTest
	@MethodSource("allTypes")
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
	@ParameterizedTest
	@MethodSource("allTypes")
	public void canFindmultipleMethodAnnotations(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		MirageMethod method = getMethod("methodWithMultipleAnnotations", mirage.getMethods());
		assertEquals(2, method.getAnnotations().size());
		assertEquals(MethodAnnotation.class.getName(), method.getAnnotations().get(0).getBaseType().getClassName());
		assertEquals(SimpleAnnotation.class.getName(), method.getAnnotations().get(1).getBaseType().getClassName());
	}
	
	@AnnotationWithString("stringVal")
	public void methodWithAnnotationWithValue(){}
	@ParameterizedTest
	@MethodSource("allTypes")
	public void catGetSimpleAnnotationValues(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		MirageMethod method = getMethod("methodWithAnnotationWithValue", mirage.getMethods());
		MirageAnnotation annotation = method.getAnnotations().get(0);
		assertTrue(annotation.getAnnotationValues().containsKey("value"));
		assertEquals("stringVal", annotation.getAnnotationValues().get("value"));
	}
	
	@AnnotationWithEnum(enumValue=SimpleEnum.FIRST)
	private void methodWithEnumAnnotation() {}
	@ParameterizedTest
	@MethodSource("allTypes")
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
	
	@AnnotationWithClass(MirageAnnotationsTest.class)
	private void methodWithAnnotationWithClass(){}
	@ParameterizedTest
	@MethodSource("allTypes")
	public void getGetAnnotationsWithAClassValue(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		MirageMethod method = getMethod("methodWithAnnotationWithClass", mirage.getMethods());
		assertEquals(1, method.getAnnotations().size());
		assertEquals(AnnotationWithClass.class.getName(), method.getAnnotations().get(0).getBaseType().getClassName());
		MirageType value = (MirageType)method.getAnnotations().get(0).getAnnotationValues().get("value");
		assertEquals(getClass().getName(), value.getClassName());
	}
	
	//TODO bug = empty array types can't be inferred without extra classpath information
	@AnnotationWithStringArray("strVal1")
	private void methodWithStringArrayAnnotation(){}
	@ParameterizedTest
	@MethodSource("allTypes")
	public void getGetAnnotationsWithAnArrayValue(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		MirageMethod method = getMethod("methodWithStringArrayAnnotation", mirage.getMethods());
		assertEquals(1, method.getAnnotations().size());
		assertEquals(AnnotationWithStringArray.class.getName(), method.getAnnotations().get(0).getBaseType().getClassName());
		String[] value = (String[])method.getAnnotations().get(0).getAnnotationValues().get("value");
		assertEquals("strVal1", value[0]);
	}
	
	@AnnotationWithAnnotation(nested=@SimpleAnnotation)
	private void methodWithNestedAnnotations() {}
	@ParameterizedTest
	@MethodSource("allTypes")
	public void canGetNestedAnnotationValues(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		MirageMethod method = getMethod("methodWithNestedAnnotations", mirage.getMethods());
		MirageAnnotation annotationWithAnnotation = method.getAnnotations().get(0);
		assertEquals(AnnotationWithAnnotation.class.getName(), annotationWithAnnotation.getBaseType().getClassName());
		MirageAnnotation nested = (MirageAnnotation)annotationWithAnnotation.getAnnotationValues().get("nested");
		assertNotNull(nested);
		assertEquals(AnnotationWithAnnotation.class.getName(), annotationWithAnnotation.getBaseType().getClassName());
		
	}
	
	@ParameterizedTest
	@MethodSource("allTypes")
	public void canFindClassAnnotations(MirageProvider provider) {
		Mirage mirage = provider.mirage(ClassWithAnnotation.class);
		assertEquals(1, mirage.getAnnotations().size());
		assertEquals(SimpleAnnotation.class.getName(), mirage.getAnnotations().get(0).getBaseType().getClassName());
	}
	
	@ParameterizedTest
	@MethodSource("allTypes")
	public void canFindInterfaceAnnotations(MirageProvider provider) {
		Mirage mirage = provider.mirage(InterfaceWithAnnotationAndTypeAnnotation.class);
		assertEquals(2, mirage.getAnnotations().size());
		//ordering :(
		
		boolean foundSimpleAnnotation = false;
		for(MirageAnnotation annotation: mirage.getAnnotations())
			foundSimpleAnnotation |= SimpleAnnotation.class.getName().equals(annotation.getBaseType().getClassName());
		assertTrue(foundSimpleAnnotation);
	}
	
	
	@SimpleAnnotation
	public Object objectWithAnnotation;
	@ParameterizedTest
	@MethodSource("allTypes")
	public void canFindFieldAnnotations(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		MirageField field = getField("objectWithAnnotation", mirage.getFields());
		assertEquals(1, field.getAnnotations().size());
		assertEquals(SimpleAnnotation.class.getName(), field.getAnnotations().get(0).getBaseType().getClassName());
	}
	
	
	private MirageMethod getMethod(String name, List<MirageMethod> methods) {
		for(MirageMethod method: methods)
			if (method.getName().equals(name))
				return method;
		return null;
	}
	
	private MirageField getField(String name, List<MirageField> fields) {
		for(MirageField field: fields)
			if (field.getName().equals(name))
				return field;
		return null;
	}
}




