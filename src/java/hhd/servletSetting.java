/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hhd;

import hhd.entity.Account;
import static hhd.servletPost.UPLOAD_DIR;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author asus
 */
@WebServlet(name = "servletSetting", urlPatterns = {"/servletSetting"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50)
public class servletSetting extends HttpServlet {
public static final String UPLOAD_DIR = "images/profile";
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
        int id = Integer.parseInt(request.getParameter("id"));
        try{
            accountDAO dao = new accountDAO();
            Account ac = dao.getRow(id);
            if(ac.getVerified() == 0){
                dao.updateVerified(id, 1);
            }else{
                dao.updateVerified(id, 0);
            }
            response.sendRedirect("/a18138/account/index.jsp?id="+id);
            
        }
        catch(Exception ex){
            ex.printStackTrace();;
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
        PrintWriter writer = response.getWriter();

        if (!ServletFileUpload.isMultipartContent(request)) {
            writer.println("Fehler: Form must has enctype=multipart/form-data.");
            writer.flush();
            return;
        }
        String applicationPath = request.getServletContext().getRealPath("");
        String uploadPath = applicationPath + File.separator + UPLOAD_DIR + File.separator;
        File fileUploadDirectory = new File(uploadPath);
        if (!fileUploadDirectory.exists()) {
            fileUploadDirectory.mkdirs();
        }
        HttpSession session = request.getSession(true);
        int user_id = (int)session.getAttribute("user_id");
        String avatar = "";
        String extension = null;
        String name = request.getParameter("fullname");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String website = request.getParameter("website");
        Date birthday = Date.valueOf(request.getParameter("birthday"));
        String favourite = request.getParameter("favourite");
        String gender = request.getParameter("gender");
        Part filePart = request.getPart("avatar");
        if (filePart != null && filePart.getSize() != 0 && filePart.getName().equals("avatar")) {
            //System.out.println("file "+getFileName(filePart));
            String fileName = getFileName(filePart);
            //File old = new File(uploadPath + File.separator + fileName);
            int i = fileName.lastIndexOf('.');
           if (i > 0) {
               extension = fileName.substring(i+1);
               extension = extension.toLowerCase();
           }
           if(extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png"))
           {
               File newName = new File(uploadPath + File.separator + user_id+"."+extension);
               filePart.write(newName.toString());
               avatar = user_id+"."+extension;
           }
           else
           {
               response.sendRedirect("/a18138/account/setting.jsp?msg=avatar");
               return;
           }
       }
       Account obj = new Account();
       obj.setId(user_id);
       obj.setFullname(name);
       obj.setEmail(email);
       obj.setAddress(address);
       obj.setPhone(phone);
       obj.setWebsite(website);
       obj.setBirthday(birthday);
       obj.setFavourite(favourite);
       obj.setGender(gender);
       if(avatar != "")
       {
           obj.setAvatar(avatar);
       }
       accountDAO dao = new accountDAO();
       int kq = dao.updateInfo(obj);
       if(kq != 0)
       {
           response.sendRedirect("/a18138/account/setting.jsp?msg=success");
       }
       else{
           response.sendRedirect("/a18138/account/setting.jsp?msg=error");
       }
    }

    // get file name
     private String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
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
