package com.kncept.mirage;

import static com.kncept.junit.dataprovider.testfactory.TestFactoryCallback.instanceProvider;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import com.kncept.junit.dataprovider.ParameterSource;
import com.kncept.junit.dataprovider.ParameterisedTest;
import com.kncept.mirage.util.MirageProvider;

public class MirageInterfacesTest {

	@TestFactory
	public Collection<DynamicTest> testFactory() {
		return instanceProvider(this);
	}
	
	@ParameterSource(name="allTypes")
	public static Object[][] allTypes() throws IOException {
		return MirageProvider.ParamsProviders.allTypesAsParameters();
	}
	
	static class NoInterfaceClass {}
	static interface NoInterfaceInterface {}
	@ParameterisedTest(source="allTypes")
	public void noInterfacesImplemented(MirageProvider provider) {
		Mirage mirage = provider.mirage(NoInterfaceClass.class);
		assertTrue(mirage.getImplementedInterfaces().isEmpty());
		
		mirage = provider.mirage(NoInterfaceInterface.class);
		assertTrue(mirage.getImplementedInterfaces().isEmpty());
	}
	
	static class RunnableInterfaceClass implements Runnable {
		@Override public void run() {}
	}
	static interface RunnableInterfaceInterface extends Runnable {}
	@ParameterisedTest(source="allTypes")
	public void singleInterfaceImplemented(MirageProvider provider) {
		Mirage mirage = provider.mirage(RunnableInterfaceClass.class);
		assertEquals(1, mirage.getImplementedInterfaces().size());
		assertEquals(Runnable.class.getName(), mirage.getImplementedInterfaces().get(0));
		
		mirage = provider.mirage(RunnableInterfaceInterface.class);
		assertEquals(1, mirage.getImplementedInterfaces().size());
		assertEquals(Runnable.class.getName(), mirage.getImplementedInterfaces().get(0));
	}
	
	static class RunnableCallableClass implements Runnable, Callable<Future> {
		@Override public void run() {}
		@Override public Future call() throws Exception { return null;}
	}
	static interface RunnableCallableInterface extends Runnable, Callable<Future> {}
	@ParameterisedTest(source="allTypes")
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




