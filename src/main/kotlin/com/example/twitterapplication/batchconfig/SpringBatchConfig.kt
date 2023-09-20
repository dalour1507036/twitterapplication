package com.example.twitterapplication.batchconfig

import com.example.twitterapplication.model.BatchUser
import com.example.twitterapplication.repository.BatchUserRepo
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.item.data.RepositoryItemWriter
import org.springframework.batch.item.file.FlatFileItemReader
import org.springframework.batch.item.file.LineMapper
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper
import org.springframework.batch.item.file.mapping.DefaultLineMapper
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.FileSystemResource

@Configuration
@EnableBatchProcessing
class SpringBatchConfig(
        private val jobBuilderFactory: JobBuilderFactory,
        private val stepBuilderFactory: StepBuilderFactory,
        private val batchUserRepo: BatchUserRepo
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

    @Bean
    fun writer(): RepositoryItemWriter<BatchUser>{
        val itemWriter = RepositoryItemWriter<BatchUser>()
        itemWriter.setRepository(batchUserRepo)
        itemWriter.setMethodName("saveAll")
        return itemWriter
    }

    @Bean
    fun step1(): Step {
        return stepBuilderFactory["csv-step"].chunk<BatchUser,BatchUser>(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build()
    }

    @Bean
    fun batchUserJob(): Job {
        return jobBuilderFactory["importBatchUsers"]
                .flow(step1()).end().build()
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