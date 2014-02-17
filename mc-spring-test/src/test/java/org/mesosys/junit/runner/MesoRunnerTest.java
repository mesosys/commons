package org.mesosys.junit.runner;

import org.junit.runner.RunWith;
import org.junit.Test;
import org.junit.runners.Parameterized;
import static org.junit.Assert.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.ParameterizedSpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.ArrayList;

/**
 * Purpose: ${PURPOSE}
 * User: Peter Cameron
 * Date: 29-Jul-2008
 * Time: 21:33:57
 */
@RunWith(ParameterizedSpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/testConfig.xml")
public class MesoRunnerTest {

  private static final ArrayList<String[]> expectedContexts = new ArrayList<String[]>(Arrays.asList(
          new String[]{"1"}, new String[]{"2"}, new String[]{"3"}));

  private final String context;

  public MesoRunnerTest(String context) {
    this.context = context;
  }

  @Parameterized.Parameters
  public static ArrayList<String[]> getExpectedContexts() {
    return expectedContexts;
  }

  @Test
  public void test() {
    assertNotNull(context);
    expectedContexts.remove(context);
  }
}
