<%-- 
    Document   : index
    Created on : Dec 14, 2020, 3:04:34 PM
    Author     : asus
--%>

<%@page import="hhd.followDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="hhd.entity.Post"%>
<%@page import="hhd.postDAO"%>
<%@page import="hhd.accountDAO"%>
<%@page import="hhd.entity.Account"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="header.jsp"%>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<%
accountDAO aDAO = new accountDAO();
Account myself = aDAO.getRow(user_id);
ArrayList<Account> suggest = aDAO.getSuggestion(user_id);
%>
<div class="body-content">
    <%
    if(aDAO.isAnyfollow(user_id) == false)
    {
        %>
        <section class="any-follow">
            <h4>Suggestion for you</h4>
            <div class="any-follow-list" id="list">
                <%
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
                                <button class="btn-follow-sm" id="fl<%=ac.getId()%>" data-fl="<%=ac.getId()%>">Follow</button>
                            </div>
                        </div>
                        <%
                    }
                %>
            </div>
        </section>
        <%
    }
    %>
        <div class="box-welcome">
            <div class="welcome-left">
                <div class="box-view" id="loadpost">
                    <%
                    followDAO fl = new followDAO(user_id);
                    ArrayList<Post> posts = fl.getList();
                    int countPost = fl.countArticle();
                    for(Post item : posts)
                    {
                       %>
                       <article class="post-index">
                        <div class="post-index-author">
                            <div class="post-index-avatar">
                                <a href="/a18138/account/index.jsp?id=<%=item.getUserId()%>"><img src="./images/profile/<%=item.getAvatar()%>" alt=""></a>
                            </div>
                            <div class="post-index-name">
                                <span><a href="/a18138/account/index.jsp?id=<%=item.getUserId()%>"><%=item.getAuthor()%></a>
                                <%
                                if(item.getVerified() == 1)
                                {
                                    %>
                                    <img src="/a18138/images/verify.png" title="Verified" style="width:13px;">
                                    <%
                                }
                                %>
                                </span>
                                
                            </div>
                        </div>
                        <div class="post-btn-index">
                            <button class="post-index-button" data-id="<%=item.getId()%>">
                                <i class="fas fa-ellipsis-h"></i>
                            </button>
                        </div>
                        <div class="post-index-img">
                            <a href="/a18138/post/view.jsp?id=<%=item.getId()%>">
                                <img src="/a18138/images/<%=item.getThumbnail()%>" alt="">
                            </a>
                        </div>
                        <div class="post-index-content">
                            <section class="post-index-operation">
                                <span>
                                    <%
                                    postDAO dao = new postDAO();
                                    if(dao.isLiked(item.getId(), user_id))
                                    {
                                        %>
                                        <i class="far fas fa-heart red like-post" data-post='<%=item.getId()%>'></i>
                                        <%
                                    }
                                    else{%>
                                        <i class="far fa-heart like-post" data-post='<%=item.getId()%>'></i>
                                    <%}
                                %>
                                </span>
                                <span>
                                    <a href="/a18138/post/view.jsp?id=<%=item.getId()%>"><i class="far fa-comment"></i></a>
                                </span>
                                <div class="post-count-img">
                                    <%
                                    int loop = fl.loopDot(item.getId());
                                    if(loop > 1)
                                    {
                                        for(int i=0;i<loop;i++)
                                        {
                                            out.print("<i></i> ");
                                        }
                                    }
                                    %>
                                </div>
                            </section>
                            <section class="post-index-like">
                                <span id="like<%=item.getId()%>"><%=item.getLike()%></span> likes
                            </section>
                            <section class="post-index-msg">
                                <div class="post-index-text">
                                    <span class="post-text-author"><a href="/a18138/account/index.jsp?id=<%=item.getUserId()%>"><%=item.getAuthor()%></a>
                                    <%
                                    if(item.getVerified() == 1)
                                    {
                                        %>
                                        <img src="/a18138/images/verify.png" title="Verified" style="width:13px;">
                                        <%
                                    }
                                    %>
                                    </span>
                                    <span class="post-text-content"><%=item.getMessage()%></span>
                                </div>
                            </section>
                            <div class="post-index-times">
                                <span><%=item.getDate()%></span>
                            </div>
                        </div>
                    </article>
                    <%
                    }
                    if(posts.size() == 0 && aDAO.isAnyfollow(user_id) == true)
                    {
                        out.print("<div class='empty'>The users that you followed haven't any post !</div>");
                    }
                    %>
                </div>
                <%
                if(countPost > 5)
                {
                %>
                <div class="center" id="loading"></div>
                <div class="center loading-sm"><i class="fas fa-plus-circle" style="font-size: 30px;" id="load-more"></i></div>
                <%
                }
                %>
            </div>
            <%
            if(aDAO.isAnyfollow(user_id))
            {
                %>
                <div class="welcome-right">
                <div class="box-right-self">
                    <div class="box-right-avatar">
                        <a href="/a18138/account/index.jsp?id=<%=myself.getId()%>"><img src="./images/profile/<%=myself.getAvatar()%>" style="width:56px;height:56px;" alt=""></a>
                    </div>
                    <div class="box-right-user">
                        <div class="user-bold"><a href="/a18138/account/index.jsp?id=<%=myself.getId()%>"><%=myself.getUsername()%></a></div>
                        <div class="user-fullname"><a href=""><%=myself.getFullname()%></a></div>
                    </div>
                    <div class="box-right-out">
                        <a href="/a18138/exit.jsp">Log out</a>
                    </div>
                </div>
                <!-- end div -->
                <div class="box-right-suggest">
                    <div class="suggest-text">
                        <div>Suggestions For You</div>
                        <a href="/a18138/explore.jsp" class="suggest-see">See all</a>
                    </div>
                    <div class="suggest-list">
                        <div class="suggest-list-user">
                            <%
                            for (Account ac : suggest) {
                                    %>
                                    <div class="suggest-user">
                                        <div class="suggest-user-avatar">
                                            <a href="/a18138/account/index.jsp?id=<%=ac.getId()%>"><img src="./images/profile/<%=ac.getAvatar()%>" style="width:32px;height:32px;" alt=""></a>
                                        </div>
                                        <div class="suggest-user-name">
                                            <div class="suggest-u"><a href="/a18138/account/index.jsp?id=<%=ac.getId()%>"><%=ac.getUsername()%></a></div>
                                            <div class="suggest-f">Suggested for you</div>
                                        </div>
                                        <div class="suggest-user-follow">
                                            <a href="/a18138/account/index.jsp?id=<%=ac.getId()%>">See profile</a>
                                        </div>
                                    </div>
                                    <%
                                }
                            %>
                        </div>
                    </div>
                </div>
            </div>
                <%
            }
            %>
        </div>
    </div>
    <div class="function-fixed">
        <div class="function-container">
            <div class="function-content">
                <button class="function-btn function-red" onclick="reportPost();">Report</button>
                <button class="function-btn" onclick="gotoPost();">Go to post</button>
                <button class="function-btn" onclick="copyLink();">Copy link</button>
                <button class="function-btn btn-huy">Cancel</button>
            </div>
        </div>
    </div>
    <div class="pop-up">
        <span>Copy linked to cache</span>
    </div>
        <div class="timbay">
        <i class="fas fa-heart"></i>
    </div>
    <input type="text" class="hidden" id="url">
    <script src="./js/post.js"></script>
    <script src="./js/like.js"></script>
    <script src="./js/follow.js"></script>
    <script src="./js/load-post.js"></script>
<%@include file="footer.jsp"%>