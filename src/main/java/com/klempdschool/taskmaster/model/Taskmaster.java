package com.klempdschool.taskmaster.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.UUID;

@DynamoDBTable(tableName = "Taskmaster")
public class Taskmaster {
    private String id;
    private String title;
    private String description;
    private String assignee;
    private String status;
    private String pic;

    public Taskmaster() {

    }

    public Taskmaster(String title, String description, String assignee, String status) {
        this.title = title;
        this.description = description;
        this.assignee = assignee;
        this.status = status;
    }

    public Taskmaster(String title, String description, String assignee, String status, String Pic) {
        this.title = title;
        this.description = description;
        this.assignee = assignee;
        this.status = status;
        this.pic = pic;
    }

    @DynamoDBHashKey
    @DynamoDBAutoGeneratedKey
    public String getId() {
        return id;
    }

    public void setId(String id) { this.id = id; }

    @DynamoDBAttribute
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @DynamoDBAttribute
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @DynamoDBAttribute
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @DynamoDBAttribute
    public String getAssignee() { return assignee; }

    public void setAssignee(String assignee) { this.assignee = assignee; }

    @DynamoDBAttribute
    public String getPic() { return pic; }

    public void setPic(String pic) { this.pic = pic; }
}