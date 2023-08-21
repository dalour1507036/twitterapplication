package com.example.twitterapplication.mapper

import com.example.twitterapplication.dto.TwitterCommentDto
import com.example.twitterapplication.dto.TwitterPostDto
import com.example.twitterapplication.dto.TwitterUserDto
import com.example.twitterapplication.model.TwitterComment
import com.example.twitterapplication.model.TwitterPost
import com.example.twitterapplication.model.TwitterUser
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service

@Service
class InstanceToDto(private val modelMapper: ModelMapper) {

    fun twitterUserToTwitterUserDto(twitterUser: TwitterUser): TwitterUserDto {
        return modelMapper.map(twitterUser, TwitterUserDto::class.java)

    }

    fun twitterPostToTwitterPostDto(twitterPost: TwitterPost): TwitterPostDto {
        return modelMapper.map(twitterPost, TwitterPostDto::class.java)
    }

    fun twitterCommentToTwitterCommentDto(twitterComment: TwitterComment): TwitterCommentDto {
        return modelMapper.map(twitterComment, TwitterCommentDto::class.java)
    }
}