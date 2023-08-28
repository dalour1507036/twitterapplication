package com.example.twitterapplication.security

import org.springframework.security.authentication.AbstractAuthenticationToken


class TwitterUserPrincipalAuthenticationToken(
    private var twitterUserPrincipal: TwitterUserPrincipal) :
    AbstractAuthenticationToken(twitterUserPrincipal.authorities) {
        override fun getCredentials(): Any {
        return ""
    }

    override fun getPrincipal(): Any {
        return twitterUserPrincipal
    }

    init {
        isAuthenticated = true
    }
}