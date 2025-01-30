package jmab.dev.task_manager_backend.util;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Response {

    private String status;
    private String message;
    private Object payload;

    public Response(String status, String message, Object payload) {
        super();
        this.status = status;
        this.message = message;
        this.payload = payload;
    }

}
