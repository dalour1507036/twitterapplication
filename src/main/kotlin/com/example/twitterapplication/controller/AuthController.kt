package com.example.twitterapplication.controller

import com.example.twitterapplication.dto.LogInRequestDto
import com.example.twitterapplication.dto.LogInResponseDto
import com.example.twitterapplication.security.JwtIssuer
import com.example.twitterapplication.security.TwitterUserPrincipal
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(private val jwtIssuer: JwtIssuer, private val authenticationManager: AuthenticationManager) {
    @PostMapping("/twitter-app/login")
    fun login(@RequestBody @Validated logInRequestDto: LogInRequestDto): ResponseEntity<LogInResponseDto> {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(logInRequestDto.email,logInRequestDto.password)
        )

        SecurityContextHolder.getContext().authentication = authentication

        val twitterUserPrincipal: TwitterUserPrincipal = authentication.principal as TwitterUserPrincipal

        val roles = twitterUserPrincipal.authorities.stream()
            .map { grantedAuthority -> grantedAuthority.authority }
            .toList()

        val logInResponseDto = LogInResponseDto(jwtIssuer.jwtTokenIssuerForUser(
            twitterUserPrincipal.userId,
            twitterUserPrincipal.email, roles )
        )

        return ResponseEntity.status(HttpStatus.CREATED).body(logInResponseDto)
    }

    @GetMapping("/secured")
    fun securedLogIn(@AuthenticationPrincipal twitterUserPrincipal: TwitterUserPrincipal): ResponseEntity<String> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body("Authorized User with User ID - " +
                    "${twitterUserPrincipal.userId} and email - ${twitterUserPrincipal.email} ")
    }
}