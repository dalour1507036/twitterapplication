package com.example.twitterapplication.model

import javax.persistence.*

@Entity
@Table(name="twitter_users")
class TwitterUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
    @Column(name = "first_name")
    var firstName: String = ""
    @Column(name = "last_name")
    var lastName: String = ""
    @Column(name = "email")
    var email: String = ""
    @Column(name = "password")
    var password: String = ""

    @OneToMany(mappedBy = "twitterUser", cascade = [CascadeType.ALL])
    var twitterPosts: List<TwitterPost> = arrayListOf()

    @OneToMany(mappedBy ="twitterUser", cascade = [CascadeType.ALL] )
     var twitterComments: List<TwitterComment> = arrayListOf()
}