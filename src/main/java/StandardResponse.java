
import com.google.gson.JsonElement;

public class StandardResponse {
    private Status status;
    private JsonElement jsonElement;

    public StandardResponse(Status status){
        this.status = status;
    }

    public StandardResponse(Status status, JsonElement jsonElement){
        this.jsonElement = jsonElement;
    }

}
