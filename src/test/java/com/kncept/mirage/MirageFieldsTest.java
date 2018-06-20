package com.kncept.mirage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.kncept.mirage.util.MirageProvider;

/**
 * A set of 'generic usage tests'
 *
 */
public class MirageFieldsTest {
	private Class<? extends MirageFieldsTest> thisClass = getClass();
	
	public static Stream<MirageProvider> allTypes() throws IOException {
		return MirageProvider.allTypesAsParameters();
	}
	
	@ParameterizedTest
	@MethodSource("allTypes")
	public void name(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		assertEquals(thisClass.getName(), mirage.getClassName());
	}
	
	@ParameterizedTest
	@MethodSource("allTypes")
	public void superClass(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		assertEquals(thisClass.getSuperclass().getName(), mirage.getSuperclassName());
	}
	
	int[] intArray;
	int[][] intArray2;
	@ParameterizedTest
	@MethodSource("allTypes")
	public void primitiveArrayType(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		MirageField intArray = getField("intArray", mirage.getFields());
		assertEquals("int", intArray.getMirageType().getClassName());
		assertEquals(1, intArray.getMirageType().getArrayDepth());
		
		intArray = getField("intArray2", mirage.getFields());
		assertEquals("int", intArray.getMirageType().getClassName());
		assertEquals(2, intArray.getMirageType().getArrayDepth());
	}
	
	MirageFieldsTest[] arrayObject;
	MirageFieldsTest[][] arrayObject2;
	@ParameterizedTest
	@MethodSource("allTypes")
	public void objectArrayType(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		MirageField intArray = getField("arrayObject", mirage.getFields());
		assertEquals(getClass().getName(), intArray.getMirageType().getClassName());
		assertEquals(1, intArray.getMirageType().getArrayDepth());
		
		intArray = getField("arrayObject2", mirage.getFields());
		assertEquals(getClass().getName(), intArray.getMirageType().getClassName());
		assertEquals(2, intArray.getMirageType().getArrayDepth());
	}
	
	int nativeInteger;
	Integer objectInteger;
	@ParameterizedTest
	@MethodSource("allTypes")
	public void integerTypes(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		MirageField nativeType = getField("nativeInteger", mirage.getFields());
		assertEquals("int", nativeType.getMirageType().getClassName());
		MirageField objectType = getField("objectInteger", mirage.getFields());
		assertEquals("java.lang.Integer", objectType.getMirageType().getClassName());
	}
	
	boolean nativeBoolean;
	Boolean objectBoolean;
	@ParameterizedTest
	@MethodSource("allTypes")
	public void booleanTypes(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		MirageField nativeType = getField("nativeBoolean", mirage.getFields());
		assertEquals("boolean", nativeType.getMirageType().getClassName());
		MirageField objectType = getField("objectBoolean", mirage.getFields());
		assertEquals("java.lang.Boolean", objectType.getMirageType().getClassName());
	}
	
	////// this is SUCH a generic test...
	@ParameterizedTest
	@MethodSource("allTypes")
	public void simpleGenericsTest(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		MirageField field = getField("thisClass", mirage.getFields());
		MirageType type = field.getMirageType();
		assertEquals(Class.class.getName(), type.getClassName(), "The type of the 'thisClass' member should be java.lang.Class");
		assertEquals(1, type.getGenerics().size(), "the 'thisClass' member should have 1 generic type");
		MirageType genericType = type.getGenerics().get(0);
		assertEquals(getClass().getName(), genericType.getClassName());
		assertTrue(genericType.getGenerics().isEmpty()); //this class has no generics in its signature
	}
	
	Map<String, List<MirageFieldsTest>> stringToListOfMirageTest;
	@ParameterizedTest
	@MethodSource("allTypes")
	public void multipleGenericsTest(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		MirageField field = getField("stringToListOfMirageTest", mirage.getFields());
		MirageType type = field.getMirageType();
		assertEquals("java.util.Map", type.getClassName());
		assertEquals(2, type.getGenerics().size());

		assertEquals("java.lang.String", type.getGenerics().get(0).getClassName());
		assertEquals("java.util.List", type.getGenerics().get(1).getClassName());
		
		assertEquals(1, type.getGenerics().get(1).getGenerics().size());
		assertEquals(getClass().getName(), type.getGenerics().get(1).getGenerics().get(0).getClassName());
	}
	
	List<?> genericUnknownList;
	@ParameterizedTest
	@MethodSource("allTypes")
	public void genericUnknownList(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		MirageField field = getField("genericUnknownList", mirage.getFields());
		MirageType type = field.getMirageType();
		assertEquals("java.util.List", type.getClassName());
		assertEquals(1, type.getGenerics().size());
		MirageType genericArg = type.getGenerics().get(0);
		assertEquals("java.lang.Object", genericArg.getClassName());
	}
	
	List<Object> genericObjectList;
	@ParameterizedTest
	@MethodSource("allTypes")
	public void genericObjectList(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		MirageField field = getField("genericObjectList", mirage.getFields());
		MirageType type = field.getMirageType();
		assertEquals("java.util.List", type.getClassName());
		assertEquals(1, type.getGenerics().size());
		MirageType genericArg = type.getGenerics().get(0);
		assertEquals("java.lang.Object", genericArg.getClassName());
	}
	
	//this one starts with the -
	List<? super MirageFieldsTest> thisClassSuperObjectList;
	@ParameterizedTest
	@MethodSource("allTypes")
	public void thisClassSuperObjectList(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		MirageField field = getField("thisClassSuperObjectList", mirage.getFields());
		MirageType type = field.getMirageType();
		assertEquals("java.util.List", type.getClassName());
		assertEquals(1, type.getGenerics().size());
		MirageType genericArg = type.getGenerics().get(0);
		assertEquals(getClass().getName(), genericArg.getClassName());
	}
	
	//this one starts with the +
	List<? extends MirageFieldsTest> thisClassExtendsObjectList;
	@ParameterizedTest
	@MethodSource("allTypes")
	public void thisClassExtendsObjectList(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		MirageField field = getField("thisClassExtendsObjectList", mirage.getFields());
		MirageType type = field.getMirageType();
		assertEquals("java.util.List", type.getClassName());
		assertEquals(1, type.getGenerics().size());
		MirageType genericArg = type.getGenerics().get(0);
		assertEquals(getClass().getName(), genericArg.getClassName());
	}
	
	private MirageField getField(String name, List<MirageField> fields) {
		for(MirageField field: fields)
			if (field.getName().equals(name))
				return field;
		return null;
	}
	
}
