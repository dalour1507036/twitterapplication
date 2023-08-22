package com.example.twitterapplication.service.imp

import com.example.twitterapplication.dto.TwitterUserRequest
import com.example.twitterapplication.dto.TwitterUserResponse
import com.example.twitterapplication.mapper.toTwitterUser
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
    override fun getAllTwitterUsers(): List<TwitterUserResponse> {
        val allTwitterUserResponse = twitterUserRepo.findAll().map { twitterUser ->
            twitterUser.toTwitterUserResponse()
        }
        return allTwitterUserResponse
    }

    override fun getTwitterUserById(id: Long): TwitterUserResponse {
        val twitterUser: TwitterUser = twitterUserRepo.findById(id).orElse(null)
        return twitterUser.toTwitterUserResponse()

    }

    override fun createTwitterUser(twitterUserRequest: TwitterUserRequest): TwitterUserResponse {
        val twitterUser: TwitterUser = twitterUserRequest.toTwitterUser()
        twitterUser.password = passwordEncoder.encode(twitterUser.password)
        return twitterUserRepo.save(twitterUser).toTwitterUserResponse()
    }

    override fun updateTwitterUser(twitterUserRequest: TwitterUserRequest): TwitterUserResponse {
        val twitterUser: TwitterUser = twitterUserRequest.toTwitterUser()
        return twitterUserRepo.save(twitterUser).toTwitterUserResponse()
    }

    override fun deleteTwitterUserById(id: Long){
        twitterUserRepo.deleteById(id)
    }
}