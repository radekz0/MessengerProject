import java.sql.*;

public class DbController {
    private Connection connection;
    private PreparedStatement preparedStatement;

    DbController(){
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:Messenger.db");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Adding registering user to a database.
    public void addUser(User user){
        String query = "INSERT INTO Users VALUES(?,?,?)";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,user.getId());
            preparedStatement.setString(2,user.getUsername());
            preparedStatement.setString(3,user.getPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Login method that checks if a username and password is in database and returns user object.
    public User userLogging(String username, String password){
        User validUser;
        String userId, name, pass;
        String query = "SELECT * FROM Users WHERE username = (?) AND password = (?)";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);
            ResultSet resultSet = preparedStatement.executeQuery();

            userId = resultSet.getString(1);
            name = resultSet.getString(2);
            pass = resultSet.getString(3);

            validUser = new User(userId,name,pass);

            return validUser;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


}