package com.task_manager.crud_application.service;

import com.task_manager.crud_application.entity.Task;
import com.task_manager.crud_application.exception.NotFoundException;
import com.task_manager.crud_application.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
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
