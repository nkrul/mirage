package com.kncept.mirage;

import static com.kncept.junit.dataprovider.testfactory.TestFactoryCallback.instanceProvider;
import static java.lang.reflect.Modifier.isPublic;
import static java.lang.reflect.Modifier.isStatic;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

public class MirageMethodsTest {

	
	@TestFactory
	public Collection<DynamicTest> testFactory() {
		return instanceProvider(this);
	}
	
	@ParameterSource(name="allTypes")
	public static Object[][] allTypes() throws IOException {
		return MirageProvider.ParamsProviders.allTypesAsParameters();
	}
	
	@ParameterisedTest(source="allTypes")
	public void methodNamesAreCorrect(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		MirageMethod method = getMethod("methodNamesAreCorrect", mirage.getMethods());
		assertNotNull(method);
	}
	
	@ParameterisedTest(source="allTypes")
	public void publicVoidModifiers(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		MirageMethod method = getMethod("publicVoidModifiers", mirage.getMethods());
		int modifiers = method.getModifiers();
		assertTrue(isPublic(modifiers));
		assertFalse(isStatic(modifiers));
	}
	
	public void methodWithVoidReturn(){}
	@ParameterisedTest(source="allTypes")
	public void voidReturnType(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		MirageMethod method = getMethod("methodWithVoidReturn", mirage.getMethods());
		assertEquals(void.class.getName(), method.getReturnType().getClassName());
	}
	
	public Object methodReturningObject(){return null;}
	@ParameterisedTest(source="allTypes")
	public void objectReturnType(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		MirageMethod method = getMethod("methodReturningObject", mirage.getMethods());
		assertEquals(Object.class.getName(), method.getReturnType().getClassName());
	}
	
	public Class<? extends MirageMethodsTest> methodReturningObjectWithGenerics(){return getClass();}
	@ParameterisedTest(source="allTypes")
	public void parameterisedReturnType(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		MirageMethod method = getMethod("methodReturningObjectWithGenerics", mirage.getMethods());
		assertEquals(Class.class.getName(), method.getReturnType().getClassName());
		assertEquals(1, method.getReturnType().getGenerics().size());
		assertEquals(getClass().getName(), method.getReturnType().getGenerics().get(0).getClassName());
	}
	
	public void methodWithTypedArg(MirageMethodsTest methodArgOne){}
	@ParameterisedTest(source="allTypes")
	public void singleTypedMethodArg(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		MirageMethod method = getMethod("methodWithTypedArg", mirage.getMethods());
		List<MirageType> returnTypes = method.getParameterTypes();
		assertEquals(1, returnTypes.size());
		assertEquals(getClass().getName(), returnTypes.get(0).getClassName());
	}
	
	public void methodWithMultipleTypedArgs(MirageMethodsTest methodArgOne, Object arg2, Runnable thirdArg){}
	@ParameterisedTest(source="allTypes")
	public void multipleTypedMethodArgs(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		MirageMethod method = getMethod("methodWithMultipleTypedArgs", mirage.getMethods());
		List<MirageType> returnTypes = method.getParameterTypes();
		assertEquals(3, returnTypes.size());
		assertEquals(getClass().getName(), returnTypes.get(0).getClassName());
		assertEquals(Object.class.getName(), returnTypes.get(1).getClassName());
		assertEquals(Runnable.class.getName(), returnTypes.get(2).getClassName());
	}
	
	
	private MirageMethod getMethod(String name, List<MirageMethod> methods) {
		for(MirageMethod method: methods)
			if (method.getName().equals(name))
				return method;
		return null;
	}
	
}
