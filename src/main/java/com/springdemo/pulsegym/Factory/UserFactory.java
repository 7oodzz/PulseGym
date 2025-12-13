package com.springdemo.pulsegym.Factory;
import com.springdemo.pulsegym.DTO.AuthRequest;
import com.springdemo.pulsegym.DTO.ReceptionistRequest;
import com.springdemo.pulsegym.Model.Admin;
import com.springdemo.pulsegym.Model.Member;
import com.springdemo.pulsegym.Model.Receptionist;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserFactory {
    private final PasswordEncoder encoder;

    public UserFactory(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    public Admin createAdmin(AuthRequest dto) {
        Admin admin = new Admin();
        admin.setUsername(dto.getUsername());
        admin.setPassword(encoder.encode(dto.getPassword()));
        return admin;
    }


    public Receptionist createReceptionist(ReceptionistRequest receptionistRequest) {
        Receptionist recep = new Receptionist();
        recep.setUsername(receptionistRequest.getUsername());
        recep.setPassword(encoder.encode(receptionistRequest.getPassword()));
        recep.setName(receptionistRequest.getName());
        recep.setSsn(receptionistRequest.getSsn());
        return recep;
    }

    public Member createMember(Member m) {
        Member newMember = new Member();
        newMember.setName(m.getName());
        newMember.setPhoneNo(m.getPhoneNo());
        newMember.setHasSession(m.getHasSession());
        newMember.setHasSubscription(m.getHasSubscription());
        return newMember;
    }
}
