package com.kncept.mirage;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.kncept.mirage.classformat.InputStreamMirage;
import com.kncept.mirage.reflection.ClassReflectionMirage;

@RunWith(Parameterized.class)
public class EquivalenceTest {

	private Class<?> reflection = getClass();
	private final Mirage mirage;
	
	@Parameters
	public static Object[][] params() throws IOException {
		return new Object[][]{
			{new ClassReflectionMirage(EquivalenceTest.class)},
			{new InputStreamMirage(classInputStream(EquivalenceTest.class))}
		};
	}
	
	private static InputStream classInputStream(Class<?> testClass) throws IOException {
		String classResourceName = testClass.getName().replaceAll("\\.", "/") + ".class";
		return testClass.getClassLoader().getResourceAsStream(classResourceName);
	}
	
	
	public EquivalenceTest(Mirage mirage) {
		this.mirage = mirage;
	}
	
	@Test
	public void name() {
		assertEquals(reflection.getName(), mirage.getName());
	}
	
	@Test
	public void superClass() {
		assertEquals(reflection.getSuperclass().getName(), mirage.getSuperclassName());
	}
	
}
