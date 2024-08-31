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
            String sql = "INSERT INTO public.tb_login(login, password, name, email, user_id, profile, gender) VALUES (?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setString(4, user.getEmail());
            statement.setLong(5, idUserLogged);
            statement.setString(6, user.getProfile());
            statement.setString(7, user.getGender());

            statement.execute();
            connection.commit();

        } else {
            String sql = "UPDATE public.tb_login SET login=?, password=?, name=?, email=?, profile=?, gender=? WHERE id = ?";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getProfile());
            statement.setString(6, user.getGender());
            statement.setLong(7, user.getId());

            statement.executeUpdate();
            connection.commit();

        }

        return this.searchUserByLogin(user.getLogin(), idUserLogged);
    }

    public List<Login> searchUsersByName(String name, Long idUserLogged) throws Exception {

        List<Login> users = new ArrayList<>();

        String sql = "SELECT * FROM tb_login WHERE upper(name) LIKE upper(?) AND (profile != 'ADMINISTRADOR' OR profile IS NULL) AND user_id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, "%" + name + "%");
        statement.setLong(2, idUserLogged);
        ResultSet result = statement.executeQuery();

        while (result.next()) {
            Login user = new Login();
            user.setId(result.getLong("id"));
            user.setName(result.getString("name"));
            user.setEmail(result.getString("email"));
            user.setLogin(result.getString("login"));
            user.setProfile(result.getString("profile"));
            user.setGender(result.getString("gender"));
            users.add(user);
        }

        return users;

    }

    public List<Login> getAllUsers(Long idUserLogged) throws Exception {

        List<Login> users = new ArrayList<>();

        String sql = "SELECT * FROM tb_login WHERE (profile != 'ADMINISTRADOR' OR profile IS NULL) AND user_id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, idUserLogged);
        ResultSet result = statement.executeQuery();

        while (result.next()) {
            Login user = new Login();
            user.setId(result.getLong("id"));
            user.setName(result.getString("name"));
            user.setEmail(result.getString("email"));
            user.setLogin(result.getString("login"));
            user.setProfile(result.getString("profile"));
            user.setGender(result.getString("gender"));
            users.add(user);
        }

        return users;

    }

    public Login searchUserByLogin(String login) throws SQLException {

        String sql = "SELECT * FROM tb_login WHERE upper(login) = upper(?) AND (profile != 'ADMINISTRADOR' OR profile IS NULL)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, login);
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

        String sql = "SELECT * FROM tb_login WHERE upper(login) = upper(?)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, login);
        ResultSet result = statement.executeQuery();

        Login user = new Login();
        while (result.next()) {
            user.setId(result.getLong("id"));
            user.setName(result.getString("name"));
            user.setEmail(result.getString("email"));
            user.setLogin(result.getString("login"));
            user.setPassword(result.getString("password"));
            user.setProfile(result.getString("profile"));
            user.setGender(result.getString("gender"));
        }

        return user;
    }

    public Login searchUserByLogin(String login, Long idLoggedUser) throws SQLException {

        String sql = "SELECT * FROM tb_login WHERE upper(login) = upper(?) AND user_id = ? ";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, login);
        statement.setLong(2, idLoggedUser);
        ResultSet result = statement.executeQuery();

        Login user = new Login();
        while (result.next()) {
            user.setId(result.getLong("id"));
            user.setName(result.getString("name"));
            user.setEmail(result.getString("email"));
            user.setLogin(result.getString("login"));
            user.setPassword(result.getString("password"));
            user.setProfile(result.getString("profile"));
            user.setGender(result.getString("gender"));
        }

        return user;
    }


    public Login searchUserById(Long userId) throws SQLException {

        String sql = "SELECT * FROM tb_login  WHERE id = ? AND (profile != 'ADMINISTRADOR' OR profile IS NULL) ";

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
            user.setProfile(result.getString("profile"));
            user.setGender(result.getString("gender"));
        }

        return user;
    }

    public boolean validateLogin(String login) throws Exception {

        String sql = "SELECT count(1) > 0 AS EXISTS FROM tb_login WHERE UPPER(login) = UPPER(?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, login);
        ResultSet result = statement.executeQuery();

        result.next();
        return result.getBoolean("exists");

    }

    public void deleteUser(Long userId) throws Exception {

        String sql = "DELETE FROM tb_login WHERE id = ? AND (profile != 'ADMINISTRADOR' OR profile IS NULL)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, userId);

        statement.execute();
        connection.commit();
    }

}
