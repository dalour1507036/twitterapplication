package com.example.twitterapplication.service.imp

import com.example.twitterapplication.model.TwitterComment
import com.example.twitterapplication.model.TwitterPost
import com.example.twitterapplication.repository.TwitterCommentRepo
import com.example.twitterapplication.repository.TwitterPostRepo
import com.example.twitterapplication.repository.TwitterUserRepo
import com.example.twitterapplication.service.TwitterCommentService
import org.springframework.stereotype.Service

@Service
class TwitterCommentServiceImp(
    private val twitterUserRepo: TwitterUserRepo,
    private val twitterPostRepo: TwitterPostRepo,
    private val twitterCommentRepo: TwitterCommentRepo
    ) : TwitterCommentService {
    override fun createTwitterUserCommentInTwitterPost(
        twitterComment: TwitterComment,
        userId: Long,
        postId: Long
    ): TwitterComment {
        val twitterUser = twitterUserRepo.findById(userId).orElse(null)
        val twitterPost = twitterPostRepo.findById(postId).orElse(null)
        twitterComment.twitterUser = twitterUser
        twitterComment.twitterPost = twitterPost

        return twitterCommentRepo.save(twitterComment)
    }

    override fun getTwitterCommentsByTwitterPost(twitterPost: TwitterPost): List<TwitterComment> {
        return twitterCommentRepo.findByTwitterPost(twitterPost)
    }
}