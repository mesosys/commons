package com.mesosys.commons.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;

/**
 * User: Peter Cameron
 * Date: 28-Feb-2007
 * Time: 23:28:34
 */
public class DurationInterceptor implements MethodInterceptor {
  private static final Logger LOG = Logger.getLogger(DurationInterceptor.class);

  public Object invoke(MethodInvocation methodInvocation) throws Throwable {
    final String identifier = getMethodIdentifier(methodInvocation);
    Throwable thrown=null;
    final long startTime = System.currentTimeMillis();
    LOG.info(identifier+":started.");
    try {
      return methodInvocation.proceed();
    }
    catch(Throwable t) {
      throw (thrown = t);
    }
    finally {
      long duration = System.currentTimeMillis() - startTime;
      String message = new StringBuffer(identifier)
              .append(":finished (took ")
              .append(duration)
              .append(").")
              .toString();

      if(thrown!=null || duration>1000)
        LOG.warn(message, thrown);
      else
        LOG.info(message);
    }
  }

  private String getMethodIdentifier(MethodInvocation methodInvocation) {
    return new StringBuffer(String.valueOf(methodInvocation.getThis()))
            .append("::")
            .append(methodInvocation.getMethod())
            .toString();
  }
}
