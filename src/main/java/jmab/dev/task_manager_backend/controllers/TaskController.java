package jmab.dev.task_manager_backend.controllers;

import jmab.dev.task_manager_backend.model.dto.TaskDto;
import jmab.dev.task_manager_backend.services.TaskService;
import jmab.dev.task_manager_backend.services.exception.TaskServiceException;
import jmab.dev.task_manager_backend.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping(path = "/health", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> health() throws TaskServiceException {
        return new ResponseEntity<>(taskService.health(), HttpStatus.OK);
    }

    @GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getTasks() throws TaskServiceException {
        return new ResponseEntity<>(taskService.getTasks(), HttpStatus.OK);
    }

    @PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createTask(@Validated(TaskDto.Create.class) @RequestBody TaskDto task) throws TaskServiceException {
            return new ResponseEntity<>(taskService.saveTask(task), HttpStatus.OK);
    }

    @PutMapping(path = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateTask(@Validated(TaskDto.Update.class) @RequestBody TaskDto task) throws TaskServiceException {
        return new ResponseEntity<>(taskService.updateTask(task),HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteTaskById(@PathVariable("id") String id) throws TaskServiceException {
        return new ResponseEntity<>(taskService.deleteTask(id),HttpStatus.OK);
    }

    @GetMapping(path = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getStatus() throws TaskServiceException {
        return new ResponseEntity<>(taskService.getStatusTask(),HttpStatus.OK);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        Response response = new Response("500", "Error en la petición", errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
