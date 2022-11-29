package com.yootoo.calcite.config;

import com.yootoo.calcite.mongodb.MongoContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;

/**
 * @author it.liu
 * @description context register
 * @date 2022/11/29
 */
@Configuration
public class ContextRegister {

    @Bean
    public MongoContext mongoContext(@Autowired MongoDatabaseFactory mongoDbFactory){
        return new MongoContext(mongoDbFactory.getMongoDatabase());
    }
}
