package com.tschannerl.superoserver.services;

import com.tschannerl.superoserver.models.Task;
import org.springframework.stereotype.Service;

import java.util.List;


public interface TaskService {
    List<Task> findAllTask();
    Task save(Task task) throws Exception;
}
