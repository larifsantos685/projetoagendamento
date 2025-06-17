package com.example.task_manager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.task_manager.classes.Task;

@Repository
public interface TaskRepositories extends JpaRepository<Task, Integer>{
}
