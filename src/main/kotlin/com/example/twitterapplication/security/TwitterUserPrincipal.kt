package com.example.twitterapplication.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


class TwitterUserPrincipal : UserDetails {
    constructor(userId:Long, email:String, password:String,authorities: MutableCollection<SimpleGrantedAuthority>){
        this.userId = userId
        this.email = email
        this.userPassword = password
        this.authorities = authorities
    }

    constructor(userId: Long, email: String, authorities: MutableCollection<out GrantedAuthority>) {
        this.userId = userId
        this.email = email
        this.authorities = authorities
    }

    var userId:Long = 0
    var email:String =""
    var userPassword:String=""
    private var authorities:MutableCollection<out GrantedAuthority>

    fun getTwitterUserId(): Long {
        return userId
    }
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return authorities
    }

    override fun getPassword(): String {
        return userPassword
    }

    override fun getUsername(): String {
        return email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}