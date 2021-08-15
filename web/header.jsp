<%-- 
    Document   : header
    Created on : Dec 15, 2020, 8:09:19 AM
    Author     : asus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if(session.getAttribute("user_id") == null)
    {
        response.sendRedirect("/a18138/login.jsp");
    }
    int user_id = 0;
    if(session.getAttribute("user_id") != null)
    {
        user_id = Integer.parseInt(session.getAttribute("user_id").toString());
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Share the memories with your friends</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css" integrity="sha512-+4zCK9k+qNFUR5X+cKL9EIR+ZOhtIloNl9GIKS57V1MyNsYpYcUrUeQc9vNfzsWfV28IaLL3i96P9sdNyeRssA==" crossorigin="anonymous" />
    <link rel="stylesheet" href="/a18138/css/style.css" type="text/css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
    <%
    if(session.getAttribute("user_id") != null)
    {
        %>
        <header>
        <nav class="nav">
            <div class="nav-container">
                <div class="nav-logo">
                    <a href="/a18138/"><img src="/a18138/images/logo.png" alt=""></a>
                </div>
                <div class="nav-search">
                    <form class="nav-search-form" action="/a18138/search.jsp" method="GET">
                        <div class="search-nav-content">
                            <input type="text" name="s" placeholder="Search">
                            <button type="submit" class="nav-search-btn"><i class="fas fa-search"></i></button>
                        </div>
                    </form>
                </div>
                <div class="nav-user">
                    <div class="nav-user-container">
                        <div class="nav-user-item"><a href="/a18138/index.jsp"><i class="fas fa-home"></i></a></div>
                        <div class="nav-user-item"><a href="/a18138/explore.jsp"><i class="fab fa-cloudscale"></i></a></div>
                        <div class="nav-user-item"><a href="/a18138/account/notification.jsp"><i class="fas fa-heart"></i></a></div>
                        <div class="nav-user-item function">
                            <a href="" class="nav-user-item"><i class="fas fa-user dropdown-toggle"></i></a>
                            <div class="guess">
                                <div class="guess-container">
                                    <a href="/a18138/account/index.jsp?id=<%=user_id%>" class="guess-item"><i class="fas fa-user"></i><span>Profile</span></a>
                                    <a href="/a18138/account/setting.jsp" class="guess-item"><i class="fas fa-cog"></i><span>Setting</span></a>
                                    <%
                                        if(session.getAttribute("rights") != null && session.getAttribute("rights").equals(1))
                                        {
                                            %>
                                    <a href="/a18138/admin/index.jsp" class="guess-item"><i class="fas fa-flag"></i><span>Report</span></a>
                                            <%
                                        }
                                    %>
                                    <a href="/a18138/exit.jsp" class="guess-item"><i class="fas fa-sign-out-alt"></i><span>Sign out</span></a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </nav>
    </header>
        <%
    }
    %>