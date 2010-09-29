package net.anotheria.net.util;

import org.junit.Test;
import static org.junit.Assert.*;

public class NetUtilsTest {
	@Test public void test(){
		assertNotNull(NetUtils.getComputerName());
		assertNotNull(NetUtils.getShortComputerName());
	}
}
 