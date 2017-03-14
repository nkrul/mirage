package com.kncept.mirage.classformat.signature.parser;

public interface DescriptorParser<T> {
	
	public T parse(String descriptor);
	
}
