package com.example.twitterapplication.service.imp

import com.example.twitterapplication.exceptionhandler.exceptions.UserAlreadyExist
import com.example.twitterapplication.mapper.toTwitterUserResponse
import com.example.twitterapplication.model.TwitterUser
import com.example.twitterapplication.repository.TwitterUserRepo
import com.example.twitterapplication.service.TwitterUserService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class TwitterUserServiceImp(
    private val passwordEncoder: PasswordEncoder,
    private val twitterUserRepo: TwitterUserRepo,
) : TwitterUserService {
    override fun getAllTwitterUsers(): List<TwitterUser> {
        val allTwitterUserResponse = twitterUserRepo.findAll().map { twitterUser ->
            twitterUser.toTwitterUserResponse()
        }
        return twitterUserRepo.findAll()
    }

    override fun getTwitterUserById(id: Long): TwitterUser {
        val twitterUser = twitterUserRepo.findById(id).orElse(null)
        twitterUser.password = passwordEncoder.matches("pass1234", twitterUser.password).toString()
        return twitterUser
    }

    override fun getTwitterUserByEmail(email: String): TwitterUser? {
        return twitterUserRepo.findByEmail(email)
    }

    override fun createTwitterUser(twitterUser: TwitterUser): TwitterUser {
        if ( twitterUserRepo.findByEmail(twitterUser.email) != null ) {
            throw UserAlreadyExist("user with ${twitterUser.email} already exist")
        }
        twitterUser.password = passwordEncoder.encode(twitterUser.password)
        return twitterUserRepo.save(twitterUser)
    }

    override fun updateTwitterUser(userId: Long, updatedTwitterUser: TwitterUser): TwitterUser {
        val twitterUser = twitterUserRepo.findById(userId).orElse(null)
        twitterUser.firstName = updatedTwitterUser.firstName
        twitterUser.lastName = updatedTwitterUser.lastName
        twitterUser.email = updatedTwitterUser.email
        twitterUser.password = passwordEncoder.encode(updatedTwitterUser.password)
        return twitterUserRepo.save(twitterUser)
    }

    override fun deleteTwitterUserById(id: Long) {
        twitterUserRepo.deleteById(id)
    }
}