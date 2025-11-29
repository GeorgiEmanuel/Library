package repository.user;

import model.Book;
import model.User;
import model.builder.BookBuilder;
import model.builder.UserBuilder;
import model.validator.Notification;
import repository.security.RightsRolesRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static database.Constants.Tables.USER;

public class UserRepositoryMySQL implements UserRepository{

    private final Connection connection;
    private final RightsRolesRepository rightsRolesRepository;

    public UserRepositoryMySQL(Connection connection, RightsRolesRepository rightsRolesRepository){
        this.connection = connection;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM user;";
        List<User> users = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                users.add(getUserFromResultSet(resultSet));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public Notification<User> findByUsernameAndPassword(String username, String password) {

        Notification<User> findByUsernameAndPasswordNotification = new Notification<>();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("Select * from "  + USER + " where `username` = ? and `password` = ?", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet userResultSet = preparedStatement.executeQuery();
            if (userResultSet.next()){
                long userId = userResultSet.getLong(1);
                User user = new UserBuilder()
                        .setId(userId)
                        .setUsername(userResultSet.getString("username"))
                        .setPassword(userResultSet.getString("password"))
                        .setRoles(rightsRolesRepository.findRolesForUser(userResultSet.getLong("id")))
                        .build();
                findByUsernameAndPasswordNotification.setResult(user);
            }else {
               findByUsernameAndPasswordNotification.addError("Invalid username or password");
               return findByUsernameAndPasswordNotification;
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
            findByUsernameAndPasswordNotification.addError("Something is wrong with the Database!");

        }
        return findByUsernameAndPasswordNotification;
    }

    @Override
    public Notification<User> save(User user) {
        Notification<User> saveNotification = new Notification<>();
        String username = user.getUsername();
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO user values (null, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setString(1, username);
            insertUserStatement.setString(2, user.getPassword());

            if (!existsByUsername(username)) {

                int rowsInserted = insertUserStatement.executeUpdate();
                if (rowsInserted == 0){
                    saveNotification.addError("User insert failed!");
                    return saveNotification;
                }
                ResultSet rs = insertUserStatement.getGeneratedKeys();
                if (rs.next()) {

                    long userId = rs.getLong(1);
                    user.setId(userId);
                    rightsRolesRepository.addRolesToUser(user, user.getRoles());
                    saveNotification.setResult(user);

                } else {
                    saveNotification.addError("User id generation failed!");
                }

            } else {
                saveNotification.addError("Email is already taken");
                return saveNotification;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            saveNotification.addError("Something is wrong with the Database!");
        }
        return saveNotification;
    }
    @Override
    public Notification<Boolean> delete(String username){
        Notification<Boolean> deleteNotification = new Notification<>();
        if (!existsByUsername(username)){
            deleteNotification.setResult(Boolean.FALSE);
            deleteNotification.addError("User does not exist !");
            return deleteNotification;
        }
        try {
            PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM " + USER + " WHERE username = ?");
            deleteStatement.setString(1, username);
            int rowsDeleted = deleteStatement.executeUpdate();
            if (rowsDeleted == 1) {
                deleteNotification.setResult(Boolean.TRUE);
            } else {
                deleteNotification.setResult(Boolean.FALSE);
                deleteNotification.addError("User delete failed");
            }

        }catch (SQLException e){
            e.printStackTrace();
            deleteNotification.setResult(Boolean.FALSE);
            deleteNotification.addError("Something is wrong with the Database");
        }
        return deleteNotification;


    }
    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from user where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean existsByUsername(String email) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("Select * from " + USER  + " where username = ?");

            preparedStatement.setString(1, email);
            ResultSet userResultSet = preparedStatement.executeQuery();
            return userResultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Optional<User> findById(Long id){
        Optional<User> user = Optional.empty();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + USER + " WHERE id = ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user =  Optional.of(getUserFromResultSet(resultSet));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return user;
    }



    @Override
    public User findByUsername(String username)  {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("Select * from " + USER + " where username = ?");
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return getUserFromResultSet(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException{
        return new UserBuilder()
                .setId(resultSet.getLong("id"))
                .setUsername(resultSet.getString("username"))
                .build();
    }

    @Override
    public List<User> findAllEmployees(){
        List<User> employeeList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT u.id, u.username " +
                        "FROM user u JOIN user_role ur ON u.id = ur.user_id " +
                        "WHERE ur.role_id = ?");
            preparedStatement.setLong(1, 2L);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                employeeList.add(getUserFromResultSet(resultSet));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return employeeList;

    }
}
