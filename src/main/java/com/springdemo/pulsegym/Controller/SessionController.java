package com.springdemo.pulsegym.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springdemo.pulsegym.Model.SessionBundle;
import com.springdemo.pulsegym.Service.SessionBundleService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/sessions")
public class SessionController {
    @Autowired
    private final SessionBundleService sessionService;

    public SessionController(SessionBundleService sessionService) {
        this.sessionService = sessionService;
    }

    private Object validationErrors(BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream().map(error -> error.getField() + ": " + error.getDefaultMessage()).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        return null;
    }
    @PostMapping("/create")
    public Object create(@Valid @RequestBody SessionBundle session,BindingResult bindingResult) {
         Object error = validationErrors(bindingResult);
          if(error != null) {
            return error;
        }
         return sessionService.create(session);
    }

    @GetMapping
    public List<SessionBundle> getAllSessions() {
        return sessionService.list();
    }

    @PutMapping("/update/{id}")
    public Object updateBundle(@PathVariable int id, @Valid @RequestBody SessionBundle session, BindingResult bindingResult) {
        Object error = validationErrors(bindingResult);
        if(error != null) {
            return error;
        }
        return sessionService.update(id, session);
    }


     @DeleteMapping("/delete/{id}")
    public Object deleteBundle(@PathVariable int id) {
        if(sessionService.exists(id)) {
            sessionService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("{id} deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{id} not found");
    }
    
}
