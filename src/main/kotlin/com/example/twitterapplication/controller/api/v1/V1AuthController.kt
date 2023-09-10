package com.example.twitterapplication.controller.api.v1

import com.example.twitterapplication.controller.api.BaseController
import com.example.twitterapplication.dto.LogInRequest
import com.example.twitterapplication.dto.LogInResponse
import com.example.twitterapplication.security.JwtIssuer
import com.example.twitterapplication.security.TwitterUserPrincipal
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/twitter-app/auth")
class V1AuthController(
    private val jwtIssuer: JwtIssuer,
    private val authenticationManager: AuthenticationManager
) : BaseController() {
    @PostMapping("/login")
    fun login(@RequestBody @Validated logInRequest: LogInRequest): ResponseEntity<LogInResponse> {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(logInRequest.email,logInRequest.password)
        )

        SecurityContextHolder.getContext().authentication = authentication

        val twitterUserPrincipal: TwitterUserPrincipal = authentication.principal as TwitterUserPrincipal

        val roles = twitterUserPrincipal.authorities.stream()
            .map { grantedAuthority -> grantedAuthority.authority }
            .toList()

        val logInResponseDto = LogInResponse(jwtIssuer.jwtTokenIssuerForUser(
            twitterUserPrincipal.userId,
            twitterUserPrincipal.email,
            roles
            )
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(logInResponseDto)
    }

    @GetMapping("/secured")
    fun securedLogIn(
    ): ResponseEntity<String> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body("Authorized User with User ID - " +
                 "${currentPrincipal().userId} " +
                 "and email - ${currentPrincipal().email} "
            )
    }
}