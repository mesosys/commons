package com.mesosys.commons.app;

import org.apache.log4j.PropertyConfigurator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ClassPathResource;

import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

/**
 * Simple base for an application. Loads in the Application-Object from a spring
 * configuration file.
 * <p/>
 * Initialises Log4J
 * <p/>
 * User: Peter Cameron
 * Date: 12-Mar-2007
 * Time: 21:10:50
 */
public abstract class MesosysSpringApp {
  private static Log LOG = LogFactory.getLog(MesosysSpringApp.class);

  protected MesosysSpringApp() {

  }

  protected abstract void runApplication() throws Exception;

  protected static void runApplication(final String applicationId) throws Exception {
    final Resource beansResource = getBeansResource(applicationId);
    runApplication(beansResource, applicationId);
  }

  protected static void runApplication(final Resource appResource, final String mainBeanId) throws Exception {
    final Resource coreResource = getCoreBeansResource();
    final DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
    final XmlBeanDefinitionReader beanReader = new XmlBeanDefinitionReader(beanFactory);

    if (coreResource != null)
      beanReader.loadBeanDefinitions(coreResource);

    beanReader.loadBeanDefinitions(appResource);

    MesosysSpringApp primaryRenewals = (MesosysSpringApp) beanFactory.getBean(mainBeanId);

    primaryRenewals.runApplication();
  }

  private static Resource getCoreBeansResource() {
    return getBeansResource("primaryCore");
  }

  private static Resource getBeansResource(String applicationId) {
    String resource = new StringBuffer("/conf/").append(applicationId).append(".xml").toString();

    if (MesosysSpringApp.class.getResourceAsStream(resource) != null) {
      LOG.info("Found class-path resource: "+resource);
      return new ClassPathResource(resource);
    }
    else {
      LOG.warn("Unable to find class-path resource: "+resource);
      return null;
    }
  }

  protected static void configure() throws IOException {
    InputStream log4jResource = MesosysSpringApp.class.getResourceAsStream("/conf/log4j.properties");

    if(log4jResource!=null) {
      Properties log4jProperties = new Properties();
      log4jProperties.load(log4jResource);
      PropertyConfigurator.configure(log4jProperties);
    }
  }
}
