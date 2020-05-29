package com.paytm.insider.service.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.paytm.insider.service.common.CommentCacheBuffer;
import com.paytm.insider.service.common.StoryCacheBuffer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableCaching
public class AppConfig {

    @Bean
    public ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    @Bean
    public CommentCacheBuffer getCommentCacheBuffer(){
        return new CommentCacheBuffer();
    }

    @Bean
    public StoryCacheBuffer getStoryCacheBuffer() {
        return new StoryCacheBuffer();
    }
}
