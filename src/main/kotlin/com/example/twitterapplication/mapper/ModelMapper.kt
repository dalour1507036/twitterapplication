package com.example.twitterapplication.mapper

import org.modelmapper.ModelMapper
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class ModelMapperClass {
    @Bean
    fun modelMapper():ModelMapper{
        return ModelMapper()
    }
}