package com.example.twitterapplication.security

import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component

@Component
class DecodedJwtToTwitterUserPrincipal {
    fun convert(jwt: DecodedJWT): TwitterUserPrincipal {
        return TwitterUserPrincipal(
            jwt.subject.toLong(),
            jwt.getClaim("e").asString(),
            extractAuthoritiesFromClaim(jwt)
        )
    }

    fun extractAuthoritiesFromClaim(jwt: DecodedJWT): MutableCollection<SimpleGrantedAuthority> {
        val claim = jwt.getClaim("roles")

        if (claim.isNull || claim.isMissing) {
            return mutableListOf()
        }

        return claim.asList(SimpleGrantedAuthority::class.java).toMutableList()
    }
}