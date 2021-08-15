/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hhd;

import hhd.entity.Account;
import hhd.entity.Post;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author asus
 */
public class followDAO {
    public int user_id;

    public followDAO(int user_id) {
        this.user_id = user_id;
    }
    // get list
    public ArrayList<Post> getList()
    {
        ArrayList<Post> post = null;
        try{
            post = new ArrayList();
            Connection con = getConnection.connect();
            String ids = getListUserFollowed();
            String sql = "SELECT TOP 5 p.post_id,p.user_id,p.post_msg,p.post_date,a.username,a.username,a.avatar,a.verified " +
            "FROM post p JOIN account a ON p.user_id = a.user_id " +
            "WHERE a.user_id in("+ids+") order by post_id desc";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                int id =  rs.getInt("post_id");
                post.add(new Post(id, rs.getString("post_msg"), rs.getInt("user_id"), 
                        rs.getString("post_date"),getImages(id),
                        getLikeOfPost(id),
                        rs.getString("username"),
                        rs.getString("avatar"),
                rs.getInt("verified")));
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        
        return post;
    }
    // get list id user followed
    public String getListUserFollowed(){
        String listId = "0,";
        try{
            Connection con = getConnection.connect();
            String sql = "SELECT to_user from account_follows WHERE from_user = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, this.user_id);
            ResultSet rs = pst.executeQuery();
            while(rs.next())
            {
                listId += rs.getInt("to_user") + ",";
            }
            if(listId != "")
            {
                listId = listId.substring(0, listId.length() - 1);
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return listId;
    }
    // get list of images
    public String getImages(int id){
        String img = "";
        try{
            Connection con = getConnection.connect();
            String sql = "SELECT top 1 * from post_images WHERE post_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                img = rs.getString(2);
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return img;
    }
    // get like of post
    public int getLikeOfPost(int id){
        int count = 0;
        try{
            Connection con = getConnection.connect();
            String sql = "SELECT COUNT(like_id) as counter FROM post_likes WHERE post_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                count = rs.getInt(1);
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return count;
    }
    // author
    public String getAuthor(int id)
    {
        String user = "";
        try{
            Connection con = getConnection.connect();
            String sql = "SELECT username FROM account WHERE user_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                user = rs.getString("username");
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return user;
    }
    // get avatar
    public String getAvatar(int id)
    {
        String user = "";
        try{
            Connection con = getConnection.connect();
            String sql = "SELECT avatar FROM account WHERE user_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                user = rs.getString("avatar");
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return user;
    }
    // loop dot images
    public int loopDot(int id){
        int loop = 0;
        try{
            Connection con = getConnection.connect();
            String sql = "SELECT count(image_id) as counter FROM post_images where post_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                loop = rs.getInt(1);
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return loop;
    }
    // count article
    public int countArticle(){
        int kq = 0;
        try{
            Connection con = getConnection.connect();
            String ids = getListUserFollowed();
            String sql = "SELECT count(p.post_id) as counter " +
            "FROM post p JOIN account a ON p.user_id = a.user_id " +
            "WHERE a.user_id in("+ids+")";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()){
                kq = rs.getInt(1);
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return kq;
    }
    // get list followers
    public static ArrayList<Account> listFollowers(int user_id){
        ArrayList<Account> list = null;
        try{
            list = new ArrayList<>();
            Connection con = getConnection.connect();
            String sql = "SELECT f.follow_id, a.user_id,a.username,a.fullname,a.avatar from account_follows f join account a " +
            "on f.from_user = a.user_id " +
            "WHERE f.to_user = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, user_id);
            ResultSet rs = pst.executeQuery();
            while(rs.next())
            {
                list.add(new Account(rs.getInt("user_id"), rs.getString("username"), rs.getString("fullname"), rs.getString("avatar")));
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return list;
    }
    // get list followers
    public static ArrayList<Account> listFollowing(int user_id){
        ArrayList<Account> list = null;
        try{
            list = new ArrayList<>();
            Connection con = getConnection.connect();
            String sql = "SELECT f.follow_id, a.user_id,a.username,a.fullname,a.avatar from account_follows f join account a " +
            "on f.to_user = a.user_id " +
            "WHERE f.from_user = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, user_id);
            ResultSet rs = pst.executeQuery();
            while(rs.next())
            {
                list.add(new Account(rs.getInt("user_id"), rs.getString("username"), rs.getString("fullname"), rs.getString("avatar")));
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return list;
    }
    
}
