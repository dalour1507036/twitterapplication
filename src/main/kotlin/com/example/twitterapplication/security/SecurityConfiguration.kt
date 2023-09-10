package com.example.twitterapplication.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val twitterUserJwtAuthenticationFilter: TwitterUserJwtAuthenticationFilter,
    private val twitterUserDetailsService: TwitterUserDetailsService
) {
    @Bean
    fun twitterAppSecurity(http: HttpSecurity ): SecurityFilterChain {
        http.addFilterBefore(twitterUserJwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)

        http
            .cors().disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .formLogin().disable() //since we are building api, this line will disable the default login page of spring security
            .antMatcher("/**")
            .authorizeHttpRequests { registry ->
                registry
                    .requestMatchers(AntPathRequestMatcher("/")).permitAll()
                    .requestMatchers(AntPathRequestMatcher("/api/v1/twitter-app/**")).permitAll()
                    .requestMatchers(AntPathRequestMatcher("/api/v1/twitter-app/login")).permitAll()
                    .anyRequest().authenticated()
            }
        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationManager(http: HttpSecurity): AuthenticationManager{
       return http.getSharedObject(AuthenticationManagerBuilder::class.java)
            .userDetailsService(twitterUserDetailsService)
            .passwordEncoder(passwordEncoder())
            .and()
            .build()
    }
}