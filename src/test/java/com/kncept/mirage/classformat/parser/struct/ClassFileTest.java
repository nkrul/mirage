package com.kncept.mirage.classformat.parser.struct;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.kncept.mirage.classformat.parser.DataTypesParser;

@RunWith(JUnitPlatform.class)
public class ClassFileTest {

	@Test
	public void magicNumber() throws IOException {
		ClassFile cf = parseClassFile();
		assertEquals((int)0xCAFEBABE, cf.magic);
	}
	
	@Test
	public void className() throws IOException {
		ClassFile cf = parseClassFile();
		assertEquals(testClass().getName().replaceAll("\\.", "/"), cf.className()); //classNames 
	}
	
	private Class<?> testClass() {
		return ClassFile.class;
	}
	
	private ClassFile parseClassFile() throws IOException {
		String classResourceName = testClass().getName().replaceAll("\\.", "/") + ".class";
		InputStream classBytesInputStream = getClass().getClassLoader().getResourceAsStream(classResourceName);
		
		ClassFile cf = new ClassFile();
		cf.parse(new DataTypesParser(classBytesInputStream));
		return cf;
	}
	
}
