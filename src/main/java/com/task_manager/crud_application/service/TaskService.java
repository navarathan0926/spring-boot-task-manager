package com.task_manager.crud_application.service;

import com.task_manager.crud_application.entity.Task;
import com.task_manager.crud_application.exception.NotFoundException;
import com.task_manager.crud_application.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final RestTemplate restTemplate;

    String url = "https://jsonplaceholder.typicode.com/todos";


    @Autowired
    public TaskService(TaskRepository taskRepository, RestTemplate restTemplate){
        this.taskRepository = taskRepository;
        this.restTemplate = restTemplate;
    }

    public List<Task> callExternalApi() {
        Task[] tasks = restTemplate.getForObject(url, Task[].class);
        return Arrays.asList(tasks);
    }


    public Task callExternalTaskById(Long id){
        Task task = restTemplate.getForObject(url+"/"+id, Task.class);
        return task;
    }

    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    @Cacheable(value= "task", key="#id")
    public Task getTaskById(Long id){
        return taskRepository.findById(id).orElse(null);
    }

    public Task saveTask(Task task){
        return taskRepository.save(task);
    }

    @CachePut(value= "task", key="#id")
    public Task updateTask(Long id, Task taskDetails){
        Task task = taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Task not found"));
        if(task != null){
            task.setCompleted(taskDetails.getCompleted());
            task.setTitle(taskDetails.getTitle());
            return taskRepository.save(task);
        }
        return null;
    }

    @Transactional
    @CacheEvict(value= "task", key="#id")
    public void deleteTask(Long id){
        taskRepository.deleteById(id);
    }
}
