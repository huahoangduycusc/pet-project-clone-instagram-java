/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hhd;
import hhd.entity.Comment;
import hhd.entity.Post;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
/**
 *
 * @author asus
 */
public class postDAO {
    // properties of post
    public int postId;
    public String author;
    public int userId;
    public int countImg;
    public String avatar;
    public int verified;
    public String created_at;
    public int report;

    public postDAO() {
    }
    
    public postDAO(int postId) {
        try{
            Connection con = getConnection.connect();
            String sql = "SELECT c.post_date, a.username, a.avatar, a.verified,c.user_id, c.report " +
                        "FROM post c JOIN account a " +
                        "ON c.user_id = a.user_id WHERE post_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, postId);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                this.author = rs.getString("username");
                this.avatar = rs.getString("avatar");
                this.userId = rs.getInt("user_id");
                this.verified = rs.getInt("verified");
                this.report = rs.getInt("report");
                this.created_at = rs.getTimestamp("post_date").toString().substring(0, 16);
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    // get list
    public ArrayList<Post> getList(int user_id)
    {
        ArrayList<Post> post = null;
        try{
            post = new ArrayList();
            Connection con = getConnection.connect();
            String sql = "SELECT top 6 * FROM post WHERE user_id = ? order by post_id desc";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, user_id);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id =  rs.getInt("post_id");
                post.add(new Post(id, rs.getString("post_msg"), rs.getInt("user_id"), 
                        rs.getString("post_date"),getThumbnail(id),
                        getLikeOfPost(id),
                        countComment(id)));
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        
        return post;
    }
    // get row
    public Post getRow(int id){
        Post post = null;
        try{
            Connection con = getConnection.connect();
            String sql = "SELECT * FROM post WHERE post_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                post = new Post(id, rs.getString("post_msg"), rs.getInt("user_id"), rs.getString("post_date").toString());
                this.postId = id;
                this.userId = rs.getInt("user_id");
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return post;
    }
    // update post
    public int updatePost(int id, String msg)
    {
        int kq = 0;
        try{
            Connection con = getConnection.connect();
            String sql = "UPDATE post set post_msg = ? WHERE post_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, msg);
            pst.setInt(2, id);
            kq = pst.executeUpdate();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return kq;
    }
    // create post
    public int createPost(Post p){
        int id = 0;
        try{
            Connection con = getConnection.connect();
            String sql = "INSERT INTO post(post_msg,user_id,post_date,report) VALUES(?,?,?,?); SELECT SCOPE_IDENTITY()";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, p.getMessage());
            pst.setInt(2, p.getUserId());
            Calendar cal = Calendar.getInstance(); 
            java.sql.Timestamp timestamp = new java.sql.Timestamp(cal.getTimeInMillis());
            pst.setTimestamp(3, timestamp);
            pst.setInt(4, 0);
            id = pst.executeUpdate();
            if(id > 0)
            {
                ResultSet rs = pst.getGeneratedKeys();
                rs.next();
                id = rs.getInt(1);
                
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return id;
    }
    // delete post
    public int deletePost(int id){
        int kq = 0;
        try{
            Connection con = getConnection.connect();
            String sql = "DELETE from post WHERE post_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            kq = pst.executeUpdate();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
       return kq;
    }
    // get list of images
    public ArrayList<String> getImages(){
        ArrayList<String> imgs = null;
        try{
            imgs = new ArrayList();
            Connection con = getConnection.connect();
            String sql = "SELECT * from post_images WHERE post_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, this.postId);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                imgs.add(rs.getString(2));
            }
            this.countImg = imgs.size();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return imgs;
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
    // count comment of post
    public int countComment(int id){
        int count = 0;
        try{
            Connection con = getConnection.connect();
            String sql = "SELECT COUNT(cmt_id) as counter FROM post_comments WHERE post_id = ?";
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
    // get thumbnail post
    public String getThumbnail(int id){
        String image = "";
        try{
            Connection con = getConnection.connect();
            String sql = "SELECT image_path FROM post_images WHERE post_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                image = rs.getString(1);
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return image;
    }
    // user like post
    public int likePost(int id, int user_id){
        int kq = 0;
        try{
            Connection con = getConnection.connect();
            String sql = "SELECT * FROM post_likes WHERE user_id = ? and post_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, user_id);
            pst.setInt(2, id);
            ResultSet rs = pst.executeQuery();
            if(rs.next())
            {
                String sql2 = "DELETE FROM post_likes WHERE like_id = "+rs.getInt("like_id");
                Statement st = con.createStatement();
                kq = st.executeUpdate(sql2);
            }
            else{
                String sql2 = "INSERT INTO post_likes(user_id,post_id) VALUES(?,?)";
                PreparedStatement pst2 = con.prepareStatement(sql2);
                pst2.setInt(1, user_id);
                pst2.setInt(2, id);
                kq = pst2.executeUpdate();
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return kq;
    }
    // if user liked
    public boolean isLiked(int id,int user_id){
        
        boolean flag = false;
        try{
            Connection con = getConnection.connect();
            String sql = "SELECT * FROM post_likes WHERE user_id = ? and post_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, user_id);
            pst.setInt(2, id);
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
    // comment on post
    public int postComment(String msg, int user_id, int postId){
        int kq  = 0;
        try{
            Connection con = getConnection.connect();
            String sql = "INSERT INTO post_comments(cmt_msg,user_id,post_id,cmt_date) VALUES(?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, msg);
            pst.setInt(2, user_id);
            pst.setInt(3, postId);
            Calendar cal = Calendar.getInstance(); 
            java.sql.Timestamp timestamp = new java.sql.Timestamp(cal.getTimeInMillis());
            pst.setTimestamp(4, timestamp);
            kq = pst.executeUpdate();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return kq;
    }
    // get list of comment
    public ArrayList<Comment> getListOfComment(int id){
        ArrayList<Comment> list = null;
        try{
            list = new ArrayList<>();
            Connection con = getConnection.connect();
            String sql = "SELECT TOP 6 c.cmt_id, c.cmt_msg, a.username, a.avatar, a.verified," +
            "c.cmt_date, a.user_id " +
            "FROM post_comments c JOIN account a " +
            "ON c.user_id = a.user_id WHERE c.post_id = ? order by c.cmt_id desc";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while(rs.next())
            {
                list.add(
                    new Comment(rs.getInt("cmt_id"), rs.getString("cmt_msg"), 
                    rs.getInt("user_id"),rs.getTimestamp("cmt_date").toString(),
                    rs.getString("avatar"), rs.getInt("verified"), rs.getString("username"))
                );
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return list;
    }
    // add number post
    public void plusPost(int user_id){
        try{
            Connection con = getConnection.connect();
            String sql = "UPDATE account set post = post + 1 WHERE user_id = "+user_id;
            Statement st = con.createStatement();
            st.executeUpdate(sql);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    // minus number post
    public void minusPost(int user_id){
        try{
            Connection con = getConnection.connect();
            String sql = "UPDATE account set post = post - 1 WHERE user_id = "+user_id;
            Statement st = con.createStatement();
            st.executeUpdate(sql);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    // coumt my post created
    public static int countMyPost(int user_id){
        int kq = 0;
        try{
            Connection con = getConnection.connect();
            String sql = "SELECT count(post_id) as counter FROM post where user_id = "+user_id;
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
    // get list report post
    public ArrayList<Post> getReportPost()
    {
        ArrayList<Post> post = null;
        try{
            post = new ArrayList();
            Connection con = getConnection.connect();
            String sql = "SELECT * FROM post WHERE report > 0 order by report desc";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id =  rs.getInt("post_id");
                post.add(new Post(id, rs.getString("post_msg"), rs.getInt("user_id"), 
                        rs.getString("post_date"),getThumbnail(id),
                        getLikeOfPost(id),
                        countComment(id),
                        rs.getInt("report")));
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        
        return post;
    }
}
