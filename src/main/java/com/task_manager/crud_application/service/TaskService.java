package com.task_manager.crud_application.service;

import com.task_manager.crud_application.entity.Task;
import com.task_manager.crud_application.exception.NotFoundException;
import com.task_manager.crud_application.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public TaskService(TaskRepository taskRepository, RestTemplate restTemplate){
        this.taskRepository = taskRepository;
        this.restTemplate = restTemplate;
    }

    public List<Task> callExternalApi() {
        String url = "https://jsonplaceholder.typicode.com/todos";
//        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        Task[] tasks = restTemplate.getForObject(url, Task[].class);
        return Arrays.asList(tasks);
    }

    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id){
        return taskRepository.findById(id).orElse(null);
    }

    public Task saveTask(Task task){
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task taskDetails){
        Task task = taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Task not found"));;
        if(task != null){
            task.setCompleted(taskDetails.getCompleted());
            task.setTitle(taskDetails.getTitle());
            return taskRepository.save(task);
        }
        return null;
    }

    public void deleteTask(Long id){
        taskRepository.deleteById(id);
    }
}
