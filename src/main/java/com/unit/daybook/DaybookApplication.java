package com.unit.daybook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DaybookApplication {

	public static void main(String[] args) {
		SpringApplication.run(DaybookApplication.class, args);
	}

}
