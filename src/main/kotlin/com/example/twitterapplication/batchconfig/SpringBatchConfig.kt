package com.example.twitterapplication.batchconfig

import com.example.twitterapplication.model.BatchUser
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
    fun reader(): FlatFileItemReader<BatchUser> {
        val itemReader = FlatFileItemReader<BatchUser>()
        itemReader.setResource(FileSystemResource("src/main/resources/tusers.csv"))
        itemReader.name = "tusersReader"
        itemReader.setLinesToSkip(1)
        itemReader.setLineMapper(lineMapper())
        return itemReader
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
    fun writer(): JdbcBatchItemWriter<BatchUser> {
        val itemWriter = JdbcBatchItemWriter<BatchUser>()
        itemWriter.setDataSource(dataSource)
        itemWriter.setSql(
                "INSERT INTO ttusers (id, first_name, last_name, email, password) VALUES (:id, :firstName, :lastName, :email, :password)"
        )
        itemWriter.setItemSqlParameterSourceProvider (BeanPropertyItemSqlParameterSourceProvider<BatchUser>() )
        return itemWriter
    }

    @Bean
    fun step1(): Step {
        return stepBuilderFactory["csv-step"].chunk<BatchUser,BatchUser>(1000)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .taskExecutor(taskExecutor())
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

    fun lineMapper(): LineMapper<BatchUser> {
        val lineMapper = DefaultLineMapper<BatchUser>()

        val lineTokenizer = DelimitedLineTokenizer()
        lineTokenizer.setDelimiter(",")
        lineTokenizer.setStrict(false)
        lineTokenizer.setNames("id","first_name","last_name","email","password")

        val fieldSetMapper = BeanWrapperFieldSetMapper<BatchUser>()
        fieldSetMapper.setTargetType(BatchUser::class.java)

        lineMapper.setLineTokenizer(lineTokenizer)
        lineMapper.setFieldSetMapper(fieldSetMapper)
        return lineMapper
    }
}