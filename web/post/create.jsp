<%@page import="hhd.entity.Account"%>
<%@page import="hhd.accountDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page errorPage="404.html"%>
<%@include file="../header.jsp"%>
<%
    accountDAO dao = new accountDAO();
    Account user = dao.getRow(user_id);
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
                    <h2 class="user-name-title"><%=user.getUsername()%></h2>
                    <%
                    if(user.getVerified() == 1)
                    {
                        out.print("<span><img src='/a18138/images/verify.png' title='Verified'></span>");
                    }
                    %>
                    <div class="user-follow">
                  
                    </div>
                </div>
                <ul class="user-more">
                    <li class="more-item">
                        <span class="bold"><%=user.getPost()%></span> posts
                    </li>
                    <li class="more-item">
                        <a href=""><span class="bold"><%=user.getFollower()%></span> followers</a>
                    </li>
                    <li class="more-item">
                        <span class="bold"><%=user.getFollowing()%></span> following
                    </li>
                </ul>
                <div class="user-about">
                    <h2 class="user-about-title"><%=user.getFullname()%></h2>
                    <span><%=user.getFavourite()%></span>
                    <a href="" class="user-author"><%=user.getWebsite()%></a>
                </div>
            </div>
        </section>
        <section class="user-posts">
            <div class="line"></div>
            <div class="user-own">
                <div class="user-own-icon">
                    <i class="fas fa-camera"></i>
                    <h1>Add a new post </h1>
                    <p>Share memories with your friends.</p>
                </div>
            </div>
            <div class="list-create">
                <form id="upload_form" method="post" action="../servletPost" enctype="multipart/form-data"  >
                    <div class="box-input">
                        <p>Message</p>
                        <textarea name="msg" rows="5" class="form-control" required></textarea>
                    </div>
                    <div class="box-input">
                        <p>Choose one or multiple images</p>
                        <input type="file" placeholder="Upload File" name="file" class="upload_input" multiple="multiple" accept="image/*" required>
                        
                    </div>
                    <div class="box-input">
                        <button class="btn-create" type="submit">Create post</button>
                    </div>
                </form>
            </div>
        </section>
    </div>
<%@include file="../footer.jsp"%>