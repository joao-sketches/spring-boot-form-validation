package io.spring;

import io.spring.mvc.response.ResponseObject;
import io.spring.mvc.response.ValidationError;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @RestController
    static class TaskController {
        @RequestMapping(value = "/tasks", method = RequestMethod.POST)
        public ResponseEntity<ResponseObject<Task>> create(@Valid @RequestBody Task task, BindingResult validation) {
            if (validation.hasErrors()) {
                return ResponseEntity.badRequest().body(ResponseObject.withError(new ValidationError(validation)));
            } else {
                return ResponseEntity.ok().body(ResponseObject.withData(task));
            }
        }

    }
}
