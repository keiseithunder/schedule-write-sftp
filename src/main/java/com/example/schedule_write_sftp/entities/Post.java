package com.example.schedule_write_sftp.entities;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement(name = "Post")
public class Post {
  @XmlElement(name = "userId")
  public int userId;
  @XmlAttribute(name = "id")
  public int id;
  @XmlElement(name = "title")
  public String title;
  @XmlElement(name = "body")
  public String body;
}
