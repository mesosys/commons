package com.mesosys.commons.transform;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Arrays;

/**
 * Created by Mesosys Ltd.
 * <p/>
 * User: Peter Cameron
 * Date: 20-Nov-2008
 * Time: 22:15:34
 */
@RunWith(JUnit4ClassRunner.class)
public class StringToIntegerListTest {
  private ListTransformer<String, Integer> transformer = null;

  @Before
  public void setupRepository() {
    transformer = new ListTransformer<String, Integer>(new StringToInteger(new TransformerRepository()));
  }

  @Test
  public void testStringToInteger() {
    List<String> as = Arrays.asList("1", "2", "3");
    List<Integer> bs = Arrays.asList(1, 2, 3);
    assertEquals(bs, transformer.transform(as));
  }

  @Test
  public void testIntegerToString() {
    List<String> as = Arrays.asList("1", "2", "3");
    List<Integer> bs = Arrays.asList(1, 2, 3);
    assertEquals(as, transformer.reverseTransform(bs));
  }


}