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
class DtoToInstance(private val modelMapper: ModelMapper) {

    fun twitterUserDtoToTwitterUser(twitterUserDto: TwitterUserDto?): TwitterUser? {
        return modelMapper.map(twitterUserDto, TwitterUser::class.java)
    }

    fun twitterPostDtoToTwitterPost(twitterPostDto: TwitterPostDto): TwitterPost {
        return modelMapper.map(twitterPostDto, TwitterPost::class.java)
    }

    fun twitterCommentDtoToTwitterComment(twitterCommentDto: TwitterCommentDto): TwitterComment {
        return modelMapper.map(twitterCommentDto, TwitterComment::class.java)
    }
}