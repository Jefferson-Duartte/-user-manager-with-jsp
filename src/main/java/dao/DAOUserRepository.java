package dao;

import connection.SingleConnection;
import model.Login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOUserRepository {

    private Connection connection;

    public DAOUserRepository() {
        connection = SingleConnection.getConnection();
    }

    public Login saveUser(Login user, Long idUserLogged) throws Exception {

        if (user.isNew()) {
            String sql = "INSERT INTO public.tb_login(login, password, name, email, user_id) VALUES (?, ?, ?, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setString(4, user.getEmail());
            statement.setLong(5, idUserLogged);

            statement.execute();
            connection.commit();

        } else {
            String sql = "UPDATE public.tb_login SET login=?, password=?, name=?, email=? WHERE id = " + user.getId();

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setString(4, user.getEmail());

            statement.executeUpdate();
            connection.commit();

        }

        return this.searchUserByLogin(user.getLogin(), idUserLogged);
    }

    public List<Login> searchUsersByName(String name, Long idUserLogged) throws Exception {

        List<Login> users = new ArrayList<>();

        String sql = "SELECT * FROM tb_login WHERE upper(name) like upper(?) and useradmin is false and user_id = " + idUserLogged;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, "%" + name + "%");
        ResultSet result = statement.executeQuery();

        while (result.next()) {
            Login user = new Login();
            user.setId(result.getLong("id"));
            user.setName(result.getString("name"));
            user.setEmail(result.getString("email"));
            user.setLogin(result.getString("login"));
            users.add(user);
        }

        return users;

    }

    public List<Login> getAllUsers(Long idUserLogged) throws Exception {

        List<Login> users = new ArrayList<>();

        String sql = "SELECT * FROM tb_login where useradmin is false and user_id = " + idUserLogged;

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet result = statement.executeQuery();

        while (result.next()) {
            Login user = new Login();
            user.setId(result.getLong("id"));
            user.setName(result.getString("name"));
            user.setEmail(result.getString("email"));
            user.setLogin(result.getString("login"));
            users.add(user);
        }

        return users;

    }

    public Login searchUserByLogin(String login) throws SQLException {

        String sql = "SELECT * FROM tb_login WHERE upper(login) = upper('" + login + "') and useradmin is false";

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet result = statement.executeQuery();

        Login user = new Login();
        while (result.next()) {
            user.setId(result.getLong("id"));
            user.setName(result.getString("name"));
            user.setEmail(result.getString("email"));
            user.setLogin(result.getString("login"));
            user.setPassword(result.getString("password"));
        }

        return user;
    }

    public Login searchUserLoggedByLogin(String login) throws SQLException {

        String sql = "SELECT * FROM tb_login WHERE upper(login) = upper('" + login + "')";

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet result = statement.executeQuery();

        Login user = new Login();
        while (result.next()) {
            user.setId(result.getLong("id"));
            user.setName(result.getString("name"));
            user.setEmail(result.getString("email"));
            user.setLogin(result.getString("login"));
            user.setPassword(result.getString("password"));
            user.setIsAdmin(result.getBoolean("useradmin"));
        }

        return user;
    }

    public Login searchUserByLogin(String login, Long idLoggedUser) throws SQLException {

        String sql = "SELECT * FROM tb_login WHERE upper(login) = upper('" + login + "') and user_id = " + idLoggedUser;

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet result = statement.executeQuery();

        Login user = new Login();
        while (result.next()) {
            user.setId(result.getLong("id"));
            user.setName(result.getString("name"));
            user.setEmail(result.getString("email"));
            user.setLogin(result.getString("login"));
            user.setPassword(result.getString("password"));
        }

        return user;
    }


    public Login searchUserById(Long userId) throws SQLException {

        String sql = "SELECT * FROM tb_login  WHERE id = ? and useradmin is false";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, userId);
        ResultSet result = statement.executeQuery();

        Login user = new Login();
        while (result.next()) {
            user.setId(result.getLong("id"));
            user.setName(result.getString("name"));
            user.setEmail(result.getString("email"));
            user.setLogin(result.getString("login"));
            user.setPassword(result.getString("password"));
        }

        return user;
    }

    public boolean validateLogin(String login) throws Exception {

        String sql = "select count(1) > 0 as exists from tb_login where upper(login) = upper('" + login + "')";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet result = statement.executeQuery();

        result.next();
        return result.getBoolean("exists");

    }

    public void deleteUser(Long userId) throws Exception {

        String sql = "DELETE FROM tb_login WHERE id = ? and useradmin is false";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, userId);

        statement.execute();
        connection.commit();
    }

}
