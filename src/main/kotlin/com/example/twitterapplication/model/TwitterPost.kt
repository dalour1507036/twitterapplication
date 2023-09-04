package com.example.twitterapplication.model

import javax.persistence.*

@Entity
@Table(name="posts")
class TwitterPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
    var twitterPostContent: String = ""

    @ManyToOne
    var twitterUser: TwitterUser? = null

    @OneToMany(mappedBy = "twitterPost", cascade = [CascadeType.ALL])
    var twitterComments: List<TwitterComment> = arrayListOf()
}