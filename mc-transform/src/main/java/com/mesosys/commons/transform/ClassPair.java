package com.mesosys.commons.transform;

/**
 * Created by Mesosys Ltd.
 * <p/>
 * User: Peter Cameron
 * Date: 20-Nov-2008
 * Time: 21:40:08
 */
public class ClassPair <A, B> {
  private Class<A> classA;
  private Class<B> classB;

  protected ClassPair(Class<A> classA, Class<B> classB) {
    this.classA = classA;
    this.classB = classB;
  }

  protected ClassPair<B, A> reverse() {
    return new ClassPair<B, A>(classB, classA);
  }

  protected Class<A> getClassA() {
    return classA;
  }

  protected Class<B> getClassB() {
    return classB;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ClassPair classPair = (ClassPair) o;

    if (!classA.equals(classPair.classA)) return false;
    if (!classB.equals(classPair.classB)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = classA.hashCode();
    result = 31 * result + classB.hashCode();
    return result;
  }

  public String toString() {
    return new StringBuffer(getClass().getName())
            .append("[").append(classA).append(":").append(classB).append("]")
            .append("@").append(Integer.toHexString(hashCode())).toString();
  }
}
