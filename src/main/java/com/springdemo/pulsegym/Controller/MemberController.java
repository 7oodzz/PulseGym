package com.springdemo.pulsegym.Controller;

import com.springdemo.pulsegym.Model.Member;
import com.springdemo.pulsegym.Service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.AbstractBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MemberController {
    @Autowired
    private MemberService memberService;
    @PostMapping("/addMember")
    public Object addMember(@Valid @RequestBody Member member, BindingResult bindingResult){
       if(bindingResult.hasErrors()) {
           List<String> errors = bindingResult.getFieldErrors()
                   .stream()
                   .map(error -> error.getField() + ": " + error.getDefaultMessage())
                   .collect(Collectors.toList());
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
       }
       return memberService.addMember(member);

    }

    @PutMapping("/addMember")
    public Object updateMember(@Valid @RequestBody Member member, BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors()
                    .stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        return memberService.updateMember(member);

    }


}
