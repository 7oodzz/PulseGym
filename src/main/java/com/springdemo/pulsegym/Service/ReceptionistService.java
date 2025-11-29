package com.springdemo.pulsegym.Service;

import com.springdemo.pulsegym.Model.Receptionist;
import com.springdemo.pulsegym.Repository.ReceptionistRepository;


public class ReceptionistService {

    @Autowired
    private final ReceptionistRepository receptionistRepo;


    public ReceptionistService(ReceptionistRepository receptionistRepo) {
        this.receptionistRepo = receptionistRepo;
    }

    public Receptionist addReceptionist(String name, String ssn) {
        Receptionist rec = new Receptionist();
        rec.setName(name);
        rec.setSsn(ssn);
        return receptionistRepo.save(rec);
    }

    public Receptionist updateReceptionist(String id, Receptionist updated) {
        Receptionist original = receptionistRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        original.setName(updated.getName());
        original.setSsn(updated.getSsn());
        return receptionistRepo.save(original);
    }

    public void deleteReceptionist(String receptionistId) {
        receptionistRepo.deleteById(receptionistId);
    }
}