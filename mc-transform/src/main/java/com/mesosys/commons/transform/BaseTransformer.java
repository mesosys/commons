package com.mesosys.commons.transform;

import static com.mesosys.commons.transform.TransformerUtils.construct;

/**
 * Created by Mesosys Ltd.
 * <p/>
 * User: Peter Cameron
 * Date: 20-Nov-2008
 * Time: 21:20:35
 */
public class BaseTransformer <A,B> implements Transformer<A,B> {
  private final TransformerRepository repository;
  private final ClassPair<A,B> classPair;

  public BaseTransformer(TransformerRepository repository, Class<A> classA, Class<B> classB) {
    this.classPair = new ClassPair<A, B>(classA, classB);
    this.repository = repository;
    this.repository.register(this);
  }

  /**
   * Standaline transformer constructor
   * @param classA
   * @param classB
   */
  public BaseTransformer(Class<A> classA, Class<B> classB) {
    this.classPair = new ClassPair<A, B>(classA, classB);
    this.repository = null;
  }

  public B transform(A a) {
    return translate(constructB(), a);
  }

  public B translate(B b, A a) {
    return b;
  }

  public A reverseTransform(B b) {
    return reverseTranslate(constructA(), b);
  }

  public A reverseTranslate(A a, B b) {
    return a;
  }

  protected A constructA() {
    return construct(classPair.getClassA());
  }
  protected B constructB() {
    return construct(classPair.getClassB());
  }

  public ClassPair<A, B> getClassPair() {
    return classPair;
  }

  protected <A2,B2> A2 transformOther(Class<A2> classA2, B2 b) {
    return repository.transform(classA2, b);
  }

  protected <A2,B2> A2 translateOther(A2 a, B2 b) {
    return repository.translate(a, b);
  }
}
