/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hhd;

import hhd.entity.Account;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;

/**
 *
 * @author asus
 */
@WebServlet(name = "servletComment", urlPatterns = {"/servletComment"})
public class servletComment extends HttpServlet {


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
        int result = 0;
        HttpSession session = request.getSession(true);
        int user_id = 0;
        user_id = Integer.parseInt(session.getAttribute("user_id").toString());
        String msg = request.getParameter("msg");
        int postId = Integer.parseInt(request.getParameter("postId").toString());
        if(!"".equals(msg) && user_id != 0)
        {
            postDAO dao = new postDAO(postId);
            result = dao.postComment(msg, user_id, postId);
            accountDAO ac = new accountDAO();
            Account info = ac.getRow(user_id);
            ac.notification(info.getUsername() + " commented on your post", postId, dao.userId);
        }
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JSONObject obj = new JSONObject();
        if(result == 0)
        {
            obj.put("msg", "Loi insert du lieu");
        }
        else{
            obj.put("msg", result);
        }
        out.print(obj.toString());
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
