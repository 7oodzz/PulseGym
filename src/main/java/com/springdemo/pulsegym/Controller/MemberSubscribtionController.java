package com.springdemo.pulsegym.Controller;

import com.springdemo.pulsegym.Model.MemberSubscription;
import com.springdemo.pulsegym.Service.MemberSubscriptionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MemberSubscribtionController {
    @Autowired
    MemberSubscriptionService memberSubscriptionService;

    @PostMapping("/addMemberSubscription")
    public Object addSubscriptionToMember(@Valid @RequestBody MemberSubscription memberSubscription, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            // Return all validation errors as a list of messages
            List<String> errors = bindingResult.getFieldErrors()
                    .stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);

        }
       int memberId= memberSubscription.getMemberId();
        int subId =memberSubscription.getSubscriptionBundleId();

        return memberSubscriptionService.addSubscriptionToMember(memberId, subId) ;

    }
    @GetMapping("/listMemberSubscriptions")
    public List<MemberSubscription> list(){
        return memberSubscriptionService.listMemberSubscriptions();
    }
    @DeleteMapping("removeMemberSubscribtion/{memberSubId}")
    public ResponseEntity removeMemberSubscription(@PathVariable int memberSubId){
        memberSubscriptionService.removeSubFromUser(memberSubId);
        return ResponseEntity.ok("member's subscription with id: " + memberSubId + "deleted succefully" );
    }


}
