package com.kncept.mirage.classformat.parser.struct.attributes;

import java.util.HashMap;
import java.util.Map;

import com.kncept.mirage.classformat.parser.descriptor.Signature_attribute;
import com.kncept.mirage.classformat.parser.struct.attribute_info.attribute_info_struct;

/**
 * 

                  <table summary="Predefined class file attributes" border="1">
                     <colgroup>
                        <col>
                        <col>
                        <col>
                        <col>
                     </colgroup>
                     <thead>
                        <tr>
                           <th>Attribute</th>
                           <th>Section</th>
                           <th>Java SE</th>
                           <th><code class="literal">class</code> file
                           </th>
                        </tr>
                     </thead>
                     <tbody>
                        <tr>
                           <td><code class="literal">ConstantValue</code></td>
                           <td><a class="xref" href="jvms-4.html#jvms-4.7.2" title="4.7.2.&nbsp;The ConstantValue Attribute">§4.7.2</a></td>
                           <td>1.0.2</td>
                           <td>45.3</td>
                        </tr>
                        <tr>
                           <td><code class="literal">Code</code></td>
                           <td><a class="xref" href="jvms-4.html#jvms-4.7.3" title="4.7.3.&nbsp;The Code Attribute">§4.7.3</a></td>
                           <td>1.0.2</td>
                           <td>45.3</td>
                        </tr>
                        <tr>
                           <td><code class="literal">StackMapTable</code></td>
                           <td><a class="xref" href="jvms-4.html#jvms-4.7.4" title="4.7.4.&nbsp;The StackMapTable Attribute">§4.7.4</a></td>
                           <td>6</td>
                           <td>50.0</td>
                        </tr>
                        <tr>
                           <td><code class="literal">Exceptions</code></td>
                           <td><a class="xref" href="jvms-4.html#jvms-4.7.5" title="4.7.5.&nbsp;The Exceptions Attribute">§4.7.5</a></td>
                           <td>1.0.2</td>
                           <td>45.3</td>
                        </tr>
                        <tr>
                           <td><code class="literal">InnerClasses</code></td>
                           <td><a class="xref" href="jvms-4.html#jvms-4.7.6" title="4.7.6.&nbsp;The InnerClasses Attribute">§4.7.6</a></td>
                           <td>1.1</td>
                           <td>45.3</td>
                        </tr>
                        <tr>
                           <td><code class="literal">EnclosingMethod</code></td>
                           <td><a class="xref" href="jvms-4.html#jvms-4.7.7" title="4.7.7.&nbsp;The EnclosingMethod Attribute">§4.7.7</a></td>
                           <td>5.0</td>
                           <td>49.0</td>
                        </tr>
                        <tr>
                           <td><code class="literal">Synthetic</code></td>
                           <td><a class="xref" href="jvms-4.html#jvms-4.7.8" title="4.7.8.&nbsp;The Synthetic Attribute">§4.7.8</a></td>
                           <td>1.1</td>
                           <td>45.3</td>
                        </tr>
                        <tr>
                           <td><code class="literal">Signature</code></td>
                           <td><a class="xref" href="jvms-4.html#jvms-4.7.9" title="4.7.9.&nbsp;The Signature Attribute">§4.7.9</a></td>
                           <td>5.0</td>
                           <td>49.0</td>
                        </tr>
                        <tr>
                           <td><code class="literal">SourceFile</code></td>
                           <td><a class="xref" href="jvms-4.html#jvms-4.7.10" title="4.7.10.&nbsp;The SourceFile Attribute">§4.7.10</a></td>
                           <td>1.0.2</td>
                           <td>45.3</td>
                        </tr>
                        <tr>
                           <td><code class="literal">SourceDebugExtension</code></td>
                           <td><a class="xref" href="jvms-4.html#jvms-4.7.11" title="4.7.11.&nbsp;The SourceDebugExtension Attribute">§4.7.11</a></td>
                           <td>5.0</td>
                           <td>49.0</td>
                        </tr>
                        <tr>
                           <td><code class="literal">LineNumberTable</code></td>
                           <td><a class="xref" href="jvms-4.html#jvms-4.7.12" title="4.7.12.&nbsp;The LineNumberTable Attribute">§4.7.12</a></td>
                           <td>1.0.2</td>
                           <td>45.3</td>
                        </tr>
                        <tr>
                           <td><code class="literal">LocalVariableTable</code></td>
                           <td><a class="xref" href="jvms-4.html#jvms-4.7.13" title="4.7.13.&nbsp;The LocalVariableTable Attribute">§4.7.13</a></td>
                           <td>1.0.2</td>
                           <td>45.3</td>
                        </tr>
                        <tr>
                           <td><code class="literal">LocalVariableTypeTable</code></td>
                           <td><a class="xref" href="jvms-4.html#jvms-4.7.14" title="4.7.14.&nbsp;The LocalVariableTypeTable Attribute">§4.7.14</a></td>
                           <td>5.0</td>
                           <td>49.0</td>
                        </tr>
                        <tr>
                           <td><code class="literal">Deprecated</code></td>
                           <td><a class="xref" href="jvms-4.html#jvms-4.7.15" title="4.7.15.&nbsp;The Deprecated Attribute">§4.7.15</a></td>
                           <td>1.1</td>
                           <td>45.3</td>
                        </tr>
                        <tr>
                           <td><code class="literal">RuntimeVisibleAnnotations</code></td>
                           <td><a class="xref" href="jvms-4.html#jvms-4.7.16" title="4.7.16.&nbsp;The RuntimeVisibleAnnotations attribute">§4.7.16</a></td>
                           <td>5.0</td>
                           <td>49.0</td>
                        </tr>
                        <tr>
                           <td><code class="literal">RuntimeInvisibleAnnotations</code></td>
                           <td><a class="xref" href="jvms-4.html#jvms-4.7.17" title="4.7.17.&nbsp;The RuntimeInvisibleAnnotations attribute">§4.7.17</a></td>
                           <td>5.0</td>
                           <td>49.0</td>
                        </tr>
                        <tr>
                           <td><code class="literal">RuntimeVisibleParameterAnnotations</code></td>
                           <td><a class="xref" href="jvms-4.html#jvms-4.7.18" title="4.7.18.&nbsp;The RuntimeVisibleParameterAnnotations attribute">§4.7.18</a></td>
                           <td>5.0</td>
                           <td>49.0</td>
                        </tr>
                        <tr>
                           <td><code class="literal">RuntimeInvisibleParameterAnnotations</code></td>
                           <td><a class="xref" href="jvms-4.html#jvms-4.7.19" title="4.7.19.&nbsp;The RuntimeInvisibleParameterAnnotations attribute">§4.7.19</a></td>
                           <td>5.0</td>
                           <td>49.0</td>
                        </tr>
                        <tr>
                           <td><code class="literal">AnnotationDefault</code></td>
                           <td><a class="xref" href="jvms-4.html#jvms-4.7.20" title="4.7.20.&nbsp;The AnnotationDefault attribute">§4.7.20</a></td>
                           <td>5.0</td>
                           <td>49.0</td>
                        </tr>
                        <tr>
                           <td><code class="literal">BootstrapMethods</code></td>
                           <td><a class="xref" href="jvms-4.html#jvms-4.7.21" title="4.7.21.&nbsp;The BootstrapMethods attribute">§4.7.21</a></td>
                           <td>7</td>
                           <td>51.0</td>
                        </tr>
                     </tbody>
                  </table>
                  
 * 
 * @author nick
 *
 */
public class AttributesMapper {

	private static final Map<String, Class<? extends attribute_info_struct>> structTypes;
	static {
		structTypes = new HashMap<String, Class<? extends attribute_info_struct>>();
		
		structTypes.put("Signature", Signature_attribute.class);
		
	}
	
	public static attribute_info_struct newAttributeInfo(String attributeName) {
		Class<? extends attribute_info_struct> type = structTypes.get(attributeName);
		if (type == null)
			return null;
		try {
			return type.newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	
}
