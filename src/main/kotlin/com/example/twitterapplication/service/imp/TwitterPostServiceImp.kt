package com.example.twitterapplication.service.imp

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
        return twitterPostRepo.findAllByOrderByIdDesc()
    }

    override fun getTwitterPostById(id: Long): TwitterPost {
        return twitterPostRepo.findById(id).orElse(null)
    }

    override fun getAllTwitterPostsByUserId(userId: Long): List<TwitterPost> {
        val twitterUser = twitterUserRepo.findById(userId).orElse(null)
        return twitterPostRepo.findByTwitterUserOrderByIdDesc(twitterUser) ?: emptyList()
    }

    override fun createTwitterPost(twitterPost: TwitterPost, id: Long): TwitterPost {
        val twitterUser = twitterUserRepo.findById(id).orElse(null)
        twitterPost.twitterUser = twitterUser
        return twitterPostRepo.save(twitterPost)
    }

    override fun updateTwitterPost(postId: Long, updatedTwitterPost: TwitterPost): TwitterPost {
        val twitterPost = twitterPostRepo.findById(postId).orElse(null)
        twitterPost.twitterPostContent = updatedTwitterPost.twitterPostContent
        return twitterPostRepo.save(twitterPost)
    }

    override fun deleteTwitterPostById(id: Long) {
        twitterPostRepo.deleteById(id)
    }
}