<%--
  Created by IntelliJ IDEA.
  User: jeffs
  Date: 22/07/2024
  Time: 14:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Tela de erros</title>
</head>
<body>
    <h1>Mensagem de erro, entre em contato com a equipe de suporte do sistema.</h1>
<%
    out.print(request.getAttribute("msg"));
%>
</body>
</html>
