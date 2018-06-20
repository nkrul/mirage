package com.kncept.mirage;

import static java.lang.reflect.Modifier.isPublic;
import static java.lang.reflect.Modifier.isStatic;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.kncept.mirage.util.MirageProvider;

public class MirageMethodsTest {

	public static Stream<MirageProvider> allTypes() throws IOException {
		return MirageProvider.allTypesAsParameters();
	}
	
	@ParameterizedTest
	@MethodSource("allTypes")
	public void methodNamesAreCorrect(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		MirageMethod method = getMethod("methodNamesAreCorrect", mirage.getMethods());
		assertNotNull(method);
	}
	
	@ParameterizedTest
	@MethodSource("allTypes")
	public void publicVoidModifiers(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		MirageMethod method = getMethod("publicVoidModifiers", mirage.getMethods());
		int modifiers = method.getModifiers();
		assertTrue(isPublic(modifiers));
		assertFalse(isStatic(modifiers));
	}
	
	public void methodWithVoidReturn(){}
	@ParameterizedTest
	@MethodSource("allTypes")
	public void voidReturnType(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		MirageMethod method = getMethod("methodWithVoidReturn", mirage.getMethods());
		assertEquals(void.class.getName(), method.getReturnType().getClassName());
	}
	
	public Object methodReturningObject(){return null;}
	@ParameterizedTest
	@MethodSource("allTypes")
	public void objectReturnType(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		MirageMethod method = getMethod("methodReturningObject", mirage.getMethods());
		assertEquals(Object.class.getName(), method.getReturnType().getClassName());
	}
	
	public Class<? extends MirageMethodsTest> methodReturningObjectWithGenerics(){return getClass();}
	@ParameterizedTest
	@MethodSource("allTypes")
	public void parameterisedReturnType(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		MirageMethod method = getMethod("methodReturningObjectWithGenerics", mirage.getMethods());
		assertEquals(Class.class.getName(), method.getReturnType().getClassName());
		assertEquals(1, method.getReturnType().getGenerics().size());
		assertEquals(getClass().getName(), method.getReturnType().getGenerics().get(0).getClassName());
	}
	
	public void methodWithTypedArg(MirageMethodsTest methodArgOne){}
	@ParameterizedTest
	@MethodSource("allTypes")
	public void singleTypedMethodArg(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		MirageMethod method = getMethod("methodWithTypedArg", mirage.getMethods());
		List<MirageType> paramTypes = method.getParameterTypes();
		assertEquals(1, paramTypes.size());
		assertEquals(getClass().getName(), paramTypes.get(0).getClassName());
	}
	
	public void methodWithMultipleTypedArgs(MirageMethodsTest methodArgOne, Object arg2, Runnable thirdArg){}
	@ParameterizedTest
	@MethodSource("allTypes")
	public void multipleTypedMethodArgs(MirageProvider provider) {
		Mirage mirage = provider.mirage(getClass());
		MirageMethod method = getMethod("methodWithMultipleTypedArgs", mirage.getMethods());
		List<MirageType> paramTypes = method.getParameterTypes();
		assertEquals(3, paramTypes.size());
		assertEquals(getClass().getName(), paramTypes.get(0).getClassName());
		assertEquals(Object.class.getName(), paramTypes.get(1).getClassName());
		assertEquals(Runnable.class.getName(), paramTypes.get(2).getClassName());
	}
	
	
	private MirageMethod getMethod(String name, List<MirageMethod> methods) {
		for(MirageMethod method: methods)
			if (method.getName().equals(name))
				return method;
		return null;
	}
	
}
