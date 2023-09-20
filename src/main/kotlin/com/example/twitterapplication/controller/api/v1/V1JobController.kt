package com.example.twitterapplication.controller.api.v1

import com.example.twitterapplication.controller.api.BaseController
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/v1/jobs")
@EnableBatchProcessing
class V1JobController(
        private val jobLauncher: JobLauncher,
        @Qualifier("batchUserJob")
        private val batchUserJob: Job
    ) : BaseController(){
    @PostMapping("/batch-user")
    fun importBatchUserToDb(): ResponseEntity<String> {
        val jobParameters = JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis()).toJobParameters()
        jobLauncher.run(batchUserJob, jobParameters);
        return ResponseEntity.status(HttpStatus.OK).body("process finished")
    }
}