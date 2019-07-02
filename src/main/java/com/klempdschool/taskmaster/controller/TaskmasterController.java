package com.klempdschool.taskmaster.controller;

import com.klempdschool.taskmaster.Taskmaster;
import com.klempdschool.taskmaster.repository.TaskmasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TaskmasterController {

    private static final String AVAILABLE="Available";
    private static final String ASSIGNED="Assigned";
    private static final String ACCEPTED="Accepted";
    private static final String FINISHED="Finished";

    @Autowired
    private TaskmasterRepository taskmasterRepository;

    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
        public ResponseEntity<Iterable<Taskmaster>> findalltask(){
            return ResponseEntity.ok(taskmasterRepository.findAll());
    }

    @RequestMapping(value = "/tasks", method = RequestMethod.POST)
    public ResponseEntity<Iterable<Taskmaster>> addnew(String title, String description, String status){
        Taskmaster potato = new Taskmaster(title, description, status);
        taskmasterRepository.save(potato);
        return ResponseEntity.ok(taskmasterRepository.findAll());
    }

    @RequestMapping(value = "/tasks/{id}/status", method = RequestMethod.PUT)
    public ResponseEntity<Taskmaster> editTask(@PathVariable String id){
       Taskmaster result = taskmasterRepository.findById(id).get();
        switch(result.getStatus()){
            case AVAILABLE:
                result.setStatus(ASSIGNED);
                break;
            case ASSIGNED:
                result.setStatus(ACCEPTED);
                break;
            case ACCEPTED:
                result.setStatus(FINISHED);
                break;
        }
        taskmasterRepository.save(result);
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Taskmaster> deleteTask(@PathVariable String id) {
        Taskmaster result = taskmasterRepository.findById(id).get();

        return ResponseEntity.ok(result);
    }
}
