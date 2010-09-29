package net.anotheria.net.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * This utility serializes a class to a byte array.
 * @author lrosenberg
 * Created on 09.09.2004
 */
public final class ByteArraySerializer {
	
	/**
	 * Serializes a serializeable object into a byte array.
	 * @param obj the object to serialize
	 * @return
	 * @throws IOException
	 */
	public static byte[] serializeObject(Serializable obj) throws IOException{
		ByteArrayOutputStream bOut = null;
		ObjectOutputStream oOut = null;
		try{
			bOut = new ByteArrayOutputStream();
			oOut = new ObjectOutputStream(bOut);
			oOut.writeObject(obj);
			return bOut.toByteArray();
		}finally{ 
			if (oOut!=null)
				try{
					oOut.close();
				}catch(Exception ignored){/*here is nothing that we can do*/}
		}
	}
	
	/**
	 * Desirializes a previously serialized from a byte array. The object has to be of serializeable class.
	 * @param array the byte array with the contents of the object.
	 */
	public static Object deserializeObject(byte[] array) throws IOException{
		ByteArrayInputStream bIn = null;
		ObjectInputStream oIn = null;
		try{
			bIn = new ByteArrayInputStream(array);
			oIn = new ObjectInputStream(bIn);
			return oIn.readObject();
		}catch(ClassNotFoundException e){
			throw new IOException("deserialization failed: "+e.getMessage());
		}finally{
			if (oIn!=null){
				try{
					oIn.close();
				}catch(Exception ignored){}
			}
		}
	}
	
	private ByteArraySerializer(){
		//prevent initialization
	}
}
