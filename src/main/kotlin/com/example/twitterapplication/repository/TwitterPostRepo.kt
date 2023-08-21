package com.example.twitterapplication.repository

import com.example.twitterapplication.model.TwitterPost
import com.example.twitterapplication.model.TwitterUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TwitterPostRepo : JpaRepository<TwitterPost, Long> {
    fun findByTwitterUser(twitterUser: TwitterUser): List<TwitterPost>
}