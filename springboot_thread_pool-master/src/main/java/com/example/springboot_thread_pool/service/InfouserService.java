package com.example.springboot_thread_pool.service;


import com.example.springboot_thread_pool.entity.Infouser;
import com.example.springboot_thread_pool.entity.Shipin;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InfouserService {
    Infouser findByUsernumber(String usernumber);
    void updateye(int userbalance, String usernumber);
    Infouser add(Infouser infouser);
    void updatestart( int userbalance,
                      int useryaoqing,
                      String usernumber,
                      int isi);
    void updateduihuan(int yiduihuan,
                       String usernumber);
    List<Infouser> select();
}