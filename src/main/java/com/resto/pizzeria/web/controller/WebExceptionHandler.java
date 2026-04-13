package com.resto.pizzeria.web.controller;

import com.resto.pizzeria.web.exception.ApiResponseException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class WebExceptionHandler {
    @ExceptionHandler(ApiResponseException.class)
    public String handleApiError(
            final ApiResponseException ex,
            final Model model) {
        model.addAttribute("errorMessage", ex);

        return "pages/error";
    }
}
