package com.example.twitterapplication.dto

class LogInResponseDto {
    var authorizationToken: String = ""

    constructor(authorizationToken: String) {
        this.authorizationToken = authorizationToken
    }

    constructor()

}