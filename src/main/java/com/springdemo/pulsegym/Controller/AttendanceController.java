package com.springdemo.pulsegym.Controller;

import com.springdemo.pulsegym.Service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/receptionist")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    
    @PostMapping("/check-in/{memberId}")
    public ResponseEntity<String> checkIn(@PathVariable int memberId) {
        String result = attendanceService.checkInMember(memberId);
        return ResponseEntity.ok(result);
    }
}