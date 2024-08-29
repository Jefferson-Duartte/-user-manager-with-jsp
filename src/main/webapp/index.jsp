<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-br">

<head>
    <title>Login - User Manager</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimal-ui">
    <meta name="description"
          content="Login"/>
    <meta name="author" content="Jefferson Duarte"/>
    <link href="https://fonts.googleapis.com/css?family=Roboto:400,500" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/assets/css/bootstrap/css/bootstrap.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="icon" href="<%=request.getContextPath()%>/assets/images/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/pages/waves/css/waves.min.css" type="text/css" media="all">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/assets/icon/themify-icons/themify-icons.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/assets/icon/icofont/css/icofont.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/assets/icon/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/assets/css/style.css">

    <style>
        .isFalse {
            display: none;
        }

    </style>
</head>

<body themebg-pattern="theme1">

<section class="login-block">
    <div class="container">
        <div class="row">
            <div class="col-sm-12">

                <form class="md-float-material form-material" action="ServletLogin" method="post">
                    <div class="auth-box card">
                        <div class="card-block">
                            <div class="row m-b-20">
                                <div class="col-md-12">
                                    <h3 class="text-center">Entrar</h3>
                                </div>
                            </div>
                            <div class="form-group form-primary">
                                <input type="text" name="login" class="form-control" required="">
                                <span class="form-bar"></span>
                                <label class="float-label">Usuário</label>
                            </div>
                            <div class="form-group form-primary">
                                <input type="password" name="password" class="form-control" required="">
                                <span class="form-bar"></span>
                                <label class="float-label">Senha</label>
                            </div>
                            <div class="row m-t-25 text-left">
                                <div class="col-12">

                                    <div class="forgot-phone text-left   f-left">
                                        <a href="<%=request.getContextPath()%>/forgotPassword.jsp" class="text-right f-w-600"> Esqueci minha senha</a>
                                    </div>
                                </div>
                            </div>
                            <div class="row m-t-30">
                                <div class="col-md-12">
                                    <button type="submit"
                                            class="btn btn-primary btn-md btn-block waves-effect waves-light text-center m-b-20">
                                        Entrar
                                    </button>
                                </div>
                            </div>
                            <hr/>
                            <div class="row">
                                <%
                                    String msg = (String) request.getAttribute("msg");
                                    String style = "";
                                    if (msg == null || msg == "null") {
                                        msg = "";
                                        style = "isFalse";
                                    }

                                %>

                                <div class="alert alert-warning <%=style%>" + role="alert">
                                    <%=msg%>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</section>

</body>

</html>