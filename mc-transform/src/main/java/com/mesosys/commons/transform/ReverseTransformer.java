package com.mesosys.commons.transform;

/**
 * Created by Mesosys Ltd.
 * <p/>
 * User: Peter Cameron
 * Date: 20-Nov-2008
 * Time: 21:20:35
 */
public final class ReverseTransformer<A,B> implements Transformer<A,B> {
  private final Transformer<B,A> transformer;

  public ReverseTransformer(Transformer<B,A> transformer) {
    this.transformer = transformer;
  }

  public B transform(A a) {
    return transformer.reverseTransform(a);
  }

  public B translate(B b, A a) {
    return transformer.reverseTranslate(b, a);
  }

  public A reverseTransform(B b) {
    return transformer.transform(b);
  }

  public A reverseTranslate(A a, B b) {
    return transformer.translate(a, b);
  }
}