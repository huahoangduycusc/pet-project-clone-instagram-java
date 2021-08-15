/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hhd.entity;

/**
 *
 * @author asus
 */
public class Notification {
    private String msg;
    private String date;

    public Notification(String msg, String date) {
        this.msg = msg;
        this.date = date;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDate() {
        return date.substring(0,19);
    }

    public void setDate(String date) {
        this.date = date;
    }
}
