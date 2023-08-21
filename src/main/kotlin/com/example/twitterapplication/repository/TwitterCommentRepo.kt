package com.example.twitterapplication.repository

import com.example.twitterapplication.model.TwitterComment
import com.example.twitterapplication.model.TwitterPost
import com.example.twitterapplication.model.TwitterUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TwitterCommentRepo : JpaRepository<TwitterComment, Long>{
    fun findByTwitterUser(twitterUser: TwitterUser): List<TwitterComment>
    fun findByTwitterPost(twitterPost: TwitterPost): List<TwitterComment>
}