package com.exostar.userprofile.service;

import java.net.MalformedURLException;
import java.text.ParseException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileUrlResource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import com.exostar.userprofile.Exception.IllegalCountryCodeException;
import com.exostar.userprofile.Exception.IllegalDateException;
import com.exostar.userprofile.VO.UserView;
import com.exostar.userprofile.batch.listener.UserRecordSkipListener;
import com.exostar.userprofile.batch.listener.UserRecordWriteListener;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserBatchService {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private ItemProcessor processor;

    @Autowired
    private FlatFileItemReader itemReader;

    @Autowired
    private JdbcBatchItemWriter itemWriter;

    @Autowired
    private JobLauncher jobLauncher;

    public void processUserFile(String fileName) {
        try {
            Job job = jobBuilderFactory.get("readCSVFileJob").incrementer(new RunIdIncrementer()).start(getStep(fileName))
                                       //.next(sendToRabbitMq())
                                       .build();
            JobParameters jobParameters = new JobParametersBuilder().addString("JobId", String.valueOf(System.currentTimeMillis())).toJobParameters();
            jobLauncher.run(job, jobParameters);
        } catch (MalformedURLException mue) {
            log.error("error finding resource ", mue);
        } catch (Exception e) {
            log.error("error running the job", e);
        }
    }

    private Step getStep(String fileName) throws MalformedURLException {
        itemReader.setResource(new FileUrlResource(fileName));
        return stepBuilderFactory.get("step").<UserView, UserView> chunk(5).processor(processor).reader(itemReader).writer(itemWriter).faultTolerant()
                                                                           .listener(new UserRecordSkipListener()).skip(IllegalDateException.class)
                                                                           .skip(ParseException.class).skip(IllegalCountryCodeException.class)
                                                                           .skip(DuplicateKeyException.class)
                                                                           .skipLimit(Integer.MAX_VALUE).listener(new UserRecordWriteListener()).build();
    }
}
