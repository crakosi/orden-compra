package mx.com.compra.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import mx.com.compra.dto.ApiResponse;
import mx.com.compra.util.exception.IdNotFoundException;

@RestControllerAdvice
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestController {

	@ExceptionHandler({IdNotFoundException.class})
    public ApiResponse handleIdNotFound(RuntimeException exception) {
        return ApiResponse.builder()
                .error(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .success(false)
                .build();
    }
}
