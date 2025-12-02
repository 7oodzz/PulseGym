package com.springdemo.pulsegym.Service;

import com.springdemo.pulsegym.Model.Subscription;
import com.springdemo.pulsegym.Repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SubscriptionService {
    @Autowired
    private SubscriptionRepository repo;

    public Subscription create(Subscription sub) {
        return repo.save(sub);
    }

    public Subscription update(int id, Subscription sub) {
        Subscription existing = repo.findById(id).orElseThrow();
        existing.setName(sub.getName());
        existing.setPrice(sub.getPrice());
        existing.setDescription(sub.getDescription());
        existing.setStartDate(sub.getStartDate());
        existing.setLength(sub.getLength());
        return repo.save(existing);
    }

    public void delete(int id) {
        repo.deleteById(id);
    }

    public List<Subscription> list() {
        return repo.findAll();
    }

    public boolean exists(int id) {
        return repo.existsById(id);
    }

    public boolean status(int id) {
        Date today = new Date();
        Subscription sup = repo.findById(id).orElseThrow();
        if(today.after(sup.getExpDate())) {
            return false;
        }
        return true;
    }
}
