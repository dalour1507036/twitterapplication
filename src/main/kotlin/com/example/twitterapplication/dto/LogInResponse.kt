package com.example.twitterapplication.dto

data class LogInResponse(
    var userId: Long,
    var authorizationToken: String
)