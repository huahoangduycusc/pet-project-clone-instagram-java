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
@WebServlet(name = "servletAccount", urlPatterns = {"/servletAccount"})
public class servletAccount extends HttpServlet {


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
    // follow user
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        int user_id = Integer.parseInt(session.getAttribute("user_id").toString());
        accountDAO dao = new accountDAO();
        int id = Integer.parseInt(request.getParameter("id"));
        int kq = dao.followUser(id, user_id);
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JSONObject obj = new JSONObject();
        if(kq == 0)
        {
            obj.put("msg", "Loi insert du lieu");
        }
        else{
            obj.put("msg", kq);
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
        try{
            String action = "";
            int error = 0;
            accountDAO dao = new accountDAO();
            if(request.getParameter("action") != null)
            {
                action = request.getParameter("action");
                if(action.equals("login"))
                {
                    String username = request.getParameter("username");
                    String password = request.getParameter("password");
                    accountDAO obj = new accountDAO();
                    
                    error = obj.login(username, password);
                    if(error != 0)
                    {
                        Account ac = dao.getRow(error);
                        HttpSession session = request.getSession(true);
                        session.setAttribute("user_id", error);
                        session.setAttribute("rights", ac.getRights());
                        response.sendRedirect("index.jsp");
                    }
                    else{
                        response.sendRedirect("login.jsp?error=fail");
                    }
                }
                else if(action.equals("register")){
                    String username = request.getParameter("username");
                    String password = request.getParameter("password");
                    String email = request.getParameter("email");
                    String fullname = request.getParameter("fullname");
                    if(!dao.isValidUser(username))
                    {
                        response.sendRedirect("register.jsp?error=uservalid");
                    }
                    else
                    {
                        if(dao.isExistUser(username))
                        {
                            response.sendRedirect("register.jsp?error=userexist");
                        }
                        else if(dao.isEmailExists(email))
                        {
                            response.sendRedirect("register.jsp?error=emailexists");
                        }
                        else{
                            Account obj = new Account();
                            obj.setUsername(username);
                            obj.setPassword(dao.md5(password));
                            obj.setFullname(fullname);
                            obj.setEmail(email);
                            int user_id = dao.insertAccount(obj);
                            if(user_id > 0)
                            {
                                HttpSession session = request.getSession(true);
                                session.setAttribute("user_id", user_id);
                                response.sendRedirect("index.jsp");
                            }
                        }
                    }
                    
                }
                
            }
            else{
                response.sendRedirect("login.jsp");
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
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
