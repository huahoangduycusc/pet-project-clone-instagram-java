<%@page import="hhd.entity.Account"%>
<%@page import="hhd.accountDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page errorPage="../404.html"%>
<%@include file="../header.jsp"%>
<%
accountDAO dao = new accountDAO();
Account my = dao.getRow(user_id);
String msg = "";
if(request.getParameter("submit") != null)
{
    String opass = request.getParameter("opass");
    String npass = request.getParameter("npass");
    String cpass = request.getParameter("cpass");
   if(!npass.equals(cpass))
   {
       msg = "no";
   }
   else{
       int kq = dao.changePassword(opass, npass, user_id);
       if(kq == 0){
           msg = "kcx";
       }
       else if(kq == 2){
           msg = "success";
       }
   }
}
int noti = dao.countNoti(user_id);
%>
<div class="body-content">
        <section class="settings">
            <ul class="setting-nav">
                <li>
                    <a href="setting.jsp" class="setting-nav-a">Edit profile</a>
                </li>
                <li>
                    <a href="change-pass.jsp" class="setting-nav-a setting-ac">Change password</a>
                </li>
                <li>
                    <a href="notification.jsp" class="setting-nav-a">Notification <% if(noti != 0) { out.print("<span class='notification'>"+noti+"</span>"); }%></a>
                </li>
                <div class="setting-bottom">
                    <hr>
                    <span>
                        Design by HHD
                    </span>
                </div>
            </ul>
            <div class="setting-content">
                <form action="" method="POST" class="list-control">
                    <div class="center">
                        <%
                        if(msg.equals("no"))
                        {
                            out.print("<div class='error'>Password and Confim password is not correct !</div>");
                        }
                        if(msg.equals("kcx"))
                        {
                            out.print("<div class='error'>Old password is not correct</div>");
                        }
                        if(msg.equals("success"))
                        {
                            out.print("<div class='success'>Changed password success!</div>");
                        }
                        %>
                    </div>
                    <div class="control-input">
                        <aside><label for="name">Old password</label></aside>
                        <div class="input">
                            <input type="password" name="opass" id="name" value="" required="">
                            <div class="input-suggest">
                                Enter your old password
                            </div>
                        </div>
                    </div>
                     <div class="control-input">
                        <aside><label for="name">New password</label></aside>
                        <div class="input">
                            <input type="password" name="npass" id="name" value="" required="">
                            <div class="input-suggest">
                                Enter your new password to complete process changing password.
                            </div>
                        </div>
                    </div>
                     <div class="control-input">
                        <aside><label for="name">Confirm password</label></aside>
                        <div class="input">
                            <input type="password" name="cpass" id="name" value="" required>
                        </div>
                    </div>
                    <div class="control-input">
                        <aside><label for="update"></label></aside>
                        <div class="input">
                            <button class="btn-follow" type="submit" name="submit" value="22">Confirm</button>
                        </div>
                    </div>
                </form>
            </div>
        </section>
    </div>
<%@include file="../footer.jsp"%>