package com.example.springboot_thread_pool.service.impl;

import com.example.springboot_thread_pool.dao.ShipinRepository;
import com.example.springboot_thread_pool.entity.Shipin;
import com.example.springboot_thread_pool.service.ShipinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipinServiceImpl implements ShipinService {
    @Autowired
    private ShipinRepository shipinRepository;


    @Override
    public Shipin add(Shipin shipin) {
       return shipinRepository.save(shipin);
    }

    @Override
    public List<Shipin> select() {
        return shipinRepository.findAll();
    }

    @Override
    public List<Shipin> findByLengths(int lengths) {
        return shipinRepository.findByLengths(lengths);
    }
}
