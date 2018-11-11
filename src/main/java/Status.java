
public enum Status {
    SUCCESS("SUCCESS"),
    Error("Error");
    private final String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus(){
        return status;
    }
}
