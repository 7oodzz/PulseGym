package com.springdemo.pulsegym.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.springdemo.pulsegym.Model.Session;

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
}
