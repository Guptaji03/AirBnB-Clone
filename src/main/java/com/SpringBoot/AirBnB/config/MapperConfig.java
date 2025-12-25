package com.SpringBoot.AirBnB.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.modelmapper.ModelMapper;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper getModelMapper(){
         return  new ModelMapper();
    }
}
