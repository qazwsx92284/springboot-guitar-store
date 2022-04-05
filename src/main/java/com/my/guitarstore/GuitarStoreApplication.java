package com.my.guitarstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

//@EnableJpaRepositories("com.my.guitarstore.repository")
@ComponentScan(basePackages = "com.my.guitarstore")
@SpringBootApplication
public class GuitarStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(GuitarStoreApplication.class, args);
	}

}
