package com.springdemo.pulsegym.Service;

import com.springdemo.pulsegym.DTO.ReceptionistRequest;
import com.springdemo.pulsegym.Factory.UserFactory;
import com.springdemo.pulsegym.Model.Receptionist;
import com.springdemo.pulsegym.Repository.ReceptionistRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Transactional
@Service
public class ReceptionistService {

    @Autowired
    private ReceptionistRepository receptionistRepo;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserFactory userFactory;

    public List<Receptionist> getReceptionists() {
        return receptionistRepo.findAll();
    }

    public Receptionist addReceptionist(ReceptionistRequest recepReq) {

        if (receptionistRepo.findBySsn(recepReq.getSsn()).isPresent()) {
            throw new IllegalStateException("Receptionist already exists");
        }

        Receptionist rec = userFactory.createReceptionist(recepReq);
        return receptionistRepo.save(rec);
    }

    public Receptionist updateReceptionist(String ssn, ReceptionistRequest updated) {
        Receptionist original = receptionistRepo.findBySsn(ssn)
                .orElseThrow(() -> new RuntimeException("Receptionist not found"));

        if (!ssn.equals(updated.getSsn()) && receptionistRepo.findBySsn(updated.getSsn()).isPresent()) {
            throw new IllegalStateException("SSN already taken by another receptionist");
        }

        original.setName(updated.getName());
        original.setSsn(updated.getSsn());
        original.setUsername(updated.getUsername());

        if (updated.getPassword() != null && !updated.getPassword().trim().isEmpty()) {
            original.setPassword(encoder.encode(updated.getPassword()));
        }

        return receptionistRepo.save(original);
    }

    public void deleteReceptionist(String ssn) {
        if (!receptionistRepo.findBySsn(ssn).isPresent()) {
            throw new IllegalStateException("Receptionist does not exist");
        }
        receptionistRepo.deleteBySsn(ssn);
    }
}