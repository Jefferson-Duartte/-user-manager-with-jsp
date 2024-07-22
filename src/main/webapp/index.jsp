<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>Hello JSP</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">

    <style>

        *{
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body{
            width: 100vw;
            height: 100vh;
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        form{
            width: 50%;
            padding-top: 50px;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;

        }
        h1{
            text-align: center;
        }

        a{
            text-decoration: none;
        }

        #btn-login{
            margin-top: 10px;
            width: 200px;
        }

        #msg-error{
            color: red;
            text-align: center;
        }

    </style>


</head>
<body>


<form class="row g-3" action="ServletLogin" method="POST">
<h1>Login</h1>

    <input type="hidden" value="<%= request.getParameter("url")%>" name="url">

    <div class="col-md-6">
        <label for="inputEmail4" class="form-label">Login</label>
        <input name="login" type="text" class="form-control" id="inputEmail4">
    </div>
    <div class="col-md-6">
        <label for="inputPassword4" class="form-label">Senha</label>
        <input name="password" type="password" class="form-control" id="inputPassword4">
        <a href="/forgotPassword.jsp" target="_blank">Esqueci minha senha</a>
    </div>

    <button type="submit" class="btn btn-primary" id="btn-login">Entrar</button>


    <p id="msg-error"><%= request.getAttribute("msg") %></p>

</form>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
</body>
</html>