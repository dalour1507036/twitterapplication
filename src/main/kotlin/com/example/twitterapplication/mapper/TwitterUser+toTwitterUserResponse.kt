package com.example.twitterapplication.mapper

import com.example.twitterapplication.dto.TwitterUserResponse
import com.example.twitterapplication.model.TwitterUser

//done
fun TwitterUser.toTwitterUserResponse(): TwitterUserResponse {
    return TwitterUserResponse(
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email
    )
}