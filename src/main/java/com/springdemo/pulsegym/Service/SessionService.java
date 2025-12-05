package com.springdemo.pulsegym.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.springdemo.pulsegym.Model.Session;
import com.springdemo.pulsegym.Repository.SessionRepository;

@Service
public class SessionService {
    @Autowired
    private SessionRepository repo;
   
    public void addSession(Session session) {
        repo.save(session);
    }


    public List<Session> getAllSessions() {
        return repo.findAll();
    }

  
  /*  public Session getSessionById(int id) {
        Optional<Session> result = sessions
                .stream()
                .filter(s -> s.getId() == id)
                .findFirst();
        return result.orElse(null);
    }*/


    
    public void deleteSession(int id) {
        repo.deleteById(id);
    }


 /*   public boolean hasAvailableSessions(Session session) {
        return session.getSessionsLeft() > 0;
    }

    public boolean isSessionExpired(Session session) {
        return session.getExpiryDate().isBefore(LocalDate.now());
    }

    public boolean decrementSessionCount(Session session) {
    if (isSessionExpired(session)) {
        return false;
    }
    if (!hasAvailableSessions(session)) {
        return false;
    }

    session.setSessionsLeft(session.getSessionsLeft() - 1);
    return true;
}*/

}