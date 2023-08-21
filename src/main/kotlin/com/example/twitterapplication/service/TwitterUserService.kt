package com.example.twitterapplication.service

import com.example.twitterapplication.dto.TwitterUserDto
import com.example.twitterapplication.model.TwitterUser


interface TwitterUserService {
    fun getAllTwitterUsers(): List<TwitterUserDto>
    fun getTwitterUserById(id: Long): TwitterUserDto
    fun createTwitterUser(twitterUser: TwitterUser): TwitterUser
    fun updateTwitterUser(twitterUser: TwitterUser): TwitterUser
    fun deleteTwitterUserById(id: Long)
}