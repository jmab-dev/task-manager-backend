package jmab.dev.task_manager_backend.services;

import jmab.dev.task_manager_backend.model.dto.TaskDto;
import jmab.dev.task_manager_backend.services.exception.TaskServiceException;

public interface TaskService {

    String health() throws TaskServiceException;

    String getTasks() throws TaskServiceException;

    String saveTask(TaskDto task) throws TaskServiceException;

    String updateTask(TaskDto task) throws TaskServiceException;

    String deleteTask(String id) throws TaskServiceException;

    String getStatusTask() throws TaskServiceException;
}
