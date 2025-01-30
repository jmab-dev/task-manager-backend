package jmab.dev.task_manager_backend.repositories;

import jmab.dev.task_manager_backend.model.TaskModel;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<TaskModel, String> {
}