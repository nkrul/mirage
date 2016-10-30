package com.kncept.mirage.classformat.parser;

import java.io.IOException;

public interface Parsable {
	
	public void parse(DataTypesParser in) throws IOException;
	
}
