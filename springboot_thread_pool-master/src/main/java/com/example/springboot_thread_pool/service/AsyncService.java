package com.example.springboot_thread_pool.service;

import com.example.springboot_thread_pool.controller.MyAmazingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface AsyncService {

    /**
     * 执行异步任务
     */
    void executeAsync(Update update, MyAmazingBot bot);
}
