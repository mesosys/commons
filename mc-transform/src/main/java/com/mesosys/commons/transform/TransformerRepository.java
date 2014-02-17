package com.mesosys.commons.transform;

import java.util.HashMap;

/**
 * Created by Mesosys Ltd.
 * <p/>
 * User: Peter Cameron
 * Date: 20-Nov-2008
 * Time: 21:20:12
 */
public class TransformerRepository {

  @SuppressWarnings("unchecked")
  private final HashMap<ClassPair<?,?>, Transformer<?,?>> transformers = new HashMap();

  public TransformerRepository() {

  }

  protected <A, B> void register(BaseTransformer<A, B> transformer) {
    register(transformer.getClassPair(), transformer);
    register(transformer.getClassPair().reverse(), new ReverseTransformer<B, A>(transformer));
  }

  private <A, B> void register(ClassPair<A, B> classPair, Transformer<A, B> transformer) {
    transformers.put(classPair, transformer);
  }

  @SuppressWarnings("unchecked")
  public <A, B> B transform(Class<B> classB, A a) {
    Class<A> classA = (Class<A>) a.getClass();
    ClassPair<A, B> classPair = new ClassPair<A, B>(classA, classB);
    Transformer<A, B> transformer = (Transformer<A, B>)transformers.get(classPair);

    if(null != transformer)
      return transformer.transform(a);
    else {
      // no specified transformer.
      try {
        return classB.getConstructor(classA).newInstance(a);
      } catch (Exception e) {
        // No valid constructor found.
        throw new TransformerException("No valid constructor or transformer defined for class-pair:" + classPair, e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  public <A, B> B translate(B b, A a) {
    Class<A> classA = (Class<A>) a.getClass();
    Class<B> classB = (Class<B>) b.getClass();
    ClassPair<A, B> classPair = new ClassPair<A, B>(classA, classB);
    Transformer<A, B> transformer = (Transformer<A, B>)transformers.get(classPair);
    return transformer.translate(b, a);
  }
}
