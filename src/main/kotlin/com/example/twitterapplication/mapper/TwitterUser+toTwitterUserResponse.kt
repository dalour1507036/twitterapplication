package com.example.twitterapplication.mapper

import com.example.twitterapplication.dto.TwitterUserResponse
import com.example.twitterapplication.model.TwitterUser

fun TwitterUser.toTwitterUserResponse(
): TwitterUserResponse {
    return TwitterUserResponse(
        id = this.id,
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email
    )
}