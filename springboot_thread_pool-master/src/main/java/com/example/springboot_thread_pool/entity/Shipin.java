package com.example.springboot_thread_pool.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Shipin {
    @Id
    private String shipinid;
    private int lengths;
    private String misoshu;

    public void setShipinid(String shipinid) {
        this.shipinid = shipinid;
    }

    public void setLengths(int lengths) {
        this.lengths = lengths;
    }

    public void setMisoshu(String misoshu) {
        this.misoshu = misoshu;
    }

    public String getShipinid() {
        return shipinid;
    }

    public int isLengths() {
        return lengths;
    }

    public String getMisoshu() {
        return misoshu;
    }
}
