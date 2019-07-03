package com.klempdschool.taskmaster.repository;

import com.klempdschool.taskmaster.model.Taskmaster;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableScan
public interface TaskmasterRepository extends CrudRepository<Taskmaster, String> {
    List<Taskmaster> findByAssignee(String name);
}