package com.example.twitterapplication.service.imp

import com.example.twitterapplication.dto.TwitterCommentDto
import com.example.twitterapplication.mapper.InstanceToDto
import com.example.twitterapplication.model.TwitterComment
import com.example.twitterapplication.repository.TwitterCommentRepo
import com.example.twitterapplication.repository.TwitterPostRepo
import com.example.twitterapplication.repository.TwitterUserRepo
import com.example.twitterapplication.service.TwitterCommentService
import org.springframework.stereotype.Service

@Service
class TwitterCommentServiceImp(
    private val twitterUserRepo: TwitterUserRepo,
    private val twitterPostRepo: TwitterPostRepo,
    private val twitterCommentRepo: TwitterCommentRepo,
    private val instanceToDto: InstanceToDto
    ) : TwitterCommentService {
    override fun createTwitterUserCommentInTwitterPost(
        twitterCommentDto: TwitterCommentDto,
        userId: Long,
        postId: Long
    ): TwitterCommentDto {
        val twitterUser = twitterUserRepo.findById(userId).orElse(null)
        val twitterPost = twitterPostRepo.findById(postId).orElse(null)
        val twitterComment = TwitterComment(
            twitterCommentDto.commentContent,
            twitterUser,
            twitterPost
        )

        return instanceToDto.twitterCommentToTwitterCommentDto(
            twitterCommentRepo.save(twitterComment)
        )

    }
}