package com.example.twitterapplication.repository

import com.example.twitterapplication.model.TwitterUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.stereotype.Repository

@EnableJpaRepositories
@Repository
interface TwitterUserRepo : JpaRepository<TwitterUser, Long> {
    fun findOneByEmailAndPassword(email: String, password: String): TwitterUser?
    fun findByEmail(email: String): TwitterUser?
}