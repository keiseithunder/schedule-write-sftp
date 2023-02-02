package com.example.schedule_write_sftp.configs;

import java.time.Duration;

import javax.net.ssl.SSLException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfigImpl implements WebClientConfig {

  @Value("${json.web.host}")
  private String jsonWebHost;
  @Value("${json.web.timeout}")
  private int jsonWebTimeout;

  @Bean
  public WebClient webClient() throws SSLException {
    SslContext sslContext = SslContextBuilder.forClient()
        .trustManager(InsecureTrustManagerFactory.INSTANCE)
        .build();
    HttpClient httpClient = HttpClient.create()
        .secure(t -> t.sslContext(sslContext))
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, jsonWebTimeout)
        .responseTimeout(Duration.ofMillis(jsonWebTimeout));
    ReactorClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);
    return WebClient.builder()
        .baseUrl(jsonWebHost)
        .clientConnector(connector)
        .defaultHeader("Content-Type", "application/json")
        .defaultHeader("Accept", "application/json")
        .build();
  }

}
