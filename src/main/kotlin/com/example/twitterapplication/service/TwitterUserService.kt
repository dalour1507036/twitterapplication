package com.example.twitterapplication.service

import com.example.twitterapplication.model.TwitterUser


interface TwitterUserService {
    fun getAllTwitterUsers(): List<TwitterUser>
    fun getTwitterUserById(id: Long): TwitterUser
    fun createTwitterUser(twitterUser: TwitterUser): TwitterUser
    fun updateTwitterUser(twitterUser: TwitterUser): TwitterUser
    fun deleteTwitterUserById(id: Long)
}