package com.example.twitterapplication.batchconfig

import com.example.twitterapplication.model.TwitterFriendRequest
import com.example.twitterapplication.repository.BatchUserRepo
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider
import org.springframework.batch.item.database.JdbcBatchItemWriter
import org.springframework.batch.item.file.FlatFileItemReader
import org.springframework.batch.item.file.LineMapper
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper
import org.springframework.batch.item.file.mapping.DefaultLineMapper
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.FileSystemResource
import org.springframework.core.task.SimpleAsyncTaskExecutor
import org.springframework.core.task.TaskExecutor
import javax.sql.DataSource

@Configuration
@EnableBatchProcessing
class SpringBatchConfig(
        private val jobBuilderFactory: JobBuilderFactory,
        private val stepBuilderFactory: StepBuilderFactory,
        private val batchUserRepo: BatchUserRepo,
        @Qualifier("dataSource")
        private val dataSource: DataSource
    ) {
    @Bean
    fun reader(): FlatFileItemReader<TwitterFriendRequest> {
        val itemReader = FlatFileItemReader<TwitterFriendRequest>()
        itemReader.setResource(FileSystemResource("src/main/resources/twitter_friend_requests.csv"))
        itemReader.name = "usersReader"
        itemReader.setLinesToSkip(1)
        itemReader.setLineMapper(lineMapper())
        return itemReader
    }

    fun lineMapper(): LineMapper<TwitterFriendRequest> {
        val lineMapper = DefaultLineMapper<TwitterFriendRequest>()

        val lineTokenizer = DelimitedLineTokenizer()
        lineTokenizer.setDelimiter(",")
        lineTokenizer.setStrict(false)
        lineTokenizer.setNames("id","sender_id","receiver_id","accepted")

        val fieldSetMapper = BeanWrapperFieldSetMapper<TwitterFriendRequest>()
        fieldSetMapper.setTargetType(TwitterFriendRequest::class.java)

        lineMapper.setLineTokenizer(lineTokenizer)
        lineMapper.setFieldSetMapper(fieldSetMapper)
        return lineMapper
    }

    @Bean
    fun processor(): BatchUserProcessor{
        return BatchUserProcessor()
    }

//    @Bean
//    fun writer(): RepositoryItemWriter<BatchUser> {
//        val itemWriter = RepositoryItemWriter<BatchUser>()
//        itemWriter.setRepository(batchUserRepo)
//        itemWriter.setMethodName("save")
//        return itemWriter
//    }

    @Bean
    fun writer(): JdbcBatchItemWriter<TwitterFriendRequest> {
        val itemWriter = JdbcBatchItemWriter<TwitterFriendRequest>()
        itemWriter.setDataSource(dataSource)
        itemWriter.setSql(
                "INSERT INTO twitter_friend_requests (sender_id, receiver_id, accepted) VALUES (:sender, :receiver, :accepted)"
        )
        itemWriter.setItemSqlParameterSourceProvider (BeanPropertyItemSqlParameterSourceProvider<TwitterFriendRequest>() )
        return itemWriter
    }

    @Bean
    fun step1(): Step {
        return stepBuilderFactory["csv-step"].chunk<TwitterFriendRequest,TwitterFriendRequest>(1000)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                //.taskExecutor(taskExecutor())
                .build()
    }

    @Bean
    fun batchUserJob(): Job {
        return jobBuilderFactory["importBatchUsers"]
                .flow(step1()).end().build()
    }

    @Bean
    fun taskExecutor(): TaskExecutor {
        val asyncTaskExecutor = SimpleAsyncTaskExecutor()
        asyncTaskExecutor.concurrencyLimit = 10
        return asyncTaskExecutor
    }
}