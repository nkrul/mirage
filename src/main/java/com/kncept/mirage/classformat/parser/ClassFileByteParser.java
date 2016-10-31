package com.kncept.mirage.classformat.parser;

import java.io.IOException;

public interface ClassFileByteParser {
	
	public void parse(SimpleDataTypesStream in) throws IOException;
	
}
