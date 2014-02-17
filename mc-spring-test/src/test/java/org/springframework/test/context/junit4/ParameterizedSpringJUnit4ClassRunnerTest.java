package org.springframework.test.context.junit4;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.Test;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.ParameterizedSpringJUnit4ClassRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Collection;
import java.util.Arrays;
import java.util.ArrayList;

/**
 * Purpose: ${PURPOSE}
 * User: Peter Cameron
 * Date: 29-Jul-2008
 * Time: 21:38:54
 */
@RunWith(ParameterizedSpringJUnit4ClassRunner.class)
//@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testConfig.xml"})
public class ParameterizedSpringJUnit4ClassRunnerTest {

  private static final Object[][] ALL_PARAMETERS = {{"a", 1}, {"b",2}, {"c",3}};

  private static Collection<Object[]> allParameters = new ArrayList<Object[]>(Arrays.asList(ALL_PARAMETERS));

  private final String parameter1;
  private final int parameter2;

  @Autowired
  @Qualifier(value = "injectedArg1")
  private final String injectedArg1 = null;

  private String injectedArg2;

  public ParameterizedSpringJUnit4ClassRunnerTest(String parameter1, int parameter2) {
    this.parameter1 = parameter1;
    this.parameter2 = parameter2;
  }

  //public ParameterizedSpringJUnit4ClassRunnerTest() {
  //  parameter1 = null; parameter2 = 0;
  //}

  @Parameterized.Parameters
  public static Collection<Object[]> getClassParameters() {
    return Arrays.asList(ALL_PARAMETERS);
  }

  @Test
  public void testParameter1() {
    assertNotNull(parameter1);
  }

  @Test
  public void testParameter2() {
    assertNotSame(parameter2, 0);
  }

  @Test
  public void testInjectedArg1() {
    assertNotNull(injectedArg1);
  }

  @Test
  public void testInjectedArg2() {
    assertNotNull(injectedArg2);
  }

  @Test
  public void testAllParametersUsedOnce() {
    for(Object[] parameters: allParameters) {
      if(parameters[0].equals(parameter1) && parameters[1].equals(parameter2)) {
        allParameters.remove(parameters);
        return;
      }
    }
    fail("Unable to locate parameters - possibly used more than once.");
  }

  @Autowired
  public void setInjectedArg2(@Qualifier(value = "injectedArg2")String injectedArg2) {
    this.injectedArg2 = injectedArg2;
  }
}
