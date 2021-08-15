/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hhd;

import hhd.entity.Account;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;
import hhd.entity.Post;

/**
 *
 * @author asus
 */
@WebServlet(name = "servletPost", urlPatterns = {"/servletPost"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50)
public class servletPost extends HttpServlet {
public static final String UPLOAD_DIR = "images";
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        PrintWriter writer = response.getWriter();

        if (!ServletFileUpload.isMultipartContent(request)) {
            writer.println("Fehler: Form must has enctype=multipart/form-data.");
            writer.flush();
            return;
        }
        String extension = null;
        String msg = request.getParameter("msg");
        int postId = 0; // id post
        String applicationPath = request.getServletContext().getRealPath("");
        int names = (int) new Date().getTime();
        String uploadPath = applicationPath + File.separator + UPLOAD_DIR + File.separator;
        File fileUploadDirectory = new File(uploadPath);
        if (!fileUploadDirectory.exists()) {
            fileUploadDirectory.mkdirs();
        }
        try {
            HttpSession session = request.getSession(true);
            int user_id = (int)session.getAttribute("user_id");
            Post post = new Post(0, msg, user_id,"");
            postDAO dao = new postDAO();
            postId = dao.createPost(post);
            Connection con = getConnection.connect();
            if(postId > 0)
            {
                dao.plusPost(user_id);
                for (Part filePart : request.getParts()) {
                if (filePart != null && filePart.getSize() != 0 && filePart.getName().equals("file")) {
                     //System.out.println("file "+getFileName(filePart));
                     String fileName = getFileName(filePart);
                     File old = new File(uploadPath + File.separator + fileName);
                     int i = fileName.lastIndexOf('.');
                    if (i > 0) {
                        extension = fileName.substring(i+1);
                        extension = extension.toLowerCase();
                    }
                    if(extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png"))
                    {
                        File newName = new File(uploadPath + File.separator + names+"."+extension);
                        filePart.write(newName.toString());
                        String sql = "INSERT INTO post_images(image_path,post_id) VALUES (?,?)";
                        PreparedStatement pst = con.prepareStatement(sql);
                        pst.setString(1, names+"."+extension);
                        pst.setInt(2, postId);
                        int kq = pst.executeUpdate();
                    }
                }
                names++;
                }
               response.sendRedirect("post/view.jsp?id="+postId);
            }
            }catch (Exception exc) {
            exc.printStackTrace();
        }
    }
    // comment
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf8");
        HttpSession session = request.getSession(true);
        int user_id = Integer.parseInt(session.getAttribute("user_id").toString());
        int id = Integer.parseInt(request.getParameter("id"));
        postDAO dao = new postDAO(id);
        int result = dao.likePost(id, user_id);
        response.setContentType("application/json");
        PrintWriter out = response.getWriter(); 
        JSONObject obj = new JSONObject();
        int numlike = dao.getLikeOfPost(id);
        if(result == 0)
        {
            obj.put("numlike", "Loi insert du lieu");
        }
        else{
            accountDAO ac = new accountDAO();
            Account info = ac.getRow(user_id);
            ac.notification(info.getUsername() + " liked your post", id, dao.userId);
            obj.put("numlike", numlike);
        }
        out.print(obj.toString());
        
        
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
