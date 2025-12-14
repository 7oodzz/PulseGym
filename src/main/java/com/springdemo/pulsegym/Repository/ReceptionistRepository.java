package com.springdemo.pulsegym.Repository;

import com.springdemo.pulsegym.Model.Receptionist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReceptionistRepository extends JpaRepository<Receptionist, Integer> {

    Optional<Receptionist> findBySsn(String ssn);

    Optional<Receptionist> deleteBySsn(String ssn);

}
