package com.example.twitterapplication.service

import com.example.twitterapplication.dto.TwitterPostRequest
import com.example.twitterapplication.dto.TwitterPostResponse
import com.example.twitterapplication.model.TwitterPost

interface TwitterPostService{
    fun getAllTwitterPosts(): List<TwitterPost>
    fun getTwitterPostById(id: Long): TwitterPostResponse
    fun getAllTwitterPostsByUserId(userId: Long): List<TwitterPostResponse>
    fun createTwitterPost(twitterPostRequest: TwitterPostRequest, id: Long): TwitterPostResponse
    fun updateTwitterPost(twitterPost: TwitterPost): TwitterPost
    fun deleteTwitterPostById(id:Long)
}