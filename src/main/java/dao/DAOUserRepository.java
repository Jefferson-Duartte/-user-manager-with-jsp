package dao;

import connection.SingleConnection;
import model.Login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOUserRepository {

    private Connection connection;

    public DAOUserRepository() {
        connection = SingleConnection.getConnection();
    }

    public Login saveUser(Login user) throws Exception {

        if (user.isNew()) {
            String sql = "INSERT INTO public.tb_login(login, password, name, email) VALUES (?, ?, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setString(4, user.getEmail());

            statement.execute();
            connection.commit();

        } else {
            String sql = "UPDATE public.tb_login SET login=?, password=?, name=?, email=? WHERE id = " + user.getId() + ";";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setString(4, user.getEmail());

            statement.executeUpdate();
            connection.commit();

        }

        return this.searchUser(user.getLogin());
    }

    public Login searchUser(String login) throws SQLException {

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

}
