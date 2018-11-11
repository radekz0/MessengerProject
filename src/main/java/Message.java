public class Message {
    private int id;
    private String text;
    private int userId;

    Message(int id, String text, int userId){
        this.id = id;
        this.text = text;
        this.userId = userId;
    }

    public int getId(){
        return id;
    }

    public String getText(){
        return text;
    }

    public int getUserId(){
        return userId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}