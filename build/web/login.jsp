<%-- 
    Document   : login
    Created on : Dec 14, 2020, 3:32:38 PM
    Author     : asus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
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
                        if(error.equals("fail"))
                        {
                            out.print("<div class='text-danger'>These credential is not match any our records.</div>");
                        }
                    }
                    %>
                    <form action="servletAccount" method="post">
                        <div class="txt-input">
                            <label>Username</label>
                            <input type="text" name="username" id="username">
                        </div>
                        <div class="txt-input">
                            <label>Password</label>
                            <input type="password" name="password" id="password">
                        </div>
                        <div class="txt-button">
                            <button type="submit" name="action" value="login">Sign in</button>
                        </div>
                    </form>
                </div>
                <!-- list1  -->
                <div class="list1">
                    <p>Don't have an account? <a href="register.jsp">Sign up</a></p>
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