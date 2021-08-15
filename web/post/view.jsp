<%-- 
    Document   : view
    Created on : Dec 15, 2020, 8:07:45 AM
    Author     : asus
--%>

<%@page import="hhd.accountDAO"%>
<%@page import="hhd.entity.Comment"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../header.jsp"%>
<%@page import="hhd.entity.Post"%>
<%@page import="hhd.postDAO"%>
<%@page errorPage="../404.html"%>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<%
    int id = Integer.parseInt(request.getParameter("id"));
    postDAO dao = new postDAO(id);
    accountDAO aDAO = new accountDAO();
    Post own = dao.getRow(id);
    ArrayList<String> img = dao.getImages();
    int countCmt = dao.countComment(id);
%>
    <div class="body-content">
        <div class="view-post">
            <%
            if(session.getAttribute("rights") != null && session.getAttribute("rights").equals(1))
            {
                out.print("<a class='btn-action warning'><i class='fas fa-flag'></i> "+dao.report+" report</a>");
                out.print("<a class='btn-action danger' onclick='deletePost2();'><i class='fas fa-trash'></i> Trash</a>");
            }
            %>
            <article class="article">
                <div class="carousel-container">
                    <%
                    if(dao.countImg > 1)
                    {
                        out.print("<i class='fas fa-angle-left' id='prevBtn'></i>");
                        out.print("<i class='fas fa-angle-right' id='nextBtn'></i>");
                    }
                    %>
                    <div class="carousel-slide">
                        <%
                        for(String item : img)
                        {
                            out.print("<img src='/a18138/images/"+item+"'>");
                        }
                        %>
                    </div>
                </div>
                <div class="article-content">
                    <div class="article-author">
                        <div class="article-avatar">
                            <a href="/a18138/account/index.jsp?id=<%=dao.userId%>"><img src="/a18138/images/profile/<%=dao.avatar%>" alt=""></a>
                        </div>
                        <div class="article-author-name">
                            <a href="/a18138/account/index.jsp?id=<%=dao.userId%>"><%=dao.author%></a>
                            <%
                            if(dao.verified == 1)
                            {
                                %>
                                <span><img src="/a18138/images/verify.png" title="Verified"></span>
                                <%
                            }
                            %>
                                <div class="follow <%if(user_id==dao.userId) {out.print("hidden");}%>">
                                <span class="RPhNB">•</span>
                                <a href="" id="btnFollow">
                                    <%
                                    if(aDAO.isFollowed(dao.userId, user_id))
                                    {
                                        out.print("UnFollow");
                                    }
                                    else
                                    {
                                        out.print("Follow");
                                    }
                                    %>
                                </a>
                            </div>
                        </div>
                        <div class="article-action">
                            <button class="post-index-button" data-id="<%=id%>">
                                <i class="fas fa-ellipsis-h"></i>
                            </button>
                        </div>
                    </div>
                    <div class="line"></div>
                    <div class="article-talk" id="talk">
                        <div class="article-talk-item">
                            <div class="talk-avatar">
                                <a href="/a18138/account/index.jsp?id=<%=dao.userId%>"><img src="/a18138/images/profile/<%=dao.avatar%>" alt=""></a>
                            </div>
                            <div class="talk-content">
                                <h3><a href="/a18138/account/index.jsp?id=<%=dao.userId%>"><%=dao.author%></a></h3>
                                <%
                                if(dao.verified == 1)
                                {
                                    %>
                                    <span><img src="/a18138/images/verify.png" title="Verified"></span>
                                    <%
                                }
                                %>
                                <span><%=own.getMessage()%></span>
                                <div class="talk-times">
                                    <time class="times"><%=dao.created_at%></time>
                                </div>
                            </div>
                        </div>
                                <div id="loadcmt">
                                    <%@include file="comments.jsp"%>
                                </div>
                                <%
                                if(countCmt > 6)
                                {
                                %>
                                    <div class="center" id="loading"></div>
                                    <div class="center loading-sm">
                                        <i class="fas fa-plus-circle" data-lm="<%=id%>" id="load-more"></i>
                                    </div>
                                <%
                                }
                                %>
                    </div>
                    <section class="article-action">
                        <span>
                            <a href="#">
                                <%
                                    if(dao.isLiked(id, user_id))
                                    {
                                        out.print("<i class='far fas fa-heart red' id='like'></i>");
                                    }
                                    else{
                                        out.print("<i class='far fa-heart' id='like'></i>");
                                    }
                                %>
                            </a>
                        </span>
                        <span>
                            <label for="msg"><i class="far fa-comment"></i></label>
                        </span>
                    </section>
                    <section class="article-info">
                        <span id="numlike"><%=dao.getLikeOfPost(id)%></span> likes
                        <div class="article-times"><%=own.getDate()%></div>
                    </section>
                    <section class="article-comment">
                        <form action="/a18138/servletComment" method="post" id="frmComment">
                            <input type="hidden" name="postId" id="postId" value="<%=id%>">
                            <textarea name="msg" id="msg" class="article-input" placeholder="Leave a comment ..." required></textarea>
                            <button  type="submit" class="btn-cmt">Post</button>
                        </form>
                    </section>
                </div>
            </article>
        </div>
        <section class="user-posts">
        <div class="line"></div>
        <div class="more-post-author">
            More posts from <a href="/a18138/account/index.jsp?id=<%=dao.userId%>"><%=dao.author%></a>
        </div>
        <div class="user-posts-list" id="loadpost">
        <%
        postDAO postController = new postDAO();
        ArrayList<Post> post = postController.getList(dao.userId);
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
            </a></article>
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
    <div class="function-fixed">
        <div class="function-container">
            <div class="function-content">
                <button class="function-btn function-red" onclick="reportPost();">Report</button>
                <%
                if(user_id == dao.userId)
                {
                    %>
                    <button class="function-btn function-red" onclick="editPost();">Edit post</button>
                    <button class="function-btn function-red" onclick="deletePost();">Delete post</button>
                    <%
                }
                %>
                <button class="function-btn" onclick="copyLink();">Copy link</button>
                <button class="function-btn btn-huy">Cancel</button>
            </div>
        </div>
    </div>
    <div class="pop-up">
        <span>Copy linked to cache</span>
    </div>
    <input type="text" class="hidden" id="url">
    <input type="text" class="hidden" id="userId" value="<%=dao.userId%>">
    <input type="hidden" id="pId" value="<%=id%>">
    <script src="/a18138/js/duy.js"></script>
    <script src="/a18138/js/app.js"></script>
    <script src="/a18138/js/loadmore.js"></script>
    <script src="/a18138/js/admin.js"></script>
    <script>
        var skip = 6;
        $(document).ready(function(){
            var bool = true;
            $("#like").on('click',function(e){
               e.preventDefault();
               $(this).toggleClass("fas red");
               $.ajax({
                  url : '/a18138/servletPost?id=<%=id%>',
                  cache : false,
                  type: 'GET',
                  dataType: 'JSON',
                  success : function(data){
                      //alert(data.message);
                      $("#numlike").html(data.numlike);
                  },
                  error : function(){
                      alert("Error, can not like this post...");
                  }
               });
            });
        });
    </script>
    <script>
        $(document).ready(function(){
            $("#frmComment").submit(function(e){
               e.preventDefault();
               //alert("submit form");
               var form = $(this);
               var url = form.attr("action");
               var msg = $("#msg").val();
               $.ajax({
                  url: url,
                  type: 'POST',
                  dateType: 'JSON',
                  cache : false,
                  data : {
                      msg: msg,
                      postId : $("#postId").val()
                  },
                  success : function(data){
                      //alert(data.msg);
                      skip = 6;
                      $("#load-more").fadeIn();
                      $("#loading").fadeIn("");
                      $("#msg").val("");
                      $("#loadcmt").load("comments.jsp?id=<%=id%>");
                  },
                  error : function(){
                      alert("Error, cannot sending comment");
                  }
               });
            });
        });
    </script>
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
                  id : $("#userId").val()
              },
              success : function(data){
                  //alert(data.msg);
                  if(data.msg == 1)
                  {
                      //alert("un");
                      $("#btnFollow").html("Follow");
                  }
                  else{
                      $("#btnFollow").html("UnFollow");
                  }
              },
              error : function(){
                  alert("Error : Cannot follow this user");
              }
           });
        }); 
    });
</script>
<script src="/a18138/js/carousel.js"></script>
</body>
</html>