/***************************************************************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations under the License.
 ***************************************************************************************************************************/
package org.apache.juneau.xml;

import static org.apache.juneau.TestUtils.*;
import static org.apache.juneau.serializer.SerializerContext.*;
import static org.apache.juneau.xml.XmlSerializerContext.*;
import static org.apache.juneau.xml.annotation.XmlFormat.*;
import static org.junit.Assert.*;

import java.net.*;
import java.net.URI;
import java.util.*;

import org.apache.juneau.*;
import org.apache.juneau.annotation.*;
import org.apache.juneau.jena.annotation.*;
import org.apache.juneau.serializer.*;
import org.apache.juneau.testbeans.*;
import org.apache.juneau.xml.annotation.*;
import org.junit.*;

@SuppressWarnings({"serial","javadoc"})
public class CommonTest {

	//====================================================================================================
	// Trim nulls from beans
	//====================================================================================================
	@Test
	public void testTrimNullsFromBeans() throws Exception {
		XmlSerializer s = new XmlSerializer.SimpleSq();
		XmlParser p = new XmlParser();
		A t1 = A.create(), t2;

		s.setProperty(SERIALIZER_trimNullProperties, false);
		String r = s.serialize(t1);
		assertEquals("<object><s1 nil='true'/><s2>s2</s2></object>", r);
		t2 = p.parse(r, A.class);
		assertEqualObjects(t1, t2);

		s.setProperty(SERIALIZER_trimNullProperties, true);
		r = s.serialize(t1);
		assertEquals("<object><s2>s2</s2></object>", r);
		t2 = p.parse(r, A.class);
		assertEqualObjects(t1, t2);
	}

	public static class A {
		public String s1, s2;

		public static A create() {
			A t = new A();
			t.s2 = "s2";
			return t;
		}
	}

	//====================================================================================================
	// Trim empty maps
	//====================================================================================================
	@Test
	public void testTrimEmptyMaps() throws Exception {
		XmlSerializer s = new XmlSerializer.SimpleSq();
		XmlParser p = XmlParser.DEFAULT;
		B t1 = B.create(), t2;
		String r;

		s.setProperty(SERIALIZER_trimEmptyMaps, false);
		r = s.serialize(t1);
		assertEquals("<object><f1/><f2><f2a nil='true'/><f2b><s2>s2</s2></f2b></f2></object>", r);
		t2 = p.parse(r, B.class);
		assertEqualObjects(t1, t2);

		s.setProperty(SERIALIZER_trimEmptyMaps, true);
		r = s.serialize(t1);
		assertEquals("<object><f2><f2a nil='true'/><f2b><s2>s2</s2></f2b></f2></object>", r);
		t2 = p.parse(r, B.class);
		assertNull(t2.f1);
	}

	public static class B {
		public TreeMap<String,A> f1, f2;

		public static B create() {
			B t = new B();
			t.f1 = new TreeMap<String,A>();
			t.f2 = new TreeMap<String,A>(){{put("f2a",null);put("f2b",A.create());}};
			return t;
		}
	}

	//====================================================================================================
	// Trim empty lists
	//====================================================================================================
	@Test
	public void testTrimEmptyLists() throws Exception {
		XmlSerializer s = new XmlSerializer.SimpleSq();
		XmlParser p = XmlParser.DEFAULT;
		C t1 = C.create(), t2;
		String r;

		s.setProperty(SERIALIZER_trimEmptyLists, false);
		r = s.serialize(t1);
		assertEquals("<object><f1></f1><f2><null/><object><s2>s2</s2></object></f2></object>", r);
		t2 = p.parse(r, C.class);
		assertEqualObjects(t1, t2);

		s.setProperty(SERIALIZER_trimEmptyLists, true);
		r = s.serialize(t1);
		assertEquals("<object><f2><null/><object><s2>s2</s2></object></f2></object>", r);
		t2 = p.parse(r, C.class);
		assertNull(t2.f1);
	}

	public static class C {
		public List<A> f1, f2;

		public static C create() {
			C t = new C();
			t.f1 = new LinkedList<A>();
			t.f2 = new LinkedList<A>(){{add(null);add(A.create());}};
			return t;
		}
	}

	//====================================================================================================
	// Trim empty arrays
	//====================================================================================================
	@Test
	public void testTrimEmptyArrays() throws Exception {
		XmlSerializer s = new XmlSerializer.SimpleSq();
		XmlParser p = XmlParser.DEFAULT;
		D t1 = D.create(), t2;
		String r;

		s.setProperty(SERIALIZER_trimEmptyLists, false);
		r = s.serialize(t1);
		assertEquals("<object><f1></f1><f2><null/><object><s2>s2</s2></object></f2></object>", r);
		t2 = p.parse(r, D.class);
		assertEqualObjects(t1, t2);

		s.setProperty(SERIALIZER_trimEmptyLists, true);
		r = s.serialize(t1);
		assertEquals("<object><f2><null/><object><s2>s2</s2></object></f2></object>", r);
		t2 = p.parse(r, D.class);
		assertNull(t2.f1);
	}

	public static class D {
		public A[] f1, f2;

		public static D create() {
			D t = new D();
			t.f1 = new A[]{};
			t.f2 = new A[]{null, A.create()};
			return t;
		}
	}

	//====================================================================================================
	// @BeanProperty.properties annotation.
	//====================================================================================================
	@Test
	public void testBeanPropertyProperties() throws Exception {
		XmlSerializer s = XmlSerializer.DEFAULT_SIMPLE_SQ;
		E1 t = new E1();
		String r = s.serialize(t);
		assertEquals("<object><x1 f2='2'><f1>1</f1></x1><x2><f1>1</f1></x2><x3><object f2='2'><f1>1</f1></object></x3><x4><object f2='2'><f1>1</f1></object></x4><x5><object><f1>1</f1></object></x5><x6><object><f1>1</f1></object></x6></object>", r);
		TestUtils.validateXml(t);
	}

	public static class E1 {
		@BeanProperty(properties={"f1","f2"}) public E2 x1 = new E2();
		@BeanProperty(properties={"f1","f2"}) public Map<String,Integer> x2 = new LinkedHashMap<String,Integer>() {{
			put("f1",1); put("f3",3);
		}};
		@BeanProperty(properties={"f1","f2"}) public E2[] x3 = {new E2()};
		@BeanProperty(properties={"f1","f2"}) public List<E2> x4 = new LinkedList<E2>() {{
			add(new E2());
		}};
		@BeanProperty(properties={"f1"}) public ObjectMap[] x5 = {new ObjectMap().append("f1",1).append("f3",3)};
		@BeanProperty(properties={"f1"}) public List<ObjectMap> x6 = new LinkedList<ObjectMap>() {{
			add(new ObjectMap().append("f1",1).append("f3",3));
		}};
	}

	public static class E2 {
		public int f1 = 1;
		@Xml(format=ATTR) public int f2 = 2;
		public int f3 = 3;
		@Xml(format=ATTR) public int f4 = 4;
	}

	//====================================================================================================
	// @BeanProperty.properties annotation on list of beans.
	//====================================================================================================
	@Test
	public void testBeanPropertyPropertiesOnListOfBeans() throws Exception {
		XmlSerializer s = XmlSerializer.DEFAULT_SIMPLE_SQ;
		List<Test7b> l = new LinkedList<Test7b>();
		Test7b t = new Test7b();
		t.x1.add(new Test7b());
		l.add(t);
		String xml = s.serialize(l);
		assertEquals("<array><object><x1><object><x2>2</x2></object></x1><x2>2</x2></object></array>", xml);
	}

	public static class Test7b {
		@BeanProperty(properties={"x2"}) public List<Test7b> x1 = new LinkedList<Test7b>();
		public int x2 = 2;
	}

	//====================================================================================================
	// Test that URLs and URIs are serialized and parsed correctly.
	//====================================================================================================
	@Test
	public void testURIAttr() throws Exception {
		XmlSerializer s = XmlSerializer.DEFAULT_SIMPLE_SQ;
		XmlParser p = XmlParser.DEFAULT;

		G t = new G();
		t.uri = new URI("http://uri");
		t.f1 = new URI("http://f1");
		t.f2 = new URL("http://f2");

		String xml = s.serialize(t);
		t = p.parse(xml, G.class);
		assertEquals("http://uri", t.uri.toString());
		assertEquals("http://f1", t.f1.toString());
		assertEquals("http://f2", t.f2.toString());
	}

	public static class G {
		@Rdf(beanUri=true) public URI uri;
		public URI f1;
		public URL f2;
	}

	//====================================================================================================
	// Test URIs with URI_CONTEXT and URI_AUTHORITY
	//====================================================================================================
	@Test
	public void testUris() throws Exception {
		WriterSerializer s = new XmlSerializer.SimpleSq();
		TestURI t = new TestURI();
		String r;
		String expected;

		s.setProperty(SERIALIZER_relativeUriBase, null);
		r = s.serialize(t);
		expected = ""
			+"<object f0='f0/x0'>"
			+"<f1>f1/x1</f1>"
			+"<f2>/f2/x2</f2>"
			+"<f3>http://www.ibm.com/f3/x3</f3>"
			+"<f4>f4/x4</f4>"
			+"<f5>/f5/x5</f5>"
			+"<f6>http://www.ibm.com/f6/x6</f6>"
			+"<f7>http://www.ibm.com/f7/x7</f7>"
			+"<f8>f8/x8</f8>"
			+"<f9>f9/x9</f9>"
			+"<fa>http://www.ibm.com/fa/xa#MY_LABEL</fa>"
			+"<fb>http://www.ibm.com/fb/xb?label=MY_LABEL&amp;foo=bar</fb>"
			+"<fc>http://www.ibm.com/fc/xc?foo=bar&amp;label=MY_LABEL</fc>"
			+"<fd>http://www.ibm.com/fd/xd?label2=MY_LABEL&amp;foo=bar</fd>"
			+"<fe>http://www.ibm.com/fe/xe?foo=bar&amp;label2=MY_LABEL</fe>"
			+"</object>";
		assertEquals(expected, r);

		s.setProperty(SERIALIZER_relativeUriBase, "");  // Same as null.
		r = s.serialize(t);
		assertEquals(expected, r);

		s.setProperty(SERIALIZER_relativeUriBase, "/cr");
		r = s.serialize(t);
		expected = ""
			+"<object f0='/cr/f0/x0'>"
			+"<f1>/cr/f1/x1</f1>"
			+"<f2>/f2/x2</f2>"
			+"<f3>http://www.ibm.com/f3/x3</f3>"
			+"<f4>/cr/f4/x4</f4>"
			+"<f5>/f5/x5</f5>"
			+"<f6>http://www.ibm.com/f6/x6</f6>"
			+"<f7>http://www.ibm.com/f7/x7</f7>"
			+"<f8>/cr/f8/x8</f8>"
			+"<f9>/cr/f9/x9</f9>"
			+"<fa>http://www.ibm.com/fa/xa#MY_LABEL</fa>"
			+"<fb>http://www.ibm.com/fb/xb?label=MY_LABEL&amp;foo=bar</fb>"
			+"<fc>http://www.ibm.com/fc/xc?foo=bar&amp;label=MY_LABEL</fc>"
			+"<fd>http://www.ibm.com/fd/xd?label2=MY_LABEL&amp;foo=bar</fd>"
			+"<fe>http://www.ibm.com/fe/xe?foo=bar&amp;label2=MY_LABEL</fe>"
			+"</object>";
		assertEquals(expected, r);

		s.setProperty(SERIALIZER_relativeUriBase, "/cr/");  // Same as above
		r = s.serialize(t);
		assertEquals(expected, r);

		s.setProperty(SERIALIZER_relativeUriBase, "/");
		r = s.serialize(t);
		expected = ""
			+"<object f0='/f0/x0'>"
			+"<f1>/f1/x1</f1>"
			+"<f2>/f2/x2</f2>"
			+"<f3>http://www.ibm.com/f3/x3</f3>"
			+"<f4>/f4/x4</f4>"
			+"<f5>/f5/x5</f5>"
			+"<f6>http://www.ibm.com/f6/x6</f6>"
			+"<f7>http://www.ibm.com/f7/x7</f7>"
			+"<f8>/f8/x8</f8>"
			+"<f9>/f9/x9</f9>"
			+"<fa>http://www.ibm.com/fa/xa#MY_LABEL</fa>"
			+"<fb>http://www.ibm.com/fb/xb?label=MY_LABEL&amp;foo=bar</fb>"
			+"<fc>http://www.ibm.com/fc/xc?foo=bar&amp;label=MY_LABEL</fc>"
			+"<fd>http://www.ibm.com/fd/xd?label2=MY_LABEL&amp;foo=bar</fd>"
			+"<fe>http://www.ibm.com/fe/xe?foo=bar&amp;label2=MY_LABEL</fe>"
			+"</object>";
		assertEquals(expected, r);

		s.setProperty(SERIALIZER_relativeUriBase, null);

		s.setProperty(SERIALIZER_absolutePathUriBase, "http://foo");
		r = s.serialize(t);
		expected = ""
			+"<object f0='f0/x0'>"
			+"<f1>f1/x1</f1>"
			+"<f2>http://foo/f2/x2</f2>"
			+"<f3>http://www.ibm.com/f3/x3</f3>"
			+"<f4>f4/x4</f4>"
			+"<f5>http://foo/f5/x5</f5>"
			+"<f6>http://www.ibm.com/f6/x6</f6>"
			+"<f7>http://www.ibm.com/f7/x7</f7>"
			+"<f8>f8/x8</f8>"
			+"<f9>f9/x9</f9>"
			+"<fa>http://www.ibm.com/fa/xa#MY_LABEL</fa>"
			+"<fb>http://www.ibm.com/fb/xb?label=MY_LABEL&amp;foo=bar</fb>"
			+"<fc>http://www.ibm.com/fc/xc?foo=bar&amp;label=MY_LABEL</fc>"
			+"<fd>http://www.ibm.com/fd/xd?label2=MY_LABEL&amp;foo=bar</fd>"
			+"<fe>http://www.ibm.com/fe/xe?foo=bar&amp;label2=MY_LABEL</fe>"
			+"</object>";
		assertEquals(expected, r);

		s.setProperty(SERIALIZER_absolutePathUriBase, "http://foo/");
		r = s.serialize(t);
		assertEquals(expected, r);

		s.setProperty(SERIALIZER_absolutePathUriBase, "");  // Same as null.
		r = s.serialize(t);
		expected = ""
			+"<object f0='f0/x0'>"
			+"<f1>f1/x1</f1>"
			+"<f2>/f2/x2</f2>"
			+"<f3>http://www.ibm.com/f3/x3</f3>"
			+"<f4>f4/x4</f4>"
			+"<f5>/f5/x5</f5>"
			+"<f6>http://www.ibm.com/f6/x6</f6>"
			+"<f7>http://www.ibm.com/f7/x7</f7>"
			+"<f8>f8/x8</f8>"
			+"<f9>f9/x9</f9>"
			+"<fa>http://www.ibm.com/fa/xa#MY_LABEL</fa>"
			+"<fb>http://www.ibm.com/fb/xb?label=MY_LABEL&amp;foo=bar</fb>"
			+"<fc>http://www.ibm.com/fc/xc?foo=bar&amp;label=MY_LABEL</fc>"
			+"<fd>http://www.ibm.com/fd/xd?label2=MY_LABEL&amp;foo=bar</fd>"
			+"<fe>http://www.ibm.com/fe/xe?foo=bar&amp;label2=MY_LABEL</fe>"
			+"</object>";
		assertEquals(expected, r);
	}

	//====================================================================================================
	// Validate that you cannot update properties on locked serializer.
	//====================================================================================================
	@Test
	public void testLockedSerializer() throws Exception {
		XmlSerializer s = new XmlSerializer().lock();
		try {
			s.setProperty(XmlSerializerContext.XML_enableNamespaces, true);
			fail("Locked exception not thrown");
		} catch (LockedException e) {}
		try {
			s.setProperty(SerializerContext.SERIALIZER_addClassAttrs, true);
			fail("Locked exception not thrown");
		} catch (LockedException e) {}
		try {
			s.setProperty(BeanContext.BEAN_beanMapPutReturnsOldValue, true);
			fail("Locked exception not thrown");
		} catch (LockedException e) {}
	}

	//====================================================================================================
	// Recursion
	//====================================================================================================
	@Test
	public void testRecursion() throws Exception {
		XmlSerializer s = new XmlSerializer().setProperty(XML_enableNamespaces, false);

		R1 r1 = new R1();
		R2 r2 = new R2();
		R3 r3 = new R3();
		r1.r2 = r2;
		r2.r3 = r3;
		r3.r1 = r1;

		// No recursion detection
		try {
			s.serialize(r1);
			fail("Exception expected!");
		} catch (Exception e) {
			String msg = e.getLocalizedMessage();
			assertTrue(msg.contains("It's recommended you use the SerializerContext.SERIALIZER_detectRecursions setting to help locate the loop."));
		}

		// Recursion detection, no ignore
		s.setProperty(SERIALIZER_detectRecursions, true);
		try {
			s.serialize(r1);
			fail("Exception expected!");
		} catch (Exception e) {
			String msg = e.getLocalizedMessage();
			assertTrue(msg.contains("[0]<noname>:org.apache.juneau.xml.CommonTest$R1"));
			assertTrue(msg.contains("->[1]r2:org.apache.juneau.xml.CommonTest$R2"));
			assertTrue(msg.contains("->[2]r3:org.apache.juneau.xml.CommonTest$R3"));
			assertTrue(msg.contains("->[3]r1:org.apache.juneau.xml.CommonTest$R1"));
		}

		s.setProperty(SERIALIZER_ignoreRecursions, true);
		assertEquals("<object><name>foo</name><r2><name>bar</name><r3><name>baz</name></r3></r2></object>", s.serialize(r1));

		// Make sure this doesn't blow up.
		s.getSchemaSerializer().serialize(r1);
	}

	public static class R1 {
		public String name = "foo";
		public R2 r2;
	}
	public static class R2 {
		public String name = "bar";
		public R3 r3;
	}
	public static class R3 {
		public String name = "baz";
		public R1 r1;
	}
}
