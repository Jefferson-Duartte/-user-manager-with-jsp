<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>Hello JSP</title>
</head>
<body>
<h1>Testando requisição</h1>

<form action="ServletLogin" method="POST">

    <input type="hidden" value="<%= request.getParameter("url")%>" name="url">

    <table>

        <tr>
            <td><label>Login</label></td>
            <td><input name="login" type="text"></td>
        </tr>

        <tr>
            <td><label>Senha</label></td>
            <td><input name="senha" type="password"></td>
        </tr>

        <tr>
            <td><input type="submit" value="enviar"></td>
        </tr>

    </table>
    <%
        String mensagem = (String) request.getAttribute("msg");
        if (mensagem != null) {
            out.print(mensagem);
        }
    %>

</form>

</body>
</html>