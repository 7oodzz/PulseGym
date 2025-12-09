package com.springdemo.pulsegym.Service;

import com.springdemo.pulsegym.Model.Attendance;
import com.springdemo.pulsegym.Model.Member;
import com.springdemo.pulsegym.Repository.AttendanceRepository;
import com.springdemo.pulsegym.Repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private MemberRepository memberRepository;  

    @Override
    public String checkInMember(Long memberId) {
        
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found!"));
                
    
        Attendance attendance = new Attendance(member);
        attendanceRepository.save(attendance);

        return "Check-in successful for member: " + member.getName(); 
    }
}