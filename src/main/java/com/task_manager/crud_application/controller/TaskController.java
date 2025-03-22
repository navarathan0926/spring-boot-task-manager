package com.task_manager.crud_application.controller;

import com.task_manager.crud_application.entity.Task;
import com.task_manager.crud_application.exception.NotFoundException;
import com.task_manager.crud_application.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    @GetMapping
    public List<Task> getAllTasks(){
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id){
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    @PostMapping
    public Task createTask(@RequestBody Task task){
        return taskService.saveTask(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task taskDetails){
        Task updatedTask = taskService.updateTask(id,taskDetails);
            return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    // operations with the external API
    @GetMapping("/taskById/{id}")
    public ResponseEntity<Task> getExternalTaskById(@PathVariable Long id){
        Task task =  taskService.callExternalTaskById(id);
        return ResponseEntity.ok(task);
    }

    @GetMapping("/fetch")
    public ResponseEntity<List<Task>> getTasksFromAPI() {
        List<Task> tasks = taskService.callExternalApi();
        return ResponseEntity.ok(tasks);
    }
    

    @PostMapping("/saveExternalData")
    public Task createTaskFromExternalData(@RequestParam Long id){
        return taskService.saveTaskFromExternal(id);
    }
}
