package com.springdemo.pulsegym.Controller;

import com.springdemo.pulsegym.DTO.MemberIdRequest;
import com.springdemo.pulsegym.Model.MemberSubscription;
import com.springdemo.pulsegym.Service.MemberSubscriptionService;
import com.springdemo.pulsegym.Util.ErrorMessageUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/receptionist/memberSubscription")
public class MemberSubscribtionController {
    @Autowired
    MemberSubscriptionService memberSubscriptionService;

    @PostMapping("/addMemberSubscription")
    public Object addSubscriptionToMember(@Valid @RequestBody MemberIdRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ErrorMessageUtil.errorMessage(bindingResult);

        }

        return memberSubscriptionService.addSubscriptionToMember(request.getItemId(), request.getMemberId());

    }

    @GetMapping("/listMemberSubscriptions")
    public List<MemberSubscription> list() {
        return memberSubscriptionService.listMemberSubscriptions();
    }

    @DeleteMapping("removeMemberSubscribtion/{memberSubId}")
    public ResponseEntity<String> removeMemberSubscription(@PathVariable int memberSubId) {
        memberSubscriptionService.removeSubFromMember(memberSubId);
        return ResponseEntity.ok("member's subscription with id: " + memberSubId + "deleted succefully");
    }


}
