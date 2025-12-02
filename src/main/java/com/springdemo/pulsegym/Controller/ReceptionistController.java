package com.springdemo.pulsegym.Controller;

import com.springdemo.pulsegym.Util.JwtUtil;
import com.springdemo.pulsegym.DTO.ReceptionistRequest;
import com.springdemo.pulsegym.Model.Receptionist;
import com.springdemo.pulsegym.Service.ReceptionistService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestHeader;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/receptionist")
public class ReceptionistController {

    private JwtUtil jwt;

    @GetMapping("/dashboard")
    public ResponseEntity<String> dashboard(@RequestHeader("Authorization") String token) {
        String type = jwt.extractType(token);
        if (!"Receptionist".equals(type)) {
            return ResponseEntity.status(403).body("Access denied");
        }
        return ResponseEntity.ok("Welcome Receptionist!");
    }

    @Autowired
    ReceptionistService receptionistService;

    @GetMapping("/")
    public List<Receptionist> getReceptionists(){
        return receptionistService.getReceptionists();
    }

    @PostMapping("/add")
    public Object addReceptionist(@Valid @RequestBody ReceptionistRequest request, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<String> errors=bindingResult.getFieldErrors()
                    .stream()
                    .map(error->error.getField()+": "+error.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        return receptionistService.addReceptionist(request.getName(),request.getSsn());
    }

    @PutMapping("/update/{ssn}")
    public Object updateReceptionist(@Valid @PathVariable("ssn") String ssn,@RequestBody Receptionist receptionist, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<String> errors=bindingResult.getFieldErrors()
                    .stream()
                    .map(error->error.getField()+": "+error.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        return receptionistService.updateReceptionist(ssn,receptionist);
    }

    @DeleteMapping("/delete/{ssn}")
    public Object deleteReceptionist(@Valid @PathVariable("ssn") String ssn, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<String> errors=bindingResult.getFieldErrors()
                    .stream()
                    .map(error->error.getField()+": "+error.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
       receptionistService.deleteReceptionist(ssn);
        return ResponseEntity.ok("Receptionist Deleted successfully");
    }
}
