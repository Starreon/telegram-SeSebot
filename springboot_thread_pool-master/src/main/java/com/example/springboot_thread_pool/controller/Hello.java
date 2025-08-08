package com.example.springboot_thread_pool.controller;

import com.example.springboot_thread_pool.service.AsyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
public class Hello  {

    private static final Logger logger = LoggerFactory.getLogger(Hello.class);

    @Autowired
    private AsyncService asyncService;


    public String submit(Update update,MyAmazingBot bot){
        logger.info("start submit");

        //调用service层的任务
        asyncService.executeAsync(update,bot);

        logger.info("end submit");

        return "success";
    }
}
