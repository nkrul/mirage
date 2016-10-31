package com.kncept.mirage.classformat.parser;

import java.io.IOException;

import com.kncept.mirage.classformat.parser.struct.cp_info;

public interface ClassFileConstantPoolByteParser {

	public void parse(SimpleDataTypesStream in, cp_info[] zeroPaddedConstantPool) throws IOException;
	
}
