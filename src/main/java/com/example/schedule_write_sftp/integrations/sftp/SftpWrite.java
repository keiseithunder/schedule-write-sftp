package com.example.schedule_write_sftp.integrations.sftp;

import org.apache.sshd.sftp.client.SftpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.file.remote.session.CachingSessionFactory;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.integration.sftp.session.SftpRemoteFileTemplate;
import org.springframework.stereotype.Service;

// @IntegrationComponentScan
@Service
public class SftpWrite {
  @Value("${sftp.host}")
  private String host;
  @Value("${sftp.port}")
  private int port;
  @Value("${sftp.username}")
  private String user;
  @Value("${sftp.password}")
  private String password;
  @Value("${sftp.remote.directory}")
  private String remoteDir;

  @Bean
  public SessionFactory<SftpClient.DirEntry> sftpSessionFactory() {
    DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory(true);
    factory.setHost(host);
    factory.setPort(port);
    factory.setUser(user);
    factory.setPassword(password);
    factory.setAllowUnknownKeys(true);
    return new CachingSessionFactory<>(factory);
  }

  @Bean
  public SftpRemoteFileTemplate template() {
    return new SftpRemoteFileTemplate(sftpSessionFactory());
  }

  // @Bean
  // @ServiceActivator(inputChannel = "toSftpChannel")
  // public MessageHandler handler() {
  //   SftpMessageHandler handler = new SftpMessageHandler(sftpSessionFactory());
  //   handler.setRemoteDirectoryExpression(new LiteralExpression(remoteDir));
  //   handler.setFileNameGenerator(new FileNameGenerator() {

  //     @Override
  //     public String generateFileName(Message<?> message) {
  //       // System.out.println("message: " + message);
  //       return "handlerContent.test";
  //     }

  //   });
  //   return handler;
  // }

}
