package com.example.twitterapplication.service

import com.example.twitterapplication.dto.TwitterPostDto
import com.example.twitterapplication.model.TwitterPost

interface TwitterPostService{
    fun getAllTwitterPosts(): List<TwitterPost>
    fun getTwitterPostById(id: Long): TwitterPostDto
    fun getAllTwitterPostsByUserId(userId: Long): List<TwitterPostDto>
    fun createTwitterPost(twitterPostDto: TwitterPostDto, id: Long): TwitterPostDto
    fun updateTwitterPost(twitterPost: TwitterPost): TwitterPost
    fun deleteTwitterPostById(id:Long)
}