package com.example.twitterapplication.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.springframework.stereotype.Component
import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit

@Component
class JwtIssuer {
    fun jwtTokenIssuerForUser(userId: Long, email: String, roles: List<String>): String {
        return JWT.create()
            .withSubject(userId.toString())
            .withExpiresAt(Instant.now().plus(Duration.of(5,ChronoUnit.DAYS)))
            .withClaim("e",email)
            .withClaim("roles", roles)
            .sign(Algorithm.HMAC256("secretkey"))
    }
}