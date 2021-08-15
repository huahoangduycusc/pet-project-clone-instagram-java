<%-- 
    Document   : search
    Created on : Dec 26, 2020, 1:04:29 PM
    Author     : asus
--%>


<%@page import="java.util.ArrayList"%>
<%@page import="hhd.entity.Account"%>
<%@page import="hhd.accountDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="header.jsp"%>
<%
String s = "";
if(request.getParameter("s") != null)
{
    s = request.getParameter("s");
}
accountDAO dao = new accountDAO();
ArrayList<Account> list = dao.searchUser(s,user_id);
%>
<div class="body-content">
    <section class="any-follow">
    <h4>Searching for user</h4>
    <div class="any-follow-list" id="list">
        <%
            for(Account ac : list)
            {
                %>
                <div class="any-item">
                    <div class="any-item-avatar">
                        <a href="/a18138/account/index.jsp?id=<%=ac.getId()%>"><img src="./images/profile/<%=ac.getAvatar()%>" alt=""></a>
                    </div>
                    <div class="any-item-profile">
                        <div class="any-profile-user"><a href="/a18138/account/index.jsp?id=<%=ac.getId()%>"><%=ac.getUsername()%></a></div>
                        <div class="any-profile-name"><%=ac.getFullname()%></div>
                        <div class="any-profile-sg">Suggestion for you</div>
                    </div>
                    <div class="any-item-btn">
                        <button class="btn-follow-sm" id="fl<%=ac.getId()%>" data-fl="<%=ac.getId()%>">
                         <%
                        if(dao.isFollowed(ac.getId(), user_id))
                        {
                            out.print("UnFollow");
                        }
                        else
                        {
                            out.print("Follow");
                        }
                        %>
                        </button>
                    </div>
                </div>
                <%
            }
            if(list.size() == 0)
            {
                out.print("<div class='empty'>There is no result ...</div>");
            }
        %>
    </div>
</section>
</div>
<%@include file="footer.jsp"%>
<script src="./js/follow.js"></script>
