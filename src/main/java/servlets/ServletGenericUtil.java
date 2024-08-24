package servlets;

import dao.DAOUserRepository;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;

import java.io.Serializable;
import java.sql.SQLException;

public class ServletGenericUtil extends HttpServlet implements Serializable {

    private final DAOUserRepository repository = new DAOUserRepository();

    public Long getIdLoggedUser(HttpServletRequest request) throws SQLException {

        String userLogged = (String) request.getSession().getAttribute("user");

        return repository.searchUserLoggedByLogin(userLogged).getId();

    }

}
