package com.springdemo.pulsegym.Factory;
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

    public Admin createAdmin(String username, String password) {
        return new Admin(username, password);
    }

    public Receptionist createReceptionist(ReceptionistRequest receptionistRequest) {
        return new Receptionist(receptionistRequest.getUsername(), receptionistRequest.getPassword(),
                receptionistRequest.getName(), receptionistRequest.getSsn());
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
