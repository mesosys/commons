package com.mesosys.commons.transform;

import static com.mesosys.commons.transform.TransformerUtils.construct;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Simple transformer that transform entries of one list into another
 * Created by Mesosys Ltd.
 * <p/>
 * User: Peter Cameron
 * Date: 23-Nov-2008
 * Time: 21:17:38
 */
public class ListTransformer<A,B> implements Transformer<List<A>, List<B>> {
  private final Transformer<A,B> transformer;
  private final Class<? extends List> listClass;

  public ListTransformer(Transformer<A,B> transformer) {
    this(transformer, ArrayList.class);
  }

  public ListTransformer(Transformer<A,B> transformer, Class<? extends List> listClass) {
    this.transformer = transformer;
    this.listClass = listClass;
  }

  @SuppressWarnings("unchecked")
  public List<B> transform(List<A> as) {
    List<B> bs = construct(listClass);
    for(A a: as) {
      bs.add(transformer.transform(a));
    }
    return bs;
  }

  public List<B> translate(List<B> bs, List<A> as) {
    Iterator<A> ia = as.iterator();
    for(B b: bs) {
      transformer.translate(b, ia.next());
    }
    return bs;
  }

  @SuppressWarnings("unchecked")
  public List<A> reverseTransform(List<B> bs) {
    List<A> as = construct(listClass);
    for(B b: bs) {
      as.add(transformer.reverseTransform(b));
    }
    return as;
  }

  public List<A> reverseTranslate(List<A> as, List<B> bs) {
    Iterator<B> ib = bs.iterator();
    for(A a: as) {
      transformer.reverseTranslate(a, ib.next());
    }
    return as;
  }
}
