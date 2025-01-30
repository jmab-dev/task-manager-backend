package jmab.dev.task_manager_backend.services.Impl;

import jmab.dev.task_manager_backend.mapper.TaskMapper;
import jmab.dev.task_manager_backend.model.StatusModel;
import jmab.dev.task_manager_backend.model.TaskModel;
import jmab.dev.task_manager_backend.model.dto.TaskDto;
import jmab.dev.task_manager_backend.repositories.StatusRepository;
import jmab.dev.task_manager_backend.util.Response;
import jmab.dev.task_manager_backend.repositories.TaskRepository;
import jmab.dev.task_manager_backend.services.TaskService;
import jmab.dev.task_manager_backend.services.exception.TaskServiceException;
import jmab.dev.task_manager_backend.util.Tools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static jmab.dev.task_manager_backend.constants.StatusConstants.*;

@Slf4j
@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    TaskMapper taskMapper;

    @Override
    public String health() throws TaskServiceException {
        try {
            return Tools.getJson(new Response(API_STATUS_SUCCESS, API_MESSAGE_SUCCESS, "Task Service is running OK"));
        } catch (Exception e) {
            log.error(e.getMessage());
            return Tools.getJson(new Response(API_STATUS_ERROR, "Task Service is failed: " + e.getMessage(), null));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public String getTasks() throws TaskServiceException {
        try {
            List<TaskModel> tasks = (List<TaskModel>) taskRepository.findAll();
            return Tools.getJson(new Response(API_STATUS_SUCCESS, API_MESSAGE_SUCCESS, tasks));
        }catch (Exception e){
            log.error(e.getMessage());
            return Tools.getJson(new Response(API_STATUS_ERROR, "Failed to retrieve taks: " + e.getMessage(), null));
        }
    }

    @Override
    @Transactional
    public String saveTask(TaskDto taskDto) throws TaskServiceException {
        try {
            if (!statusRepository.existsById(taskDto.getStatus())) {
                return Tools.getJson(new Response(API_STATUS_ERROR, API_MESSAGE_ERROR + ": Failed to save task, invalid state ", null));
            }
            TaskModel taskModel = taskRepository.save(taskMapper.toModel(taskDto));
            return Tools.getJson(new Response(API_STATUS_SUCCESS_CREATED, API_MESSAGE_SUCCESS, taskModel));
        }catch (Exception e){
            log.error(e.getMessage());
            return Tools.getJson(new Response(API_STATUS_ERROR, "Failed to save task: " + e.getMessage(), null));
        }
    }

    @Override
    @Transactional
    public String updateTask(TaskDto task) throws TaskServiceException {
        try {
            if (taskRepository.existsById(task.getId())) {
                if (!statusRepository.existsById(task.getStatus())) {
                    return Tools.getJson(new Response(API_STATUS_ERROR, API_MESSAGE_ERROR + ": Failed to save task, invalid state ", null));
                }
                TaskModel updatedTask = taskRepository.save(taskMapper.toModel(task));
                return Tools.getJson(new Response(API_STATUS_SUCCESS, API_MESSAGE_SUCCESS, updatedTask));
            }
            return Tools.getJson(new Response(API_STATUS_NOT_FOUND, "Failed to find task with id: " + task.getId(), null));
        }catch (Exception e){
            log.error(e.getMessage());
            return Tools.getJson(new Response(API_STATUS_ERROR, "Failed to update task: " + e.getMessage(), null));
        }
    }

    @Override
    @Transactional
    public String deleteTask(String id) throws TaskServiceException {
        try {
            if (taskRepository.existsById(id)) {
                taskRepository.deleteById(id);
                return Tools.getJson(new Response(API_STATUS_SUCCESS_NO_CONTENT, API_MESSAGE_SUCCESS + ": Task deleted.", null));
            }
            return Tools.getJson(new Response(API_STATUS_NOT_FOUND, "Failed to find task with id: " + id, null));
        }catch (Exception e){
            log.error(e.getMessage());
            return Tools.getJson(new Response(API_STATUS_ERROR, "Failed to delete session: " + e.getMessage(), null));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public String getStatusTask() throws TaskServiceException {
        try {
            List<StatusModel> status = (List<StatusModel>) statusRepository.findAll();
            return Tools.getJson(new Response(API_STATUS_SUCCESS, API_MESSAGE_SUCCESS, status));
        }catch (Exception e){
            log.error(e.getMessage());
            return Tools.getJson(new Response(API_STATUS_ERROR, "Failed to retrieve status list: " + e.getMessage(), null));
        }
    }
}
