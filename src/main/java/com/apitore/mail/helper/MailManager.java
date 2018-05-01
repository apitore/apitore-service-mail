package com.apitore.mail.helper;

import java.util.List;

import com.apitore.mail.entity.MailEntity;

public interface MailManager {

  public void send(String to, String cc, String bcc, String subject, String content);
  public void send(List<MailEntity> tos, String cc, String bcc, String subject, String content);

}
