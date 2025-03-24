package ru.ns.t_jobs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
public class TJobsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TJobsApplication.class, args);
	}

}
