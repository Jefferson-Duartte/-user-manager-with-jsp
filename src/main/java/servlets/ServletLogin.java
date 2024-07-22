package servlets;

import dao.DAOLoginRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Login;

import java.io.IOException;


@WebServlet(urlPatterns = {"/ServletLogin", "/main/ServletLogin"})
public class ServletLogin extends HttpServlet {

    private DAOLoginRepository daoLoginRepositoty = new DAOLoginRepository();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String login = request.getParameter("login");
        String senha = request.getParameter("password");
        String url = request.getParameter("url");

        try {
            if (login != null && !login.isEmpty() && senha != null && !senha.isEmpty()) {
                Login userLogin = new Login();
                userLogin.setLogin(login);
                userLogin.setPassword(senha);

                if (daoLoginRepositoty.validateAuthentication(userLogin)) {

                    request.getSession().setAttribute("usuario", userLogin.getLogin());

                    if (url == null || url.equals("null")) {
                        url = "/main/main.jsp";
                    }
                    RequestDispatcher redirect = request.getRequestDispatcher(url);
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

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", e.getMessage());
            RequestDispatcher redirect = request.getRequestDispatcher("error.jsp");
            redirect.forward(request, response);
        }

    }

}