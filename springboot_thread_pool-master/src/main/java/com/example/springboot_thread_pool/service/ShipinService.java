package com.example.springboot_thread_pool.service;


import com.example.springboot_thread_pool.entity.Infouser;
import com.example.springboot_thread_pool.entity.Shipin;

import java.util.List;

public interface ShipinService {

      Shipin add(Shipin shipin);
      List<Shipin> select();
      List<Shipin> findByLengths(int lengths);
}
