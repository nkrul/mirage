package com.kncept.mirage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.kncept.mirage.classformat.InputStreamMirage;
import com.kncept.mirage.reflection.ClassReflectionMirage;
import com.kncept.mirage.util.MirageProvider;

/**
 * @author nick
 *
 */
@RunWith(Parameterized.class)
public class MirageTest {

	private Class<?> reflection = getClass();
	private final MirageProvider provider;
	
	@Parameters
	public static Object[][] params() throws IOException {
		return MirageProvider.ParamsProviders.allTypesAsParameters();
	}
	
	public MirageTest(MirageProvider provider) {
		this.provider = provider;
	}
	
	@Test
	public void name() {
		Mirage mirage = provider.mirage(getClass());
		assertEquals(reflection.getName(), mirage.getName());
	}
	
	@Test
	public void superClass() {
		Mirage mirage = provider.mirage(getClass());
		assertEquals(reflection.getSuperclass().getName(), mirage.getSuperclassName());
	}
	
	@Test
	public void emptymplementedInterfaces() {
		Mirage mirage = provider.mirage(getClass());
		assertTrue(mirage.getImplementedInterfaces().isEmpty());
	}
	
	@Test
	public void singleInterfaceImplemented() {
		List<Class<?>> types = Arrays.asList(ClassReflectionMirage.class, InputStreamMirage.class);
		for(Class<?> type: types) {
			Mirage mirage = provider.mirage(type);
			List<String> interfaces = mirage.getImplementedInterfaces();
			assertEquals(1, interfaces.size());
			assertTrue(interfaces.contains(Mirage.class.getName()));
		}
	}
	
}
