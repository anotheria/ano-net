package net.anotheria.net.util;

import java.net.InetAddress;

import org.apache.log4j.Logger;

/**
 * Utilities for the network layer.
 * @author lrosenberg.
 *
 */
public class NetUtils {
    private static Logger log = Logger.getLogger(NetUtils.class);

    public static final String getComputerName(){
        try{
            InetAddress address = InetAddress.getLocalHost();
            return address.getHostName();
        }catch(Exception e){
            log.warn("getComputerName",e);
        }
        return null;
    }
    
    public static final String getShortComputerName(){
    	String fullName = getComputerName();
    	if (fullName==null)
    		return fullName;
    	int dotIndex = fullName.indexOf('.');
    	return dotIndex == -1 ? fullName : fullName.substring(0, dotIndex);
    }
}
