package com.task_manager.crud_application.repository;

import com.task_manager.crud_application.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
