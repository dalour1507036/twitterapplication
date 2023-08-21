package com.example.twitterapplication.dto

class TwitterPostDto {
    constructor(twitterPostContent: String) {
        this.twitterPostContent = twitterPostContent
    }

    constructor(
        twitterPostContent: String,
        twitterUser: TwitterUserDto,
        twitterUserComments: List<TwitterCommentDto>) {
        this.twitterPostContent = twitterPostContent
        this.twitterUser = twitterUser
        this.twitterUserComments = twitterUserComments
    }

    constructor()
    constructor(twitterPostContent: String, twitterUserComments: List<TwitterCommentDto>) {
        this.twitterPostContent = twitterPostContent
        this.twitterUserComments = twitterUserComments
    }

    var twitterPostContent: String = ""
    var twitterUser: TwitterUserDto? = null
    var twitterUserComments: List<TwitterCommentDto> = arrayListOf()
}