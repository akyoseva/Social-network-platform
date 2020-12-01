package com.bulpros.javaknights.controllers;

import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.sql.SQLException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResponseStatusException.class)
    public String entityNotFound(Exception exception, Model model){
        model.addAttribute("exception",exception.getMessage());
        return "exception";
    }

    @ExceptionHandler(IOException.class)
    public String fileError(Exception exception, Model model){
        model.addAttribute("exception","File error.Please upload file again.");
        return "exception";
    }

    @ExceptionHandler(SQLException.class)
    public String dataBaseError(Exception exception, Model model){
        model.addAttribute("exception","Could not execute operation.Please try again.");
        return "exception";
    }

}
