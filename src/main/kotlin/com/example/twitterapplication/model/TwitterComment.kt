package com.example.twitterapplication.model

import javax.persistence.*

@Entity
@Table(name = "comments")
class TwitterComment {
    constructor(commentContent: String, twitterUser: TwitterUser, twitterPost: TwitterPost){
        this.commentContent = commentContent
        this.twitterUser = twitterUser
        this.twitterPost = twitterPost
    }

    constructor()

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
    @Column(name = "content") var commentContent: String = ""

    @ManyToOne
    @JoinColumn(name = "user_id")
    lateinit var twitterUser: TwitterUser

    @ManyToOne
    @JoinColumn(name = "post_id")
    lateinit var twitterPost: TwitterPost

}