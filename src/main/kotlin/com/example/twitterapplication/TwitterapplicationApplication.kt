package com.example.twitterapplication

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableBatchProcessing
class TwitterapplicationApplication

fun main(args: Array<String>) {
	runApplication<TwitterapplicationApplication>(*args)
}
