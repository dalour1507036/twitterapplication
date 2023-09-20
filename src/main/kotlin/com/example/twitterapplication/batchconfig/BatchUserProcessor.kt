package com.example.twitterapplication.batchconfig

import com.example.twitterapplication.model.BatchUser
import org.springframework.batch.item.ItemProcessor

class BatchUserProcessor : ItemProcessor<BatchUser,BatchUser> {
    override fun process(p0: BatchUser): BatchUser {
        return p0
    }
}