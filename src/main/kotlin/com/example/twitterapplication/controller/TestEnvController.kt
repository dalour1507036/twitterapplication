package com.example.twitterapplication.controller

import org.springframework.context.annotation.Profile
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("twitter-app")
@Profile("test")
class TestEnvController {
    @GetMapping("/testenv")
    fun testEnv(): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.OK).body(
            "You are seeing this message when the application in test mode noly"
        )
    }
}