package com.jovo.ScienceCenter.config;


import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.impl.digest.SaltGenerator;
import org.camunda.bpm.engine.spring.ProcessEngineFactoryBean;
import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Arrays;

// Ova konfiguracija je dodata samo zato sto moramo podesiti PasswordEncryptor
@Configuration
// https://docs.camunda.org/manual/7.7/user-guide/spring-framework-integration/configuration/#using-spring-javaconfig
public class CamundaProcessEngineConfig {

    @Value("${spring.datasource.driver-class-name}")
    private Class driverClass;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("classpath:*.bpmn")
    private Resource[] deploymentResources;


    @Bean
    public DataSource dataSource() {
        // Use a JNDI data source or read the properties from
        // env or a properties file.
        // Note: The following shows only a simple data source
        // for In-Memory H2 database.

        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public SpringProcessEngineConfiguration processEngineConfiguration() {
        SpringProcessEngineConfiguration config = new SpringProcessEngineConfiguration();

        BCryptPasswordEncryptor bCryptPasswordEncryptor = new BCryptPasswordEncryptor();
        config.setPasswordEncryptor(bCryptPasswordEncryptor);
        //config.setCustomPasswordChecker(Arrays.asList(bCryptPasswordEncryptor));
        config.setSaltGenerator(new SaltGenerator() {
            // https://docs.camunda.org/manual/7.7/user-guide/process-engine/password-hashing/#customize-the-salt-generation
            @Override
            public String generateSalt() {
                return "";
            }
        });
        config.setDataSource(dataSource());
        config.setTransactionManager(transactionManager());

        config.setDatabaseSchemaUpdate("true");
        config.setHistory("audit");
        config.setJobExecutorActivate(true);
        config.setDeploymentResources(deploymentResources);

        return config;
    }

    @Bean
    public ProcessEngineFactoryBean processEngine() {
        ProcessEngineFactoryBean factoryBean = new ProcessEngineFactoryBean();
        factoryBean.setProcessEngineConfiguration(processEngineConfiguration());
        return factoryBean;
    }

//    @Bean
//    public RepositoryService repositoryService(ProcessEngine processEngine) {
//        return processEngine.getRepositoryService();
//    }
//
//    @Bean
//    public RuntimeService runtimeService(ProcessEngine processEngine) {
//        return processEngine.getRuntimeService();
//    }
//
//    @Bean
//    public TaskService taskService(ProcessEngine processEngine) {
//        return processEngine.getTaskService();
//    }

    // more engine services and additional beans ...

}
