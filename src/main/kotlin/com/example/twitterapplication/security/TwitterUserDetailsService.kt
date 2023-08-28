package com.example.twitterapplication.security

import com.example.twitterapplication.model.TwitterUser
import com.example.twitterapplication.repository.TwitterUserRepo
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class TwitterUserDetailsService(private val twitterUserRepo: TwitterUserRepo) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails? {
        val twitterUser: TwitterUser? = username?.let { twitterUserRepo.findByEmail(it) }

        if (twitterUser != null) {
            return TwitterUserPrincipal(
                twitterUser.id,
                twitterUser.email,
                twitterUser.password,
                mutableListOf<SimpleGrantedAuthority>(SimpleGrantedAuthority("ROLE_USER")))
        }
            return null
    }
}