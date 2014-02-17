package org.mesosys.junit.mesoassert.assertion;

import static org.mesosys.junit.mesoassert.StringAssert.*;

/**
 * Purpose: ${PURPOSE}
 * User: Peter Cameron
 * Date: 29-Jul-2008
 * Time: 20:56:26
 */
public class NotEmptyStringAssertion implements StringAssertion {
  public void doAssert(String actual) {
    assertNotEmpty(actual);
  }
}
