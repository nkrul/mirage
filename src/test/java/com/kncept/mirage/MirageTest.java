package com.kncept.mirage;

import static com.kncept.junit.dataprovider.testfactory.TestFactoryCallback.instanceProvider;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import com.kncept.mirage.classformat.InputStreamMirage;
import com.kncept.mirage.reflection.ClassReflectionMirage;
import com.kncept.mirage.util.MirageProvider;

/**
 * @author nick
 *
 */
public class MirageTest {

	private Class<? extends MirageTest> reflection = getClass();
	
	//fields for testing types and permissions against
	Object[] arrayObject;
	Object[][] arrayObject2;
	int intType;
	int[] intArray;
	Integer objectInteger;
	
	boolean nativeBoolean;
	Boolean objectBoolean;
	
	List<?> genericUnknownList;
	List<Object> genericObjectList;
	List<MirageTest> thisClassObjectList;
	
	Map<String, String> stringStringMap;
	
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
		assertEquals(reflection.getName(), mirage.getName());
	}
	
	@ParameterisedTest(source="allTypes")
	public void superClass(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		assertEquals(reflection.getSuperclass().getName(), mirage.getSuperclassName());
	}
	
	@ParameterisedTest(source="allTypes")
	public void emptymplementedInterfaces(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		assertTrue(mirage.getImplementedInterfaces().isEmpty());
	}
	
	@ParameterisedTest(source="allTypes")
	public void singleInterfaceImplemented(MirageProvider provider) {
		@SuppressWarnings("unchecked")
		List<Class<? extends Mirage>> types = Arrays.asList(ClassReflectionMirage.class, InputStreamMirage.class);
		for(Class<? extends Mirage> type: types) {
			Mirage mirage = provider.mirage(type);
			List<String> interfaces = mirage.getImplementedInterfaces();
			assertEquals(1, interfaces.size());
			assertTrue(interfaces.contains(Mirage.class.getName()));
		}
	}
	
	@ParameterisedTest(source="allTypes")
	public void fields(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		assertNotNull(mirage.getFields());
		for (MirageField field: mirage.getFields()) {
			MirageType type = field.getType();
			if (field.getName().equals("reflection")) {
				assertEquals(Class.class.getName(), type.getBaseType());
//				assertFalse(type.getGenerics().isEmpty());
			} else if (field.getName().equals("provider")) {
				assertEquals(MirageProvider.class.getName(), type.getBaseType());
				assertTrue(type.getGenerics().isEmpty());
			}
		}
		
	}
	
}
