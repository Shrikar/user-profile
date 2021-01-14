package com.exostar.userprofile.batch.configuration;

import java.net.MalformedURLException;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@Configuration
public class DataSourceInit {
    @Autowired
    private DataSource dataSource;

    @Value ("classpath:org/springframework/batch/core/schema-drop-mysql.sql")
    private Resource dropReopsitoryTables;

    @Value("classpath:org/springframework/batch/core/schema-mysql.sql")
    private Resource dataReopsitorySchema;

    @Bean
    public DataSourceInitializer dataSourceInitializer () throws MalformedURLException {
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.addScript(dropReopsitoryTables);
        databasePopulator.addScript(dataReopsitorySchema);
        databasePopulator.setIgnoreFailedDrops(true);

        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(databasePopulator);

        return initializer;
    }
}
