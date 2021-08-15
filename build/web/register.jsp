<%-- 
    Document   : login
    Created on : Dec 14, 2020, 3:32:38 PM
    Author     : asus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
if(session.getAttribute("user_id") != null)
{
    response.sendRedirect("/a18138/index.jsp");
}
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register</title>
    <link rel="stylesheet" href="css/login.css" type="text/css">
</head>
<body>
    <div class="container">
        <main>
            <div class="box">
                <div class="list1">
                    <h1 class="title">Instagram</h1>
                    <%
                    if(request.getParameter("error") != null)
                    {
                        String error = request.getParameter("error");
                        if(error.equals("uservalid"))
                        {
                            out.print("<div class='text-danger'>Username is not contains any special character and must be from 5 to 10 characters.</div>");
                        }
                        else if(error.equals("userexist"))
                        {
                            out.print("<div class='text-danger'>Username is already exists.</div>");
                        }
                        else if(error.equals("emailexists"))
                        {
                            out.print("<div class='text-danger'>Email is already exists.</div>");
                        }
                    }
                    %>
                    <p class="slogan">Sign up to see photos from your friends.</p>
                    <form action="servletAccount" method="post" autocomplete="on">
                        <div class="txt-input">
                            <label>Email</label>
                            <input type="text" name="email" required>
                        </div>
                        <div class="txt-input">
                            <label>Full name</label>
                            <input type="text" name="fullname" required>
                        </div>
                        <div class="txt-input">
                            <label>Username</label>
                            <input type="text" name="username" required>
                        </div>
                        <div class="txt-input">
                            <label>Password</label>
                            <input type="password1" name="password" required>
                        </div>
                        <div class="txt-button">
                            <button type="submit" name="action" value="register">Sign up</button>
                        </div>
                    </form>
                </div>
                <!-- list1  -->
                <div class="list1">
                    <p>Have an account? <a href="login.jsp">Log in</a></p>
                </div>
            </div>
        </main>
        <footer class="footer">
            <p>&copy;  All Rights Reversed 2020</p>
            <span>Founded and developed by Hua Hoang Duy</span>
        </footer>
    </div>
    <script>
    const input = document.querySelectorAll('input');
    input.forEach(item => {
        item.addEventListener('focus',(e) => {
        item.offsetParent.classList.add('active');
        });
        item.addEventListener('blur',(e) => {
            if(e.target.value == ""){
                item.offsetParent.classList.remove('active');
            }
        });
        item.addEventListener('keyup',(e) => {
            if(e.target.value == ""){
                item.offsetParent.classList.remove('active');
            }
        });
    });
    </script>
</body>
</html>