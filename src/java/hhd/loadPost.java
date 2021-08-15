/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hhd;

import hhd.entity.Post;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author asus
 */
@WebServlet(name = "loadPost", urlPatterns = {"/loadPost"})
public class loadPost extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, InterruptedException {
        response.setContentType("text/html;charset=UTF-8");
        Thread.sleep(1000);
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String type = request.getParameter("type");
            int rows = Integer.parseInt(request.getParameter("row"));
            HttpSession session = request.getSession(true);
            int user_id = Integer.parseInt(session.getAttribute("user_id").toString());
            followDAO fl = new followDAO(user_id);
            // connection
            Connection con = getConnection.connect();
            if(type.equals("post"))
            {
                String ids = fl.getListUserFollowed();
                String sql = "SELECT p.post_id,p.user_id,p.post_msg,p.post_date,a.username,a.username,a.avatar,a.verified " +
                "FROM post p JOIN account a ON p.user_id = a.user_id " +
                "WHERE a.user_id in("+ids+") order by post_id desc OFFSET ? ROWS FETCH NEXT 6 ROWS ONLY";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setInt(1, rows);
                ResultSet rs = pst.executeQuery();
                ArrayList<Post> post = new ArrayList<>();
                while(rs.next()){
                    int id =  rs.getInt("post_id");
                    post.add(new Post(id, rs.getString("post_msg"), rs.getInt("user_id"), 
                            rs.getString("post_date"),fl.getImages(id),
                            fl.getLikeOfPost(id),
                            rs.getString("username"),
                            rs.getString("avatar"),
                    rs.getInt("verified")));
                }
                for(Post item : post)
                {
                    out.print("<article class=\"post-index\">");
                    out.print("<div class=\"post-index-author\">");
                    out.print("<div class=\"post-index-avatar\">\n" +
"                                <a href=\"/a18138/account/index.jsp?id="+item.getUserId()+"\"><img src=\"./images/profile/"+item.getAvatar()+"\" alt=\"\"></a>\n" +
"                            </div>");
                    out.print("<div class=\"post-index-name\">");
                    out.print(" <span><a href=\"/a18138/account/index.jsp?id="+item.getUserId()+"\">"+item.getAuthor()+"</a>");
                    if(item.getVerified() == 1)
                    {
                        out.print(" <img src=\"/a18138/images/verify.png\" title=\"Verified\" style=\"width:13px;\">");
                    }
                    out.print("</span>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div class=\"post-btn-index\">\n" +
"                            <button class=\"post-index-button\" data-id=\""+item.getId()+"\">\n" +
"                                <i class=\"fas fa-ellipsis-h\"></i>\n" +
"                            </button>\n" +
"                        </div>");
                    out.print("<div class=\"post-index-img\">\n" +
"                            <a href=\"/a18138/post/view.jsp?id="+item.getId()+"\">\n" +
"                                <img src=\"/a18138/images/"+item.getThumbnail()+"\" alt=\"\">\n" +
"                            </a>\n" +
"                        </div>");
                    out.print("<div class=\"post-index-content\">");
                    out.print("<section class=\"post-index-operation\">");
                    out.print("<span>");
                    postDAO dao = new postDAO();
                    if(dao.isLiked(item.getId(), user_id))
                    {

                        out.print("<i class=\"far fas fa-heart red like-post\" data-post='"+item.getId()+"'></i>");

                    }
                    else{
                        out.print("<i class=\"far fa-heart like-post\" data-post='"+item.getId()+"'></i>");
                    }
                    out.print("</span>");
                    out.print("<span>\n" +
"                                    <a href=\"/a18138/post/view.jsp?id="+item.getId()+"\"><i class=\"far fa-comment\"></i></a>\n" +
"                                </span>");
                    out.print("<div class=\"post-count-img\">");
                    int loop = fl.loopDot(item.getId());
                    if(loop > 1)
                    {
                        for(int i=0;i<loop;i++)
                        {
                            out.print("<i></i> ");
                        }
                    }
                    out.print("</div>");
                    out.print("</section>");
                    out.print("<section class=\"post-index-like\">\n" +
"                                <span id=\"like"+item.getId()+"\">"+item.getLike()+"</span> likes\n" +
"                            </section>");
                    out.print("<section class=\"post-index-msg\">\n" +
"                                <div class=\"post-index-text\">");
                    out.print("<span class=\"post-text-author\"><a href=\"/a18138/account/index.jsp?id="+item.getUserId()+"\"> "+item.getAuthor()+"</a>");
                    if(item.getVerified() == 1)
                    {
                                        
                        out.print(" <img src=\"/a18138/images/verify.png\" title=\"Verified\" style=\"width:13px;\">");
                                        
                    }
                    out.print("</span>");
                    out.print("<span class=\"post-text-content\"> "+item.getMessage()+"</span>");
                    out.print("</div>\n" +
"                            </section>");
                    out.print("<div class=\"post-index-times\">\n" +
"                                <span>"+item.getDate()+"</span>\n" +
"                            </div>");
                    out.print("</div>");
                    out.print("</article>");
                }
            }
            else if(type.equals("myself"))
            {
                int uid = Integer.parseInt(request.getParameter("id"));
                ArrayList<Post> post = new ArrayList<>();
                String sql = "SELECT * FROM post WHERE user_id = ? order by post_id desc OFFSET ? ROWS FETCH NEXT 6 ROWS ONLY";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setInt(1, uid);
                pst.setInt(2, rows);
                ResultSet rs = pst.executeQuery();
                postDAO dao = new postDAO();
                while(rs.next()){
                    int id =  rs.getInt("post_id");
                    post.add(new Post(id, rs.getString("post_msg"), rs.getInt("user_id"), 
                            rs.getString("post_date"),dao.getThumbnail(id),
                            dao.getLikeOfPost(id),
                            dao.countComment(id)));
                }
                for(Post item : post)
                {
                   out.print("<article class=\"article-post\">\n" +
"                    <a href=\"/a18138/post/view.jsp?id="+item.getId()+"\" class=\"post-a\">\n" +
"                        <img src=\"/a18138/images/"+item.getThumbnail()+"\" class=\"post-thumb\">\n" +
"                        <i class=\"fas fa-clone\"></i>\n" +
"                        <div class=\"article-more\">\n" +
"                            <span><i class=\"fas fa-heart\"></i> "+item.getLike()+"</span>\n" +
"                            <span><i class=\"fas fa-comment\"></i> "+item.getReply()+"</span>\n" +
"                        </div>\n" +
"                    </a>\n" +
"                </article>");
                }
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(loadPost.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(loadPost.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(loadPost.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(loadPost.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
