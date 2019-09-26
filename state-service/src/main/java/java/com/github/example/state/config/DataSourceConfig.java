package java.com.github.example.state.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties
public class DataSourceConfig {



    @Bean
    @ConfigurationProperties(prefix = "database.datasource")
    public DataSource configureDataSource() {
        return DataSourceBuilder.create().build();
    }


    }
