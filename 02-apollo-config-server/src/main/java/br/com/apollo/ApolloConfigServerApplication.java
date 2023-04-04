package br.com.apollo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ApolloConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApolloConfigServerApplication.class, args);
	}

}
