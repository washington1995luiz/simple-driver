package br.com.washington.simple_driver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SimpleDriverApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleDriverApplication.class, args);
	}

}
