package java.com.github.example.catalog.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import java.com.github.example.catalog.audit.AuditorAwareImpl;

import javax.sql.DataSource;

/**
 * Created by RV.Luchnikov on 04.02.2018.
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef="auditorProvider")
public class DataSourceConfig {
    @Bean
    @ConfigurationProperties(prefix = "database.datasource")
    public DataSource configureDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    AuditorAware<String> auditorProvider() {
        return new AuditorAwareImpl();
    }
}


