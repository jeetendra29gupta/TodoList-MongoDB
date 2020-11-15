package org.jeetu.work;

import java.io.File;
import java.io.IOException;

import org.jeetu.work.model.Users;
import org.jeetu.work.service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class StsTodoMongodbApplication implements CommandLineRunner {
	private static final Logger log = LoggerFactory.getLogger(StsTodoMongodbApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(StsTodoMongodbApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		log.info("Application has started.");
		// createAdmin();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private UsersService usersService;

	public void createAdmin() {
		ObjectMapper mapper = new ObjectMapper();
		Users user = null;
		try {
			File jsonFile = new File("user.json");
			if (jsonFile.exists()) {
				user = mapper.readValue(jsonFile, Users.class);
				user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
				usersService.saveUser(user);
			}
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
}
