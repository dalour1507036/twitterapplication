package com.example.twitterapplication.controller.api

import com.example.twitterapplication.security.TwitterUserPrincipal
import org.springframework.security.core.context.SecurityContextHolder

open class BaseController {
    fun currentPrincipal(): TwitterUserPrincipal {
        val authentication = SecurityContextHolder.getContext().authentication
        return authentication.principal as TwitterUserPrincipal
    }

    fun currentUserId(): Long {
        return currentPrincipal().getTwitterUserId()
    }
}