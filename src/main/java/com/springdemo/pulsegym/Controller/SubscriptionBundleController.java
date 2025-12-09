package com.springdemo.pulsegym.Controller;

import com.springdemo.pulsegym.Model.SubscriptionBundle;
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
@RequestMapping("/admin/subscriptionBundles")
public class SubscriptionBundleController {
    @Autowired
    private SubscriptionService service;

    private Object validationErrors(BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream().map(error -> error.getField() + ": " + error.getDefaultMessage()).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        return null;
    }

    @PostMapping("/createSubBundle")
    public Object createSubscriptionBundle(@Valid @RequestBody SubscriptionBundle sub, BindingResult bindingResult) {
        Object error = validationErrors(bindingResult);
        if(error != null) {
            return error;
        }
        return service.create(sub);
    }

    @PutMapping("/updateSubBundle/{id}")
    public Object updateSubscriptionBundle(@PathVariable int id, @Valid @RequestBody SubscriptionBundle sub, BindingResult bindingResult) {
        Object error = validationErrors(bindingResult);
        if(error != null) {
            return error;
        }
        return service.update(id, sub);
    }

    @DeleteMapping("/deleteSubBundle/{id}")
    public Object deleteSubscriptionBundle(@PathVariable int id) {
        if(service.exists(id)) {
            service.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("{id} deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{id} not found");
    }

    @GetMapping("/listSubBundles")
    public List<SubscriptionBundle> listSubscriptionBundles() {
        return service.list();
    }


}
