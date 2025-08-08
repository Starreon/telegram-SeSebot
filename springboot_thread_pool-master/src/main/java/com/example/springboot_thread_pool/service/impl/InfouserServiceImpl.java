package com.example.springboot_thread_pool.service.impl;


import com.example.springboot_thread_pool.dao.InfouserRepository;
import com.example.springboot_thread_pool.entity.Infouser;

import com.example.springboot_thread_pool.service.InfouserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//会员的实现类
@Service
public class InfouserServiceImpl implements InfouserService {
    @Autowired
    private InfouserRepository infouserRepository;
    @Override
    public Infouser findByUsernumber(String usernumber) {
        return infouserRepository.findByUsernumber(usernumber);
    }

    @Override
    public void updateye(int userbalance, String usernumber) {
        infouserRepository.updateye(userbalance,usernumber);
    }

    @Override
    public Infouser add(Infouser infouser) {
        return infouserRepository.save(infouser);
    }

    @Override
    public void updatestart(int userbalance, int useryaoqing, String usernumber,int isi) {
        infouserRepository.updatestart(userbalance,useryaoqing,usernumber,isi);
    }

    @Override
    public void updateduihuan(int yiduihuan, String usernumber) {
        infouserRepository.updateduihuan(yiduihuan,usernumber);
    }

    @Override
    public List<Infouser> select() {
        return infouserRepository.findAll();
    }


}
