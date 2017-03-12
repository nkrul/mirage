package com.kncept.mirage.classformat.signature.parser;

import com.kncept.mirage.MirageType;

public interface DescriptorParser {
	
	public MirageType parse(String descriptor);
	
}
