package com.mesosys.commons.transform;

/**
 * Created by Mesosys Ltd.
 * <p/>
 * User: Peter Cameron
 * Date: 20-Nov-2008
 * Time: 22:01:32
 */
public class StringToInteger extends BaseTransformer<String,Integer> {
  public StringToInteger(TransformerRepository repository) {
    super(repository, String.class, Integer.class);
  }

  public Integer transform(String s) {
    return new Integer(s);
  }

  public String reverseTransform(Integer i) {
    return i.toString();
  }
}
