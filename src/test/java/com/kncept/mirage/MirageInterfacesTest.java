package com.kncept.mirage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.kncept.mirage.interfacestest.NoInterfaceClass;
import com.kncept.mirage.interfacestest.NoInterfaceInterface;
import com.kncept.mirage.interfacestest.RunnableCallableClass;
import com.kncept.mirage.interfacestest.RunnableCallableInterface;
import com.kncept.mirage.interfacestest.RunnableInterfaceClass;
import com.kncept.mirage.interfacestest.RunnableInterfaceInterface;
import com.kncept.mirage.util.MirageProvider;

public class MirageInterfacesTest {
	
	public static Stream<MirageProvider> allTypes() throws IOException {
		return MirageProvider.allTypesAsParameters();
	}
	
	@ParameterizedTest
	@MethodSource("allTypes")
	public void noInterfacesImplemented(MirageProvider provider) {
		Mirage mirage = provider.mirage(NoInterfaceClass.class);
		assertTrue(mirage.getImplementedInterfaces().isEmpty());
		
		mirage = provider.mirage(NoInterfaceInterface.class);
		assertTrue(mirage.getImplementedInterfaces().isEmpty());
	}
	
	@ParameterizedTest
	@MethodSource("allTypes")
	public void singleInterfaceImplemented(MirageProvider provider) {
		Mirage mirage = provider.mirage(RunnableInterfaceClass.class);
		assertEquals(1, mirage.getImplementedInterfaces().size());
		assertEquals(Runnable.class.getName(), mirage.getImplementedInterfaces().get(0));
		
		mirage = provider.mirage(RunnableInterfaceInterface.class);
		assertEquals(1, mirage.getImplementedInterfaces().size());
		assertEquals(Runnable.class.getName(), mirage.getImplementedInterfaces().get(0));
	}
	
	@ParameterizedTest
	@MethodSource("allTypes")
	public void multipleInterfaceImplemented(MirageProvider provider) {
		Mirage mirage = provider.mirage(RunnableCallableClass.class);
		assertEquals(2, mirage.getImplementedInterfaces().size());
		assertEquals(Runnable.class.getName(), mirage.getImplementedInterfaces().get(0));
		assertEquals(Callable.class.getName(), mirage.getImplementedInterfaces().get(1));
		
		mirage = provider.mirage(RunnableCallableInterface.class);
		assertEquals(2, mirage.getImplementedInterfaces().size());
		assertEquals(Runnable.class.getName(), mirage.getImplementedInterfaces().get(0));
		assertEquals(Callable.class.getName(), mirage.getImplementedInterfaces().get(1));
	}
}




