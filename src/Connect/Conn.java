package Connect;

public class Conn {

    private String url;
    public final String user;
    public final String password;

    public Conn() {
        url = "jdbc:mysql://localhost:3306/mydb";
        user = "root";
        password = "";
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

}
