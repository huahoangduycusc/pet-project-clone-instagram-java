/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hhd.entity;

import hhd.getConnection;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author asus
 */
public class Account {
    private int id;
    private String username;
    private String password;
    private String fullname;
    private String email;
    private String address;
    private String favourite;
    private String phone;
    private Date birthday;
    private String website;
    private int post;
    private int follower;

    public int getRights() {
        return rights;
    }

    public void setRights(int rights) {
        this.rights = rights;
    }
    private int following;
    private String avatar;
    private int verified;
    private String gender;
    private int rights;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getVerified() {
        return verified;
    }

    public void setVerified(int verified) {
        this.verified = verified;
    }

    public Account() {
    }

    public Account(int id, String username, String fullname, String avatar) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.avatar = avatar;
    }

    public Account(int id, String username, String password, String fullname, String email, String address, String favourite, String phone, Date birthday, String website, int post) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.email = email;
        this.address = address;
        this.favourite = favourite;
        this.phone = phone;
        this.birthday = birthday;
        this.website = website;
        this.post = post;
        this.getInfo(id);
    }
    public Account(int id, String username, String password, String fullname, String email, String address, String favourite, String phone, Date birthday, String website, int post, String avatar, int verified, String gender, int rights) {
            this.id = id;
            this.username = username;
            this.password = password;
            this.fullname = fullname;
            this.email = email;
            this.address = address;
            this.favourite = favourite;
            this.phone = phone;
            this.birthday = birthday;
            this.website = website;
            this.post = post;
            this.avatar = avatar;
            this.verified = verified;
            this.gender = gender;
            this.rights = rights;
            this.getInfo(id);
        }
    public int getFollower() {
        return follower;
    }

    public int getFollowing() {
        return following;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFavourite() {
        return favourite;
    }

    public void setFavourite(String favourite) {
        this.favourite = favourite;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public int getPost() {
        return post;
    }

    public void setPost(int post) {
        this.post = post;
    }
    // get follower and following number
    public void getInfo(int id){
        Connection con = getConnection.connect();
        try{
            String sql1 = "SELECT count(follow_id) as counter FROM account_follows WHERE  from_user ="+id;
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql1);
            if(rs.next()){
                this.following = rs.getInt(1);
            }
            String sql2 = "SELECT count(follow_id) as counter FROM account_follows WHERE  to_user ="+id;
            Statement st2 = con.createStatement();
            ResultSet rs2 = st2.executeQuery(sql2);
            if(rs2.next()){
                this.follower = rs2.getInt(1);
            }
            
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
