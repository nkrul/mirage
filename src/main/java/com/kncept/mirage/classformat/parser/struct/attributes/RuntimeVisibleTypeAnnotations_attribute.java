package com.kncept.mirage.classformat.parser.struct.attributes;

import java.io.IOException;

import com.kncept.mirage.classformat.parser.SimpleDataTypesStream;
import com.kncept.mirage.classformat.parser.struct.attribute_info;
import com.kncept.mirage.classformat.parser.struct.cp_info;

/**
 * 
<pre>
RuntimeVisibleTypeAnnotations_attribute {
    u2              attribute_name_index;
    u4              attribute_length;
    u2              num_annotations;
    type_annotation annotations[num_annotations];
}

type_annotation {
    u1 target_type;
    union {
        type_parameter_target;
        supertype_target;
        type_parameter_bound_target;
        empty_target;
        method_formal_parameter_target;
        throws_target;
        localvar_target;
        catch_target;
        offset_target;
        type_argument_target;
    } target_info;
    type_path target_path;
    u2        type_index;
    u2        num_element_value_pairs;
    {   u2            element_name_index;
        element_value value;
    } element_value_pairs[num_element_value_pairs];
}



Value	Kind of target	target_info item
0x00	type parameter declaration of generic class or interface	type_parameter_target
0x01	type parameter declaration of generic method or constructor	type_parameter_target
0x10	type in extends or implements clause of class declaration (including the direct superclass or direct superinterface of an anonymous class declaration), or in extends clause of interface declaration	supertype_target
0x11	type in bound of type parameter declaration of generic class or interface	type_parameter_bound_target
0x12	type in bound of type parameter declaration of generic method or constructor	type_parameter_bound_target
0x13	type in field declaration	empty_target
0x14	return type of method, or type of newly constructed object	empty_target
0x15	receiver type of method or constructor	empty_target
0x16	type in formal parameter declaration of method, constructor, or lambda expression	formal_parameter_target
0x17	type in throws clause of method or constructor	throws_target
0x40	type in local variable declaration	localvar_target
0x41	type in resource variable declaration	localvar_target
0x42	type in exception parameter declaration	catch_target
0x43	type in instanceof expression	offset_target
0x44	type in new expression	offset_target
0x45	type in method reference expression using ::new	offset_target
0x46	type in method reference expression using ::Identifier	offset_target
0x47	type in cast expression	type_argument_target
0x48	type argument for generic constructor in new expression or explicit constructor invocation statement	type_argument_target
0x49	type argument for generic method in method invocation expression	type_argument_target
0x4A	type argument for generic constructor in method reference expression using ::new	type_argument_target
0x4B	type argument for generic method in method reference expression using ::Identifier	type_argument_target


</pre>
 * 
 * @author nick
 *
 */
public class RuntimeVisibleTypeAnnotations_attribute extends attribute_info {
	
	public int num_annotations;
	public type_annotation annotations[];
	
	public RuntimeVisibleTypeAnnotations_attribute(
			int attribute_name_index,
			int attribute_length,
			SimpleDataTypesStream in,
			cp_info[] zeroPaddedConstantPool
			) throws IOException {
		super(attribute_name_index, attribute_length, in, zeroPaddedConstantPool);
		num_annotations = in.u2();
		annotations = new type_annotation[num_annotations];
		for(int i = 0; i < num_annotations; i++)
			annotations[i] = new type_annotation(in);
	}
	
	public static class type_annotation {
		byte target_type;
		
//		type_parameter_target {
//		    u1 type_parameter_index;
//		}
		byte type_parameter_index;
		
//		supertype_target {
//		    u2 supertype_index;
//		}
		int supertype_index;
		
//		type_parameter_bound_target {
//		    u1 type_parameter_index;
//		    u1 bound_index;
//		}
//		byte type_parameter_index; //can't double declare this...
		byte bound_index;
        
//		empty_target {
//		}
		
//		formal_parameter_target {
//		    u1 formal_parameter_index;
//		}
        byte formal_parameter_index;
        
//        throws_target {
//            u2 throws_type_index;
//        }
        int throws_type_index;
        
//		TODO
//        localvar_target {
//            u2 table_length;
//            {   u2 start_pc;
//                u2 length;
//                u2 index;
//            } table[table_length];
//        }
        int table_length;
        byte[] table;
        
//        catch_target {
//            u2 exception_table_index;
//        }
        int exception_table_index;
        
//        offset_target {
//            u2 offset;
//        }
        int offset;
        
//        type_argument_target {
//            u2 offset;
//            u1 type_argument_index;
//        }
//        int offset;
        byte type_argument_index;
		
		public type_annotation(SimpleDataTypesStream in) throws IOException {
			target_type = in.u1();
			switch(target_type) {
			case 0x00:
			case 0x01:
				type_parameter_index = in.u1();
				break;
			case 0x10:
				supertype_index = in.u2();
				break;
			case 0x11:
			case 0x12:
				type_parameter_index = in.u1();
				bound_index = in.u1();
				break;
			case 0x13:
			case 0x14:
			case 0x15:
				in.u2();
				break;
			case 0x16:
				formal_parameter_index = in.u1();
				break;
			case 0x17:
				throws_type_index = in.u2();
				break;
			case 0x40:
			case 0x41:
				table_length = in.u2();
				table = in.bytes(table_length);
				break;
			case 0x42:
				exception_table_index = in.u2();
				break;
			case 0x43:
			case 0x44:
			case 0x45:
			case 0x46:
				offset = in.u2();
				break;
			case 0x47:
			case 0x48:
			case 0x49:
			case 0x4A:
			case 0x4B:
				offset = in.u2();
				type_argument_index = in.u1();
				break;
			}
			
		}
	}

}
