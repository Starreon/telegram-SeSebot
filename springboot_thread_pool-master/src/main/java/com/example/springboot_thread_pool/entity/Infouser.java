package com.example.springboot_thread_pool.entity;


import javax.persistence.*;

@Entity
public class Infouser {
    @Id
    private String usernumber;
    private String username;
    private int useryaoqing;
    private int userbalance;
    private int yiduihuan;
    private String tokens;
    private String shangjitok;

    private int isi;

    public void setShangjitok(String shangjitok) {
        this.shangjitok = shangjitok;
    }

    public void setIsi(int isi) {
        this.isi = isi;
    }

    public String getShangjitok() {
        return shangjitok;
    }

    public int getIsi() {
        return isi;
    }

    public void setUsernumber(String usernumber) {
        this.usernumber = usernumber;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUseryaoqing(int useryaoqing) {
        this.useryaoqing = useryaoqing;
    }

    public void setUserbalance(int userbalance) {
        this.userbalance = userbalance;
    }

    public void setYiduihuan(int yiduihuan) {
        this.yiduihuan = yiduihuan;
    }

    public void setTokens(String tokens) {
        this.tokens = tokens;
    }

    public String getUsernumber() {
        return usernumber;
    }

    public String getUsername() {
        return username;
    }

    public int getUseryaoqing() {
        return useryaoqing;
    }

    public int getUserbalance() {
        return userbalance;
    }

    public int getYiduihuan() {
        return yiduihuan;
    }

    public String getTokens() {
        return tokens;
    }
}
