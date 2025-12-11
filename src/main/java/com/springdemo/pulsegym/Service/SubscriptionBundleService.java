package com.springdemo.pulsegym.Service;

import com.springdemo.pulsegym.Model.SubscriptionBundle;
import com.springdemo.pulsegym.Repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionBundleService {
    @Autowired
    private SubscriptionRepository repo;

    public SubscriptionBundle create(SubscriptionBundle sub) {
        return repo.save(sub);
    }

    public SubscriptionBundle update(int id, SubscriptionBundle sub) {
        SubscriptionBundle existing = repo.findById(id).orElseThrow();
        existing.setName(sub.getName());
        existing.setPrice(sub.getPrice());
        existing.setDescription(sub.getDescription());
        existing.setDurationInMonth(sub.getDurationInMonth());
        return repo.save(existing);
    }

    public void delete(int id) {
        repo.deleteById(id);
    }

    public List<SubscriptionBundle> list() {
        return repo.findAll();
    }

    public boolean exists(int id) {
        return repo.existsById(id);
    }

}