package com.task_manager.crud_application.service;

import com.task_manager.crud_application.entity.Task;
import com.task_manager.crud_application.exception.NotFoundException;
import com.task_manager.crud_application.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final RestTemplate restTemplate;

//    String url = "https://jsonplaceholder.typicode.com/todos";
    @Value("${external.api.url}")
    private String apiUrl;


    @Autowired
    public TaskService(TaskRepository taskRepository, RestTemplate restTemplate){
        this.taskRepository = taskRepository;
        this.restTemplate = restTemplate;
    }

    public List<Task> getAllTasks(){
        List<Task> tasks =  taskRepository.findAll();
        if (tasks.isEmpty()) {
            throw new NotFoundException("No tasks available.");
        }
        return tasks;
    }

    @Cacheable(value= "task", key="#id")
    public Task getTaskById(Long id){
        return taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Task with ID " + id + " not found"));
    }

    public Task saveTask(Task task){
        return taskRepository.save(task);
    }


    @CachePut(value= "task", key="#id")
    public Task updateTask(Long id, Task taskDetails){
        Task task = taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Task not found for ID "+id));
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



    // operations with external API
    public List<Task> callExternalApi() {
        try{
            Task[] tasks = restTemplate.getForObject(apiUrl, Task[].class);
            if(tasks == null){
                throw new NotFoundException("No tasks found from external API");
            }
            return Arrays.asList(tasks);
        }catch (HttpClientErrorException.NotFound ex) {
            throw new NotFoundException("No tasks found from external API");
        } catch (RestClientException ex) {
            throw new RuntimeException("Error fetching task from external API: " + ex.getMessage());
        }
    }


    @Cacheable(value= "externalTask", key="#id")
    public Task callExternalTaskById(Long id){
        try{
            Task task = restTemplate.getForObject(apiUrl+"/"+id, Task.class);
            if(task == null){
                throw new NotFoundException("No tasks found for ID "+id+" from external API");
            }
            return task;
        }catch (HttpClientErrorException.NotFound ex) {
            throw new NotFoundException("No tasks found for ID " + id + " from external API");
        } catch (RestClientException ex) {
            throw new RuntimeException("Error fetching task from external API: " + ex.getMessage());
        }
    }


    public Task saveTaskFromExternal(Long id){
        Task task =  callExternalTaskById(id);
        Task taskDetails = new Task();
        if(task != null){
            taskDetails.setCompleted(task.getCompleted());
            taskDetails.setTitle(task.getTitle());
            return taskRepository.save(taskDetails);
        }
        return null;
    }
}
