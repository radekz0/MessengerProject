
import com.google.gson.Gson;
import org.apache.log4j.BasicConfigurator;
import java.util.*;

import static spark.Spark.*;

public class SparkRestService {
    public static void main(String[] args) {
        BasicConfigurator.configure();

        DbController controller = new DbController();
        List<User> usersOnline = Collections.synchronizedList(new ArrayList<>());

        //Adding user to the db.
        post("/register", (req,res) -> {
            res.type("application/json");
            User user = new Gson().fromJson(req.body(), User.class);
            controller.addUser(user);
            return new Gson().toJson(new StandardResponse(Status.SUCCESS));
        });

        //Logging in.
        get("/login/:username/:password", (req,res) -> {
            res.type("application/json");
            try{
                User user = controller.userLogging(req.params(":username"), req.params(":password"));
                if(user == null){
                    return new Gson().toJson(new StandardResponse(Status.Error));
                }
                else{
                    usersOnline.add(user);   //Adding user to usersOnline List if username and password exist in database.
                }

            }catch(Exception e){
                e.getMessage();
            }

            return new Gson().toJson(new StandardResponse(Status.SUCCESS));

        });

        //Adding messages to the db.
        post("/message", (req,res) -> {
           res.type("application/json");
           Message message = new Gson().fromJson(req.body(), Message.class);
           controller.addMessage(message);
           return new Gson().toJson(new StandardResponse(Status.SUCCESS));
        });
    }
}
