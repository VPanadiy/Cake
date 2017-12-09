package com.Aleksandr.Cake.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartException;

import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exception(final Throwable throwable, final Model model) {
        logger.error("Exception during execution of SpringSecurity application", throwable);
        String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }

    @ExceptionHandler(IOException.class)
    public String handleIOException(Exception e, final Model model) {
        logger.error("IOException handler executed");
        model.addAttribute("errorMessage", e.getMessage());
        return "error";
    }

    @ExceptionHandler(MultipartException.class)
    public String multipartException(MultipartException e, final Model model) {
        logger.info("Exception during loading multipart file");
        model.addAttribute("errorMessage", e.getCause().getMessage());
        return "error";
    }

}
