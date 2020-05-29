package com.paytm.insider.api;

import com.paytm.insider.service.ServiceConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@Import(ServiceConfiguration.class)
@RestController
public class HackernewsApiApplication {

    @RequestMapping("/")
    public String home() {
        return "Hello Docker World";
    }

    public static void main(String[] args) {

        SpringApplication.run(HackernewsApiApplication.class, args);
    }
}
