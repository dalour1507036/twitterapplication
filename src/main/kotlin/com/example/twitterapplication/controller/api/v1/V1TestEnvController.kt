package com.example.twitterapplication.controller.api.v1

import org.springframework.context.annotation.Profile
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
@Profile("test")
class V1TestEnvController {
    @GetMapping("/testenv")
    fun testEnv(): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.OK).body(
            "You are seeing this message when the application in test mode only"
        )
    }
}