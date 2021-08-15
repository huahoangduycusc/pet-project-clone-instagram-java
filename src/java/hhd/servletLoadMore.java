/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hhd;

import hhd.entity.Account;
import hhd.entity.Comment;
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
@WebServlet(name = "servletLoadMore", urlPatterns = {"/servletLoadMore"})
public class servletLoadMore extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            Thread.sleep(1000);
            int rows = Integer.parseInt(request.getParameter("row"));
            int id = Integer.parseInt(request.getParameter("id"));
            String sql = "SELECT c.cmt_id, c.cmt_msg, a.username, a.avatar, a.verified," +
            "c.cmt_date, a.user_id " +
            "FROM post_comments c JOIN account a " +
            "ON c.user_id = a.user_id WHERE c.post_id = ? " +
            "order by c.cmt_id desc OFFSET ? ROWS FETCH NEXT 6 ROWS ONLY";
            Connection con = getConnection.connect();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            pst.setInt(2, rows);
            ResultSet rs = pst.executeQuery();
            ArrayList<Comment> list = new ArrayList<>();
            while(rs.next())
            {
                list.add(
                    new Comment(rs.getInt("cmt_id"), rs.getString("cmt_msg"), 
                    rs.getInt("user_id"),rs.getTimestamp("cmt_date").toString(),
                    rs.getString("avatar"), rs.getInt("verified"), rs.getString("username"))
                );
                
            }
            for(Comment item : list)
            {
                out.print("<div class=\"article-talk-item\">");
                out.print("<div class=\"talk-avatar\">\n" +
"                <a href=\"/a18138/account/index.jsp?id="+item.getUserId()+" \"><img src=\"/a18138/images/profile/"+item.getAvatar()+"\" alt=\"\"></a>\n" +
"               </div>");
                out.print("<div class=\"talk-content\">");
                out.print("<h3><a href=\"/a18138/account/index.jsp?id="+item.getUserId()+"\">"+item.getUsername()+"</a></h3>");
                if(item.getVerified() == 1)
                {
                    out.print(" <span><img src=\"/a18138/images/verify.png\" title=\"Verified\"></span>");
                }
                out.print(" <span>"+item.getMessage()+"</span>");
                out.print("<div class=\"talk-times\">\n" +
"                    <time class=\"times\">"+item.getDate()+"</time>\n" +
"                </div>");
                out.print("</div>");
                out.print("</div>");
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
            Logger.getLogger(servletLoadMore.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(servletLoadMore.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(servletLoadMore.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(servletLoadMore.class.getName()).log(Level.SEVERE, null, ex);
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
