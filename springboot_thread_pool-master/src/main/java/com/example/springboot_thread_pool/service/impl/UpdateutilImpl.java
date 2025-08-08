package com.example.springboot_thread_pool.service.impl;

import com.example.springboot_thread_pool.controller.MyAmazingBot;
import com.example.springboot_thread_pool.service.Updateutil;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class UpdateutilImpl implements Updateutil {
    @Override
    public SendMessage getSendMessagetext(String message_text, String chat_id) {
        SendMessage message = new SendMessage();// Create a message object object
        message.setChatId(chat_id);
        message.setText(message_text);
        message.setParseMode(ParseMode.HTML);
        message.setDisableWebPagePreview(true);
        //返回内联信息
        //InlineKeyboardMarkup inlineKeyboardMarkup = getInlineKeyboardMarkup();
        //message.setReplyMarkup(inlineKeyboardMarkup);

        //TODO 获取底部键盘
        ReplyKeyboardMarkup replyKeyboardMarkup = getReplyKeyboardMarkup();
        message.setReplyMarkup(replyKeyboardMarkup);

        return message;
    }

    public InlineKeyboardMarkup getinlineKeyboardMarkup(){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> listMarkup = new ArrayList<>();
        InlineKeyboardButton button1 = new InlineKeyboardButton();
        InlineKeyboardButton button2 = new InlineKeyboardButton();
        InlineKeyboardButton button3 = new InlineKeyboardButton();
        button1.setText("\uD83D\uDC8B长视频");
        button1.setCallbackData("长视频");
        button2.setText("\uD83D\uDC8B短视频");
        button2.setCallbackData("短视频短视频");
        button3.setText("\uD83D\uDC8B随机视频");
        button3.setCallbackData("\uD83D\uDC8B随机视频");

        List<InlineKeyboardButton> listbutton1 = new ArrayList<>();
        listbutton1.add(button1);
        listbutton1.add(button2);
        listbutton1.add(button3);
        listMarkup.add(listbutton1);

        InlineKeyboardButton button4 = new InlineKeyboardButton();
        //InlineKeyboardButton button5 = new InlineKeyboardButton();
        button4.setText("\uD83E\uDDE7每晚官方频道活动\uD83E\uDDE7");
        button4.setUrl("https://t.me/+6kjm8sUBIPI5NDMx");

        List<InlineKeyboardButton> listbutton2 = new ArrayList<>();

        listMarkup.add(listbutton2);

        InlineKeyboardButton button6 = new InlineKeyboardButton();
        button6.setText("\uD83D\uDCB4出租广告位\uD83D\uDCB4");
        button6.setUrl("https://t.me/tadb0");

        InlineKeyboardButton button7 = new InlineKeyboardButton();
        button7.setText("\uD83D\uDCB4出租广告位\uD83D\uDCB4");
        button7.setUrl("https://t.me/tadb0");

       /* InlineKeyboardButton button8 = new InlineKeyboardButton();
        button8.setText("官方频道");
        button8.setUrl("https://t.me/YDN109");

        InlineKeyboardButton button9 = new InlineKeyboardButton();
        button9.setText("我要推广赚钱\uD83D\uDCB0");
        button9.setUrl("https://t.me/TZ66669");
*/
        List<InlineKeyboardButton> listbutton3 = new ArrayList<>();
        listbutton3.add(button7);
        listbutton3.add(button6);
        List<InlineKeyboardButton> listbutton4 = new ArrayList<>();
        listbutton4.add(button4);

/*        listbutton2.add(button8);*/

        listMarkup.add(listbutton3);
        listMarkup.add(listbutton4);






        inlineKeyboardMarkup.setKeyboard(listMarkup);
        return inlineKeyboardMarkup;
    }
    @Override
    public ReplyKeyboardMarkup getReplyKeyboardMarkup() {
        //todo 底部菜单
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        //todo 第一行按钮
        KeyboardRow keyboardRow2 = new KeyboardRow();
        KeyboardButton button1 = new KeyboardButton();
        button1.setText("\uD83D\uDCB0推广赚钱");
        keyboardRow2.add(button1);

        KeyboardButton button4 = new KeyboardButton();
        button4.setText("推广结账\uD83D\uDCB4");
        keyboardRow2.add(button4);
        //todo 第二行按钮
        KeyboardRow keyboardRow1 = new KeyboardRow();

        KeyboardButton button5 = new KeyboardButton();
        button5.setText("✅官方频道");
        KeyboardButton button6 = new KeyboardButton();
        button6.setText("开始看片\uD83D\uDE0D");


        keyboardRow1.add(button5);
        keyboardRow1.add(button6);

       //todo 第三行按钮
        KeyboardRow keyboardRow3 = new KeyboardRow();
        KeyboardButton button9 = new KeyboardButton();
        button9.setText("\uD83D\uDCA1自助服务");
        KeyboardButton button0 = new KeyboardButton();
        button0.setText("推广大群\uD83D\uDCB0");
        keyboardRow3.add(button9);
        keyboardRow3.add(button0);


        //todo 第三行按钮
        KeyboardRow keyboardRow4 = new KeyboardRow();
        KeyboardButton button7 = new KeyboardButton();
        button7.setText("\uD83E\uDDE7每晚官方频道活动\uD83E\uDDE7");
       /* KeyboardButton button8 = new KeyboardButton();
        button8.setText("✅太子担保交流2群");
        keyboardRow3.add(button8);*/
        keyboardRow4.add(button7);





        keyboardRows.add(keyboardRow1);
        keyboardRows.add(keyboardRow2);
        keyboardRows.add(keyboardRow3);
        keyboardRows.add(keyboardRow4);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;
    }

    //发送视频
    @Override
    public void fasongvideo(Update update, MyAmazingBot bot,String fileid,String chatid) {




        SendVideo sendVideo = new SendVideo();
        sendVideo.setChatId(chatid);

        InputFile inputFile = new InputFile();


        inputFile.setMedia(fileid);

        sendVideo.setVideo(inputFile);


        InlineKeyboardMarkup inlineKeyboardMarkup = getinlineKeyboardMarkup();
        sendVideo.setReplyMarkup(inlineKeyboardMarkup);





        try {
            bot.execute(sendVideo);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }



    /*@Autowired
    private GroupRepository groupRepository;*/


}
