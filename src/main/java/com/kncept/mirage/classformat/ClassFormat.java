package com.kncept.mirage.classformat;

import java.io.IOException;
import java.io.InputStream;

import com.kncept.mirage.Mirage;
import com.kncept.mirage.classformat.parser.DataTypesParser;
import com.kncept.mirage.classformat.parser.struct.ClassFile;

public class ClassFormat implements Mirage {
	
	private String name;
	
	public ClassFormat(InputStream in) {
		try {
			ClassFile cf = new ClassFile();
			cf.parse(new DataTypesParser(in));

			this.name = cf.className().replaceAll("\\/", ".");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public String getName() {
		return name;
	}
	
}
