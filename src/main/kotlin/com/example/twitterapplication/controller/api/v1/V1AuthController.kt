package com.example.twitterapplication.controller.api.v1

import com.example.twitterapplication.controller.api.BaseController
import com.example.twitterapplication.dto.LogInRequest
import com.example.twitterapplication.dto.LogInResponse
import com.example.twitterapplication.exceptionhandler.exceptions.UserNotFound
import com.example.twitterapplication.exceptionhandler.exceptions.UsernamePasswordMismatch
import com.example.twitterapplication.security.JwtIssuer
import com.example.twitterapplication.security.TwitterUserPrincipal
import com.example.twitterapplication.service.TwitterUserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/auth")
class V1AuthController(
    private val jwtIssuer: JwtIssuer,
    private val authenticationManager: AuthenticationManager,
    private val twitterUserService: TwitterUserService
) : BaseController() {
    @PostMapping("/login")
    fun login(@RequestBody @Validated logInRequest: LogInRequest): ResponseEntity<LogInResponse> {
        val twitterUser = twitterUserService.getTwitterUserByEmail(logInRequest.email)
            ?: throw UserNotFound("user with email- ${logInRequest.email} doesn't exist")

        val authentication = try {
            authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(logInRequest.email,logInRequest.password)
        )
        } catch (ex:org.springframework.security.core.AuthenticationException) {
            throw UsernamePasswordMismatch("username and password doesn't match")
        }

        SecurityContextHolder.getContext().authentication = authentication

        val twitterUserPrincipal: TwitterUserPrincipal = authentication.principal as TwitterUserPrincipal

        val roles = twitterUserPrincipal.authorities.stream()
            .map { grantedAuthority -> grantedAuthority.authority }
            .toList()

        val logInResponseDto = LogInResponse(twitterUser.id, jwtIssuer.jwtTokenIssuerForUser(
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