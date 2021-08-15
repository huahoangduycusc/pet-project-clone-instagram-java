<%-- 
    Document   : setting
    Created on : Dec 22, 2020, 3:34:38 PM
    Author     : asus
--%>

<%@page import="hhd.accountDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="hhd.entity.Account"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page errorPage="404.html"%>
<%@include file="header.jsp"%>
<div class="body-content">
        <section class="any-follow">
            <h4>Suggestion for you</h4>
            <div class="any-follow-list" id="list">
                <%
                    accountDAO dao = new accountDAO();
                    ArrayList<Account> suggest = dao.getSuggestion(user_id);

                    for(Account ac : suggest)
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
                %>
            </div>
        </section>
<%@include file="footer.jsp"%>
<script src="js/follow.js"></script>