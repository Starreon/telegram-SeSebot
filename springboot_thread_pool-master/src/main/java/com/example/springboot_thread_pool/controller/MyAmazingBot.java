package com.example.springboot_thread_pool.controller;


/*import com.pengrad.telegrambot.model.ChatMember;*/
import com.example.springboot_thread_pool.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.groupadministration.RestrictChatMember;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
@Component
public class MyAmazingBot extends TelegramLongPollingBot {

    private String token = "6174697064:AAHc--ujSUnhKsWnlQ-BnzYyHEKSCAH_x_8";
    private String userName = "00001";
    private  List<String> liststring = Arrays.asList("徦","低价","代開","钱包","自动","v","出","代开", "代充", "会员","會员","t","T","专业","U","u","项目","佰度","谷歌");

    private static AsyncService asyncService;
    @Autowired  //关键点3
    public void setAsyncService (AsyncService asyncService){
        MyAmazingBot.asyncService = asyncService;
    }


    public MyAmazingBot() {
        this(new DefaultBotOptions());
    }

    public MyAmazingBot(DefaultBotOptions options) {
        super(options);
    }
    //将日期格式转换成时间戳
    public static String Date2TimeStamp(String dateStr, String format) {

        try {

            SimpleDateFormat sdf = new SimpleDateFormat(format);

            return String.valueOf(sdf.parse(dateStr).getTime() / 1000);

        } catch (Exception e) {

            e.printStackTrace();

        }

        return "";

    }
    public  void  jinyan(Update update){
        RestrictChatMember rm =   new RestrictChatMember();
        rm.setChatId(update.getMessage().getChatId()+"");
        rm.setUserId(update.getMessage().getFrom().getId());

        ChatPermissions aa = new ChatPermissions();
        rm.setPermissions( aa);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = new Date();


        String AAA =  Date2TimeStamp(dateFormat.format(date).toString(),"yyyy-MM-dd HH:mm:ss");
        rm.setUntilDate(Integer.parseInt(AAA)+21000);
        try {
            execute(rm);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }



    public void onUpdateReceived(Update update) {


        MyAmazingBot.asyncService.executeAsync(update,this);

    }

    //发送普通消息
    public SendMessage getSendMessage(String message_text, Long chat_id) {
        SendMessage message = new SendMessage();// Create a message object object
        message.setChatId(String.valueOf(chat_id));
        message.setText(message_text);
        message.setParseMode(ParseMode.HTML);
        //返回内联信息
        InlineKeyboardMarkup inlineKeyboardMarkup = getInlineKeyboardMarkup();
        message.setReplyMarkup(inlineKeyboardMarkup);

        //TODO 获取底部键盘
        ReplyKeyboardMarkup replyKeyboardMarkup = getReplyKeyboardMarkup();
        message.setReplyMarkup(replyKeyboardMarkup);

        return message;
    }
    //删除消息
    public DeleteMessage getDeleteMessage(String chat_id, Integer message_id){
        DeleteMessage deleteMessage = null;
        try {
             deleteMessage = new DeleteMessage(chat_id, message_id);
            execute(deleteMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
     return  deleteMessage;
    }
    // todo 获取文本底部按钮
    public InlineKeyboardMarkup getInlineKeyboardMarkup() {
        //TODO 文本底部键盘
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> inlineButtons = new ArrayList<>();

        List<InlineKeyboardButton> inlineKeyboardButtonList1 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText("查看文档");
        inlineKeyboardButton1.setUrl("https://www.baidu.com");
        inlineKeyboardButton1.setCallbackData("查看文档");
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton2.setText("联系客服");
        inlineKeyboardButton2.setUrl("http://www.baidu.com");
        inlineKeyboardButton2.setCallbackData("联系客服");
        inlineKeyboardButtonList1.add(inlineKeyboardButton1);
        inlineKeyboardButtonList1.add(inlineKeyboardButton2);


        List<InlineKeyboardButton> inlineKeyboardButtonList2 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
        inlineKeyboardButton3.setText("查看文档");
        inlineKeyboardButton3.setCallbackData("查看文档");
        InlineKeyboardButton inlineKeyboardButton4 = new InlineKeyboardButton();
        inlineKeyboardButton4.setText("联系客服");
        inlineKeyboardButton4.setCallbackData("联系客服");
        inlineKeyboardButtonList2.add(inlineKeyboardButton3);
        inlineKeyboardButtonList2.add(inlineKeyboardButton4);

        inlineButtons.add(inlineKeyboardButtonList1);
        inlineButtons.add(inlineKeyboardButtonList2);


        inlineKeyboardMarkup.setKeyboard(inlineButtons);
        return inlineKeyboardMarkup;
    }

    // TODO 设置底部键盘

    private ReplyKeyboardMarkup getReplyKeyboardMarkup() {
        //todo 底部菜单
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        //todo 第一行按钮
        KeyboardRow keyboardRow1 = new KeyboardRow();

        KeyboardButton button1 = new KeyboardButton();
//        button1.setText("查看帮助");
        button1.setText("分享名片");

        button1.setRequestContact(true);
        KeyboardButton button2 = new KeyboardButton();
        button2.setText("分享位置");
        button2.setRequestLocation(true);
        KeyboardButton button3 = new KeyboardButton();
        button3.setText("授权列表");


        keyboardRow1.add(button1);
        keyboardRow1.add(button2);
        keyboardRow1.add(button3);
        //todo 第二行按钮
        KeyboardRow keyboardRow2 = new KeyboardRow();

        KeyboardButton button4 = new KeyboardButton();
        button4.setText("查看余额");
        KeyboardButton button5 = new KeyboardButton();
        button5.setText("会员充值");
        KeyboardButton button6 = new KeyboardButton();
        button6.setText("联系客服");

        keyboardRow2.add(button4);
        keyboardRow2.add(button5);
        keyboardRow2.add(button6);


        keyboardRows.add(keyboardRow1);
        keyboardRows.add(keyboardRow2);

        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;
    }


    @Override
    public String getBotUsername() {
        // TODO
        return this.userName;
    }

    @Override
    public String getBotToken() {
        // TODO
        return this.token;
    }




}
