package dao;

import connection.SingleConnection;
import model.Login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DAOLoginRepositoty {

    private Connection connection;

    public DAOLoginRepositoty() {
        connection = SingleConnection.getConnection();
    }

    public boolean validateAuthentication(Login login) throws Exception {

        String sql = "select * from tb_login where upper(login) = upper(?) and upper(password) = upper(?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, login.getLogin());
        statement.setString(2, login.getPassword());

        ResultSet result = statement.executeQuery();

        return result.next();

    }

}
