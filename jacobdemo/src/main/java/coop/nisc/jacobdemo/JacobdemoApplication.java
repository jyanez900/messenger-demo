package coop.nisc.jacobdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;

/**
 * Demo project built by Bedrock.
 */
@SpringBootApplication
@EnableDiscoveryClient
@Import(FeignClientsConfiguration.class)
public class JacobdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(JacobdemoApplication.class, args);
    }

}
