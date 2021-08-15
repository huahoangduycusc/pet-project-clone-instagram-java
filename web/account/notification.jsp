<%@page import="hhd.entity.Notification"%>
<%@page import="java.util.ArrayList"%>
<%@page import="hhd.entity.Account"%>
<%@page import="hhd.accountDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page errorPage="../404.html"%>
<%@include file="../header.jsp"%>
<%
accountDAO dao = new accountDAO();
int noti = dao.countNoti(user_id);
dao.seenNotification(user_id);
%>
<div class="body-content">
        <section class="settings">
            <ul class="setting-nav">
                <li>
                    <a href="setting.jsp" class="setting-nav-a">Edit profile</a>
                </li>
                <li>
                    <a href="change-pass.jsp" class="setting-nav-a">Change password</a>
                </li>
                <li>
                    <a href="notification.jsp" class="setting-nav-a setting-ac">Notification <% if(noti != 0) { out.print("<span class='notification'>"+noti+"</span>"); }%></a>
                </li>
                <div class="setting-bottom">
                    <hr>
                    <span>
                        Design by HHD
                    </span>
                </div>
            </ul>
            <div class="setting-content">
                <div class="notification-list">
                    <%
                    ArrayList<Notification> list = dao.getNotification(user_id);
                    for(Notification item : list)
                    {
                        %>
                        <div class="notification-item">
                           <%=item.getMsg()%>
                           <p class="notification-date"><%=item.getDate()%></p>
                        </div>
                        <%
                    }
                    if(list.size() == 0){
                        out.print("<div class='empty'>Empty notification !</div>");
                    }
                    %>
                </div>
            </div>
        </section>
    </div>
<%@include file="../footer.jsp"%>