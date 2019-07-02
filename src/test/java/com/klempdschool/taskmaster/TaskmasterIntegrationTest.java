package com.klempdschool.taskmaster;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.klempdschool.taskmaster.repository.TaskmasterRepository;
//import javafx.application.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TaskmasterApplication.class)
@WebAppConfiguration
@ActiveProfiles("local")
public class TaskmasterIntegrationTest {

    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Autowired
    TaskmasterRepository repository;

    private static final String EXPECTED_TITLE = "test title";
    private static final String EXPECTED_DESCRIPTION = "this is the description for taskmaster";
    private static final String EXPECTED_STATUS = "available";

    @Before
    public void setup() throws Exception {
        dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);

        CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(Taskmaster.class);

        tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));

        dynamoDBMapper.batchDelete((List<Taskmaster>)repository.findAll());
    }

    @Test
    public void readWriteTestCase() {
        Taskmaster potato = new Taskmaster(EXPECTED_TITLE, EXPECTED_DESCRIPTION, EXPECTED_STATUS);
        repository.save(potato);

        List<Taskmaster> result = (List<Taskmaster>) repository.findAll();

        assertTrue("Not empty", result.size() > 0);
        assertTrue("test title", result.get(0).getTitle().equals(EXPECTED_TITLE));
    }
}