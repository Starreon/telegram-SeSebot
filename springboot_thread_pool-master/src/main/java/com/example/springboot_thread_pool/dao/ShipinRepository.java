package com.example.springboot_thread_pool.dao;


import com.example.springboot_thread_pool.entity.Shipin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ShipinRepository extends JpaRepository<Shipin, Long> {

    List<Shipin> findByLengths(int lengths);



}
