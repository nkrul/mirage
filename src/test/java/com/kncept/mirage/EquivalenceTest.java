package com.kncept.mirage;

import org.junit.Assert;
import org.junit.Test;

import com.kncept.mirage.reflection.ClassMirage;

public class EquivalenceTest {

	Class<?> reflection = getClass();
	Mirage mirage = new ClassMirage(getClass());
//	Mirage mirage = new ClassFormat()
	
	@Test
	public void name() {
		Assert.assertEquals(reflection.getName(), mirage.getName());
	}
	
}
