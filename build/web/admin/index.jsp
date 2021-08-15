<%-- 
    Document   : index
    Created on : Dec 14, 2020, 3:04:34 PM
    Author     : asus
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="hhd.entity.Post"%>
<%@page import="hhd.postDAO"%>
<%@page import="hhd.accountDAO"%>
<%@page import="hhd.entity.Account"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page errorPage="../404.html"%>
<%@include file="../header.jsp"%>
<%
    if(session.getAttribute("rights") != null && !session.getAttribute("rights").equals(1))
    {
        response.sendRedirect("/a18138/index.jsp");
    }
    accountDAO dao = new accountDAO();
%>

    <div class="body-content">
        <section class="user-posts">
            <div class="line"></div>
                <div class="user-own">
                    <div class="user-own-icon">
                        <i class="fas fa-flag"></i>
                        <h1>Report </h1>
                        <p>Infringing posts</p>
                    </div>
                </div>
            <div class="line-title">
                <span><i class="fas fa-border-all"></i> POSTS</span>
            </div>
            <div class="user-posts-list" id="loadpost">
                <%
                postDAO postController = new postDAO();
                ArrayList<Post> post = postController.getReportPost();
                for(Post item : post)
                {
                    %>
                    <article class="article-post">
                    <a href="/a18138/post/view.jsp?id=<%=item.getId()%>" class="post-a">
                        <img src="/a18138/images/<%=item.getThumbnail()%>" class="post-thumb">
                        <i class="fas fa-clone"></i>
                        <div class="article-more">
                            <span><i class="fas fa-flag"></i> <%=item.getReport()%></span>
                        </div>
                    </a>
                </article>
                    <%
                }
                if(post.size() == 0)
                {
                    out.print("<div class='empty'>No posts yet</div>");
                }
                %>
            </div>
        </section>
    </div>
<%@include file="../footer.jsp"%>