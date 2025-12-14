package com.springdemo.pulsegym.Controller;

import java.util.List;
import java.util.stream.Collectors;

import com.springdemo.pulsegym.DTO.MemberIdRequest;
import com.springdemo.pulsegym.Util.ErrorMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springdemo.pulsegym.Model.MemberSession;
import com.springdemo.pulsegym.Service.MemberSessionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("receptionist/memberSession")
public class MemberSessionController {

    @Autowired
    MemberSessionService memberSessionService;

    @PostMapping("/addMemberSession")
    public Object addSessionToMember(@Valid @RequestBody MemberIdRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ErrorMessageUtil.errorMessage(bindingResult);

        }

        return memberSessionService.addSessionToMember(request.getItemId(), request.getMemberId());

    }

    @GetMapping("/listMemberSessions")
    public List<MemberSession> list() {
        return memberSessionService.listMemberSessions();
    }

    @DeleteMapping("removeMemberSession/{memberSessionId}")
    public ResponseEntity<String> removeMemberSession(@PathVariable int memberSessionId) {
        memberSessionService.removeSessionFromMember(memberSessionId);
        return ResponseEntity.ok("member's subscription with id: " + memberSessionId + "deleted succefully");
    }

}
