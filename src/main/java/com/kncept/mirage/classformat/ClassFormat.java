package com.kncept.mirage.classformat;

import com.kncept.mirage.Mirage;

public class ClassFormat implements Mirage {
	
	final byte[] data;
	
	public ClassFormat(byte[] data) {
		this.data = data;
	}

	@Override
	public Object source() {
		return data;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
