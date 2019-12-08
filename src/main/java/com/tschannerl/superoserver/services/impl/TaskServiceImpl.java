package com.tschannerl.superoserver.services.impl;


import com.tschannerl.superoserver.models.Task;
import com.tschannerl.superoserver.repositories.TaskRepository;
import com.tschannerl.superoserver.services.TaskService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    private static final Logger logger = LogManager.getLogger(TaskServiceImpl.class);

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Optional<Task> findById(Long id) {
        logger.info(String.format("Buscando uma Tarefas"));
        return taskRepository.findById(id);
    }

    @Override
    public List<Task> findAllTask() {
        logger.info(String.format("Buscando todos as Tarefas"));
        return taskRepository.findAll();
    }

    @Override
    public Task save(Task task) throws Exception {
        logger.info(String.format("Salvando a Tarefa [%s]", task.getTitle()));
        return taskRepository.save(task);
    }
}
