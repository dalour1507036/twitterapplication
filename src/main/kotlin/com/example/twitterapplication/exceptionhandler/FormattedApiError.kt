package com.example.twitterapplication.exceptionhandler

import org.springframework.http.HttpStatus

data class FormattedApiError (
    var status: HttpStatus,
    var exceptionMessage: String
    )