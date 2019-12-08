package com.tschannerl.superoserver.repositories;

import com.tschannerl.superoserver.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
