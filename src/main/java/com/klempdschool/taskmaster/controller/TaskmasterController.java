package com.klempdschool.taskmaster.controller;

import com.klempdschool.taskmaster.model.Taskmaster;
import com.klempdschool.taskmaster.repository.S3Client;
import com.klempdschool.taskmaster.repository.TaskmasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class TaskmasterController {

    private static final String AVAILABLE = "Available";
    private static final String ASSIGNED = "Assigned";
    private static final String ACCEPTED = "Accepted";
    private static final String FINISHED = "Finished";

    @Autowired
    private TaskmasterRepository taskmasterRepository;

    @Autowired
    S3Client s3Client;


    /*** Get all Tasks ***/
    @CrossOrigin
    @GetMapping("/tasks")
    public ResponseEntity<Iterable<Taskmaster>> findalltask() {
        return ResponseEntity.ok(taskmasterRepository.findAll());
    }

    /*** Create new Task ***/
    // Example Postman usage to add data:
    // http://localhost:8080/tasks/?title=Sample Task3&description=Created using postman&status=Available&assignee=Doug
    @CrossOrigin
    @PostMapping("/tasks")
    public ResponseEntity<Iterable<Taskmaster>> addnew(String title, String description, String assignee, String
            status) {
        Taskmaster newTask = new Taskmaster(title, description, assignee, status);
        taskmasterRepository.save(newTask);
        return ResponseEntity.ok(taskmasterRepository.findAll());
    }

    /*** Change user's task status ***/
    @CrossOrigin
    @PutMapping("/tasks/{id}/state")
    public ResponseEntity<Taskmaster> editTask(
            @PathVariable String id
    ) {

        Taskmaster statusChange = taskmasterRepository.findById(id).get();
        switch (statusChange.getStatus()) {
            case AVAILABLE:
                statusChange.setStatus(ASSIGNED);
                break;
            case ASSIGNED:
                statusChange.setStatus(ACCEPTED);
                break;
            case ACCEPTED:
                statusChange.setStatus(FINISHED);
                break;
        }
        taskmasterRepository.save(statusChange);
        return ResponseEntity.ok(statusChange);
    }

    /*** View assigned user's tasks ***/
    @GetMapping("/tasks/{name}/tasks")
    public ResponseEntity<List<Taskmaster>> getUserTask(
            @PathVariable String name
    ) {

        List<Taskmaster> inputName = taskmasterRepository.findByAssignee(name);
        if (inputName != null) {
            return ResponseEntity.ok(inputName);
        }
        return null;
    }

    /****** Re-assign task *********/
    @CrossOrigin
    @PutMapping("/tasks/{id}/assign/{assignee}")
    public ResponseEntity<Taskmaster> updateAssignee(
            @PathVariable String id,
            @PathVariable String assignee
    ) {

        Taskmaster current = taskmasterRepository.findById(id).get();
        if (!assignee.equals(current.getAssignee())) {
            current.setStatus(ASSIGNED);
            current.setAssignee(assignee);
            taskmasterRepository.save(current);
        }
        return ResponseEntity.ok(current);
    }

    /*** Store new Image ***/
    @CrossOrigin
    @PostMapping("/tasks/{id}/images")
    public ResponseEntity<Taskmaster> uploadFile(
            @PathVariable String id,
            @RequestPart(value = "file") MultipartFile file
    ) {

        String pic = this.s3Client.uploadFile(file);
        Taskmaster updateTask = taskmasterRepository.findById(id).get();
        updateTask.setPic(pic);
        taskmasterRepository.save(updateTask);
        return ResponseEntity.ok(updateTask);
    }
}