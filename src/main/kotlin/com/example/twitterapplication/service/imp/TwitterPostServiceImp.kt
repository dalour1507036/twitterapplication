package com.example.twitterapplication.service.imp

import com.example.twitterapplication.dto.TwitterPostRequest
import com.example.twitterapplication.dto.TwitterPostResponse
import com.example.twitterapplication.mapper.toTwitterCommentResponse
import com.example.twitterapplication.mapper.toTwitterPost
import com.example.twitterapplication.mapper.toTwitterPostResponse
import com.example.twitterapplication.mapper.toTwitterUserResponse
import com.example.twitterapplication.model.TwitterPost
import com.example.twitterapplication.repository.TwitterCommentRepo
import com.example.twitterapplication.repository.TwitterPostRepo
import com.example.twitterapplication.repository.TwitterUserRepo
import com.example.twitterapplication.service.TwitterPostService
import org.springframework.stereotype.Service

@Service
class TwitterPostServiceImp(
     private val twitterUserRepo: TwitterUserRepo,
     private val twitterPostRepo: TwitterPostRepo,
    private val twitterCommentRepo: TwitterCommentRepo
) : TwitterPostService {
    override fun getAllTwitterPosts(): List<TwitterPost> {
        return twitterPostRepo.findAll()
    }

    override fun getTwitterPostById(id: Long): TwitterPostResponse {
        val twitterPost =  twitterPostRepo.findById(id).orElse(null)
        val allCommentsInAPost = twitterCommentRepo.findByTwitterPost(twitterPost)

        val allCommentResponseInAPost = allCommentsInAPost.map { twitterComment ->
            twitterComment.toTwitterCommentResponse()
        }
        return TwitterPostResponse(
            twitterPost.twitterPostContent,
            twitterPost.twitterUser?.toTwitterUserResponse(),
            allCommentResponseInAPost
        )

    }

    override fun getAllTwitterPostsByUserId(userId: Long): List<TwitterPostResponse> {
        val twitterUser = twitterUserRepo.findById(userId).orElse(null)
        val allTwitterPostsByUserId = twitterPostRepo.findByTwitterUser(twitterUser)?: emptyList()

        return allTwitterPostsByUserId.map { twitterPost ->
            twitterPost.toTwitterPostResponse()
        }
    }

    override fun createTwitterPost(twitterPostRequest: TwitterPostRequest, id: Long): TwitterPostResponse {
        val twitterUser = twitterUserRepo.findById(id).orElse(null)
        //val twitterPost = TwitterPost(twitterPostDto.twitterPostContent, twitterUser)
        val twitterPost: TwitterPost = twitterPostRequest.toTwitterPost()
        twitterPost.twitterUser = twitterUser

        return twitterPostRepo.save(twitterPost).toTwitterPostResponse()
    }

    override fun updateTwitterPost(twitterPost: TwitterPost): TwitterPost {
        return twitterPostRepo.save(twitterPost)
    }

    override fun deleteTwitterPostById(id:Long){
        twitterPostRepo.deleteById(id)
    }
}