package com.kncept.mirage;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.kncept.mirage.reflection.ClassMirage;

public class EquivalenceTest {

	Class<?> reflection = getClass();
	Mirage mirage = new ClassMirage(getClass());
//	Mirage mirage = new ClassFormat()
	
	@Test
	public void name() {
		assertEquals(reflection.getName(), mirage.getName());
	}
	
}
