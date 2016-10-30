package com.kncept.mirage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.kncept.mirage.reflection.ClassMirage;

@RunWith(JUnitPlatform.class)
public class EquivalenceTest {

	Class<?> reflection = getClass();
	Mirage mirage = new ClassMirage(getClass());
//	Mirage mirage = new ClassFormat()
	
	@Test
	public void name() {
		assertEquals(reflection.getName(), mirage.getName());
	}
	
}
