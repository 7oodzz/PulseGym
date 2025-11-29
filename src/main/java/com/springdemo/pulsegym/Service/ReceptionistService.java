package com.springdemo.pulsegym.Service;

import com.springdemo.pulsegym.DTO.ReceptionistRequest;
import com.springdemo.pulsegym.Model.Receptionist;
import com.springdemo.pulsegym.Repository.ReceptionistRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReceptionistService {

    @Autowired
    private final ReceptionistRepository receptionistRepo;


    public ReceptionistService(ReceptionistRepository receptionistRepo) {

        this.receptionistRepo = receptionistRepo;
    }

    public List<Receptionist> getReceptionists(){
        return receptionistRepo.findAll();
    }

    public Receptionist addReceptionist(String name ,String ssn) {
        if(receptionistRepo.findBySsn(ssn).isPresent()){
            throw new IllegalStateException("Receptionist is already exist with this ssn "+ssn);
        }
        Receptionist rec = new Receptionist();
        rec.setName(name);
        rec.setSsn(ssn);
        return receptionistRepo.save(rec);
    }

    public Receptionist updateReceptionist(String ssn, Receptionist updated) {
        Receptionist original = receptionistRepo.findBySsn(ssn)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        original.setName(updated.getName());
        original.setSsn(updated.getSsn());
        return receptionistRepo.save(original);
    }

    public void deleteReceptionist(String ssn) {
        if(!receptionistRepo.findBySsn(ssn).isPresent()){
            throw new IllegalStateException("Receptionist does not exist");
        }
        receptionistRepo.deleteBySsn(ssn);
    }
}