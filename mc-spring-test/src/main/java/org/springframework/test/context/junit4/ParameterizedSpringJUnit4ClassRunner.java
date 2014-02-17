package org.springframework.test.context.junit4;

import org.junit.internal.runners.*;
import org.junit.runners.Parameterized;
import org.junit.runner.notification.RunNotifier;
import org.junit.Assert;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Collection;
import java.util.ArrayList;

/**
 * Purpose: This Test runner serves as a composite of two existing runners - Parameterized & SpringJunit4ClassRunner.<br>
 * <br>
 * It supports both spring injection and parameterized constructors.
 *
 * User: Peter Cameron
 * Company: Mesosys Ltd
 * Email: peter.cameron@mesosys.co.uk
 * Date: 29-Jul-2008
 * Time: 21:35:01
 */
public class ParameterizedSpringJUnit4ClassRunner extends CompositeRunner {

  private final TestClass fTestClass;

  public ParameterizedSpringJUnit4ClassRunner(Class<?> klass) throws Exception {
    super(klass.getName());
    fTestClass= new TestClass(klass);

    MethodValidator methodValidator= new MethodValidator(fTestClass);
    methodValidator.validateStaticMethods();
    methodValidator.validateInstanceMethods();
    methodValidator.assertValid();

    int i= 0;
    for (final Object each : getParametersList()) {
      if (each instanceof Object[])
        add(new TestClassRunnerForParameters(fTestClass, (Object[])each, i++));
      else
        throw new Exception(String.format("%s.%s() must return a Collection of arrays.", fTestClass.getName(), getParametersMethod().getName()));
    }
  }

  @Override
  public void run(final RunNotifier notifier) {
    new ClassRoadie(notifier, fTestClass, getDescription(), new Runnable() {
      public void run() {
        runChildren(notifier);
      }
    }).runProtected();
  }

  private Collection<?> getParametersList() throws IllegalAccessException, InvocationTargetException, Exception {
    return (Collection<?>) getParametersMethod().invoke(null);
  }

  private Method getParametersMethod() throws Exception {
    List<Method> methods= fTestClass.getAnnotatedMethods(Parameterized.Parameters.class);
    for (Method each : methods) {
      int modifiers= each.getModifiers();
      if (Modifier.isStatic(modifiers) && Modifier.isPublic(modifiers))
        return each;
    }

    throw new Exception("No public static parameters method on class " + getName());
  }

  public static Collection<Object[]> eachOne(Object... params) {
    List<Object[]> results= new ArrayList<Object[]>();
    for (Object param : params)
      results.add(new Object[] { param });
    return results;
  }

  private class TestClassRunnerForParameters extends SpringJUnit4ClassRunner {
    private final Object[] parameters;
    private final int fParameterSetNumber;

    public TestClassRunnerForParameters(TestClass fTestClass, Object[] parameters, int fParameterSetNumber) throws InitializationError {
      super(fTestClass.getJavaClass());
      this.parameters = parameters;
      this.fParameterSetNumber = fParameterSetNumber;
    }

    @Override
    protected void validate() throws InitializationError {
      // dont do validation of superclass.
    }
    
    @Override
    protected Object createTest() throws Exception {
      Object test = getOnlyConstructor().newInstance(parameters);
      getTestContextManager().prepareTestInstance(test);
      return test;
    }

    @Override
    protected String getName() {
      return String.format("[%s]", fParameterSetNumber);
    }

    @Override
    protected String testName(final Method method) {
      return String.format("%s[%s]", method.getName(), fParameterSetNumber);
    }

    private Constructor<?> getOnlyConstructor() {
      Constructor<?>[] constructors= getTestClass().getJavaClass().getConstructors();
      Assert.assertEquals(1, constructors.length);
      return constructors[0];
    }
  }
}
