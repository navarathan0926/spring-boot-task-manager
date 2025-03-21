package com.task_manager.crud_application.controller;

import com.task_manager.crud_application.entity.Task;
import com.task_manager.crud_application.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/taskById/{id}")
    public ResponseEntity<Task> getExternalTaskById(@PathVariable Long id){
        Task task =  taskService.callExternalTaskById(id);
        if(task != null){
            return ResponseEntity.ok(task);
        }
        return ResponseEntity.notFound().build();
    }


    @GetMapping("/fetch")
    public ResponseEntity<List<Task>> getTasksFromAPI() {
        List<Task> tasks = taskService.callExternalApi();
        // need to check empty
        return ResponseEntity.ok(tasks);
    }

    @GetMapping
    public List<Task> getAllTasks(){
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id){
        Task task = taskService.getTaskById(id);
        if(task != null){
            return ResponseEntity.ok(task);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public Task createTask(@RequestBody Task task){
        return taskService.saveTask(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task taskDetails){
        Task updatedTask = taskService.updateTask(id,taskDetails);
        if(updatedTask != null){
            return ResponseEntity.ok(updatedTask);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
