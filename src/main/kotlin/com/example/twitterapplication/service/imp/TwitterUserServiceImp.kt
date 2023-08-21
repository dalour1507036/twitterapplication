package com.example.twitterapplication.service.imp

import com.example.twitterapplication.dto.TwitterUserDto
import com.example.twitterapplication.mapper.InstanceToDto
import com.example.twitterapplication.model.TwitterUser
import com.example.twitterapplication.repository.TwitterUserRepo
import com.example.twitterapplication.service.TwitterUserService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class TwitterUserServiceImp(
    private val passwordEncoder: PasswordEncoder,
    private val twitterUserRepo: TwitterUserRepo,
    private val instanceToDto: InstanceToDto
) : TwitterUserService {
    override fun getAllTwitterUsers(): List<TwitterUserDto> {
        val allTwitterUserDto = twitterUserRepo.findAll().map { twitterUser ->
            instanceToDto.twitterUserToTwitterUserDto(twitterUser)
        }
        return allTwitterUserDto
    }

    override fun getTwitterUserById(id: Long): TwitterUserDto {
        val twitterUser: TwitterUser = twitterUserRepo.findById(id).orElse(null)
        return instanceToDto.twitterUserToTwitterUserDto(twitterUser)

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