package com.mesosys.commons.mail.io;

import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.BodyPart;
import java.io.OutputStream;
import java.io.IOException;

/**
 * User: Peter Cameron
 * Date: 20-Mar-2007
 * Time: 11:02:46
 */
public class MimeMessageOutputStream extends OutputStream {
  private final OutputStream stream;

  public MimeMessageOutputStream(OutputStream stream) {
    this.stream = stream;
  }

  public void write(int b) throws IOException {
    stream.write(b);
  }

  public void writeHeader(String header, String value) throws IOException {
    write(new StringBuffer(header).append(":").append(value).append("\n").toString().getBytes());
  }

  public void writeMimeMessage(MimeMessage mimeMessage) throws MessagingException, IOException {
    Address[] toRecipients = mimeMessage.getRecipients(MimeMessage.RecipientType.TO);

    String messageContent = new StringBuffer()
            .append("Sent: ").append(mimeMessage.getSentDate()).append("\n")
            .append("To: ").append(toString(toRecipients)).append("\n")
            .append("Cc: ").append(toString(mimeMessage.getRecipients(MimeMessage.RecipientType.CC))).append("\n")
            .append("Bcc: ").append(toString(mimeMessage.getRecipients(MimeMessage.RecipientType.BCC))).append("\n")
            .append("From: ").append(toString(mimeMessage.getFrom())).append("\n")
            .append("Sender: ").append(toString(mimeMessage.getSender())).append("\n")
            .append("Reply-To: ").append(toString(mimeMessage.getReplyTo())).append("\n")
            .append("Subject: ").append(mimeMessage.getSubject()).append("\n")
            .append("Size: ").append(mimeMessage.getSize()).append("\n")
            .append("Content:\n").append(getContent(mimeMessage)).append("\n")
            .toString();

    write(messageContent.getBytes());
  }

  private String toString(Address[] recipients) {
    return toString(recipients, Integer.MAX_VALUE);
  }

  private String toString(Address[] recipients, int max) {
    StringBuffer content = new StringBuffer();
    for(int i=0; recipients!=null && i<recipients.length && i<max; i++) {
      content.append((i==0) ? "" : ", ").append(toString(recipients[i]));
    }
    return content.toString();
  }

  private String toString(Address recipient) {
    return String.valueOf(recipient);
  }

  private String getContent(Object content) throws MessagingException, IOException {
    StringBuffer contentString = new StringBuffer();
    buildContent(contentString, content);
    return contentString.toString();
  }

  private void buildContent(StringBuffer contentString, Object content) throws MessagingException, IOException {
    if(content instanceof MimeMultipart) {
      MimeMultipart multipart = (MimeMultipart) content;
      for(int i=0; i<multipart.getCount(); i++) {
        BodyPart part = multipart.getBodyPart(i);
        contentString.append("--------------------  MultiPart ").append(i).append(" [").append(part.getContentType()).append("]--------------------\n");
        buildContent(contentString, part.getContent());
      }
    }
    else if(content instanceof MimeMessage) {
      buildContent(contentString, ((MimeMessage)content).getContent());
    }
    else
      contentString.append(content);
  }


}
