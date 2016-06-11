package sql;

import conn.Conn;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Strana {
private String strana[] = new String[239];
    public Strana() {
        Conn conn = new Conn();
        String url = conn.getUrl();
        String user = conn.getUser();
        String password = conn.getPassword();
        try (Connection c = DriverManager.getConnection(url, user, password)) {
            int i = 0;
            c.setAutoCommit(false);
            c.setReadOnly(false);
            try (PreparedStatement ps = c.prepareStatement("SELECT  * FROM `strana`")) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        strana[i] = rs.getString("Nazvanie");
                        i++;
                    }
                }
                ps.close();
            }
            c.commit();
            c.commit();
            c.close();
        } catch (SQLException se) {
        } catch (Exception e) {
        }
    }

    /**
     * @return the strana
     */
    public String[] getStrana() {
        return strana;
    }
}
