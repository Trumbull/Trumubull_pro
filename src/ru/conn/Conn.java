package ru.conn;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Conn {

    private final String url;
    public final String user;
    public final String password;

    public Conn() {
        String line[] = new String[3];
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream("D:\\Connect.txt"), StandardCharsets.UTF_8))) {
            int id = 0;
            while ((line[id] = reader.readLine()) != null) {
                //  System.out.println(line[id]);
                id++;
            }
        } catch (IOException e) {
            System.out.println("Файл с настрйоками не найден!");
        }
        /*
        url = "jdbc:mysql://localhost:3306/mydb?useUnicode=true&characterEncoding=utf8";
        user = "root";
        password = "";
         */
        url = line[0];
        user = line[1];
        password = line[2];
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
