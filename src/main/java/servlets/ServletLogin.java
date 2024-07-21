package servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Login;

import java.io.IOException;


@WebServlet("/ServletLogin")
public class ServletLogin extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String senha = request.getParameter("senha");

        if (login != null && !login.isEmpty() && senha != null && !senha.isEmpty()) {
            Login userLogin = new Login();
            userLogin.setLogin(login);
            userLogin.setSenha(senha);

            if (userLogin.getLogin().equalsIgnoreCase("admin") && userLogin.getSenha().equalsIgnoreCase("admin")) {
                request.setAttribute("usuario", userLogin.getLogin());
                RequestDispatcher redirect = request.getRequestDispatcher("main/main.jsp");
                redirect.forward(request, response);
            } else {
                request.setAttribute("msg", "Informe o login e senha corretamente!");
                RequestDispatcher redirect = request.getRequestDispatcher("/index.jsp");
                redirect.forward(request, response);
            }

        } else {
            request.setAttribute("msg", "Informe o login e senha corretamente!");
            RequestDispatcher redirect = request.getRequestDispatcher("/index.jsp");
            redirect.forward(request, response);

        }

    }

}