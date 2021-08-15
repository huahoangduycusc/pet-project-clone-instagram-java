<%@page import="hhd.followDAO"%>
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
        <section class="any-follow">
            <h4>The people are following <%=user.getUsername()%></h4>
            <div class="any-follow-list" id="list">
                <%
                    ArrayList<Account> suggest = followDAO.listFollowers(id);

                    for(Account ac : suggest)
                    {
                        %>
                        <div class="any-item">
                            <div class="any-item-avatar">
                                <a href="/a18138/account/index.jsp?id=<%=ac.getId()%>"><img src="/a18138/images/profile/<%=ac.getAvatar()%>" alt=""></a>
                            </div>
                            <div class="any-item-profile">
                                <div class="any-profile-user"><a href="/a18138/account/index.jsp?id=<%=ac.getId()%>"><%=ac.getUsername()%></a></div>
                                <div class="any-profile-name"><%=ac.getFullname()%></div>
                            </div>
                            <div class="any-item-btn">
                                <button class="btn-follow-sm <% if(user_id == ac.getId()) {out.print("hidden");}%>" id="fl<%=ac.getId()%>" data-fl="<%=ac.getId()%>">
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
<script src="/a18138/js/follow.js"></script>