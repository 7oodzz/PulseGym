package com.springdemo.pulsegym.Service;

import com.springdemo.pulsegym.DTO.ReceptionistRequest;
import com.springdemo.pulsegym.Factory.UserFactory;
import com.springdemo.pulsegym.Model.Receptionist;
import com.springdemo.pulsegym.Repository.ReceptionistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Service
public class ReceptionistService {

    @Autowired
    private final ReceptionistRepository receptionistRepo;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserFactory userFactory;


    public ReceptionistService(ReceptionistRepository receptionistRepo) {

        this.receptionistRepo = receptionistRepo;
    }

    public List<Receptionist> getReceptionists() {
        return receptionistRepo.findAll();
    }

//    public Receptionist addReceptionist(ReceptionistRequest recepReq) {
//        if (receptionistRepo.findBySsn(recepReq.getSsn()).isPresent()) {
//            throw new IllegalStateException("Receptionist is already exist with this ssn " + recepReq.getSsn());
//        }
//        Receptionist rec = new Receptionist();
//        rec.setUsername(recepReq.getUsername());
//        rec.setPassword(encoder.encode(recepReq.getPassword()));
//        rec.setName(recepReq.getName());
//        rec.setSsn(recepReq.getSsn());
//        return receptionistRepo.save(rec);
//    }

    public Receptionist addReceptionist(ReceptionistRequest recepReq) {

        if (receptionistRepo.findBySsn(recepReq.getSsn()).isPresent()) {
            throw new IllegalStateException("Receptionist already exists");
        }

        Receptionist rec = userFactory.createReceptionist(recepReq);
        return receptionistRepo.save(rec);
    }

    public Receptionist updateReceptionist(String ssn, ReceptionistRequest updated) {
        Receptionist original = receptionistRepo.findBySsn(ssn)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        original.setPassword(encoder.encode(updated.getPassword()));
        original.setSsn(updated.getSsn());
        return receptionistRepo.save(original);
    }

    public void deleteReceptionist(String ssn) {
        if (!receptionistRepo.findBySsn(ssn).isPresent()) {
            throw new IllegalStateException("Receptionist does not exist");
        }
        receptionistRepo.deleteBySsn(ssn);
    }
}