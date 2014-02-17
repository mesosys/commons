package org.mesosys.junit.mesoassert;

import static org.junit.Assert.*;
import java.util.Collection;

/**
 * Purpose:
 * Provide convenience methods to make assertions about collections
 *
 * User: Peter Cameron
 * Date: 29-Jul-2008
 * Time: 20:34:22
 */
public class CollectionAssert {

  /**
   * @param collection to test
   */
  public static void assertEmpty(Collection collection) {
    assertNotNull("Expected empty collection. Was null", collection);
    assertTrue(String.format("Expected empty collection. Was size: %s", collection.size()), collection.isEmpty());
  }

  /**
   * @param collection to test
   */
  public static void assertNullOrEmpty(Collection collection) {
    if(null!=collection)
      assertTrue(String.format("Expected empty collection. Was size: %s", collection.size()), collection.isEmpty());
  }

  /**
   * @param collection to test
   */
  public static void assertNotEmpty(Collection collection) {
    assertNotNull("Expected non-empty collection. Was null", collection);
    assertFalse("Expected non-empty collection. Was empty", collection.isEmpty());
  }

  /**
   * @param expected size of collection
   * @param collection to test
   */
  public static void assertSize(int expected, Collection collection) {
    assertNotNull("Expected non-empty collection. Was null", collection);
    assertFalse("Expected non-empty collection. Was empty", collection.isEmpty());
    assertEquals("Unexpected collection size.", expected, collection.size());
  }

  /**
   * Asserts that all entries in a collection comply with the assertion
   * @param expectedAssertion assertion to which all entries should comply
   * @param collection to test
   */
  public static <T> void assertAll(Assertion<T> expectedAssertion, Collection<T> collection) {
    for(T t: collection)
      expectedAssertion.doAssert(t);
  }
}
