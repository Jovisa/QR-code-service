package com.hyperskill.qrcodeapi.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.hyperskill.qrcodeapi.model.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidParamException.class)
    public ResponseEntity<ErrorResponse> handleBadRequests(InvalidParamException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(QRCodeGenerationException.class)
    public ResponseEntity<ErrorResponse> handleQrGenerationException(QRCodeGenerationException e) {
        return ResponseEntity.internalServerError().body(new ErrorResponse(e.getMessage()));
    }

    // for @requestParam
//    @ExceptionHandler(ConstraintViolationException.class)
//    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
//        String message = ex.getConstraintViolations()
//                .stream()
//                .sorted(Comparator.comparing(v -> getPriority(v.getPropertyPath().toString())))
//                .findFirst()
//                .map(ConstraintViolation::getMessage)
//                .orElse("Validation failed");
//        return ResponseEntity.badRequest().body(new ErrorResponse(message));
//    }
//
    //for @RequestBody
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
//        String errorMessage = ex.getBindingResult()
//                .getFieldErrors()
//                .stream()
//                .findFirst()
//                .map(DefaultMessageSourceResolvable::getDefaultMessage)
//                .orElse("Validation failed");
//
//        return ResponseEntity.badRequest().body(new ErrorResponse(errorMessage));
//    }

}
