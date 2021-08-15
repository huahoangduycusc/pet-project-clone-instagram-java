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
    int id;
    if(request.getParameter("id") == null)
    {
        id = user_id;
    }
    else
    {
         id = Integer.parseInt(request.getParameter("id"));
    }
   
    accountDAO dao = new accountDAO();
    Account user = dao.getRow(id);
    int countPost = postDAO.countMyPost(id);
%>

    <div class="body-content">
        <section class="user-info">
            <div class="user-avatar">
                <div class="avatar-profile">
                    <img src="/a18138/images/profile/<%=user.getAvatar()%>" alt="">
                </div>
            </div>
            <div class="user-introduce">
                <div class="user-name">
                    <h2 class="user-name-title"><a href="index.jsp?id=<%=id%>"><%=user.getUsername()%></a></h2>
                    <%
                    if(user.getVerified() == 1)
                    {
                        out.print("<span><img src='/a18138/images/verify.png' title='Verified'></span>");
                    }
                    %>
                    <div class="user-follow">
                        <%
                        if(user_id != id)
                        {
                            if(dao.isFollowed(id, user_id))
                            {
                                out.print("<a href='#' class='btn-follow' id='btnFollow'>UnFollow</a>");
                            }
                            else{
                                out.print("<a href='#' class='btn-follow' id='btnFollow'>Follow</a>");
                            }
                        }
                        else
                        {
                            out.print("<a href='setting.jsp' class='btn-setting'>Setting profile <i class='fas fa-cog'></i></a>");
                        }
                        if(session.getAttribute("rights") != null && session.getAttribute("rights").equals(1))
                        {
                            out.print("<a href='/a18138/servletSetting?id="+id+"' class='btn-setting'><i class='fas fa-user-check'></i></a>");
                        }
                        //out.print(session.getAttribute("rights").toString());
                        %>
                    </div>
                </div>
                <ul class="user-more">
                    <li class="more-item">
                        <span class="bold"><%=user.getPost()%></span> posts
                    </li>
                    <li class="more-item">
                        <a href="followers.jsp?id=<%=id%>"><span class="bold"><%=user.getFollower()%></span> followers</a>
                    </li>
                    <li class="more-item">
                        <a href="following.jsp?id=<%=id%>"><span class="bold"><%=user.getFollowing()%></span> following</a>
                    </li>
                </ul>
                <div class="user-about">
                    <h2 class="user-about-title"><%=user.getFullname()%></h2>
                    <span><%=user.getFavourite()%></span>
                    <a href="" class="user-author"><%=user.getWebsite()%></a>
                </div>
            </div>
        </section>
        <se class="user-posts">
            <section class="user-posts">
            <div class="line"></div>
            <%
            if(user_id == id)
            {
                %>
                <div class="user-own">
                    <div class="user-own-icon">
                        <i class="fas fa-camera"></i>
                        <h1>Add a new post </h1>
                        <p>Share memories with your friends.</p>
                        <a href="/a18138/post/create.jsp" class="btn-follow">Add new post</a>
                    </div>
                </div>
                <%
            }
            %>
            <div class="line-title">
                <span><i class="fas fa-border-all"></i> POSTS</span>
            </div>
            <div class="user-posts-list" id="loadpost">
                <%
                postDAO postController = new postDAO();
                ArrayList<Post> post = postController.getList(id);
                for(Post item : post)
                {
                    %>
                    <article class="article-post">
                    <a href="/a18138/post/view.jsp?id=<%=item.getId()%>" class="post-a">
                        <img src="/a18138/images/<%=item.getThumbnail()%>" class="post-thumb">
                        <i class="fas fa-clone"></i>
                        <div class="article-more">
                            <span><i class="fas fa-heart"></i> <%=item.getLike()%></span>
                            <span><i class="fas fa-comment"></i> <%=item.getReply()%></span>
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
            <%
            if(countPost > 6)
            {
            %>
            <div class="center" id="loading"></div>
            <div class="center loading-sm"><i class="fas fa-plus-circle" style="font-size: 30px;" id="load-more"></i></div>
            <%
            }
            %>
        </section>
    </div>
            <input type="hidden" id="user" value="<%=id%>">
<%@include file="../footer.jsp"%>
<script src="../js/load-article.js"></script>
<script>
    $(document).ready(function(){
       $("#btnFollow").on('click',function(e){
           e.preventDefault();
           $.ajax({
              url : '/a18138/servletAccount',
              type: 'GET',
              dataType: 'JSON',
              cache: false,
              data : {
                  id : <%=id%>
              },
              success : function(data){
                  //alert(data.msg);
                  if(data.msg == 1)
                  {
                      //alert("un");
                      $("#btnFollow").text("Follow");
                  }
                  else{
                      $("#btnFollow").text("UnFollow");
                  }
              },
              error : function(){
                  alert("Error : Cannot follow this user");
              }
           });
        }); 
    });
</script>