package com.example.twitterapplication.batchconfig

import com.example.twitterapplication.model.TwitterFriendRequest
import org.springframework.batch.item.ItemProcessor

class BatchUserProcessor : ItemProcessor<TwitterFriendRequest,TwitterFriendRequest> {
    override fun process(p0: TwitterFriendRequest): TwitterFriendRequest {
        return p0
    }
}