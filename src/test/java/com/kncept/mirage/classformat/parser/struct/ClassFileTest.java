package com.kncept.mirage.classformat.parser.struct;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.kncept.mirage.classformat.parser.DataTypesParser;


public class ClassFileTest {

	@Test
	public void magicNumber() throws IOException {
		ClassFile cf = parseClassFile();
		assertEquals((int)0xCAFEBABE, cf.magic);
	}
	
	@Test
	public void className() throws IOException {
		ClassFile cf = parseClassFile();
		cp_info info = cf.constant_pool[cf.this_class];
		assertEquals(CONSTANT_Class_info.class, info.info.getClass());
		info = cf.constant_pool[((CONSTANT_Class_info)info.info).name_index];
		assertEquals(CONSTANT_Utf8_info.class, info.info.getClass());
		String className = new String(((CONSTANT_Utf8_info)info.info).bytes, "UTF-8");
		
		assertEquals(testClass().getName().replaceAll("\\.", "/"), className); //classNames 
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
