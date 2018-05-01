package com.apitore.mail.entity;


import java.io.Serializable;

import lombok.Data;


@Data
public class MailEntity implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 4825082233168538171L;

  private String email;
  private String name;

}
