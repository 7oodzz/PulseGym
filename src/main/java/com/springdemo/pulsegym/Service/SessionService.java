package com.springdemo.pulsegym.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springdemo.pulsegym.Model.SessionBundle;

@Service
public class SessionService {
       private List<SessionBundle> sessions = new ArrayList<>();

   
    public void addSession(SessionBundle session) {
        sessions.add(session);
    }


    public List<SessionBundle> getAllSessions() {
        return sessions;
    }

  
    public SessionBundle getSessionById(int id) {
        Optional<SessionBundle> result = sessions
                .stream()
                .filter(s -> s.getId() == id)
                .findFirst();
        return result.orElse(null);
    }

   
    public boolean updateSession(int id, SessionBundle updatedSession) {
        for (int i = 0; i < sessions.size(); i++) {
            if (sessions.get(i).getId() == id) {
                updatedSession.setId(id); 
                sessions.set(i, updatedSession);
                return true;
            }
        }
        return false;
    }

    
    public boolean deleteSession(int id) {
        return sessions.removeIf(s -> s.getId() == id);
    }

    public boolean hasAvailableSessions(SessionBundle session) {
        return session.getSessionsLeft() > 0;
    }

    public boolean isSessionExpired(SessionBundle session) {
        return session.getExpiryDate().isBefore(LocalDate.now());
    }

    public boolean decrementSessionCount(SessionBundle session) {
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