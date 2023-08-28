package com.example.twitterapplication.config

import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BeanConfig {
    @Bean
    fun loggingFilter(): FilterRegistrationBean<TimingFilter> {
        val registrationBean: FilterRegistrationBean<TimingFilter> = FilterRegistrationBean()
        registrationBean.filter = TimingFilter()
        registrationBean.addUrlPatterns("/twitter-app/*")
        return registrationBean
    }
}