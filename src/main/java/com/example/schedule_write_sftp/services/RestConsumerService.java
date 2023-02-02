package com.example.schedule_write_sftp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.schedule_write_sftp.entities.Post;

@Service
public class RestConsumerService {
  @Value("${json.web.path}")
  private String jsonWebPath;

  @Autowired
  private WebClient webClient;

  public Post[] getAllPost(){
    Post[] posts = webClient.get().uri(jsonWebPath).retrieve().bodyToMono(Post[].class).block();
    return posts;
  }
}
