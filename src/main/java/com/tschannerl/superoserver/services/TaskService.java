package com.tschannerl.superoserver.services;

import com.tschannerl.superoserver.models.Task;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface TaskService {
    Optional<Task> findById(Long id);
    List<Task> findAllTask();
    Task save(Task task) throws Exception;
}
