/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hhd;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

/**
 *
 * @author asus
 */
@WebServlet(name = "reportPost", urlPatterns = {"/reportPost"})
public class reportPost extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JSONObject obj = new JSONObject();
        int id = Integer.parseInt(request.getParameter("id"));
        int kq = 0;
        try{
            Connection con = getConnection.connect();
            String sql = "UPDATE post SET report = report + 1 WHERE post_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            kq = pst.executeUpdate();
            if(kq == 0)
            {
                obj.put("msg", "error");
            }
            else{
                obj.put("msg", "success");
            }
            out.print(obj.toString());
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
