package com.mesosys.commons.transform;

/**
 * Created by Mesosys Ltd.
 * <p/>
 * User: Peter Cameron
 * Date: 20-Nov-2008
 * Time: 22:04:43
 */
public class TransformerException extends RuntimeException {
  public TransformerException(String msg) {
    super(msg);
  }

  public TransformerException(Exception e) {
    super(e);
  }

  public TransformerException(String msg, Exception e) {
    super(msg, e);
  }
}
