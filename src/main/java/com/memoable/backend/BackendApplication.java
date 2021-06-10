package com.memoable.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;



/**
 * Main application of the backend
 * @author Jere Salmensaari
 */
@SpringBootApplication
public class BackendApplication implements CommandLineRunner {

	/**
	 * Main method of the backend
	 * <p>
	 * Starts the SpringApplication
	 * @param args commandline arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}

	/**
	 * Password encoder bean for the backend
	 * @return passwordEncoder
	 */
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

}
