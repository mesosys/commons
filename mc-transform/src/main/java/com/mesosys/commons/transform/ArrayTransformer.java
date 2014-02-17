package com.mesosys.commons.transform;

/**
 * Simple transformer that transform entries of one list into another
 * Created by Mesosys Ltd.
 * <p/>
 * User: Peter Cameron
 * Date: 23-Nov-2008
 * Time: 21:17:38
 */
public class ArrayTransformer<A,B> implements Transformer<A[], B[]> {
  private final BaseTransformer<A,B> transformer;

  public ArrayTransformer(BaseTransformer<A,B> transformer) {
    this.transformer = transformer;
  }

  public B[] transform(A[] as) {
    throw new TransformerException("Unable to construct new Array instance");
  }

  public B[] translate(B[] bs, A[] as) {
    for(int i=0; i<as.length; i++) {
      transformer.translate(bs[i], as[i]);
    }
    return bs;
  }

  public A[] reverseTransform(B[] bs) {
    throw new TransformerException("Unable to construct new Array instance");
  }

  public A[] reverseTranslate(A[] as, B[] bs) {
    for(int i=0; i<bs.length; i++) {
      transformer.translate(bs[i], as[i]);
    }
    return as;
  }
}