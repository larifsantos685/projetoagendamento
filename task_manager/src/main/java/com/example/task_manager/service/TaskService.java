package com.example.task_manager.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.task_manager.classes.Task;

@Service
public class TaskService {
    private final List<Task> tasks = new ArrayList<>();
    private int currentId = 1;

    public TaskService() {}

    public void add(Task task){
        task.setId(currentId++); 
        task.setDataCriada(LocalDate.now());
        tasks.add(task);
    }

    public List<Task> getAll() { return tasks; }

    public Task get(Integer id) {
        for (Task t : tasks) {
            if(t.getId().equals(id)) {
                return t;
            }
        }
        return null;
    }

    // public Task get(Integer id){
    //     return get(new Task(id));
    // }

    @Override
    public String toString() {
        return tasks.toString();
    }

    public boolean update(Task updatedTask) {
        for (int i = 0; i < tasks.size(); i++) {
            Task current = tasks.get(i);
            if (current.getId().equals(updatedTask.getId())) {
                  updatedTask.setDataCriada(current.getDataCriada());  // ✅ Mantém a data original
                tasks.set(i, updatedTask);
                return true;
            }
        }
        return false;
    }

    public boolean remove(Integer id) {
        return tasks.remove(new Task(id));
    }
}

