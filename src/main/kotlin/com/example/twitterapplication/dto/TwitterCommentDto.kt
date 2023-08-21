package com.example.twitterapplication.dto

class TwitterCommentDto {
    constructor(id: Long, commentContent: String) {
        this.id = id
        this.commentContent = commentContent
    }

    constructor()

    constructor(commentContent: String) {
        this.commentContent = commentContent
    }

    var id: Long = 0
    var commentContent: String = ""
}