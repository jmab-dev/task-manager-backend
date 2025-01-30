package jmab.dev.task_manager_backend.mapper;

import jmab.dev.task_manager_backend.model.TaskModel;
import jmab.dev.task_manager_backend.model.dto.TaskDto;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TaskMapper {

    public TaskModel toModel(TaskDto taskDto){
        TaskModel taskModel = new TaskModel();
        if (taskDto.getId() == null) {
            taskModel.setId(UUID.randomUUID().toString());
        }else{
            taskModel.setId(taskDto.getId());
        }
        taskModel.setTitle(taskDto.getTitle());
        taskModel.setDescription(taskDto.getDescription());
        taskModel.setStatus(taskDto.getStatus());
        return  taskModel;
    }
}
