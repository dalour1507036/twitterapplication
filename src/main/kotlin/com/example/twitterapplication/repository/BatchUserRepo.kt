package com.example.twitterapplication.repository

import com.example.twitterapplication.model.BatchUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BatchUserRepo : JpaRepository<BatchUser,Long> {
}