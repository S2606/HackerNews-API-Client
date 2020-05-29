package com.paytm.insider.service;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.paytm.insider.service")
@EntityScan("com.paytm.insider.service.entity")
public class ServiceConfiguration {
}
