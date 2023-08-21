package com.example.twitterapplication.service.imp

import com.example.twitterapplication.dto.TwitterPostDto
import com.example.twitterapplication.mapper.DtoToInstance
import com.example.twitterapplication.mapper.InstanceToDto
import com.example.twitterapplication.model.TwitterPost
import com.example.twitterapplication.repository.TwitterCommentRepo
import com.example.twitterapplication.repository.TwitterPostRepo
import com.example.twitterapplication.repository.TwitterUserRepo
import com.example.twitterapplication.service.TwitterPostService
import org.springframework.stereotype.Service

@Service
class TwitterPostServiceImp(
     private val twitterUserRepo: TwitterUserRepo,
     private val dtoToInstance: DtoToInstance,
     private val instanceToDto: InstanceToDto,
     private val twitterPostRepo: TwitterPostRepo,
    private val twitterCommentRepo: TwitterCommentRepo
) : TwitterPostService {
    override fun getAllTwitterPosts(): List<TwitterPost> {
        return twitterPostRepo.findAll()
    }

    override fun getTwitterPostById(id: Long): TwitterPostDto {
        val twitterPost =  twitterPostRepo.findById(id).orElse(null)
        val allCommentsInAPost = twitterCommentRepo.findByTwitterPost(twitterPost)

        val allCommentsDtoInAPost = allCommentsInAPost.map { twitterComment ->
            instanceToDto.twitterCommentToTwitterCommentDto(twitterComment)
        }
        return TwitterPostDto(twitterPost.twitterPostContent, allCommentsDtoInAPost)
    }

    override fun getAllTwitterPostsByUserId(userId: Long): List<TwitterPostDto> {
        val twitterUser = twitterUserRepo.findById(userId).orElse(null)
        val allTwitterPostsByUserId = twitterPostRepo.findByTwitterUser(twitterUser)?: emptyList()

        return allTwitterPostsByUserId.map { twitterPost ->
            instanceToDto.twitterPostToTwitterPostDto(twitterPost) }
    }

    override fun createTwitterPost(twitterPostDto: TwitterPostDto, id: Long): TwitterPostDto {
        val twitterUser = twitterUserRepo.findById(id).orElse(null)
        val twitterPost = TwitterPost(twitterPostDto.twitterPostContent, twitterUser)

        return instanceToDto.twitterPostToTwitterPostDto(twitterPostRepo.save(twitterPost))
    }

    override fun updateTwitterPost(twitterPost: TwitterPost): TwitterPost {
        return twitterPostRepo.save(twitterPost)
    }

    override fun deleteTwitterPostById(id:Long){
        twitterPostRepo.deleteById(id)
    }
}