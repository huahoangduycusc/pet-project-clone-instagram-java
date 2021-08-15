/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hhd;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import hhd.entity.Account;
import hhd.entity.Notification;
import java.sql.Date;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
/**
 *
 * @author asus
 */
public class accountDAO {
    // get row
    public Account getRow(int id){
        Account ac = null;
        try{
            Connection con = getConnection.connect();
            String sql = "SELECT * FROM account WHERE user_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                ac = new Account(rs.getInt("user_id"), rs.getString("username"), 
                        rs.getString("password"), rs.getString("fullname"), rs.getString("email"), 
                        rs.getString("address"), rs.getString("favourite"), rs.getString("phone"),
                        rs.getDate("birthday"), rs.getString("website"),
                        rs.getInt("post"),
                rs.getString("avatar"),
                rs.getInt("verified"),
                rs.getString("gender"),
                rs.getInt("rights"));
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        
        return ac;
    }
    // follow another user
    public int followUser(int to_user, int from_user){
        int kq = 0;
        try{
            Connection con = getConnection.connect();
            String sql = "SELECT * FROM account_follows WHERE to_user = ? AND from_user = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, to_user);
            pst.setInt(2, from_user);
            ResultSet rs = pst.executeQuery();
            if(rs.next())
            {
                String sql2 = "DELETE FROM account_follows WHERE follow_id = "+rs.getInt("follow_id");
                Statement st = con.createStatement();
                kq = st.executeUpdate(sql2);
                kq = 1;
            }
            else{
                String sql2 = "INSERT INTO account_follows(to_user,from_user) VALUES(?,?)";
                PreparedStatement pst2 = con.prepareStatement(sql2);
                pst2.setInt(1, to_user);
                pst2.setInt(2, from_user);
                kq = pst2.executeUpdate();
                kq = 2;
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return kq;
    }
    // login
    public int login(String username, String password)
    {
        int login = 0;
        try{
            Connection con = getConnection.connect();
            String hashPassword = md5(password);
            String sql = "SELECT * FROM account WHERE username = ? and password = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1,username);
            pst.setString(2, hashPassword);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                login = rs.getInt("user_id");
            }
            else{
                login = 0;
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return login;
    }
    // hash password = ma hoa password
    public String md5(String input) {
		
        String md5 = null;

        if(null == input) return null;

        try {

        //Create MessageDigest object for MD5
        MessageDigest digest = MessageDigest.getInstance("MD5");

        //Update input string in message digest
        digest.update(input.getBytes(), 0, input.length());

        //Converts message digest value in base 16 (hex) 
        md5 = new BigInteger(1, digest.digest()).toString(16);

        } catch (NoSuchAlgorithmException e) {

                e.printStackTrace();
        }
        return md5;
    }
    // check is followed ?
    public boolean isFollowed(int to_user, int from_user){
        boolean flag = false;
        try{
            Connection con = getConnection.connect();
            String sql = "SELECT * FROM account_follows WHERE to_user = ? AND from_user = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, to_user);
            pst.setInt(2, from_user);
            ResultSet rs = pst.executeQuery();
            if(rs.next())
            {
                flag = true;
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return flag;
    }
    // check valid email
    public boolean isValid(String email) {
      String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
      return email.matches(regex);
   }
    // check if user is contains special character ?
    public boolean isValidUser(String user){
        String regex = "^[a-zA-Z0-9]{5,10}$";
        return user.matches(regex);
    }
    // check is if exists account in db a18138
    public boolean isExistUser(String user){
        boolean flag = false;
        try{
            Connection con = getConnection.connect();
            String sql = "SELECT * FROM account WHERE username = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, user);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                flag = true;
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return flag;
    }
     // check is if exists account in db a18138
    public boolean isEmailExists(String email){
        boolean flag = false;
        try{
            Connection con = getConnection.connect();
            String sql = "SELECT * FROM account WHERE email = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                flag = true;
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return flag;
    }
    // insert account
    public int insertAccount(Account a){
        int kq = 0;
        try{
            Connection con = getConnection.connect();
            String sql = "INSERT INTO account(username,password,fullname,email,address,favourite,birthday,phone,website,post,verified,avatar,gender,rights)"
                    + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?); SELECT SCOPE_IDENTITY()";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, a.getUsername());
            pst.setString(2, a.getPassword());
            pst.setString(3, a.getFullname());
            pst.setString(4, a.getEmail());
            pst.setString(5, "");
            pst.setString(6, "");
            pst.setDate(7, java.sql.Date.valueOf("1970-06-27"));
            pst.setString(8, "");
            pst.setString(9, "");
            pst.setInt(10, 0);
            pst.setInt(11, 0);
            pst.setString(12, "default.jpg");
            pst.setString(13, "no");
            pst.setInt(14, 0);
            kq = pst.executeUpdate();
            if(kq > 0)
            {
                ResultSet rs = pst.getGeneratedKeys();
                rs.next();
                kq = rs.getInt(1);
            }
            
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return kq;
    }
    // update
    public int updateInfo(Account a){
        int kq = 0;
        try{
            Connection con = getConnection.connect();
            String sql = "";
            if(a.getAvatar()== null)
            {
                sql = "UPDATE account SET fullname = ?, email = ?, address = ?, favourite = ?, birthday = ?, phone = ?, website = ?, gender = ? WHERE user_id = ?";
            }
            else
            {
                sql = "UPDATE account SET fullname = ?, email = ?, address = ?, favourite = ?, birthday = ?, phone = ?, website = ?, gender = ?, avatar = ? WHERE user_id = ?";
            }
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, a.getFullname());
            pst.setString(2, a.getEmail());
            pst.setString(3, a.getAddress());
            pst.setString(4, a.getFavourite());
            pst.setDate(5, a.getBirthday());
            pst.setString(6, a.getPhone());
            pst.setString(7, a.getWebsite());
            pst.setString(8, a.getGender());
           if(a.getAvatar() == null)
           {
                pst.setInt(9, a.getId());
           }
           else
           {
                pst.setString(9, a.getAvatar());
                pst.setInt(10, a.getId());
           }
           kq = pst.executeUpdate();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return kq;
    }
    // get random suggestion account
    public ArrayList<Account> getSuggestion(int id){
        ArrayList<Account> account = null;
        try{
            account = new ArrayList<>();
            Connection con = getConnection.connect();
            String sql = "select top 10 * from account WHERE user_id != "+id+"order by newid()";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                account.add(new Account(rs.getInt("user_id"), rs.getString("username"), rs.getString("fullname"), rs.getString("avatar")));
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return account;
    }
    // is have any follow to anyone
    public boolean isAnyfollow(int id){
        boolean flag = false;
        Connection con = getConnection.connect();
        try{
            String sql1 = "SELECT * FROM account_follows WHERE  from_user ="+id;
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql1);
            if(rs.next()){
                flag = true;
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return flag;
    }
    // search user
    public ArrayList<Account> searchUser(String user, int id){
        ArrayList<Account> list = null;
        try{
            list = new ArrayList<>();
            Connection con = getConnection.connect();
            String sql = "SELECT top 10 * FROM account WHERE username LIKE ? AND user_id != ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, "%"+user+"%");
            pst.setInt(2, id);
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
    // change password
    public int changePassword(String old_pass, String new_passString, int user_id){
        int kq = 0;
        String md5 = md5(old_pass);
        try{
            Connection con = getConnection.connect();
            String sql = "SELECT password from account WHERE user_id = ? and password = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, user_id);
            pst.setString(2, md5);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                kq = 1;
            }
            if(kq == 1){
                String sql1 = "UPDATE account SET password =  ? WHERE user_id = ?";
                PreparedStatement pst1 = con.prepareStatement(sql1);
                pst1.setString(1, md5(new_passString));
                pst1.setInt(2, user_id);
                pst1.executeUpdate();
                kq = 2;
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return kq;
    }
    // notification
    public void notification(String msg, int id, int user_id){
        try{
            Connection con = getConnection.connect();
            String sql = "INSERT INTO notifications(msg,created_at,user_id,seen) VALUES(?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            String str = "<a href='/a18138/post/view.jsp?id="+id+"'>"+msg+"</a>";
            pst.setString(1, str);
            Calendar cal = Calendar.getInstance(); 
            java.sql.Timestamp timestamp = new java.sql.Timestamp(cal.getTimeInMillis());
            pst.setTimestamp(2, timestamp);
            pst.setInt(3, user_id);
            pst.setInt(4, 0);
            pst.executeUpdate();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    // get list notification
    public ArrayList<Notification> getNotification(int user_id){
        ArrayList<Notification> list = null;
        try{
            list = new ArrayList<>();
            Connection con = getConnection.connect();
            String sql = "SELECT * FROM notifications WHERE user_id = "+user_id+" order by noti_id desc";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                list.add(new Notification(rs.getString("msg"),rs.getString("created_at")));
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return list;
    }
    // count notification
    public int countNoti(int user_id){
        int kq = 0;
        try{
            Connection con = getConnection.connect();
            String sql = "SELECT count(noti_id) as counter FROM notifications WHERE seen = 0 and user_id = "+user_id;
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
    // update seen notification
    public void seenNotification(int user_id){
        try{
            Connection con = getConnection.connect();
            String sql = "update notifications set seen = 1 where user_id = "+user_id;
            Statement st = con.createStatement();
            st.executeUpdate(sql);
            
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    // update verified
    public void updateVerified(int user_id, int veri){
        try{
            Connection con = getConnection.connect();
            String sql = "UPDATE account SET verified = ? WHERE user_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, veri);
            pst.setInt(2, user_id);
            pst.executeUpdate();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
