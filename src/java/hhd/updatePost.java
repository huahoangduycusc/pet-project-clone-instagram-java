/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hhd;

import hhd.entity.Post;
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
@WebServlet(name = "updatePost", urlPatterns = {"/updatePost"})
public class updatePost extends HttpServlet {

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
       HttpSession session = request.getSession(true);
       int user_id = Integer.parseInt(session.getAttribute("user_id").toString());
       int id = Integer.parseInt(request.getParameter("id"));
       int rights = Integer.parseInt(session.getAttribute("rights").toString());
       postDAO pdao = new postDAO(id);
       response.setContentType("application/json");
       PrintWriter out = response.getWriter();
       JSONObject obj = new JSONObject();
       int kq = 0;
       if((user_id != pdao.userId) && (rights != 1))
       {
            obj.put("msg", "no");
       }
       else
       {
           kq = pdao.deletePost(id);
           if(kq != 0){
               pdao.minusPost(user_id);
               obj.put("msg", "success");
           }
           else{
               obj.put("msg", "error");
           }
       }
       out.print(obj.toString());
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
        int kq = 0;
        String msg = request.getParameter("msg");
        int id = Integer.parseInt(request.getParameter("id"));
        postDAO dao = new postDAO();
        kq = dao.updatePost(id, msg);
        if(kq != 0)
        {
            response.sendRedirect("/a18138/post/view.jsp?id="+id+"&msg=success");
        }
        else{
            response.sendRedirect("/a18138/post/edit.jsp?id="+id+"&msg=error");
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
