package com.springdemo.pulsegym.Controller;
import com.springdemo.pulsegym.Service.AdminService;
import com.springdemo.pulsegym.Service.CloudGatewayService;
import com.springdemo.pulsegym.Service.MemberService;
import com.springdemo.pulsegym.Service.ReceptionistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gateway")
public class CloudGatewayController {

    @Autowired
    private CloudGatewayService cloudService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private ReceptionistService receptionistService;

    @GetMapping("/members")
    public Object listMembers() {
        cloudService.logRequest("/members");
        var result = memberService.listMembers();
        cloudService.logResponse("/members");
        return result;
    }

    @GetMapping("/receptionists")
    public Object listReceptionists() {
        cloudService.logRequest("/receptionists");
        var result = receptionistService.getReceptionists();
        cloudService.logResponse("/receptionists");
        return result;
    }

    @DeleteMapping("/admin/{id}")
    public Object deleteAdmin(@PathVariable int id) {
        cloudService.logRequest("/admin/delete");
        adminService.deleteAdmin(id);
        cloudService.logResponse("/admin/delete");
        return "Admin deleted.";
    }
}
