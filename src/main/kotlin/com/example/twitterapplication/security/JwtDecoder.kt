package com.example.twitterapplication.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.stereotype.Component

@Component
class JwtDecoder {
    fun decode(token: String): DecodedJWT {
        return JWT.require(Algorithm.HMAC256("secretkey"))
            .build()
            .verify(token)
    }
}