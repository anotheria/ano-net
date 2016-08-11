package net.anotheria.net;


import net.anotheria.net.util.ByteArraySerializerTest;
import net.anotheria.net.util.NetUtilsTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(value=Suite.class)
@SuiteClasses(value={ByteArraySerializerTest.class, NetUtilsTest.class})
public class AnoNetSuite {

}
