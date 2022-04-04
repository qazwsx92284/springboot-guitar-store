package com.my.guitarstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@ComponentScan(basePackages = "com.my.guitarstore")
@EnableJpaRepositories("com.my.guitarstore.repository")
@SpringBootApplication
public class GuitarStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(GuitarStoreApplication.class, args);
	}

}
