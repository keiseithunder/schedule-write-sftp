package com.example.schedule_write_sftp.configs;

import javax.net.ssl.SSLException;

import org.springframework.web.reactive.function.client.WebClient;

public interface WebClientConfig {
  public WebClient webClient() throws SSLException ;
}
