package com.kncept.src.roaster;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.Annotation;
import org.jboss.forge.roaster.model.Extendable;
import org.jboss.forge.roaster.model.Field;
import org.jboss.forge.roaster.model.FieldHolder;
import org.jboss.forge.roaster.model.InterfaceCapable;
import org.jboss.forge.roaster.model.JavaType;
import org.jboss.forge.roaster.model.JavaUnit;
import org.jboss.forge.roaster.model.Method;
import org.jboss.forge.roaster.model.MethodHolder;

import com.kncept.mirage.Mirage;
import com.kncept.mirage.MirageAnnotation;
import com.kncept.mirage.MirageField;
import com.kncept.mirage.MirageMethod;

/**
 * This is unfinished and doesn't work correctly yet.
 * Its not really deprecated. 
 * @author nick
 *
 */
@Deprecated
public class RoasterMirage implements Mirage {

	public final JavaType<?> javaType;
	public final JavaUnit javaUnit;
	
	public RoasterMirage(InputStream in) {
		javaUnit = Roaster.parseUnit(in);
		
		List<JavaType<?>> topLevelTypes = javaUnit.getTopLevelTypes();
		if (topLevelTypes.isEmpty())
			throw new RuntimeException("Error: Nothing parsed");
		if (topLevelTypes.size() != 1)
			throw new RuntimeException("Error: Unable to process multiple types per file yet");
		javaType = topLevelTypes.get(0);
		
	}
	
	@Override
	public String getClassName() {
		return javaType.getCanonicalName();
	}
	
	@Override
	public String getSuperclassName() {
		if (javaType instanceof Extendable)
			return ((Extendable)javaType).getSuperType();
		return "java.lang.Object";
	}
	
	@Override
	public List<MirageAnnotation> getAnnotations() {
		List<MirageAnnotation> mirageAnnotations = new ArrayList<>();
		List<? extends Annotation<?>> roasterAnnotations = javaType.getAnnotations();
		for(Annotation a: roasterAnnotations)
			mirageAnnotations.add(new RoasterMirageAnnotation(a));
		return mirageAnnotations;
	}
	
	@Override
	public List<MirageField> getFields() {
		List<MirageField> mirageFields = new ArrayList<>();
		if (javaType instanceof FieldHolder) {
			List<? extends Field<?>> roasterFields = ((FieldHolder)javaType).getFields();	
			for(Field<?> roasterField: roasterFields)
				mirageFields.add(new RoasterField(this, roasterField));
		}
		return mirageFields;
	}
	
	@Override
	public List<MirageMethod> getMethods() {
		List<MirageMethod> mirageMethods = new ArrayList<>();
		if (javaType instanceof MethodHolder) {
			List<? extends Method> roasterMethods = ((MethodHolder)javaType).getMethods();
			for(Method m: roasterMethods)
				mirageMethods.add(new RoasterMirageMethod(this, m));
		}
		return mirageMethods;
	}
	
	@Override
	public List<String> getImplementedInterfaces() {
		if (javaType instanceof InterfaceCapable)
			return ((InterfaceCapable)javaType).getInterfaces();
		return Collections.emptyList();
	}
	
}
