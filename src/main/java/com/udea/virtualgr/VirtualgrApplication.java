package com.udea.virtualgr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class VirtualgrApplication {

	public static void main(String[] args) {
		SpringApplication.run(VirtualgrApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void onApplicationReady() {
		log.info("=================================");
		log.info("VirtualGR Application Started!");
		log.info("Available endpoints:");
		log.info("- Home: http://localhost:8080/");
		log.info("- Status: http://localhost:8080/api/status");
		log.info("- Test: http://localhost:8080/api/test");
		log.info("- GraphQL: http://localhost:8080/graphql");
		log.info("- GraphiQL: http://localhost:8080/graphiql");
		log.info("- Health: http://localhost:8080/actuator/health");
		log.info("- Prometheus : http://localhost:9090");
		log.info("- Grafana : http://localhost:3000");
		log.info("=================================");
	}
}