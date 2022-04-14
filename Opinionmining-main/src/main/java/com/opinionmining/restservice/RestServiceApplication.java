package com.opinionmining.restservice;

import com.opinionmining.restservice.entity.Queue;
import com.opinionmining.restservice.repository.QueueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.ArrayList;

@SpringBootApplication
@EnableScheduling
public class RestServiceApplication {

	private static final Logger logger = LoggerFactory.getLogger(RestServiceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RestServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner initializeDB(QueueRepository queueRepository){
		return (args)->{
			if (queueRepository.findAll().size() < 2){
				queueRepository.save(new Queue("Facebook"));
				queueRepository.save(new Queue("Twitter"));
				logger.info("The queues have been initialized!!!");
			}
		};
	}
}
