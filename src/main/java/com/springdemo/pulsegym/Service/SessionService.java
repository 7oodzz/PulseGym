package com.springdemo.pulsegym.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.springdemo.pulsegym.Model.Session;

@Service
public class SessionService {
       private List<Session> sessions = new ArrayList<>();

   
    public void addSession(Session session) {
        sessions.add(session);
    }


    public List<Session> getAllSessions() {
        return sessions;
    }

  
    public Session getSessionById(int id) {
        Optional<Session> result = sessions
                .stream()
                .filter(s -> s.getId() == id)
                .findFirst();
        return result.orElse(null);
    }

   
    public boolean updateSession(int id, Session updatedSession) {
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

    public boolean hasAvailableSessions(Session session) {
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
}

}