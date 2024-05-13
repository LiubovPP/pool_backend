package de.ait.pool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
//@EnableAsync

public class PoolApplication {

	public static void main(String[] args) {
		SpringApplication.run(PoolApplication.class, args);
	}

}
