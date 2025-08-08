package com.example.springboot_thread_pool.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Pindaouser {

    private String usernama;

    @Id
    private String usernumber;

    public void setUsernama(String usernama) {
        this.usernama = usernama;
    }

    public void setUsernumber(String usernumber) {
        this.usernumber = usernumber;
    }

    public String getUsernama() {
        return usernama;
    }

    public String getUsernumber() {
        return usernumber;
    }
}
