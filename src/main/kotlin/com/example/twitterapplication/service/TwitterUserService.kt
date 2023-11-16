package com.example.twitterapplication.service

import com.example.twitterapplication.model.TwitterUser


interface TwitterUserService {
    fun getAllTwitterUsers(): List<TwitterUser>
    fun getTwitterUserById(id: Long): TwitterUser
    fun getTwitterUserByEmail(email: String): TwitterUser?
    fun createTwitterUser(twitterUser: TwitterUser): TwitterUser
    fun updateTwitterUser(userId: Long, updatedTwitterUser: TwitterUser): TwitterUser
    fun deleteTwitterUserById(id: Long)
}