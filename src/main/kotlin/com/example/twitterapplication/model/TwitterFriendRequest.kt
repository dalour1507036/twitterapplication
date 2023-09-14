package com.example.twitterapplication.model

import javax.persistence.*

@Entity
@Table(name = "twitter_friend_requests")
class TwitterFriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
    @ManyToOne
    @JoinColumn(name = "sender_id")
    lateinit var sender: TwitterUser
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    lateinit var receiver: TwitterUser
    var accepted: Boolean = false
}