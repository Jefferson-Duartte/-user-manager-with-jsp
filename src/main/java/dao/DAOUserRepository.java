package dao;

import connection.SingleConnection;
import dto.SalaryGraphDTO;
import model.Login;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOUserRepository {

    private Connection connection;

    public DAOUserRepository() {
        connection = SingleConnection.getConnection();
    }

    public SalaryGraphDTO createAverageSalaryChart(Long idLoggedUser, String startDate, String endDate) throws Exception {
        String sql = "select avg (income) as average_income, profile from tb_login where user_id = ? and birth_date >= ? and birth_date <= ? group by profile";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, idLoggedUser);
        statement.setDate(2, Date.valueOf(startDate));
        statement.setDate(3, Date.valueOf(endDate));

        ResultSet resultSet = statement.executeQuery();

        List<String> profiles = new ArrayList<>();
        List<Double> averagesIncomes = new ArrayList<>();

        SalaryGraphDTO salaryGraphDTO = new SalaryGraphDTO();

        while (resultSet.next()) {
            Double averageIncome = resultSet.getDouble("average_income");
            String profile = resultSet.getString("profile");
            profiles.add(profile);
            averagesIncomes.add(averageIncome);
        }

        salaryGraphDTO.setProfiles(profiles);
        salaryGraphDTO.setAveragesIncomes(averagesIncomes);

        return salaryGraphDTO;
    }

    public SalaryGraphDTO createAverageSalaryChart(Long idUserLogged) throws Exception {
        String sql = "select avg (income) as average_income, profile from tb_login where user_id = ? group by profile";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, idUserLogged);

        ResultSet resultSet = statement.executeQuery();

        List<String> profiles = new ArrayList<>();
        List<Double> averagesIncomes = new ArrayList<>();

        SalaryGraphDTO salaryGraphDTO = new SalaryGraphDTO();

        while (resultSet.next()) {
            Double averageIncome = resultSet.getDouble("average_income");
            String profile = resultSet.getString("profile");
            profiles.add(profile);
            averagesIncomes.add(averageIncome);
        }

        salaryGraphDTO.setProfiles(profiles);
        salaryGraphDTO.setAveragesIncomes(averagesIncomes);

        return salaryGraphDTO;
    }


    public int totalPages(Long idUserLogged) throws SQLException {
        String sql = "select count(1) as total from tb_login where user_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, idUserLogged);

        ResultSet result = statement.executeQuery();
        result.next();

        double registrations = result.getDouble("total");

        double perPage = 5.0;

        int totalPages = (int) Math.ceil(registrations / perPage);

        return totalPages;
    }

    public Login saveUser(Login user, Long idUserLogged) throws Exception {

        if (user.isNew()) {
            String sql =
                    "INSERT INTO public.tb_login(login, password, name, email, user_id, profile, gender, zipCode, street, neighborhood, city, state, number, phone_number, birth_date, income) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setString(4, user.getEmail());
            statement.setLong(5, idUserLogged);
            statement.setString(6, user.getProfile());
            statement.setString(7, user.getGender());
            statement.setString(8, user.getZipCode());
            statement.setString(9, user.getStreet());
            statement.setString(10, user.getNeighborhood());
            statement.setString(11, user.getCity());
            statement.setString(12, user.getState());
            statement.setString(13, user.getNumber());
            statement.setString(14, user.getPhoneNumber());
            statement.setDate(15, user.getBirthDate());
            statement.setDouble(16, user.getIncome());

            statement.execute();
            connection.commit();

            if (user.getPhotoUser() != null && !user.getPhotoUser().isEmpty()) {
                sql = "UPDATE tb_login set profile_image_url = ?, profile_image_extension = ? WHERE login = ?";
                statement = connection.prepareStatement(sql);

                statement.setString(1, user.getPhotoUser());
                statement.setString(2, user.getPhotoUserExtension());
                statement.setString(3, user.getLogin());

                statement.execute();
                connection.commit();

            }

        } else {
            String sql = "UPDATE public.tb_login SET login=?, password=?, name=?, email=?, profile=?, gender=?, zipCode=?, street=?, neighborhood=?, city=?, state=?, number=?, phone_number=?, birth_date=?, income=? WHERE id = ?";

            PreparedStatement statement = connection.prepareStatement(sql);

            setStatment(statement, user);

            statement.executeUpdate();
            connection.commit();


            if (user.getPhotoUser() != null && !user.getPhotoUser().isEmpty()) {
                sql = "UPDATE tb_login set profile_image_url = ?, profile_image_extension = ? WHERE id = ?";
                statement = connection.prepareStatement(sql);

                statement.setString(1, user.getPhotoUser());
                statement.setString(2, user.getPhotoUserExtension());
                statement.setLong(3, user.getId());

                statement.executeUpdate();
                connection.commit();

            }

        }

        return this.searchUserByLogin(user.getLogin(), idUserLogged);
    }

    public List<Login> searchUsersByName(String name, Long idUserLogged) throws Exception {

        List<Login> users = new ArrayList<>();

        String sql = "SELECT * FROM tb_login WHERE upper(name) LIKE upper(?) AND (profile != 'ADMINISTRADOR' OR profile IS NULL) AND user_id = ? limit 5";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, "%" + name + "%");
        statement.setLong(2, idUserLogged);
        ResultSet result = statement.executeQuery();

        while (result.next()) {
            Login user = new Login();
            setDataUser(user, result);
            users.add(user);
        }

        return users;

    }

    public List<Login> getAllUsersPaginated(Long idUserLogged, int offset) throws Exception {

        List<Login> users = new ArrayList<>();

        String sql = "SELECT * FROM tb_login WHERE (profile != 'ADMINISTRADOR' OR profile IS NULL) AND user_id = ?  order by name offset ? limit 5";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, idUserLogged);
        statement.setInt(2, offset);

        ResultSet result = statement.executeQuery();

        while (result.next()) {
            Login user = new Login();
            setDataUser(user, result);
            users.add(user);
        }

        return users;

    }


    public List<Login> getAllUsers(Long idUserLogged) throws Exception {

        List<Login> users = new ArrayList<>();

        String sql = "SELECT * FROM tb_login WHERE (profile != 'ADMINISTRADOR' OR profile IS NULL) AND user_id = ? order by name limit 5";

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
            user.setPhoneNumber(result.getString("phone_number"));
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
            user.setPhotoUser(result.getString("profile_image_url")); // Verifique se este valor está correto
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
            setDataUser(user, result);
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
            setDataUser(user, result);
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

    public void setDataUser(Login user, ResultSet result) throws SQLException {
        user.setId(result.getLong("id"));
        user.setName(result.getString("name"));
        user.setEmail(result.getString("email"));
        user.setLogin(result.getString("login"));
        user.setPassword(result.getString("password"));
        user.setProfile(result.getString("profile"));
        user.setGender(result.getString("gender"));
        user.setPhotoUser(result.getString("profile_image_url"));
        user.setPhotoUserExtension(result.getString("profile_image_extension"));
        user.setZipCode(result.getString("zipCode"));
        user.setStreet(result.getString("street"));
        user.setNeighborhood(result.getString("neighborhood"));
        user.setCity(result.getString("city"));
        user.setState(result.getString("state"));
        user.setNumber(result.getString("number"));
        user.setPhoneNumber(result.getString("phone_number"));
        user.setBirthDate(result.getDate("birth_date"));
        user.setIncome(result.getDouble("income"));
    }

    public void setStatment(PreparedStatement statement, Login user) throws SQLException {
        statement.setString(1, user.getLogin());
        statement.setString(2, user.getPassword());
        statement.setString(3, user.getName());
        statement.setString(4, user.getEmail());
        statement.setString(5, user.getProfile());
        statement.setString(6, user.getGender());
        statement.setString(7, user.getZipCode());
        statement.setString(8, user.getStreet());
        statement.setString(9, user.getNeighborhood());
        statement.setString(10, user.getCity());
        statement.setString(11, user.getState());
        statement.setString(12, user.getNumber());
        statement.setString(13, user.getPhoneNumber());
        statement.setDate(14, user.getBirthDate());
        statement.setDouble(15, user.getIncome());
        statement.setLong(16, user.getId());
    }


}
