package com.example.schedule_write_sftp.schdulers;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.file.remote.session.Session;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.schedule_write_sftp.entities.Post;
import com.example.schedule_write_sftp.integrations.sftp.SftpWrite;
import com.example.schedule_write_sftp.services.RestConsumerService;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Scheduler {

  @Autowired
  private RestConsumerService restConsumerService;

  @Autowired
  private SftpWrite sftpWrite;

  @Value("${sftp.remote.directory}")
  private String remoteDir;
  
  private static final Logger log = LoggerFactory.getLogger(Scheduler.class);

  @Scheduled(cron = "*/10 * * * * *")
  public void cronJobSch() throws Exception {
    log.info("Cron Job Scheduler :: Execution Time - {}", (new Date()).toString());
    Post[] result = restConsumerService.getAllPost();
    List<Post> posts = Arrays.asList(result);
    XmlMapper xmlMapper = new XmlMapper();
    String xml = xmlMapper.writeValueAsString(posts);
    InputStream targetStream  = new ByteArrayInputStream(xml.getBytes());
    Session session = sftpWrite.sftpSessionFactory().getSession();
    boolean isRemoteDirExist = session.exists(remoteDir+"/newFiles");
    if(!isRemoteDirExist) {
      session.mkdir(remoteDir+"/newFiles");
    }
    session.write(targetStream, remoteDir+"/newFiles/test-" + (new Date()).toString()+".txt");
    session.close();
  }
}
