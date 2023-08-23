package com.example.twitterapplication.service.imp

import com.example.twitterapplication.mapper.toTwitterUserResponse
import com.example.twitterapplication.model.TwitterUser
import com.example.twitterapplication.repository.TwitterUserRepo
import com.example.twitterapplication.service.TwitterUserService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class TwitterUserServiceImp(
    private val passwordEncoder: PasswordEncoder,
    private val twitterUserRepo: TwitterUserRepo
) : TwitterUserService {
    override fun getAllTwitterUsers(): List<TwitterUser> {
        val allTwitterUserResponse = twitterUserRepo.findAll().map { twitterUser ->
            twitterUser.toTwitterUserResponse()
        }
        return twitterUserRepo.findAll()
    }

    override fun getTwitterUserById(id: Long): TwitterUser {
        return twitterUserRepo.findById(id).orElse(null)
    }

    override fun createTwitterUser(twitterUser: TwitterUser): TwitterUser {
        twitterUser.password = passwordEncoder.encode(twitterUser.password)
        return twitterUserRepo.save(twitterUser)
    }

    override fun updateTwitterUser(twitterUser: TwitterUser): TwitterUser {
        return twitterUserRepo.save(twitterUser)
    }

    override fun deleteTwitterUserById(id: Long){
        twitterUserRepo.deleteById(id)
    }
}