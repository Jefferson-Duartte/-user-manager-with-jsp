package servlets;

import dao.DAOUserRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Login;

import java.io.IOException;

@WebServlet("/ServletUserController")
public class ServletUserController extends HttpServlet {

    private DAOUserRepository daoUserRepository = new DAOUserRepository();

    public ServletUserController() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String msg = "Operação realizada com sucesso!";

        try {
            String id = request.getParameter("id");
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String login = request.getParameter("login");
            String password = request.getParameter("password");

            Login user = new Login();
            user.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
            user.setName(name);
            user.setEmail(email);
            user.setLogin(login);
            user.setPassword(password);

            if (daoUserRepository.validateLogin(user.getLogin()) && user.getId() == null) {
                msg = "Já existe um usuário com o mesmo login.";
            }else{
                if(user.isNew()){
                    msg = "Usuário cadastrado com sucesso!";
                }else {
                    msg = "Usuário atualizado com sucesso!";
                }
                user = daoUserRepository.saveUser(user);
            }

            request.setAttribute("msg", msg);
            request.setAttribute("dataLogin", user);
            request.getRequestDispatcher("main/create-user.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            msg = "Ocorreu um erro: " + e.getMessage();
            request.setAttribute("msg", msg);
            RequestDispatcher redirect = request.getRequestDispatcher("/error.jsp");
            redirect.forward(request, response);
        }
    }
}
