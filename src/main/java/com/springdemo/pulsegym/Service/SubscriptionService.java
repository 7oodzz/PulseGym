package com.springdemo.pulsegym.Service;

import com.springdemo.pulsegym.Model.Subscription;
import com.springdemo.pulsegym.Repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionService {
    @Autowired
    private SubscriptionRepository repo;

    public Subscription create(Subscription s) {
        return repo.save(s);
    }

    public Subscription update(int id, Subscription s) {
        Subscription existing = repo.findById(id).orElseThrow();
        existing.setName(s.getName());
        existing.setPrice(s.getPrice());
        existing.setDescription(s.getDescription());
        existing.setStartDate(s.getStartDate());
        existing.setExpDate(s.getExpDate());
        return repo.save(existing);
    }

    public void delete(int id) {
        repo.deleteById(id);
    }

    public List<Subscription> list() {
        return repo.findAll();
    }
}
