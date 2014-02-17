package com.mesosys.commons.transform;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;

/**
 * Created by Mesosys Ltd.
 * <p/>
 * User: Peter Cameron
 * Date: 20-Nov-2008
 * Time: 22:15:34
 */
@RunWith(JUnit4ClassRunner.class)
public class StringToIntegerTest {
  private Transformer<String, Integer> transformer = null;

  @Before
  public void setupRepository() {
    transformer = new StringToInteger(new TransformerRepository());
  }

  @Test
  public void testStringToInteger() {
    assertEquals(new Integer(1), transformer.transform("1"));
  }

  @Test
  public void testIntegerToString() {
    assertEquals("1", transformer.reverseTransform(1));
  }


}
