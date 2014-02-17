package org.mesosys.junit.mesoassert;

import static org.mesosys.junit.mesoassert.CollectionAssert.*;
import org.junit.Test;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Purpose: ${PURPOSE}
 * User: Peter Cameron
 * Date: 29-Jul-2008
 * Time: 20:42:22
 */
public class CollectionAssertTest {
  public Collection<String> EMPTY = new ArrayList<String>();
  public Collection<String> SIZE1 = Arrays.asList("1");
  public Collection<String> SIZE2 = Arrays.asList("1", "2");

  @Test
  public void happyAssertEmpty() {
    assertEmpty(EMPTY);
    assertThat
            ()
  }

  @Test(expected = AssertionError.class)
  public void failAssertEmpty() {
    assertEmpty(SIZE1);
  }

  @Test
  public void happyAssertNullOrEmpty() {
    assertNullOrEmpty(null);
    assertNullOrEmpty(EMPTY);
  }

  @Test(expected = AssertionError.class)
  public void failAssertNullOrEmpty() {
    assertNullOrEmpty(SIZE1);
  }

  @Test
  public void happyAssertNotEmpty() {
    assertNotEmpty(SIZE1);
    assertNotEmpty(SIZE2);
  }

  @Test(expected = AssertionError.class)
  public void failAssertNotEmpty1() {
    assertNotEmpty(null);
  }

  @Test(expected = AssertionError.class)
  public void failAssertNotEmpty2() {
    assertNotEmpty(EMPTY);
  }

  @Test
  public void happyAssertSize() {
    assertSize(1, SIZE1);
    assertSize(2, SIZE2);
  }

  @Test(expected = AssertionError.class)
  public void failAssertSize1() {
    assertSize(1, null);
  }

  @Test(expected = AssertionError.class)
  public void failAssertSize2() {
    assertSize(1, EMPTY);
  }

  @Test(expected = AssertionError.class)
  public void failAssertSize3() {
    assertSize(1, SIZE2);
  }

  public void happyAssertAll() {
    assertAll();
  }
}
