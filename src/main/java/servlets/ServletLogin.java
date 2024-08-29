package servlets;

import dao.DAOLoginRepository;
import dao.DAOUserRepository;
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

    private final DAOLoginRepository daoLoginRepositoty = new DAOLoginRepository();
    private final DAOUserRepository userRepository = new DAOUserRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("Logout");
        if (action != null && action.equalsIgnoreCase("logout")) {
            request.getSession().invalidate();
            RequestDispatcher redirect = request.getRequestDispatcher("index.jsp");
            redirect.forward(request, response);
        } else {
            doPost(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String url = request.getParameter("url");

        try {
            if (login != null && !login.isEmpty() && password != null && !password.isEmpty()) {
                Login userLogin = new Login();
                userLogin.setLogin(login);
                userLogin.setPassword(password);

                if (daoLoginRepositoty.validateAuthentication(userLogin)) {

                    request.getSession().setAttribute("user", userLogin.getLogin());
                    request.getSession().setAttribute("isAdmin", userRepository.searchUserLoggedByLogin(login).getIsAdmin());

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
                RequestDispatcher redirect = request.getRequestDispatcher("/index.jsp");
                redirect.forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "Ocorreu um erro: " + e.getMessage());
            RequestDispatcher redirect = request.getRequestDispatcher("/error.jsp");
            redirect.forward(request, response);
        }
    }
}
