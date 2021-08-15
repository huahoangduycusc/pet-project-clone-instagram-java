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
public class Post {
    private int id;
    private String message ;
    private int userId;
    private String date;
    private String thumbnail;
    private int like;
    private int reply;
    private String author;
    private String avatar;
    private int verified;
    private int report;

    public int getReport() {
        return report;
    }

    public void setReport(int report) {
        this.report = report;
    }

    public int getVerified() {
        return verified;
    }

    public String getAvatar() {
        return avatar;
    }
    
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getReply() {
        return reply;
    }

    public void setReply(int reply) {
        this.reply = reply;
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

    public Post(int id, String message, int userId, String date) {
        this.id = id;
        this.message = message;
        this.userId = userId;
        this.date = date;
    }

    public Post(int id, String message, int userId, String date, String thumbnail, int like, int reply) {
        this.id = id;
        this.message = message;
        this.userId = userId;
        this.date = date;
        this.thumbnail = thumbnail;
        this.like = like;
        this.reply = reply;
    }
    public Post(int id, String message, int userId, String date, String thumbnail, int like, int reply, int report) {
        this.id = id;
        this.message = message;
        this.userId = userId;
        this.date = date;
        this.thumbnail = thumbnail;
        this.like = like;
        this.reply = reply;
        this.report = report;
    }

    public Post(int id, String message, int userId, String date, String thumbnail, int like, String author, String avatar, int verified) {
        this.id = id;
        this.message = message;
        this.userId = userId;
        this.date = date;
        this.thumbnail = thumbnail;
        this.like = like;
        this.author = author;
        this.avatar = avatar;
        this.verified = verified;
    }
    
}
