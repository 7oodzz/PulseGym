package com.springdemo.pulsegym.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.springdemo.pulsegym.Model.SessionBundle;
import com.springdemo.pulsegym.Repository.ReceptionistRepository;
import com.springdemo.pulsegym.Model.Member;

@Service
public class MemberSessionService {
    @Autowired
    private final MemberSessionRepository Repo;


}
