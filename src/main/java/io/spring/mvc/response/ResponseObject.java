package io.spring.mvc.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

/**
 * @author Joao Pedro Evangelista
 */
public class ResponseObject<T> {

    @JsonUnwrapped
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ValidationError errors;

    public T getData() {
        return data;
    }

    public ValidationError getErrors() {
        return errors;
    }

    public static <T> ResponseObject<T> withError(ValidationError error) {
        ResponseObject<T> responseObject = new ResponseObject<>();
        responseObject.errors =  error;
        return responseObject;
    }

    public static <T> ResponseObject<T> withData(T content){
        ResponseObject<T> responseObject = new ResponseObject<>();
        responseObject.data =  content;
        return responseObject;
    }
}
