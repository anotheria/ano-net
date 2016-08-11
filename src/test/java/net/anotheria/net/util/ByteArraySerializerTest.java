package net.anotheria.net.util;

import java.io.IOException;

import org.junit.Test;
import static org.junit.Assert.*;

public class ByteArraySerializerTest {
	//TODO add tests for proper reaction on null!
	
	
	@Test public void serializeAndDeserialize() throws IOException{
		String foo = new String("foo");
		byte[] data = ByteArraySerializer.serializeObject(foo);
		String anotherFoo = (String)ByteArraySerializer.deserializeObject(data);
		assertNotSame(foo,anotherFoo);
		assertEquals(foo, anotherFoo);
	}
}
