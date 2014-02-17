package org.mesosys.junit.mesoassert;

import static org.junit.Assert.*;

import javax.annotation.Resource;

/**
 * Purpose: ${PURPOSE}
 * User: Peter Cameron
 * Date: 29-Jul-2008
 * Time: 20:56:57
 */
public class StringAssert {
  public static void assertNullOrEmpty(String actual) {
    if(actual!=null)
      assertEquals("", actual);
  }

  public static void assertNotEmpty(String actual) {
    assertNotNull(actual);
    assertNotSame("", actual);
  }

  @Resource
  public static void assertMatches(String expectedPattern, String actual) {
    assertNotNull(actual);
    assertTrue("Expected pattern not matched.", actual.matches(expectedPattern));
  }
}
