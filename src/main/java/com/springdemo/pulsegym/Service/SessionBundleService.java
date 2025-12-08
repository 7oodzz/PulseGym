package com.springdemo.pulsegym.Service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springdemo.pulsegym.Model.SessionBundle;
import com.springdemo.pulsegym.Repository.SessionRepository;

@Service
public class SessionBundleService {
    @Autowired
    private SessionRepository repo;

    public SessionBundle create(SessionBundle sub) {
        return repo.save(sub);
    }

    public SessionBundle update(int id, SessionBundle x) {
        SessionBundle existing = repo.findById(id).orElseThrow();
        existing.setNumberOfSessions(x.getNumberOfSessions());
        existing.setSessionDate(x.getSessionDate());
        existing.setPlan(x.getPlan());      
        return repo.save(existing);
    }

    public void delete(int id) {
        repo.deleteById(id);
    }

    public List<SessionBundle> list() {
        return repo.findAll();
    }

    public boolean exists(int id) {
        return repo.existsById(id);
    }

}
