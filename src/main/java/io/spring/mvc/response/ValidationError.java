package io.spring.mvc.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Joao Pedro Evangelista
 */
public class ValidationError {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> causes = new ArrayList<>();

    private final String message;

    public ValidationError(String message) {
        this.message = message;
    }


    public ValidationError(String message, Errors errors) {
        this(message);
        for (ObjectError error : errors.getAllErrors()) {
            String defaultMessage = error.getDefaultMessage();
            this.causes.add(defaultMessage);
        }
    }

    public ValidationError(Errors errors) {
        this(String.format("Validation has %s error(s).", errors.getErrorCount()), errors);
    }


    public List<String> getCauses() {
        return causes;
    }

    public String getMessage() {
        return message;
    }

    public void addError(ObjectError error) {
        this.causes.add(error.getDefaultMessage());

    }
}
