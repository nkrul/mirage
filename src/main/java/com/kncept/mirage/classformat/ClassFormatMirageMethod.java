package com.kncept.mirage.classformat;

import java.util.List;

import com.kncept.mirage.Mirage;
import com.kncept.mirage.MirageMethod;
import com.kncept.mirage.MirageType;
import com.kncept.mirage.classformat.parser.struct.CONSTANT_Utf8_info;
import com.kncept.mirage.classformat.parser.struct.ClassFile;
import com.kncept.mirage.classformat.parser.struct.attribute_info;
import com.kncept.mirage.classformat.parser.struct.method_info;
import com.kncept.mirage.classformat.parser.struct.attributes.Signature_attribute;
import com.kncept.mirage.classformat.signature.parser.MethodDescriptorParser;
import com.kncept.mirage.classformat.signature.parser.MethodTypeSignatureParser;

public class ClassFormatMirageMethod implements MirageMethod {
	
	private final Mirage parent;
	private final ClassFile cf;
	private final method_info method_info;
	
	public ClassFormatMirageMethod(Mirage parent, ClassFile cf, method_info method_info) {
		this.parent = parent;
		this.cf = cf;
		this.method_info = method_info;
	}

	@Override
	public Mirage getDeclaredBy() {
		return parent;
	}
	
	@Override
	public int getModifiers() {
		return method_info.access_flags;
	}
	
	@Override
	public String getName() {
		return ((CONSTANT_Utf8_info)cf.constant_pool[method_info.name_index]).value();
	}
	
	@Override
	public MirageType getReturnType() {
		for(attribute_info attr: method_info.attributes) {
			if (attr instanceof Signature_attribute) {
				Signature_attribute sAttr = (Signature_attribute)attr;
				String signatureDescriptor = ((CONSTANT_Utf8_info)cf.constant_pool[sAttr.signature_index]).value();
				return new MethodTypeSignatureParser().parse(signatureDescriptor).returnType;
			}
		}
		String descriptor = ((CONSTANT_Utf8_info)cf.constant_pool[method_info.descriptor_index]).value();
		return new MethodDescriptorParser().parse(descriptor).returnType;
	}
	
	@Override
	public List<MirageType> getParameterTypes() {
		for(attribute_info attr: method_info.attributes) {
			if (attr instanceof Signature_attribute) {
				Signature_attribute sAttr = (Signature_attribute)attr;
				String signatureDescriptor = ((CONSTANT_Utf8_info)cf.constant_pool[sAttr.signature_index]).value();
				return new MethodTypeSignatureParser().parse(signatureDescriptor).params;
			}
		}
		String descriptor = ((CONSTANT_Utf8_info)cf.constant_pool[method_info.descriptor_index]).value();
		return new MethodDescriptorParser().parse(descriptor).params;
	}
	
}
