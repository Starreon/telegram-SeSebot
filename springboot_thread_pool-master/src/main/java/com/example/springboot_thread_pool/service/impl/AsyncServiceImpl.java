package com.example.springboot_thread_pool.service.impl;

import com.example.springboot_thread_pool.entity.Infouser;
import com.example.springboot_thread_pool.entity.Shipin;
import com.example.springboot_thread_pool.service.AsyncService;
import com.example.springboot_thread_pool.controller.MyAmazingBot;
import com.example.springboot_thread_pool.service.InfouserService;
import com.example.springboot_thread_pool.service.ShipinService;
import com.example.springboot_thread_pool.service.Updateutil;


import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatMember;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.*;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Service
public class AsyncServiceImpl  implements AsyncService {

    private static final Logger logger = LoggerFactory.getLogger(AsyncServiceImpl.class);
    int i=0;
    private SendVideo sendVideo;
    @Autowired
    public Updateutil updateutil;
    @Autowired
    public ShipinService shipinService;
    @Autowired
    public InfouserService infouserService;

    public boolean yzgz(MyAmazingBot bot,long usersid){
        boolean isi = false;
        GetChatMember getChatMember = new GetChatMember();
        getChatMember.setChatId("-1001923282287");
        getChatMember.setUserId(usersid);
        try {
            ChatMember aa = bot.execute(getChatMember);
            if (aa!=null){
                if(aa.getStatus().equals("member")||aa.getStatus().equals("creator")
                        ||aa.getStatus().equals("administrator")){
                    isi =  true;
                }else if (aa.getStatus().equals("left")){
                    isi =  false;
                }
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return isi;
    }

    public void kaiche(Update update,MyAmazingBot bot,String chatid,boolean isi){

        if(isi){
            //已经关注
            //需要开始开车
            List<Shipin> shipinList = new ArrayList<>();
            shipinList = shipinService.select();
            Random rm = new Random();

            int index = rm.nextInt(shipinList.size());
            updateutil.fasongvideo(update,bot,shipinList.get(index).getShipinid(),chatid);
        }else{
            //没有关注
            SendMessage sendMessage = updateutil.getSendMessagetext(
                    "**请加入此<a href=\"https://t.me/tadb2\">频道</a> 后才能使用**\n" +
                            "—————————————-\n" +
                            "关注后 开车 /kc  \uD83D\uDC48点击\n" +
                            "〖邀请好友〗 /yq \n" +
                            "〖活动介绍〗 /hd",chatid);
            try {
                bot.execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

    }

    public void kaiches(Update update,MyAmazingBot bot,String chatid,boolean isi,List<Shipin> list){

        if(isi){
            //已经关注
            //需要开始开车

            Random rm = new Random();

            int index = rm.nextInt(list.size());
            updateutil.fasongvideo(update,bot,list.get(index).getShipinid(),chatid);
        }else{
            //没有关注
            SendMessage sendMessage = updateutil.getSendMessagetext(
                    "**请加入此<a href=\"https://t.me/tadb2\">频道</a> 后才能使用**\n" +
                            "—————————————-\n" +
                            "关注后 开车 /kc  \uD83D\uDC48点击\n" +
                            "〖邀请好友〗 /yq \n" +
                            "〖活动介绍〗 /hd",chatid);
            try {
                bot.execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

    }



    @Async("asyncServiceExecutor")
    public void executeAsync(Update update,MyAmazingBot bot) {
        logger.info("start executeAsync");
        //如果编辑消息
        //ChatMemberUpdated chatMember  = update.getChatMember(update.getMessage().getFrom().getId());
        if (update.getMessage() != null){
            String ids = update.getMessage().getFrom().getId().toString();
            if (update.getMessage().getChat().getType().equals("supergroup")) {
                //群内超级管理
                if (ids.equals("6017229603")||ids.equals("6017229603")) {
                    if (update.getMessage().getText().equals("查看")){
                        if(update.getMessage().getReplyToMessage()!=null){
                            Infouser infouser = infouserService.findByUsernumber(update.getMessage().getReplyToMessage().getFrom().getId()+"");
                            SendMessage sendMessage = new SendMessage();
                            sendMessage.setChatId(update.getMessage().getChat().getId()+"");
                            sendMessage.setParseMode("HTML");
                            try {
                                if(infouser!=null){
                                    String yaoqingzhuan ="\uD83C\uDF81邀请赚\uD83D\uDCA9" +
                                            "〖您的账号〗<code>"+infouser.getUsernumber()+"</code>（点击复制）\n" +
                                            "〖您的余额〗"+infouser.getUserbalance()+"坨\uD83D\uDCA9" +
                                            "〖邀请链接〗点击复制⬇️\n" +
                                            "<code>https://t.me/cesh222i_BOT?start="+infouser.getTokens()+"</code>\n" +
                                            "〖您已邀请〗"+infouser.getUseryaoqing()+" 人\n" +
                                            "〖您已结算〗"+infouser.getYiduihuan()+" 人\n" +
                                            "〖您未结算〗"+(infouser.getUseryaoqing()-infouser.getYiduihuan())+" 人\n" +
                                            "\uD83D\uDCA1每邀请 10 人，奖励 188 坨\uD83D\uDCA9（邀请越多，奖励越多！)";
                                    sendMessage.setText(yaoqingzhuan);
                                    ReplyKeyboardMarkup replyKeyboardMarkup = updateutil.getReplyKeyboardMarkup();
                                    sendMessage.setReplyMarkup(replyKeyboardMarkup);
                                    bot.execute(sendMessage);
                                }else{
                                    String yaoqingzhuan1 ="\uD83C\uDF81查询的账号不存在\uD83D\uDCA9";
                                    sendMessage.setText(yaoqingzhuan1);
                                    ReplyKeyboardMarkup replyKeyboardMarkup = updateutil.getReplyKeyboardMarkup();
                                    sendMessage.setReplyMarkup(replyKeyboardMarkup);
                                    bot.execute(sendMessage);
                                }

                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                    if (update.getMessage().getText().contains("兑换")){

                        String test =update.getMessage().getText();
                        test = test.replace("兑换","").trim();
                        if(NumberUtils.isDigits(test)){
                            if(update.getMessage().getReplyToMessage()!=null){
                                Infouser infouser = infouserService.findByUsernumber(update.getMessage().getReplyToMessage().getFrom().getId()+"");
                                SendMessage sendMessage = new SendMessage();
                                sendMessage.setChatId(update.getMessage().getChat().getId()+"");
                                sendMessage.setParseMode("HTML");
                                try {
                                    if(infouser!=null){
                                        int duihuan = infouser.getYiduihuan();
                                        int yq = infouser.getUseryaoqing();
                                        int weijiesuan = yq-duihuan;
                                       int yaoduihuan = Integer.parseInt(test);
                                        if(yaoduihuan>weijiesuan){
                                            //提示没有那么多
                                            String yaoqingzhuan2 ="\uD83C\uDF81可用余额不足\uD83D\uDCA9";
                                            sendMessage.setText(yaoqingzhuan2);
                                            ReplyKeyboardMarkup replyKeyboardMarkup = updateutil.getReplyKeyboardMarkup();
                                            sendMessage.setReplyMarkup(replyKeyboardMarkup);
                                            bot.execute(sendMessage);
                                        }else{

                                            infouserService.updateduihuan(infouser.getYiduihuan()+Integer.parseInt(test),
                                                    infouser.getUsernumber());
                                            Infouser infouser1 = infouserService.findByUsernumber(update.getMessage().getReplyToMessage().getFrom().getId()+"");
                                            String yaoqingzhuan ="\uD83C\uDF81邀请赚\uD83D\uDCA9" +
                                                    "〖您的账号〗"+infouser1.getUsernumber()+"（点击复制）\n" +
                                                    "〖您的余额〗"+infouser1.getUserbalance()+"坨\uD83D\uDCA9" +
                                                    "〖邀请链接〗点击复制⬇️\n" +
                                                    "<code>https://t.me/cesh222i_BOT?start="+infouser1.getTokens()+"</code>\n" +
                                                    "〖您已邀请〗"+infouser1.getUseryaoqing()+" 人\n" +
                                                    "〖您已结算〗"+infouser1.getYiduihuan()+" 人\n" +
                                                    "〖您未结算〗"+(infouser1.getUseryaoqing()-infouser1.getYiduihuan())+" 人\n" +
                                                    "\uD83D\uDCA1每邀请 10 人，奖励 188 坨\uD83D\uDCA9（邀请越多，奖励越多！)";
                                            sendMessage.setText(yaoqingzhuan);
                                            ReplyKeyboardMarkup replyKeyboardMarkup = updateutil.getReplyKeyboardMarkup();
                                            sendMessage.setReplyMarkup(replyKeyboardMarkup);
                                            bot.execute(sendMessage);
                                        }



                                    }else{
                                        String yaoqingzhuan1 ="\uD83C\uDF81查询的账号不存在\uD83D\uDCA9";
                                        sendMessage.setText(yaoqingzhuan1);
                                        ReplyKeyboardMarkup replyKeyboardMarkup = updateutil.getReplyKeyboardMarkup();
                                        sendMessage.setReplyMarkup(replyKeyboardMarkup);
                                        bot.execute(sendMessage);
                                    }

                                } catch (TelegramApiException e) {
                                    e.printStackTrace();
                                }
                            }
                        }


                    }
                } else {
                    if (update.getMessage().getText().equals("查看")){


                            Infouser infouser = infouserService.findByUsernumber(update.getMessage().getFrom().getId()+"");
                            SendMessage sendMessage = new SendMessage();

                            sendMessage.setChatId(update.getMessage().getChat().getId()+"");


                            sendMessage.setParseMode("HTML");
                            try {
                                if(infouser!=null){
                                    String yaoqingzhuan ="\uD83C\uDF81邀请赚\uD83D\uDCA9" +
                                            "〖您的账号〗<code>"+infouser.getUsernumber()+"</code>（点击复制）\n" +
                                            "〖您的余额〗"+infouser.getUserbalance()+"坨\uD83D\uDCA9" +
                                            "〖邀请链接〗点击复制⬇️\n" +
                                            "<code>https://t.me/cesh222i_BOT?start="+infouser.getTokens()+"</code>\n" +
                                            "〖您已邀请〗"+infouser.getUseryaoqing()+" 人\n" +
                                            "〖您已结算〗"+infouser.getYiduihuan()+" 人\n" +
                                            "〖您未结算〗"+(infouser.getUseryaoqing()-infouser.getYiduihuan())+" 人\n" +
                                            "\uD83D\uDCA1每邀请 10 人，奖励 188 坨\uD83D\uDCA9（邀请越多，奖励越多！)";
                                    sendMessage.setText(yaoqingzhuan);
                                    bot.execute(sendMessage);
                                }else{
                                    String yaoqingzhuan1 ="\uD83C\uDF81查询的账号不存在\uD83D\uDCA9";
                                    sendMessage.setText(yaoqingzhuan1);
                                    ReplyKeyboardMarkup replyKeyboardMarkup = updateutil.getReplyKeyboardMarkup();
                                    sendMessage.setReplyMarkup(replyKeyboardMarkup);
                                    bot.execute(sendMessage);
                                }

                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }


                    }
                    //群内地址
                    if (update.getMessage().getText().equals("结账")){


                        Infouser infouser = infouserService.findByUsernumber(update.getMessage().getFrom().getId()+"");
                        SendMessage sendMessage = new SendMessage();

                        sendMessage.setChatId(update.getMessage().getChat().getId()+"");


                        sendMessage.setParseMode("HTML");
                        try {
                            if(infouser!=null){
                                String yaoqingzhuan ="结账详情\n" +
                                        "<code>正在为您呼叫管理员..........</code>\n"+
                                        "\n" +
                                        "结账负责人：<b><a href=\"https://t.me/tadb009\">小猫</a></b> \n" +
                                        "\n" +
                                        "结账时间：1点--4点--9点\n" +
                                        "\n" +
                                        "结账要求：<code>满足成功邀请30人，点击上方蓝色字体结账。</code>\n" +
                                        "\n" +
                                        "结账疑问请联系：点我联系<a href=\"https://t.me/tadb6/5\">\uD83D\uDC69\u200D\uD83D\uDCBC公群疑问</a> ";
                                sendMessage.setText(yaoqingzhuan);
                                bot.execute(sendMessage);
                            }else{
                                String yaoqingzhuan1 ="\uD83C\uDF81查询的账号不存在\uD83D\uDCA9";
                                sendMessage.setText(yaoqingzhuan1);
                                ReplyKeyboardMarkup replyKeyboardMarkup = updateutil.getReplyKeyboardMarkup();
                                sendMessage.setReplyMarkup(replyKeyboardMarkup);
                                bot.execute(sendMessage);
                            }

                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }


                    }
                    //验证群内消息
                    if (update.getMessage().getText().equals("1")){


                        Infouser infouser = infouserService.findByUsernumber(update.getMessage().getFrom().getId()+"");
                        SendMessage sendMessage = new SendMessage();

                        sendMessage.setChatId(update.getMessage().getChat().getId()+"");


                        sendMessage.setParseMode("HTML");
                        try {
                            if(infouser!=null){
                                String yaoqingzhuan ="公群上押明细\n" +
                                        "公群编号：<code>00923</code>\n"+
                                        "\n" +
                                        "公群负责人：<b><a href=\"https://t.me/tadb009\">小猫</a></b> \n" +
                                        "\n" +
                                        "上压金额：6999U\n" +
                                        "\n" +
                                        "上押时间：<code>2023年xx月xx日</code>\n" +
                                        "\n" +
                                        "公群内疑问请联系：点我联系<a href=\"https://t.me/tadb6/5\">\uD83D\uDC69\u200D\uD83D\uDCBC公群疑问</a> ";
                                sendMessage.setText(yaoqingzhuan);
                                bot.execute(sendMessage);
                            }else{
                                String yaoqingzhuan1 ="您暂无权限，请先私聊机器人后重新操作。";
                                sendMessage.setText(yaoqingzhuan1);
                                ReplyKeyboardMarkup replyKeyboardMarkup = updateutil.getReplyKeyboardMarkup();
                                sendMessage.setReplyMarkup(replyKeyboardMarkup);
                                bot.execute(sendMessage);
                            }

                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }


                    }


                }


            }
            else if (update.getMessage().getChat().getType().equals("private")) {

                    if (update.getMessage().getText() != null) {
                        if (update.getMessage().getText().contains("/start")) {// 处理普通文本消息
                            if (update.getMessage().getText().equals("/start")) {
// 处理普通文本消息
                                //1.查询用户是不是已经收录
                                Infouser infouser = infouserService.findByUsernumber(update.getMessage().getChat().getId()+"");
                                //2.如果收录了
                                if (infouser!=null){//已经收录，进行修改看片次数，每次减一，
                                    boolean isi = yzgz(bot,update.getMessage().getFrom().getId());
                                    if (isi){
                                        if (infouser.getUserbalance()>0){
                                            kaiche(update, bot,update.getMessage().getChat().getId()+"",isi);
                                            infouserService.updateye(infouser.getUserbalance()-1,infouser.getUsernumber());
                                        }else{
                                            //观影不足，需要邀请
                                            //观影不足，需要邀请
                                            SendMessage sendMessage = new SendMessage();
                                            sendMessage.setText("您的观影次数不足，可通过邀请用户获得\n" +
                                                    "\n" +
                                                    "发送 /yq");
                                            sendMessage.setChatId(update.getMessage().getChat().getId()+"");
                                            ReplyKeyboardMarkup replyKeyboardMarkup = updateutil.getReplyKeyboardMarkup();
                                            sendMessage.setReplyMarkup(replyKeyboardMarkup);
                                            try {
                                                bot.execute(sendMessage);
                                            } catch (TelegramApiException e) {
                                                e.printStackTrace();
                                            }

                                        }

                                    }else{
                                        SendMessage sendMessage = updateutil.getSendMessagetext(
                                                "**请加入此<a href=\"https://t.me/tadb2\">频道</a> 后才能使用**\n" +
                                                        "—————————————-\n" +
                                                        "关注后 开车 /kc  \uD83D\uDC48点击\n" +
                                                        "〖邀请好友〗 /yq \n" +
                                                        "〖活动介绍〗 /hd",update.getMessage().getChat().getId()+"");
                                        ReplyKeyboardMarkup replyKeyboardMarkup = updateutil.getReplyKeyboardMarkup();
                                        sendMessage.setReplyMarkup(replyKeyboardMarkup);
                                        try {
                                            bot.execute(sendMessage);
                                        } catch (TelegramApiException e) {
                                            e.printStackTrace();
                                        }
                                    }


                                }else{//进行收录
                                    infouser = new Infouser();
                                    //初始爱心
                                    infouser.setUserbalance(30);
                                    infouser.setUsername("123");
                                    infouser.setUseryaoqing(0);
                                    //没有邀请人
                                    infouser.setIsi(1);
                                    infouser.setShangjitok("0");
                                    infouser.setUsernumber(update.getMessage().getChat().getId()+"");
                                    infouser.setYiduihuan(0);
                                    infouser.setTokens(update.getMessage().getChat().getId()+"");
                                    infouserService.add(infouser);
                                    boolean isi = yzgz(bot,update.getMessage().getFrom().getId());
                                    kaiche(update, bot,update.getMessage().getChat().getId()+"",isi);


                                }
                            }else{
                                //处理请求 有邀请人
                                String test =update.getMessage().getText();
                                test = test.replace("/start","").trim();
                                //上级
                                Infouser infouser = infouserService.findByUsernumber(test);
                                //下级
                                Infouser infouser1 = infouserService.findByUsernumber(update.getMessage().getChat().getId()+"");
                                boolean isi = yzgz(bot,update.getMessage().getFrom().getId());
                                if (infouser1!=null){






                                    if (isi){
                                        if (infouser1.getUserbalance()>0){
                                            kaiche(update, bot,update.getMessage().getChat().getId()+"",isi);
                                            infouserService.updateye(infouser1.getUserbalance()-1,infouser1.getUsernumber());
                                        }else{
                                            //观影不足，需要邀请
                                            //观影不足，需要邀请
                                            SendMessage sendMessage = new SendMessage();
                                            sendMessage.setText("您的观影次数不足，可通过邀请用户获得\n" +
                                                    "\n" +
                                                    "发送 /yq");
                                            sendMessage.setChatId(update.getMessage().getChat().getId()+"");
                                            ReplyKeyboardMarkup replyKeyboardMarkup = updateutil.getReplyKeyboardMarkup();
                                            sendMessage.setReplyMarkup(replyKeyboardMarkup);
                                            try {
                                                bot.execute(sendMessage);
                                            } catch (TelegramApiException e) {
                                                e.printStackTrace();
                                            }

                                        }

                                    }else{
                                        SendMessage sendMessage = updateutil.getSendMessagetext(
                                                "**请加入此<a href=\"https://t.me/tadb2\">频道</a> 后才能使用**\n" +
                                                        "—————————————-\n" +
                                                        "关注后 开车 /kc  \uD83D\uDC48点击\n" +
                                                        "〖邀请好友〗 /yq \n" +
                                                        "〖活动介绍〗 /hd",update.getMessage().getChat().getId()+"");
                                        ReplyKeyboardMarkup replyKeyboardMarkup = updateutil.getReplyKeyboardMarkup();
                                        sendMessage.setReplyMarkup(replyKeyboardMarkup);

                                        try {
                                            bot.execute(sendMessage);
                                        } catch (TelegramApiException e) {
                                            e.printStackTrace();
                                        }
                                    }



                                }else {
                                    Infouser  infouser2 = new Infouser();
                                    //初始爱心
                                    infouser2.setUserbalance(3);
                                    infouser2.setUsername("123");
                                    infouser2.setUseryaoqing(0);
                                    infouser2.setShangjitok(test);
                                    infouser2.setIsi(0);
                                    infouser2.setUsernumber(update.getMessage().getChat().getId()+"");
                                    infouser2.setYiduihuan(0);
                                    infouser2.setTokens(update.getMessage().getChat().getId()+"");
                                    infouserService.add(infouser2);
                                    boolean isi1 = yzgz(bot,update.getMessage().getFrom().getId());
                                    kaiche(update, bot,update.getMessage().getChat().getId()+"",isi1);

                                    if (infouser!=null){
                                        int cishu = infouser.getUserbalance()+15;
                                        int cishui = infouser.getUseryaoqing();
                                        infouserService.updatestart(cishu,cishui,infouser.getUsernumber(),0);
                                    }


                                }






                            }
                        }
                        else if (update.getMessage().getText().equals("/jx")||
                                update.getMessage().getText().equals("jx")){
                                  //新加机器人介绍
                            Infouser infouser = infouserService.findByUsernumber(update.getMessage().getChat().getId()+"");
                            SendMessage sendMessage = new SendMessage();
                            String yaoqingzhuan ="\uD83D\uDD33机器人介绍 \n" +
                                    "\uD83D\uDD39欢迎您加入，福利开车机器人\uD83C\uDF89\uD83C\uDF89\n" +
                                    "\n" +
                                    "本软件由 【<a href=\"https://t.me/tadb2\">泰安担保</a>】 独家打造，为了给大家免费的观看平台从而打造。\n" +
                                    "\n" +
                                    "在此泰安担保由衷的祝愿各位狼友，升官发财，日日做新郎，日入过万。\n" +
                                    "\n" +
                                    "本机器人的主要几大功能如下请各位狼友随时免费观看。\n" +
                                    "\n" +
                                    "1️⃣：免费观看全网上万条私密视频，随时随地免费观看，拜托群内被封的几率。\n" +
                                    "\n" +
                                    "2️⃣：推广狼友使用本机器人可以赚取生活费用，让各位狼友既可以看片也可以赚钱。\n" +
                                    "\n" +
                                    "3️⃣：汇集全网的各大盘口项目随时随地的赚钱，做项目，并且关注【<a href=\"https://t.me/tadb2\">泰安担保</a>】每晚11点发放万元红包。";

                            sendMessage.setChatId(update.getMessage().getChat().getId()+"");
                            ReplyKeyboardMarkup replyKeyboardMarkup = updateutil.getReplyKeyboardMarkup();
                            sendMessage.setReplyMarkup(replyKeyboardMarkup);
                            sendMessage.setText(yaoqingzhuan);
                            sendMessage.setParseMode("HTML");
                            try {
                                bot.execute(sendMessage);
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }

                        }
                        else if (update.getMessage().getText().equals("\uD83D\uDCB0推广赚钱")||
                                update.getMessage().getText().equals("/yq")){

                            Infouser infouser = infouserService.findByUsernumber(update.getMessage().getChat().getId()+"");
                            SendMessage sendMessage = new SendMessage();
                            String yaoqingzhuan ="\uD83C\uDF81邀请赚\uD83C\uDF1F" +
                                    "〖您的账号〗"+infouser.getUsernumber()+"（点击复制）\n" +
                                    "〖您的余额〗"+infouser.getUserbalance()+"坨\uD83C\uDF1F" +
                                    "〖邀请链接〗点击复制⬇️\n" +
                                    "<code>https://t.me/cesh222i_BOT?start="+infouser.getTokens()+"</code>\n" +
                                    "〖您已邀请〗"+infouser.getUseryaoqing()+" 人\n" +
                                    "〖您已结算〗"+infouser.getYiduihuan()+" 人\n" +
                                    "〖您未结算〗"+(infouser.getUseryaoqing()-infouser.getYiduihuan())+" 人\n" +
                                    "\uD83D\uDCA1每邀请 10 人，奖励 188 坨\uD83C\uDF1F（邀请越多，奖励越多！)";
                            sendMessage.setChatId(update.getMessage().getChat().getId()+"");
                            ReplyKeyboardMarkup replyKeyboardMarkup = updateutil.getReplyKeyboardMarkup();
                            sendMessage.setReplyMarkup(replyKeyboardMarkup);
                            sendMessage.setText(yaoqingzhuan);
                            sendMessage.setParseMode("HTML");
                            try {
                                bot.execute(sendMessage);
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }

                        }
                        else if (update.getMessage().getText().equals("开始看片\uD83D\uDE0D")){
                            Infouser infouser = infouserService.findByUsernumber(update.getMessage().getChat().getId()+"");
                            //2.如果收录了
                            if (infouser!=null){//已经收录，进行修改看片次数，每次减一，
                                boolean isi = yzgz(bot,update.getMessage().getFrom().getId());
                                if (isi){
                                    if (infouser.getUserbalance()>0){
                                        kaiche(update, bot,update.getMessage().getChat().getId()+"",isi);
                                        infouserService.updateye(infouser.getUserbalance()-1,infouser.getUsernumber());
                                        // 正常开车
                                        Infouser shangji = infouserService.findByUsernumber(infouser.getShangjitok());
                                        //如果下级没有给上级做出贡献

                                        if (infouser.getIsi() == 0) {
                                            //给上级邀请人数加1
                                            if(shangji!=null){
                                                int cishu = shangji.getUserbalance();
                                                int cishui = shangji.getUseryaoqing()+1;
                                                infouserService.updatestart(cishu,cishui,infouser.getShangjitok(), shangji.getIsi());
                                            }


                                            //修改下级的贡献标识
                                            int xiajicishu = infouser.getUserbalance();
                                            int xiajicishui = infouser.getUseryaoqing();

                                            infouserService.updatestart(xiajicishu,xiajicishui,infouser.getUsernumber(), 1);

                                        } else {

                                            //如果下级给上级做出贡献
                                        }

                                    }else{
                                        //观影不足，需要邀请
                                        //观影不足，需要邀请
                                        SendMessage sendMessage = new SendMessage();
                                        sendMessage.setText("您的观影次数不足，可通过邀请用户获得\n" +
                                                "\n" +
                                                "发送 /yq");
                                        sendMessage.setChatId(update.getMessage().getChat().getId()+"");
                                        ReplyKeyboardMarkup replyKeyboardMarkup = updateutil.getReplyKeyboardMarkup();
                                        sendMessage.setReplyMarkup(replyKeyboardMarkup);
                                        try {
                                            bot.execute(sendMessage);
                                        } catch (TelegramApiException e) {
                                            e.printStackTrace();
                                        }

                                    }

                                }else{
                                    SendMessage sendMessage = updateutil.getSendMessagetext(
                                            "**请加入此<a href=\"https://t.me/tadb2\">频道</a> 后才能使用**\n" +
                                                    "—————————————-\n" +
                                                    "关注后 开车 /kc  \uD83D\uDC48点击\n" +
                                                    "〖邀请好友〗 /yq \n" +
                                                    "〖活动介绍〗 /hd",update.getMessage().getChat().getId()+"");
                                    ReplyKeyboardMarkup replyKeyboardMarkup = updateutil.getReplyKeyboardMarkup();
                                    sendMessage.setReplyMarkup(replyKeyboardMarkup);
                                    try {
                                        bot.execute(sendMessage);
                                    } catch (TelegramApiException e) {
                                        e.printStackTrace();
                                    }
                                }

                            }
                        }
                        else if (update.getMessage().getText().equals("✅官方频道")){
                            //https://t.me/tadb2 为跳转链接  频道为显示文本
                            SendMessage sendMessage = updateutil.getSendMessagetext(
                                    "\uD83D\uDD39泰安官方\n"+
                                            "\n" +
                                            "官方频道点击下方蓝色字体进入\n" +
                                            "\n" +
                                            "点我进入<a href=\"https://t.me/+6kjm8sUBIPI5NDMx\">✅泰安官方频道</a> "
                                            ,update.getMessage().getChat().getId()+"");
                            ReplyKeyboardMarkup replyKeyboardMarkup = updateutil.getReplyKeyboardMarkup();
                            sendMessage.setReplyMarkup(replyKeyboardMarkup);
                            try {
                                bot.execute(sendMessage);
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                        }
                        else if (update.getMessage().getText().equals("推广结账\uD83D\uDCB4")){
                            //https://t.me/tadb2 为跳转链接  频道为显示文本
                            SendMessage sendMessage = updateutil.getSendMessagetext(
                                    "\uD83D\uDD39我要结账\n"+
                                            "\n" +
                                            "推广结账点击下方蓝色字体进入\n" +
                                            "\n" +
                                            "点我进入<a href=\"https://t.me/tadb0\">\uD83D\uDC4D我要结账</a> "
                                    ,update.getMessage().getChat().getId()+"");
                            ReplyKeyboardMarkup replyKeyboardMarkup = updateutil.getReplyKeyboardMarkup();
                            sendMessage.setReplyMarkup(replyKeyboardMarkup);
                            try {
                                bot.execute(sendMessage);
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                        }
                        else if (update.getMessage().getText().equals("\uD83D\uDCA1自助服务")){
                            //https://t.me/tadb2 为跳转链接  频道为显示文本
                            SendMessage sendMessage = updateutil.getSendMessagetext(
                                    "\uD83D\uDD38自助服务\n"+
                                            "\n" +
                                            "自助服务点击下方蓝色字体进入\n" +
                                            "\n" +
                                            "点我进入<a href=\"https://t.me/tadb6/5\">\uD83D\uDCA1自助服务中心</a> "
                                    ,update.getMessage().getChat().getId()+"");
                            ReplyKeyboardMarkup replyKeyboardMarkup = updateutil.getReplyKeyboardMarkup();
                            sendMessage.setReplyMarkup(replyKeyboardMarkup);
                            try {
                                bot.execute(sendMessage);
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }

                        }
                        else if (update.getMessage().getText().equals("推广大群\uD83D\uDCB0")){
                            //https://t.me/tadb2 为跳转链接  频道为显示文本
                            SendMessage sendMessage = updateutil.getSendMessagetext(
                                    "\uD83D\uDD38色色推广 \n"+
                                            "\n" +
                                            "色色推广群击下方蓝色字体进入\n" +
                                            "\n" +
                                            "点我进入<a href=\"https://t.me/tasstg1\">\uD83D\uDCA1色色推广群</a> "

                                    ,update.getMessage().getChat().getId()+"");
                            ReplyKeyboardMarkup replyKeyboardMarkup = updateutil.getReplyKeyboardMarkup();
                            sendMessage.setReplyMarkup(replyKeyboardMarkup);
                            try {
                                bot.execute(sendMessage);
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                        }
                        else if (update.getMessage().getText().equals("✅太子担保交流2群")){
                            //https://t.me/tadb2 为跳转链接  频道为显示文本
                            SendMessage sendMessage = updateutil.getSendMessagetext(
                                    "<a href=\"https://t.me/tadb2\">✅太子担保交流2群</a>"

                                    ,update.getMessage().getChat().getId()+"");

                            ReplyKeyboardMarkup replyKeyboardMarkup = updateutil.getReplyKeyboardMarkup();
                            sendMessage.setReplyMarkup(replyKeyboardMarkup);
                            try {
                                bot.execute(sendMessage);
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                        }
                        else if (update.getMessage().getText().equals("\uD83E\uDDE7每晚官方频道活动\uD83E\uDDE7")){
                            //https://t.me/tadb2 为跳转链接  频道为显示文本
                            SendMessage sendMessage = updateutil.getSendMessagetext(
                                    "\uD83E\uDDE7每晚活动 \n"+
                                            "\n" +
                                            "泰安担保-为了回馈各位老板设立了每晚万元红包大群免费领取红包\n" +
                                            "\n" +
                                            "参与方式点击下方蓝色字体加群领红包\n" +
                                            "\n" +
                                            "\n" +
                                            "点我进入<a href=\"https://t.me/+6kjm8sUBIPI5NDMx\">\uD83E\uDDE7免费领取红包</a> "
                                    ,update.getMessage().getChat().getId()+"");
                            ReplyKeyboardMarkup replyKeyboardMarkup = updateutil.getReplyKeyboardMarkup();
                            sendMessage.setReplyMarkup(replyKeyboardMarkup);
                            try {
                                bot.execute(sendMessage);
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                //上传视频的用户信息
                if (ids.equals("6017229603")||ids.equals("5930350622")) {
                    if (update.getMessage().getVideo() != null) {
                        //上传视频
                        Shipin shipin = new Shipin();
                        shipin.setShipinid(update.getMessage().getVideo().getFileId());
                        if(update.getMessage().getCaption()!=null){
                            if (update.getMessage().getCaption().equals("d")) {
                                shipin.setLengths(1);
                            } else {
                                shipin.setLengths(0);
                            }
                        }else{
                            shipin.setLengths(0);
                        }


                        shipin.setMisoshu("123");
                        shipinService.add(shipin);

                    }
                    if (update.getMessage().getText() != null) {
                        if (update.getMessage().getText().equals("查看")) {
                            List<Infouser> list = infouserService.select();
                            SendMessage sendMessage = new SendMessage();
                            sendMessage.setText("机器人使用（" +list.size()+")人");
                            sendMessage.setChatId(update.getMessage().getChat().getId()+"");
                            try {
                                bot.execute(sendMessage);
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                        }
                        if (NumberUtils.isDigits(update.getMessage().getText())){


                                Infouser infouser = infouserService.findByUsernumber(update.getMessage().getText());
                                SendMessage sendMessage = new SendMessage();

                                sendMessage.setChatId(update.getMessage().getChat().getId()+"");


                                sendMessage.setParseMode("HTML");
                                try {
                                    if(infouser!=null){
                                        String yaoqingzhuan ="\uD83C\uDF81邀请赚\uD83C\uDF1F" +
                                                "〖您的账号〗<code>"+infouser.getUsernumber()+"</code>（点击复制）\n" +
                                                "〖您的余额〗"+infouser.getUserbalance()+"坨\uD83C\uDF1F" +
                                                "〖邀请链接〗点击复制⬇️\n" +
                                                "<code>https://t.me/cesh222i_BOT?start="+infouser.getTokens()+"</code>\n" +
                                                "〖您已邀请〗"+infouser.getUseryaoqing()+" 人\n" +
                                                "〖您已结算〗"+infouser.getYiduihuan()+" 人\n" +
                                                "〖您未结算〗"+(infouser.getUseryaoqing()-infouser.getYiduihuan())+" 人\n" +
                                                "\uD83D\uDCA1每邀请 10 人，奖励 188 坨\uD83C\uDF1F（邀请越多，奖励越多！)";
                                        sendMessage.setText(yaoqingzhuan);
                                        ReplyKeyboardMarkup replyKeyboardMarkup = updateutil.getReplyKeyboardMarkup();
                                        sendMessage.setReplyMarkup(replyKeyboardMarkup);
                                        bot.execute(sendMessage);
                                    }else{
                                        String yaoqingzhuan1 ="\uD83C\uDF81查询的账号不存在\uD83C\uDF1F";
                                        sendMessage.setText(yaoqingzhuan1);
                                        ReplyKeyboardMarkup replyKeyboardMarkup = updateutil.getReplyKeyboardMarkup();
                                        sendMessage.setReplyMarkup(replyKeyboardMarkup);
                                        bot.execute(sendMessage);
                                    }

                                } catch (TelegramApiException e) {
                                    e.printStackTrace();
                                }


                        }
                        if (update.getMessage().getText().contains("兑")){

                            String test =update.getMessage().getText();
                            String dh[] = test.split("兑");
                            if(NumberUtils.isDigits(dh[1])&&NumberUtils.isDigits(dh[0])){


                                    Infouser infouser = infouserService.findByUsernumber(dh[0]);
                                    SendMessage sendMessage = new SendMessage();

                                    sendMessage.setChatId(update.getMessage().getChat().getId()+"");


                                    sendMessage.setParseMode("HTML");
                                    try {
                                        if(infouser!=null){
                                            int duihuan = infouser.getYiduihuan();
                                            int yq = infouser.getUseryaoqing();
                                            int weijiesuan = yq-duihuan;
                                            int yaoduihuan = Integer.parseInt(dh[1]);
                                            if(yaoduihuan>weijiesuan){
                                                //提示没有那么多
                                                String yaoqingzhuan2 ="\uD83C\uDF81可用余额不足\uD83C\uDF1F";
                                                sendMessage.setText(yaoqingzhuan2);
                                                ReplyKeyboardMarkup replyKeyboardMarkup = updateutil.getReplyKeyboardMarkup();
                                                sendMessage.setReplyMarkup(replyKeyboardMarkup);
                                                bot.execute(sendMessage);
                                            }else{

                                                infouserService.updateduihuan(infouser.getYiduihuan()+Integer.parseInt(dh[1]),
                                                        infouser.getUsernumber());
                                                Infouser infouser1 = infouserService.findByUsernumber(dh[0]);
                                                String yaoqingzhuan ="\uD83C\uDF81邀请赚\uD83C\uDF1F" +
                                                        "〖您的账号〗"+infouser1.getUsernumber()+"（点击复制）\n" +
                                                        "〖您的余额〗"+infouser1.getUserbalance()+"坨\uD83C\uDF1F" +
                                                        "〖邀请链接〗点击复制⬇️\n" +
                                                        "<code>https://t.me/cesh222i_BOT?start="+infouser1.getTokens()+"</code>\n" +
                                                        "〖您已邀请〗"+infouser1.getUseryaoqing()+" 人\n" +
                                                        "〖您已结算〗"+infouser1.getYiduihuan()+" 人\n" +
                                                        "〖您未结算〗"+(infouser1.getUseryaoqing()-infouser1.getYiduihuan())+" 人\n" +
                                                        "\uD83D\uDCA1每邀请 10 人，奖励 188 坨\uD83C\uDF1F（邀请越多，奖励越多！)";
                                                sendMessage.setText(yaoqingzhuan);
                                                ReplyKeyboardMarkup replyKeyboardMarkup = updateutil.getReplyKeyboardMarkup();
                                                sendMessage.setReplyMarkup(replyKeyboardMarkup);
                                                bot.execute(sendMessage);
                                            }



                                        }else{
                                            String yaoqingzhuan1 ="\uD83C\uDF81查询的账号不存在\uD83D\uDCA9";
                                            sendMessage.setText(yaoqingzhuan1);
                                            ReplyKeyboardMarkup replyKeyboardMarkup = updateutil.getReplyKeyboardMarkup();
                                            sendMessage.setReplyMarkup(replyKeyboardMarkup);
                                            bot.execute(sendMessage);
                                        }

                                    } catch (TelegramApiException e) {
                                        e.printStackTrace();
                                    }

                            }


                        }
                    }
                }

                    if (update.getMessage().getText() != null) {
                        if (update.getMessage().getText().equals("/kc")) {// 处理普通文本消息
                            Infouser infouser = infouserService.findByUsernumber(update.getMessage().getChat().getId()+"");
                            //2.如果收录了
                            if (infouser!=null){//已经收录，进行修改看片次数，每次减一，
                                boolean isi = yzgz(bot,update.getMessage().getFrom().getId());
                                if (isi){
                                    if (infouser.getUserbalance()>0){
                                        kaiche(update, bot,update.getMessage().getChat().getId()+"",isi);
                                        infouserService.updateye(infouser.getUserbalance()-1,infouser.getUsernumber());
                                        // 正常开车
                                        Infouser shangji = infouserService.findByUsernumber(infouser.getShangjitok());
                                        //如果下级没有给上级做出贡献

                                        if (infouser.getIsi() == 0) {
                                            //给上级邀请人数加1
                                            if(shangji!=null){
                                                int cishu = shangji.getUserbalance();
                                                int cishui = shangji.getUseryaoqing()+1;
                                                infouserService.updatestart(cishu,cishui,infouser.getShangjitok(), shangji.getIsi());
                                            }


                                            //修改下级的贡献标识
                                            int xiajicishu = infouser.getUserbalance();
                                            int xiajicishui = infouser.getUseryaoqing();

                                            infouserService.updatestart(xiajicishu,xiajicishui,infouser.getUsernumber(), 1);

                                        } else {

                                            //如果下级给上级做出贡献
                                        }

                                    }else{
                                        //观影不足，需要邀请
                                        //观影不足，需要邀请
                                        SendMessage sendMessage = new SendMessage();
                                        sendMessage.setText("您的观影次数不足，可通过邀请用户获得\n" +
                                                "\n" +
                                                "发送 /yq");
                                        sendMessage.setChatId(update.getMessage().getChat().getId()+"");
                                        ReplyKeyboardMarkup replyKeyboardMarkup = updateutil.getReplyKeyboardMarkup();
                                        sendMessage.setReplyMarkup(replyKeyboardMarkup);
                                        try {
                                            bot.execute(sendMessage);
                                        } catch (TelegramApiException e) {
                                            e.printStackTrace();
                                        }

                                    }

                                }else{
                                    SendMessage sendMessage = updateutil.getSendMessagetext(
                                            "**请加入此<a href=\"https://t.me/tadb2\">频道</a> 后才能使用**\n" +
                                                    "—————————————-\n" +
                                                    "关注后 开车 /kc  \uD83D\uDC48点击\n" +
                                                    "〖邀请好友〗 /yq \n" +
                                                    "〖活动介绍〗 /hd",update.getMessage().getChat().getId()+"");
                                    ReplyKeyboardMarkup replyKeyboardMarkup = updateutil.getReplyKeyboardMarkup();
                                    sendMessage.setReplyMarkup(replyKeyboardMarkup);
                                    try {
                                        bot.execute(sendMessage);
                                    } catch (TelegramApiException e) {
                                        e.printStackTrace();
                                    }
                                }

                            }

                        }
                    }


            }

    }else{
            if (update.getCallbackQuery() != null) {
                if (update.getCallbackQuery().getData().equals("长视频")) {



                        Infouser infouser = infouserService.findByUsernumber(update.getCallbackQuery().getMessage().getChat().getId()+"");
                        //2.如果收录了
                        if (infouser!=null){//已经收录，进行修改看片次数，每次减一，
                            boolean isi = yzgz(bot,update.getCallbackQuery().getFrom().getId());
                            if (isi){
                                if (infouser.getUserbalance()>0){
                                    List<Shipin> list = shipinService.findByLengths(0);
                                    kaiches(update, bot,update.getCallbackQuery().getMessage().getChat().getId()+"",isi,list);
                                    infouserService.updateye(infouser.getUserbalance()-1,infouser.getUsernumber());
                                }else{
                                    //观影不足，需要邀请
                                    //观影不足，需要邀请
                                    SendMessage sendMessage = new SendMessage();
                                    sendMessage.setText("您的观影次数不足，可通过邀请用户获得\n" +
                                            "\n" +
                                            "发送 /yq");
                                    sendMessage.setChatId(update.getCallbackQuery().getMessage().getChat().getId()+"");
                                    ReplyKeyboardMarkup replyKeyboardMarkup = updateutil.getReplyKeyboardMarkup();
                                    sendMessage.setReplyMarkup(replyKeyboardMarkup);
                                    try {
                                        bot.execute(sendMessage);
                                    } catch (TelegramApiException e) {
                                        e.printStackTrace();
                                    }

                                }

                            }else{
                                SendMessage sendMessage = updateutil.getSendMessagetext(
                                        "**请加入此<a href=\"https://t.me/tadb2\">频道</a> 后才能使用**\n" +
                                                "—————————————-\n" +
                                                "关注后 开车 /kc  \uD83D\uDC48点击\n" +
                                                "〖邀请好友〗 /yq \n" +
                                                "〖活动介绍〗 /hd",update.getCallbackQuery().getMessage().getChat().getId()+"");
                                ReplyKeyboardMarkup replyKeyboardMarkup = updateutil.getReplyKeyboardMarkup();
                                sendMessage.setReplyMarkup(replyKeyboardMarkup);
                                try {
                                    bot.execute(sendMessage);
                                } catch (TelegramApiException e) {
                                    e.printStackTrace();
                                }
                            }















                        }

                }
                else if (update.getCallbackQuery().getData().equals("短视频")) {
                    Infouser infouser = infouserService.findByUsernumber(update.getCallbackQuery().getMessage().getChat().getId()+"");
                    //2.如果收录了
                    if (infouser!=null){//已经收录，进行修改看片次数，每次减一，
                        boolean isi = yzgz(bot,update.getCallbackQuery().getFrom().getId());
                        if (isi){
                            if (infouser.getUserbalance()>0){
                                List<Shipin> list = shipinService.findByLengths(1);
                                kaiches(update, bot,update.getCallbackQuery().getMessage().getChat().getId()+"",isi,list);
                                infouserService.updateye(infouser.getUserbalance()-1,infouser.getUsernumber());
                            }else{
                                //观影不足，需要邀请
                                //观影不足，需要邀请
                                SendMessage sendMessage = new SendMessage();
                                sendMessage.setText("您的观影次数不足，可通过邀请用户获得\n" +
                                        "\n" +
                                        "发送 /yq");
                                sendMessage.setChatId(update.getCallbackQuery().getMessage().getChat().getId()+"");
                                ReplyKeyboardMarkup replyKeyboardMarkup = updateutil.getReplyKeyboardMarkup();
                                sendMessage.setReplyMarkup(replyKeyboardMarkup);
                                try {
                                    bot.execute(sendMessage);
                                } catch (TelegramApiException e) {
                                    e.printStackTrace();
                                }

                            }

                        }else{
                            SendMessage sendMessage = updateutil.getSendMessagetext(
                                    "**请加入此<a href=\"https://t.me/qianlongceshipindao\">频道</a> 后才能使用**\n" +
                                            "—————————————-\n" +
                                            "关注后 开车 /kc  \uD83D\uDC48点击\n" +
                                            "〖邀请好友〗 /yq \n" +
                                            "〖活动介绍〗 /hd",update.getCallbackQuery().getMessage().getChat().getId()+"");
                            ReplyKeyboardMarkup replyKeyboardMarkup = updateutil.getReplyKeyboardMarkup();
                            sendMessage.setReplyMarkup(replyKeyboardMarkup);
                            try {
                                bot.execute(sendMessage);
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }
                else if (update.getCallbackQuery().getData().equals("随机视频")) {
                    Infouser infouser = infouserService.findByUsernumber(update.getCallbackQuery().getMessage().getChat().getId()+"");
                    //2.如果收录了
                    if (infouser!=null){//已经收录，进行修改看片次数，每次减一，
                        boolean isi = yzgz(bot,update.getCallbackQuery().getFrom().getId());
                        if (isi){
                            if (infouser.getUserbalance()>0){
                                List<Shipin> list = shipinService.select();
                                kaiches(update, bot,update.getCallbackQuery().getMessage().getChat().getId()+"",isi,list);
                                infouserService.updateye(infouser.getUserbalance()-1,infouser.getUsernumber());
                            }else{
                                //观影不足，需要邀请
                                //观影不足，需要邀请
                                SendMessage sendMessage = new SendMessage();
                                sendMessage.setText("您的观影次数不足，可通过邀请用户获得\n" +
                                        "\n" +
                                        "发送 /yq");
                                sendMessage.setChatId(update.getCallbackQuery().getMessage().getChat().getId()+"");
                                ReplyKeyboardMarkup replyKeyboardMarkup = updateutil.getReplyKeyboardMarkup();
                                sendMessage.setReplyMarkup(replyKeyboardMarkup);
                                try {
                                    bot.execute(sendMessage);
                                } catch (TelegramApiException e) {
                                    e.printStackTrace();
                                }

                            }

                        }else{
                            SendMessage sendMessage = updateutil.getSendMessagetext(
                                    "**请加入此<a href=\"https://t.me/qianlongceshipindao\">频道</a> 后才能使用**\n" +
                                            "—————————————-\n" +
                                            "关注后 开车 /kc  \uD83D\uDC48点击\n" +
                                            "〖邀请好友〗 /yq \n" +
                                            "〖活动介绍〗 /hd",update.getCallbackQuery().getMessage().getChat().getId()+"");
                            try {
                                bot.execute(sendMessage);
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }
            }
        }


        logger.info("end executeAsync");
    }

}
