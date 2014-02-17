package com.mesosys.commons.transform;

/**
 * Created by Mesosys Ltd.
 * <p/>
 * User: Peter Cameron
 * Date: 20-Nov-2008
 * Time: 21:19:04
 */
public interface Transformer <A, B> {

  public B transform(A a);

  public B translate(B b, A a);

  public A reverseTransform(B b);

  public A reverseTranslate(A a, B b);
}
