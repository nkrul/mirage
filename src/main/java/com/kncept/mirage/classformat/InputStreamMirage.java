package com.kncept.mirage.classformat;

import java.io.IOException;
import java.io.InputStream;

import com.kncept.mirage.Mirage;
import com.kncept.mirage.classformat.parser.DataTypesParser;
import com.kncept.mirage.classformat.parser.struct.ClassFile;

public class InputStreamMirage implements Mirage {
	
	private String name;
	private String superclassName;
	
	public InputStreamMirage(InputStream in) {
		try {
			ClassFile cf = new ClassFile();
			cf.parse(new DataTypesParser(in));

			name = jvmClassnameToJavaClassname(cf.className());
			superclassName = jvmClassnameToJavaClassname(cf.superclassName());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private String jvmClassnameToJavaClassname(String name) {
		return name.replaceAll("\\/", ".");
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public String getSuperclassName() {
		return superclassName;
	}
	
}
