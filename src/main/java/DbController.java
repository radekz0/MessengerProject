import java.sql.*;
import java.util.ArrayList;

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
            preparedStatement.setInt(1,user.getId());
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
        int userId;
        String name, pass;
        String query = "SELECT * FROM Users WHERE username = (?) AND password = (?)";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);
            ResultSet resultSet = preparedStatement.executeQuery();

            userId = resultSet.getInt(1);
            name = resultSet.getString(2);
            pass = resultSet.getString(3);

            validUser = new User(userId,name,pass);

            return validUser;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    //Adding user's message to the db.
    public void addMessage(Message message){
       String query = "INSERT INTO Messages VALUES (?,?,?)";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, message.getId());
            preparedStatement.setString(2,message.getText());
            preparedStatement.setInt(3,message.getUserId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //Getting messages from a db and putting it in an ArrayList for displaying as Json.
    public ArrayList<String> getMessages(){
        String text, userId, output;
        ArrayList<String> arrayList = new ArrayList<>();
        String query = "SELECT * FROM Messages";

        try {
            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                text = resultSet.getString(2);
                userId = resultSet.getString(3);
                output = (userId + ": " + text);
                arrayList.add(output);
            }
            return arrayList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
