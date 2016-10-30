package com.kncept.mirage.classformat.parser;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class DataTypesParser {
	private final InputStream in;
	
	Charset utf8 = Charset.forName("UTF-8");
	CharsetDecoder utf8Decoder = utf8.newDecoder();
	
	
	public DataTypesParser(InputStream in) {
		this.in = in;
	}

	public byte u1() throws IOException {
		return bytes(1)[0];
	}
	
	public int u2() throws IOException {
		byte[] data = bytes(2);
		return (data[0] & 0xFF) << 8 | (data[1] & 0xFF);
	}
	
	public int u4() throws IOException {
		byte[] data = bytes(4);
		return (data[0] << 24) | (data[1] & 0xFF) << 16 | (data[2] & 0xFF) << 8 | (data[3] & 0xFF);
	}
	
	public byte[] bytes(int length) throws IOException {
		byte[] data = new byte[length];
		int read = in.read(data);
		if (read != data.length) {
			throw new IOException("Unable to read enough bytes: read " + read + " of " + length);
		}
		return data;
	}
}
