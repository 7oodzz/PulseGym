package com.springdemo.pulsegym.Controller;

import com.springdemo.pulsegym.Model.Subscription;
import com.springdemo.pulsegym.Service.SubscriptionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {
    @Autowired
    private SubscriptionService service;

    private Object validationErrors(BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream().map(error -> error.getField() + ": " + error.getDefaultMessage()).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        return null;
    }

    @PostMapping("/create")
    public Object createBundle(@Valid @RequestBody Subscription sub, BindingResult bindingResult) {
        Object error = validationErrors(bindingResult);
        if(error != null) {
            return error;
        }
        return service.create(sub);
    }

    @PutMapping("/update/{id}")
    public Object updateBundle(@PathVariable int id, @Valid @RequestBody Subscription sub, BindingResult bindingResult) {
        Object error = validationErrors(bindingResult);
        if(error != null) {
            return error;
        }
        return service.update(id, sub);
    }

    @DeleteMapping("/delete/{id}")
    public Object deleteBundle(@PathVariable int id) {
        if(service.exists(id)) {
            service.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("{id} deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{id} not found");
    }

    @GetMapping("/list")
    public List<Subscription> listBundles() {
        return service.list();
    }

    @GetMapping("/status/{id}")
    public Object subscriptionStatus(@PathVariable int id) {
        if(service.exists(id)) {
            return service.status(id);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{id} not found");
    }
}
