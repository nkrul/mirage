package com.kncept.mirage;

import static com.kncept.junit.dataprovider.testfactory.TestFactoryCallback.instanceProvider;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import com.kncept.junit.dataprovider.ParameterSource;
import com.kncept.junit.dataprovider.ParameterisedTest;
import com.kncept.mirage.classformat.ClassFormatMirage;
import com.kncept.mirage.reflection.ClassReflectionMirage;
import com.kncept.mirage.util.MirageProvider;

/**
 * A set of 'generic usage tests'
 *
 */
public class MirageFieldsTest {
	private Class<? extends MirageFieldsTest> thisClass = getClass();
	
	@TestFactory
	public Collection<DynamicTest> testFactory() {
		return instanceProvider(this);
	}
	
	@ParameterSource(name="allTypes")
	public static Object[][] allTypes() throws IOException {
		return MirageProvider.ParamsProviders.allTypesAsParameters();
	}
	
	@ParameterisedTest(source="allTypes")
	public void name(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		assertEquals(thisClass.getName(), mirage.getName());
	}
	
	@ParameterisedTest(source="allTypes")
	public void superClass(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		assertEquals(thisClass.getSuperclass().getName(), mirage.getSuperclassName());
	}
	
	int[] intArray;
	int[][] intArray2;
	@ParameterisedTest(source="allTypes")
	public void primitiveArrayType(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		MirageField intArray = getField("intArray", mirage.getFields());
		assertEquals("int", intArray.getMirageType().getBaseType());
		assertEquals(1, intArray.getMirageType().getArrayDepth());
		
		intArray = getField("intArray2", mirage.getFields());
		assertEquals("int", intArray.getMirageType().getBaseType());
		assertEquals(2, intArray.getMirageType().getArrayDepth());
	}
	
	MirageFieldsTest[] arrayObject;
	MirageFieldsTest[][] arrayObject2;
	@ParameterisedTest(source="allTypes")
	public void objectArrayType(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		MirageField intArray = getField("arrayObject", mirage.getFields());
		assertEquals(getClass().getName(), intArray.getMirageType().getBaseType());
		assertEquals(1, intArray.getMirageType().getArrayDepth());
		
		intArray = getField("arrayObject2", mirage.getFields());
		assertEquals(getClass().getName(), intArray.getMirageType().getBaseType());
		assertEquals(2, intArray.getMirageType().getArrayDepth());
	}
	
	int nativeInteger;
	Integer objectInteger;
	@ParameterisedTest(source="allTypes")
	public void integerTypes(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		MirageField nativeType = getField("nativeInteger", mirage.getFields());
		assertEquals("int", nativeType.getMirageType().getBaseType());
		MirageField objectType = getField("objectInteger", mirage.getFields());
		assertEquals("java.lang.Integer", objectType.getMirageType().getBaseType());
	}
	
	boolean nativeBoolean;
	Boolean objectBoolean;
	@ParameterisedTest(source="allTypes")
	public void booleanTYpes(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		MirageField nativeType = getField("nativeBoolean", mirage.getFields());
		assertEquals("boolean", nativeType.getMirageType().getBaseType());
		MirageField objectType = getField("objectBoolean", mirage.getFields());
		assertEquals("java.lang.Boolean", objectType.getMirageType().getBaseType());
	}
	
	////// this is SUCH a generic test...
	@ParameterisedTest(source="allTypes")
	public void simpleGenericsTest(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		MirageField field = getField("thisClass", mirage.getFields());
		MirageType type = field.getMirageType();
		assertEquals(Class.class.getName(), type.getBaseType(), "The type of the 'thisClass' member should be java.lang.Class");
		assertEquals(1, type.getGenerics().size(), "the 'thisClass' member should have 1 generic type");
		MirageType genericType = type.getGenerics().get(0);
		assertEquals(getClass().getName(), genericType.getBaseType());
		assertTrue(genericType.getGenerics().isEmpty()); //this class has no generics in its signature
	}
	
	Map<String, List<MirageFieldsTest>> stringToListOfMirageTest;
	@ParameterisedTest(source="allTypes")
	public void multipleGenericsTest(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		MirageField field = getField("stringToListOfMirageTest", mirage.getFields());
		MirageType type = field.getMirageType();
		assertEquals("java.util.Map", type.getBaseType());
		assertEquals(2, type.getGenerics().size());

		assertEquals("java.lang.String", type.getGenerics().get(0).getBaseType());
		assertEquals("java.util.List", type.getGenerics().get(1).getBaseType());
		
		assertEquals(1, type.getGenerics().get(1).getGenerics().size());
		assertEquals(getClass().getName(), type.getGenerics().get(1).getGenerics().get(0).getBaseType());
	}
	
	List<?> genericUnknownList;
	@ParameterisedTest(source="allTypes")
	public void genericUnknownList(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		MirageField field = getField("genericUnknownList", mirage.getFields());
		MirageType type = field.getMirageType();
		assertEquals("java.util.List", type.getBaseType());
		assertEquals(1, type.getGenerics().size());
		MirageType genericArg = type.getGenerics().get(0);
		assertEquals("java.lang.Object", genericArg.getBaseType());
	}
	
	List<Object> genericObjectList;
	@ParameterisedTest(source="allTypes")
	public void genericObjectList(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		MirageField field = getField("genericObjectList", mirage.getFields());
		MirageType type = field.getMirageType();
		assertEquals("java.util.List", type.getBaseType());
		assertEquals(1, type.getGenerics().size());
		MirageType genericArg = type.getGenerics().get(0);
		assertEquals("java.lang.Object", genericArg.getBaseType());
	}
	
	//this one starts with the -
	List<? super MirageFieldsTest> thisClassSuperObjectList;
	@ParameterisedTest(source="allTypes")
	public void thisClassSuperObjectList(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		MirageField field = getField("thisClassSuperObjectList", mirage.getFields());
		MirageType type = field.getMirageType();
		assertEquals("java.util.List", type.getBaseType());
		assertEquals(1, type.getGenerics().size());
		MirageType genericArg = type.getGenerics().get(0);
		assertEquals(getClass().getName(), genericArg.getBaseType());
	}
	
	//this one starts with the +
	List<? extends MirageFieldsTest> thisClassExtendsObjectList;
	@ParameterisedTest(source="allTypes")
	public void thisClassExtendsObjectList(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		MirageField field = getField("thisClassExtendsObjectList", mirage.getFields());
		MirageType type = field.getMirageType();
		assertEquals("java.util.List", type.getBaseType());
		assertEquals(1, type.getGenerics().size());
		MirageType genericArg = type.getGenerics().get(0);
		assertEquals(getClass().getName(), genericArg.getBaseType());
	}
	
	private MirageField getField(String name, List<MirageField> fields) {
		for(MirageField field: fields)
			if (field.getName().equals(name))
				return field;
		return null;
	}
	
}
