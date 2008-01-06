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
public class ByteArraySerializer {
	
	public static byte[] serializeObject(Serializable obj) throws IOException{
		ByteArrayOutputStream bOut = null;
		ObjectOutputStream oOut = null;
		try{
			bOut = new ByteArrayOutputStream();
			oOut = new ObjectOutputStream(bOut);
			oOut.writeObject(obj);
			oOut.close();
			return bOut.toByteArray();
		}catch(IOException e){
			if (oOut!=null)
				try{
					oOut.close();
				}catch(Exception ignored){}
			throw e;
		}
	}
	
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
}
