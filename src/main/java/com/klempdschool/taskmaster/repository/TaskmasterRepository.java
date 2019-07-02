package com.klempdschool.taskmaster.repository;

import com.klempdschool.taskmaster.Taskmaster;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@EnableScan
public interface TaskmasterRepository extends CrudRepository<Taskmaster, String> {
}