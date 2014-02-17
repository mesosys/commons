package com.mesosys.commons.aop;

import org.apache.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.MailParseException;

import javax.mail.internet.MimeMessage;
import javax.mail.MessagingException;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mesosys.commons.mail.io.MimeMessageOutputStream;

/**
 * User: Peter Cameron
 * Date: 01-Mar-2007
 * Time: 20:11:53
 */
public class MailInterceptor implements JavaMailSender {
  private static final Logger LOG = Logger.getLogger(DurationInterceptor.class);

  private final JavaMailSender trueService;
  private File outputLocation;
  private boolean allowSend = true;

  public MailInterceptor(JavaMailSender trueService) {
    this.trueService = trueService;
  }

  public MimeMessage createMimeMessage() {
    return trueService.createMimeMessage();
  }

  public MimeMessage createMimeMessage(InputStream contentStream) throws MailException {
    return trueService.createMimeMessage(contentStream);
  }

  public void send(MimeMessage mimeMessage) throws MailException {
    storeMessage(mimeMessage);
    if(allowSend)
      trueService.send(mimeMessage);
  }

  public void send(MimeMessage[] mimeMessages) throws MailException {
    storeMessages(mimeMessages);
    if(allowSend)
      trueService.send(mimeMessages);
  }

  public void send(MimeMessagePreparator mimeMessagePreparator) throws MailException {
    if(allowSend)
      trueService.send(mimeMessagePreparator);
  }

  public void send(MimeMessagePreparator[] mimeMessagePreparators) throws MailException {
    if(allowSend)
      trueService.send(mimeMessagePreparators);
  }

  public void send(SimpleMailMessage simpleMessage) throws MailException {
    if(allowSend)
      trueService.send(simpleMessage);
  }

  public void send(SimpleMailMessage[] simpleMessages) throws MailException {
    if(allowSend)
      trueService.send(simpleMessages);
  }

  public void setOutputLocation(String outputLocation) {
    this.outputLocation = new File(outputLocation);
  }

  private void storeMessages(MimeMessage[] mimeMessages) {
    for (int i = 0; mimeMessages != null && i < mimeMessages.length; i++)
      storeMessage(mimeMessages[i]);
  }

  private void storeMessage(MimeMessage mimeMessage) {
    if (mimeMessage != null) {

      File messageFile = outputLocation;
      try {
        Date sentDate = mimeMessage.getSentDate();
        String messageDate = (sentDate!=null) ? new SimpleDateFormat("yyyyMMdd").format(sentDate) : null;
        String[] policyNoHeaders = mimeMessage.getHeader("POLICY_NUMBER");
        String policyNo = (policyNoHeaders!=null && policyNoHeaders.length>0) ? policyNoHeaders[0] : "no_policy";

        String messageFilename = new StringBuffer()
                .append(messageDate)
                .append("-")
                .append(policyNo)
                .append(".msg")
                .toString();

        messageFile = new File(getOutputLocation(), messageFilename);

        if(messageFile.exists() && !"no_policy".equals(policyNo)) {
          // message for this policy already sent today
          throw new MailParseException("Duplicate message intercepted for policy: "+policyNo);
        }

        FileOutputStream fos = new FileOutputStream(messageFile);
        MimeMessageOutputStream mmos = new MimeMessageOutputStream(fos);
        mmos.writeHeader("Sent", String.valueOf(allowSend));
        mmos.writeMimeMessage(mimeMessage);

        mmos.close();
        fos.close();
      }
      catch (IOException e) {
        LOG.warn("Unable to save message to file: " + messageFile.getAbsolutePath(), e);
      }
      catch (MessagingException e) {
        LOG.warn("Unable to save message contents to file: " + messageFile.getAbsolutePath(), e);
      }
    }
  }

  private File getOutputLocation() {
    if(!outputLocation.exists())
      outputLocation.mkdirs();
    return outputLocation;
  }

  public void setAllowSend(boolean allowSend) {
    this.allowSend = allowSend;
  }
}
