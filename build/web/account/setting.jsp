<%-- 
    Document   : setting
    Created on : Dec 22, 2020, 3:34:38 PM
    Author     : asus
--%>

<%@page import="hhd.accountDAO"%>
<%@page import="hhd.entity.Account"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page errorPage="/a18138/404.html"%>
<%@include file="../header.jsp"%>
<%
accountDAO dao = new accountDAO();
Account my = dao.getRow(user_id);
int noti = dao.countNoti(user_id);
%>
<div class="body-content">
        <section class="settings">
            <ul class="setting-nav">
                <li>
                    <a href="setting.jsp" class="setting-nav-a setting-ac">Edit profile</a>
                </li>
                <li>
                    <a href="change-pass.jsp" class="setting-nav-a">Change password</a>
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
                <form action="/a18138/servletSetting" method="POST" class="list-control" enctype="multipart/form-data">
                    <div class="center">
                        <%
                        String msg = "";
                        if(request.getParameter("msg") != null)
                        {
                            msg = request.getParameter("msg");
                        }
                        if(msg.equals("avatar"))
                        {
                            out.print("<div class='error'>You just can uploaded file with extension .png, .jpg or .jpeg</div>");
                        }
                        if(msg.equals("error"))
                        {
                            out.print("<div class='error'>Can not setting your profile</div>");
                        }
                        if(msg.equals("success"))
                        {
                            out.print("<div class='success'>Setting your profile success</div>");
                        }
                        %>
                    </div>
                    <div class="control-input">
                        <aside><label for="avatar"></label></aside>
                        <div class="input">
                            <img src="/a18138/images/profile/<%=my.getAvatar()%>" title="">
                        </div>
                    </div>
                    <div class="control-input">
                        <aside><label for="name">Name</label></aside>
                        <div class="input">
                            <input type="text" name="fullname" id="name" value="<%=my.getFullname()%>">
                            <div class="input-suggest">
                                Help people discover your account by using the name you're known by: either your full name, nickname, or business name.
                            </div>
                        </div>
                    </div>
                    <div class="control-input">
                        <aside><label for="email">Email</label></aside>
                        <div class="input">
                            <input type="email" name="email" id="email" value="<%=my.getEmail()%>">
                        </div>
                    </div>
                    <div class="control-input">
                        <aside><label for="address">Address</label></aside>
                        <div class="input">
                            <input type="text" name="address" id="address" value="<%=my.getAddress()%>">
                        </div>
                    </div>
                    <div class="control-input">
                        <aside><label for="phone">Phone number</label></aside>
                        <div class="input">
                            <input type="text" name="phone" id="phone" value="<%=my.getPhone()%>">
                        </div>
                    </div>
                    <div class="control-input">
                        <aside><label for="birthday">Birthday</label></aside>
                        <div class="input">
                            <input type="date" name="birthday" id="birthday" value="<%=my.getBirthday()%>">
                        </div>
                    </div>
                    <div class="control-input">
                        <aside><label for="Website">Website</label></aside>
                        <div class="input">
                            <input type="text" name="website" id="Website" value="<%=my.getWebsite()%>">
                        </div>
                    </div>
                    <div class="control-input">
                        <aside><label for="favourite">Favourite</label></aside>
                        <div class="input">
                            <textarea name="favourite" id="favourite" rows="10"><%=my.getFavourite()%></textarea>
                        </div>
                    </div>
                    <div class="control-input">
                        <aside><label for="gender">Gender</label></aside>
                        <div class="input">
                            <select name="gender" id="gender">
                                <option value="m" <% if(my.getGender().equals("m")) out.print("selected"); %>>Male</option>
                                <option value="fm" <% if(my.getGender().equals("fm")) out.print("selected"); %>>Female</option>
                            </select>
                        </div>
                    </div>
                    <div class="control-input">
                        <aside><label for="avatar">Avatar</label></aside>
                        <div class="input">
                           <input type="file" name="avatar" id="avatar">
                        </div>
                    </div>
                    <div class="control-input">
                        <aside><label for="update"></label></aside>
                        <div class="input">
                            <button class="btn-follow" type="submit">Update</button>
                        </div>
                    </div>
                </form>
            </div>
        </section>
    </div>
<%@include file="../footer.jsp"%>
