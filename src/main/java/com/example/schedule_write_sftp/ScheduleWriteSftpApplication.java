package com.example.schedule_write_sftp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@IntegrationComponentScan
@EnableIntegration
public class ScheduleWriteSftpApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScheduleWriteSftpApplication.class, args);
	}

}
