package mx.com.compra.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import mx.com.compra.dto.ApiResponse;

@RestControllerAdvice
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ExceptionRquestController {

	
	@ExceptionHandler({Exception.class})
    public ApiResponse exceptionError(Exception exception) {
        return ApiResponse.builder()
                .error(exception.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .success(false)
                .build();
    }
}
