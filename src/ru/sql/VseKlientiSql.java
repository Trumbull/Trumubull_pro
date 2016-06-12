package ru.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import ru.conn.Conn;

public class VseKlientiSql {

    private Map<Integer, String> codes = new HashMap<>();
    private ArrayList<String> imja = new ArrayList<>();
    private ArrayList<String> familiya = new ArrayList<>();
    private ArrayList<String> otchestvo = new ArrayList<>();
    private ArrayList<String> mili = new ArrayList<>();
    private ArrayList<String> data_rog = new ArrayList<>();
    private ArrayList<Integer> id_flag = new ArrayList<>();

    public VseKlientiSql() {
        Conn conn = new Conn();
        String url = conn.getUrl();
        String user = conn.getUser();
        String password = conn.getPassword();

        try (Connection c = DriverManager.getConnection(url, user, password)) {
            c.setAutoCommit(false);
            c.setReadOnly(false);
            int id = 0;
            try (PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM `klient`")) {
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        id = rs.getInt("COUNT(*)");
                    }
                }
                ps.close();
            }
            try (PreparedStatement ps = c.prepareStatement("SELECT * FROM `strana`")) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        int strana_id = rs.getInt("Strana_id");
                        String nazv = rs.getString("Nazvanie");

                        codes.put(strana_id, nazv);
                    }
                }
                ps.close();
            }
            try (PreparedStatement ps = c.prepareStatement("SELECT  * FROM `klient`")) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        imja.add(rs.getString("Imja"));
                        familiya.add(rs.getString("Familiya"));
                        otchestvo.add(rs.getString("Otshestvo"));
                        mili.add(rs.getString("Mili"));
                        data_rog.add(rs.getString("Data_rogdeniya"));
                        id_flag.add(rs.getInt("Strana_id"));
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

 
    public Map<Integer, String> getCodes() {
        return codes;
    }


    public ArrayList<String> getImja() {
        return imja;
    }


    public ArrayList<String> getFamiliya() {
        return familiya;
    }


    public ArrayList<String> getOtchestvo() {
        return otchestvo;
    }


    public ArrayList<String> getMili() {
        return mili;
    }


    public ArrayList<String> getData_rog() {
        return data_rog;
    }


    public ArrayList<Integer> getId_flag() {
        return id_flag;
    }
}
