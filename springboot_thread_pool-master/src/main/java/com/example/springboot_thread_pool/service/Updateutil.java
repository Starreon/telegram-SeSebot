package com.example.springboot_thread_pool.service;

import com.example.springboot_thread_pool.controller.MyAmazingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public interface Updateutil {
     SendMessage getSendMessagetext(String message_text, String chat_id);
     ReplyKeyboardMarkup getReplyKeyboardMarkup();
      void  fasongvideo(Update update, MyAmazingBot bot,String fileid,String chatid);


}
