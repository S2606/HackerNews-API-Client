package com.paytm.insider.api;

import com.paytm.insider.service.ServiceConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(ServiceConfiguration.class)
public class HackernewsApiApplication {

    public static void main(String[] args) {

        SpringApplication.run(HackernewsApiApplication.class, args);
    }
}
