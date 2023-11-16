package com.example.twitterapplication.controller.api.v1

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/encode")
//@Profile("test")
class V1TestEnvController(private val passwordEncoder: BCryptPasswordEncoder) {
//    @GetMapping("/testenv")
    @GetMapping
    fun testEnv(): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.OK).body(
//            "You are seeing this message when the application in test mode only"
                passwordEncoder.encode("pass1234")
        )
    }
}