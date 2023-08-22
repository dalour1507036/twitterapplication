package com.example.twitterapplication.service

import com.example.twitterapplication.dto.TwitterUserRequest
import com.example.twitterapplication.dto.TwitterUserResponse


interface TwitterUserService {
    fun getAllTwitterUsers(): List<TwitterUserResponse>
    fun getTwitterUserById(id: Long): TwitterUserResponse
    fun createTwitterUser(twitterUserRequest: TwitterUserRequest): TwitterUserResponse
    fun updateTwitterUser(twitterUserRequest: TwitterUserRequest): TwitterUserResponse
    fun deleteTwitterUserById(id: Long)
}