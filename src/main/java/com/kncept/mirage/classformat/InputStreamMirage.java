package com.kncept.mirage.classformat;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.kncept.mirage.Mirage;
import com.kncept.mirage.classformat.parser.DataTypesParser;
import com.kncept.mirage.classformat.parser.struct.ClassFile;

public class InputStreamMirage implements Mirage {
	
	private String name;
	private String superclassName;
	private List<String> implementedInterfaces;
	
	public InputStreamMirage(InputStream in) {
		try {
			ClassFile cf = new ClassFile();
			cf.parse(new DataTypesParser(in));

			name = jvmClassnameToJavaClassname(cf.className());
			superclassName = jvmClassnameToJavaClassname(cf.superclassName());
			
			List<String> jvmInterfaces = cf.interfaceNames();
			implementedInterfaces = new ArrayList<>(jvmInterfaces.size());
			for(String interfaceName: jvmInterfaces) {
				implementedInterfaces.add(jvmClassnameToJavaClassname(interfaceName));
			}
			
			
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
	
	@Override
	public List<String> getImplementedInterfaces() {
		return implementedInterfaces;
	}
	
}
