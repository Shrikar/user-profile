package com.exostar.userprofile.batch.configuration;

import javax.sql.DataSource;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.exostar.userprofile.VO.UserView;
import com.exostar.userprofile.batch.processor.UserItemProcessor;
import com.exostar.userprofile.entity.UserEntity;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
    @Autowired
    private DataSource dataSource;

    @Bean
    public ItemProcessor<UserView, UserEntity> processor() {
        return new UserItemProcessor();
    }

    @Bean
    public FlatFileItemReader reader() {
        FlatFileItemReader<UserView> itemReader = new FlatFileItemReader<>();
        itemReader.setLineMapper(lineMapper());
        itemReader.setLinesToSkip(1);
        return itemReader;
    }

    @Bean
    public LineMapper<UserView> lineMapper() {
        DefaultLineMapper<UserView> lineMapper = new DefaultLineMapper<UserView>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames(new String[] { "firstName", "lastName", "email", "dateOfBirth", "countryCode" });
        lineTokenizer.setIncludedFields(new int[] { 0, 1, 2, 3, 4 });
        BeanWrapperFieldSetMapper<UserView> fieldSetMapper = new BeanWrapperFieldSetMapper<UserView>();
        fieldSetMapper.setTargetType(UserView.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }

    @Bean
    public JdbcBatchItemWriter<UserEntity> writer() {
        JdbcBatchItemWriter<UserEntity> itemWriter = new JdbcBatchItemWriter<>();
        itemWriter.setDataSource(dataSource);
        itemWriter
                .setSql("INSERT INTO users (first_name,last_name,email,date_of_birth,age,country) " + "VALUES (:firstName, :lastName, :email, :dateOfBirth, :age, :country)");
        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<UserEntity>());
        return itemWriter;
    }
}
