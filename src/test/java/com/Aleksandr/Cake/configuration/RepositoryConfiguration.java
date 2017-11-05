package com.Aleksandr.Cake.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//tells the Spring Framework this is a Java configuration class
@Configuration
//tells Spring Boot to do its auto configuration magic.
// This is what has Spring Boot automatically create the Spring Beans with sensible defaults for our tests
@EnableAutoConfiguration
//specifies the packages to look for JPA Entities
@EntityScan(basePackages = {"com.Aleksandr.Cake.model"})
//enables the auto configuration of Spring Data JPA
@EnableJpaRepositories(basePackages = {"com.Aleksandr.Cake.repository"})
//enables Spring’s annotation driven transaction management
@EnableTransactionManagement
//Through this configuration, we have everything we need to use the H2 database with Spring Data JPA in JUnit tests
public class RepositoryConfiguration {
}
