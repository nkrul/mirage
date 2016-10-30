package com.kncept.mirage.classformat.parser.struct;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;

import com.kncept.mirage.classformat.parser.DataTypesParser;

public class ClassFileTest {

	@Test
	public void magicNumber() throws IOException {
		ClassFile cf = parseClassFile();
		Assert.assertEquals((int)0xCAFEBABE, cf.magic);
	}
	
	@Test
	public void className() throws IOException {
		ClassFile cf = parseClassFile();
		
		Assert.assertEquals(getClass().getName().replaceAll("\\.", "/"), cf.className()); //classNames 
	}
	
	private ClassFile parseClassFile() throws IOException {
		String classResourceName = getClass().getName().replaceAll("\\.", "/") + ".class";
		InputStream classBytesInputStream = getClass().getClassLoader().getResourceAsStream(classResourceName);
		
		ClassFile cf = new ClassFile();
		cf.parse(new DataTypesParser(classBytesInputStream));
		return cf;
	}
	
}
