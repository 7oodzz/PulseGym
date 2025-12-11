package com.springdemo.pulsegym.Factory;
import com.springdemo.pulsegym.DTO.ReceptionistRequest;
import com.springdemo.pulsegym.Model.Member;
import com.springdemo.pulsegym.Model.Receptionist;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

public class UserFactory {
    private final PasswordEncoder encoder;

    public UserFactory(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    public Receptionist createReceptionist(ReceptionistRequest dto) {
        Receptionist r = new Receptionist();
        r.setUsername(dto.getUsername());
        r.setPassword(encoder.encode(dto.getPassword()));
        r.setSsn(dto.getSsn());
        r.setName(dto.getName());
        return r;
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
