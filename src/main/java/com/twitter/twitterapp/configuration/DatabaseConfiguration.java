package com.twitter.twitterapp.configuration;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@EnableJpaRepositories
public class DatabaseConfiguration {

    @Configuration
    @Profile({"!embedded"})
    static class CloudConfig {
        @Bean
        @Primary
        LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) {
            return builder.dataSource(db())
                    .packages("com.twitter.twitterapp")
                    .persistenceUnit("postgres")
                    .build();
        }

        @Bean
        @Primary
        @ConfigurationProperties(prefix = "datasource.postgres")
        DataSource db(){
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName("org.postgresql.Driver");
            dataSource.setUrl(System.getenv("DB_CONN_URL"));
            dataSource.setUsername(System.getenv("DB_USERNAME"));
            dataSource.setPassword(System.getenv("DB_PASSWORD"));
            return  dataSource;
        }
    }

    @Configuration
    @Profile("embedded")
    static class LocalConfig {
        @Bean
        @Primary
        LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) throws IOException {
            return builder.dataSource(db())
                    .packages("com.twitter.twitterapp")
                    .persistenceUnit("postgres")
                    .build();
        }

        @Bean
        @Primary
        @FlywayDataSource
        DataSource db() throws IOException {
            EmbeddedPostgres pg = EmbeddedPostgres.builder().setPort(62776).start();
            return pg.getPostgresDatabase();
        }
    }


}
