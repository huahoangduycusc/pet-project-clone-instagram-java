<%@page import="hhd.entity.Post"%>
<%@page import="hhd.postDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="hhd.entity.Comment"%>
<%
    postDAO cDAO = new postDAO();
    int idPost = Integer.parseInt(request.getParameter("id").toString());
    ArrayList<Comment> comments = cDAO.getListOfComment(idPost);
    int countCMT = cDAO.countComment(idPost);
    for(Comment item : comments)
    {
        %>
        <div class="article-talk-item">
            <div class="talk-avatar">
                <a href="/a18138/account/index.jsp?id=<%=item.getUserId()%>"><img src="/a18138/images/profile/<%=item.getAvatar()%>" alt=""></a>
            </div>
            <div class="talk-content">
                <h3><a href="/a18138/account/index.jsp?id=<%=item.getUserId()%>"><%=item.getUsername()%></a></h3>
                <%
                if(item.getVerified() == 1)
                {
                    %>
                    <span><img src="/a18138/images/verify.png" title="Verified"></span>
                    <%
                }
                %>
                <span><%=item.getMessage()%></span>
                <div class="talk-times">
                    <time class="times"><%=item.getDate()%></time>
                </div>
            </div>
        </div>
        <%
    }
%>

