package com.apitore.mail.helper;


import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.apitore.mail.entity.MailEntity;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.MailSettings;
import com.sendgrid.Method;
import com.sendgrid.Personalization;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.Setting;


@Service
public class MailManagerImpl implements MailManager {
  private final Logger LOG = LoggerFactory.getLogger(MailManagerImpl.class);

  @Autowired
  SendGrid sendGrid;

  @Value("${apitore.support.mail}")
  private String APITORE_SUPPORT;

  @Override
  public void send(String to, String cc, String bcc, String subject, String content) {
    try {
      Mail mail = new Mail();

      Email fromEmail = new Email();
      fromEmail.setName("Apitore");
      fromEmail.setEmail(APITORE_SUPPORT);
      mail.setFrom(fromEmail);
      mail.setReplyTo(fromEmail);

      mail.setSubject(subject);

      //TODO HTML mail.
      Content cnt = new Content();
      //content = content.replace("\r", "");
      //content = content.replace("\n", "\n\n");
      //cnt.setType("text/plain");
      //cnt.setValue(content);
      //mail.addContent(cnt);
      content = content.replace("\r", "");
      content = content.replace("\n", "<BR>");
      cnt.setType("text/html");
      cnt.setValue(content);
      mail.addContent(cnt);

      MailSettings mailSettings = new MailSettings();
      Setting sandBoxMode = new Setting();
      sandBoxMode.setEnable(true);
      //mailSettings.setSandboxMode(sandBoxMode);
      mail.setMailSettings(mailSettings);

      Personalization personalization = new Personalization();
      Email tmp;
      tmp = new Email();
      tmp.setEmail(to);
      personalization.addTo(tmp);
      if (cc != null) {
        tmp = new Email();
        tmp.setEmail(cc);
        personalization.addCc(tmp);
      }
      if (bcc != null) {
        tmp = new Email();
        tmp.setEmail(bcc);
        personalization.addBcc(tmp);
      }
      mail.addPersonalization(personalization);

      Request request = new Request();
      request.setMethod(Method.POST);
      request.setEndpoint("mail/send");
      request.setBody(mail.build());
      Response response = sendGrid.api(request);
      System.out.println(response.getStatusCode());
      System.out.println(response.getBody());
      System.out.println(response.getHeaders());
    } catch (IOException e) {
      LOG.error("IOException",e);
    }
  }

  @Override
  public void send(List<MailEntity> tos, String cc, String bcc, String subject, String content) {
    try {
      Mail mail = new Mail();

      Email fromEmail = new Email();
      fromEmail.setName("Apitore");
      fromEmail.setEmail(APITORE_SUPPORT);
      mail.setFrom(fromEmail);
      mail.setReplyTo(fromEmail);

      mail.setSubject(subject);

      //TODO HTML mail.
      Content cnt = new Content();
      //content = content.replace("\r", "");
      //content = content.replace("\n", "\n\n");
      //cnt.setType("text/plain");
      //cnt.setValue(content);
      //mail.addContent(cnt);
      content = content.replace("\r", "");
      content = content.replace("\n", "<BR>");
      cnt.setType("text/html");
      cnt.setValue(content);
      mail.addContent(cnt);

      MailSettings mailSettings = new MailSettings();
      Setting sandBoxMode = new Setting();
      sandBoxMode.setEnable(true);
      //mailSettings.setSandboxMode(sandBoxMode);
      mail.setMailSettings(mailSettings);

      for (MailEntity ent: tos) {
        Personalization personalization = new Personalization();
        Email tmp = new Email();
        tmp.setEmail(ent.getEmail());
        personalization.addTo(tmp);
        personalization.addSubstitution("%name%", ent.getName());
        if (cc != null) {
          tmp = new Email();
          tmp.setEmail(cc);
          personalization.addCc(tmp);
        }
        if (bcc != null) {
          tmp = new Email();
          tmp.setEmail(bcc);
          personalization.addBcc(tmp);
        }
        mail.addPersonalization(personalization);
      }

      Request request = new Request();
      request.setMethod(Method.POST);
      request.setEndpoint("mail/send");
      request.setBody(mail.build());
      Response response = sendGrid.api(request);
      System.out.println(response.getStatusCode());
      System.out.println(response.getBody());
      System.out.println(response.getHeaders());
    } catch (IOException e) {
      LOG.error("IOException",e);
    }
  }

}
