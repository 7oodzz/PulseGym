package com.springdemo.pulsegym.Controller;


import com.springdemo.pulsegym.DTO.ReceptionistRequest;
import com.springdemo.pulsegym.Model.Receptionist;
import com.springdemo.pulsegym.Service.ReceptionistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/receptionist")
public class ReceptionistController {

    @Autowired
    ReceptionistService receptionistService;

    @GetMapping("/")
    public List<Receptionist> getReceptionists(){
        return receptionistService.getReceptionists();
    }

    @PostMapping("/add")
    public Object addReceptionist(@RequestBody ReceptionistRequest request){

        return receptionistService.addReceptionist(request.getName(),request.getSsn());
    }

    @PutMapping("/update/{ssn}")
    public Object updateReceptionist(@PathVariable("ssn") String ssn,@RequestBody Receptionist receptionist){
        return receptionistService.updateReceptionist(ssn,receptionist);
    }

    @DeleteMapping("/delete/{ssn}")
    public void deleteReceptionist(@PathVariable("ssn") String ssn){
       receptionistService.deleteReceptionist(ssn);
    }
}
