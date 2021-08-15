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
public class Comment {
    private int id;
    private String message;
    private int userId;
    private String date;
    private String avatar;
    private int verified;
    private String username;

    public int getVerified() {
        return verified;
    }

    public void setVerified(int verified) {
        this.verified = verified;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date.substring(0, 16);
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Comment(int id, String message, int userId, String date, String avatar, int verified, String username) {
        this.id = id;
        this.message = message;
        this.userId = userId;
        this.date = date;
        this.avatar = avatar;
        this.verified = verified;
        this.username = username;
    }

}
