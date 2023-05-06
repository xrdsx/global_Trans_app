package com.managment.global_Trans_App.service;

import com.managment.global_Trans_App.model.Forwarder;
import com.managment.global_Trans_App.repository.ForwarderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ForwarderService {
    private final ForwarderRepository forwarderRepository;

    @Autowired
    public ForwarderService(ForwarderRepository forwarderRepository) {
        this.forwarderRepository = forwarderRepository;
    }

    public List<Forwarder> getAllForwarders() {
        return forwarderRepository.findAll();
    }

    public Optional<Forwarder> getForwarderById(Integer id) {
        return forwarderRepository.findById(id);
    }

    public void saveForwarder(Forwarder forwarder) {
        forwarderRepository.save(forwarder);
    }

    public void deleteForwarder(Forwarder forwarder) {
        forwarderRepository.delete(forwarder);
    }
}
