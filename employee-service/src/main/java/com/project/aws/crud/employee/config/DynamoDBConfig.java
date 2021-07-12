package com.project.aws.crud.employee.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configs do banco de dados DynamoDB
 */
@Slf4j
@Configuration
@EnableDynamoDBRepositories(basePackages = "com.project.aws.crud.employee.repository")
public class DynamoDBConfig {

    @Value("${amazon.dynamodb.endpoint}")
    private String endpoint;
    @Value("${amazon.aws.accesskey}")
    private String accessKey;
    @Value("${amazon.aws.secretkey}")
    private String secretKey;
    @Value("${amazon.aws.region}")
    private String region;

    /**
     * Bean responsável por instanciar o Client do banco de dados
     * @return {@code AmazonDynamoDB} dados de conexão com o banco de dados
     */
    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        log.info("M=amazonDynamoDB, message=Configuring DynamonDB. endpoint={}", endpoint);
        AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBAsyncClientBuilder.standard()
                                            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, region))
                                            .withCredentials(awsCredentialsProvider())
                                            .build();
        return amazonDynamoDB;
    }

    /**
     * Bean responsável por instanciar as credenciais da AWS
     * @return {@code AWSCredentialsProvider} dados da credencial
     */
    @Bean
    public AWSCredentialsProvider awsCredentialsProvider() {
        log.info("M=awsCredentialsProvider, message=Configuring AWS Credentials. accessKey={}, secretKey={}", accessKey, secretKey);
        return new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey));
    }
}
