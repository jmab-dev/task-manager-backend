package jmab.dev.task_manager_backend.model.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class TaskDto {

    @Null(message = "El ID debe ser nulo al crear una tarea", groups = Create.class)
    @NotNull(message = "El ID es obligatorio al actualizar una tarea", groups = Update.class)
    private String id;

    @NotNull(message = "El título no puede ser nulo", groups = {Create.class, Update.class})
    @NotBlank(message = "El título es obligatorio", groups = {Create.class, Update.class})
    @Size(min = 1, max = 100, message = "El título debe tener entre 1 y 100 caracteres", groups = {Create.class, Update.class})
    private String title;

    @Size(max = 500, message = "La descripción no puede exceder los 500 caracteres", groups = {Create.class, Update.class})
    private String description;

    @NotNull(message = "El estado no puede ser nulo", groups = {Create.class, Update.class})
    @Min(value = 1, message = "El estado no puede ser menor a 1", groups = {Create.class, Update.class})
    private Integer status;

    // Interfaces para validaciones condicionales
    public interface Create {}
    public interface Update {}
}
