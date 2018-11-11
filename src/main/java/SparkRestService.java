
import com.google.gson.Gson;
import org.apache.log4j.BasicConfigurator;
import java.util.*;

import static spark.Spark.*;

public class SparkRestService {
    public static void main(String[] args) {
        BasicConfigurator.configure();

        DbController controller = new DbController();
        List<User> usersOnline = Collections.synchronizedList(new ArrayList<>());

        //Users
        post("/register", (req,res) -> {
            res.type("application/json");
            User user = new Gson().fromJson(req.body(), User.class);
            controller.addUser(user);
            return new Gson().toJson(new StandardResponse(Status.SUCCESS));
        });

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

    }
}
