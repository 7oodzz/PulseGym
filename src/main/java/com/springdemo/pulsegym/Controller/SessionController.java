package com.springdemo.pulsegym.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springdemo.pulsegym.Model.Session;
import com.springdemo.pulsegym.Service.SessionService;


@RestController
@RequestMapping("/sessions")
public class SessionController {

    private SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping
    public void addSession(@RequestBody Session session) {
        sessionService.addSession(session);
    }

    @GetMapping
    public List<Session> getAllSessions() {
        return sessionService.getAllSessions();
    }

    // Read by id
    @GetMapping("/{id}")
    public Session getSessionById(@PathVariable int id) {
        return sessionService.getSessionById(id);
    }

    @PutMapping("/{id}")
    public boolean updateSession(@PathVariable int id, @RequestBody Session updatedSession) {
        return sessionService.updateSession(id, updatedSession);
    }

    @DeleteMapping("/{id}")
    public boolean deleteSession(@PathVariable int id) {
        return sessionService.deleteSession(id);
    }

     @PostMapping("/{id}/use")
    public String useASession(@PathVariable int id) {

        Session session = sessionService.getSessionById(id);

        if (session == null) {
            return "error: session not found";
        }

        if (sessionService.isSessionExpired(session)) {
            return "error: session expired";
        }

        if (!sessionService.hasAvailableSessions(session)) {
            return "error: no sessions left";
        }

    
        sessionService.decrementSessionCount(session);

        return "session used successfully";
    }
}
