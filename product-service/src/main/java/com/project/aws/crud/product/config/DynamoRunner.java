package com.project.aws.crud.product.config;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ResourceInUseException;
import com.project.aws.crud.product.model.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class DynamoRunner implements ApplicationRunner {

    private final DynamoDBMapper mapper;
    private final DynamoDBConfig config;

    @Override
    public void run(ApplicationArguments args) {
        log.info("M=run, message=Creating tables in DynamoDB");
        List<Class> classes = new ArrayList<>();
        classes.add(Product.class);

        classes.forEach(clazz -> {
            CreateTableRequest tableRequest = mapper.generateCreateTableRequest(clazz);
            tableRequest.setProvisionedThroughput(
                    new ProvisionedThroughput(1L, 1L));
            try {
                config.amazonDynamoDB().createTable(tableRequest);
            } catch (ResourceInUseException e) {
                return;
            }
        });
        log.info("M=run, message=Tables created with Success in DynamoDB");
    }
}
