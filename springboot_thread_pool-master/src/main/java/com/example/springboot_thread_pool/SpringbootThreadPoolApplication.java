package com.example.springboot_thread_pool;

import com.example.springboot_thread_pool.controller.MyAmazingBot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.telegram.telegrambots.bots.DefaultBotOptions;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@EnableAsync
@SpringBootApplication
public class SpringbootThreadPoolApplication {

    public static void main(String[] args) {

        SpringApplication.run(SpringbootThreadPoolApplication.class, args);
        DefaultBotOptions botOptions = new DefaultBotOptions();

       String proxyHost = "127.0.0.1";
        Integer proxyPort = 10809;
        botOptions.setProxyHost(proxyHost); //注释
        botOptions.setProxyPort(proxyPort); //注释
        botOptions.setProxyType(DefaultBotOptions.ProxyType.HTTP);

        //注意一下这里，ProxyType是个枚举，看源码你就知道有NO_PROXY,HTTP,SOCKS4,SOCKS5;


        DefaultBotSession defaultBotSession = new DefaultBotSession();
        defaultBotSession.setOptions(botOptions);

        // Register our bot
        try {
            // Instantiate Telegram Bots API
            TelegramBotsApi botsApi = new TelegramBotsApi(defaultBotSession.getClass());
            botsApi.registerBot(new MyAmazingBot(botOptions));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

}
