package com.example.twitterapplication.service

import com.example.twitterapplication.model.TwitterPost

interface TwitterPostService{
    fun getAllTwitterPosts(): List<TwitterPost>
    fun getTwitterPostById(id: Long): TwitterPost
    fun getAllTwitterPostsByUserId(userId: Long): List<TwitterPost>
    fun createTwitterPost(twitterPost: TwitterPost, id: Long): TwitterPost
    fun updateTwitterPost(twitterPost: TwitterPost): TwitterPost
    fun deleteTwitterPostById(id:Long)
}