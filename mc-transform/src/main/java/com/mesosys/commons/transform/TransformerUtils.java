package com.mesosys.commons.transform;

/**
 * Created by Mesosys Ltd.
 * <p/>
 * User: Peter Cameron
 * Date: 23-Nov-2008
 * Time: 21:24:33
 */
public class TransformerUtils {
  protected static <Any> Any construct(Class<Any> classAny) {
    try {
      return classAny.newInstance();
    } catch (InstantiationException e) {
      throw new TransformerException(e);
    } catch (IllegalAccessException e) {
      throw new TransformerException(e);
    }
  }

}
