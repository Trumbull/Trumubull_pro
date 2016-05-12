package Zakaz_panels;

import Connect.Conn;
import Interface.Mesto;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

//Добавить Заказ
public class Panel_begin extends JPanel {

    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox_kuda_gorod;
    private javax.swing.JComboBox<String> jComboBox_klass_obsl;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JSpinner jSpinner_1;
    private javax.swing.JSpinner jSpinner_mld;
    private javax.swing.JComboBox<String> jComboBox_otk_gorod;
    private javax.swing.JTextField jTextField9;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();// определяем размер экрана
    private javax.swing.JComboBox<String> jComboBox_otk_strana;
    private javax.swing.JComboBox<String> jComboBox_kuda_strana;

    public Panel_begin() throws SQLException {
        String strana[] = new String[239];
        setBounds(0, 0, screenSize.width, screenSize.height);
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jComboBox_kuda_gorod = new javax.swing.JComboBox<>();
        jSpinner1 = new javax.swing.JSpinner();
        jSpinner_1 = new javax.swing.JSpinner();
        jComboBox_klass_obsl = new javax.swing.JComboBox<>();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jSpinner_mld = new javax.swing.JSpinner();
        jLabel9 = new javax.swing.JLabel();
        jComboBox_otk_gorod = new JComboBox();
        jComboBox_otk_strana = new JComboBox();
        jComboBox_kuda_strana = new JComboBox();
        jLabel10 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        //jButton1.setBounds(screenSize.width - 68 / 2, (screenSize.height - 68) / 2, 68, 68);
        jLabel8.setText("Взрослый");
        jLabel1.setText("Ребенок");
        jLabel10.setText("Младенец");
        jLabel2.setText("Откуда");
        jLabel3.setText("Куда");
        jLabel4.setText("Класс Обслуживания");
        jLabel5.setText("Класс обслуживания");
        jButton1.setText("Отмена");
        jButton2.setText("Далее");
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
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        gui(strana, url, user, password);

    }

    public void gui(String[] strana, String url, String user, String password) {
        this.setLayout(null);
        ArrayList<String> gorod = new ArrayList<>(); //динамический массив
        ArrayList<String> IATA = new ArrayList<>();
        ArrayList<String> ICAO = new ArrayList<>();

        ArrayList<String> gorod_kuda = new ArrayList<>(); //динамический массив
        ArrayList<String> IATA_kuda = new ArrayList<>();
        ArrayList<String> ICAO_kuda = new ArrayList<>();
        int win_w = screenSize.width / 2 - 180;
        int win_h = screenSize.height / 3 - 180;
        int w = 200;
        int w_field = 200;
        int h = 30;
        //Взрослый

        jLabel8.setSize(w, h);
        jLabel8.setLocation(win_w, win_h);
        jSpinner_1.setSize(w_field, h);
        jSpinner_1.setLocation(win_w + w, win_h);

        //Ребенок
        jLabel1.setSize(w, h);
        jLabel1.setLocation(win_w, win_h + 30);
        jSpinner1.setSize(w_field, h);

        jSpinner1.setLocation(win_w + w, win_h + 30);

        //Младенец
        jLabel10.setSize(w, h);
        jLabel10.setLocation(win_w, win_h + 60);
        jSpinner_mld.setSize(w_field, h);
        jSpinner_mld.setLocation(win_w + w, win_h + 60);

        //Откуда
        jLabel2.setSize(w, h);
        jLabel2.setLocation(win_w, win_h + 90);
        ComBox_otk(strana, url, user, password, gorod, IATA, ICAO);
        jComboBox_otk_strana.setSize(w, h);
        jComboBox_otk_strana.setLocation(win_w, win_h + 120);
        jComboBox_otk_gorod.setSize(w_field, h);
        jComboBox_otk_gorod.setLocation(win_w + w, win_h + 120);

        //Куда
        jLabel3.setSize(w, h);
        jLabel3.setLocation(win_w, win_h + 150);
        Combox_kuda(strana, url, user, password, gorod_kuda, IATA_kuda, ICAO_kuda, gorod, IATA, ICAO);
        jComboBox_kuda_strana.setSize(w_field, h);
        jComboBox_kuda_strana.setLocation(win_w, win_h + 180);
        jComboBox_kuda_gorod.setSize(w_field, h);
        jComboBox_kuda_gorod.setLocation(win_w + w, win_h + 180);

        //Класс обслуживания
        jLabel4.setSize(w, h);
        jLabel4.setLocation(win_w, win_h + 210);
        jComboBox_klass_obsl.setSize(w_field, h);
        jComboBox_klass_obsl.setLocation(win_w + w, win_h + 210);

        jButton1.setSize(w_field, h);
        jButton1.setLocation(win_w, win_h + 290);
        jButton2.setSize(w_field, h);
        jButton2.setLocation(win_w + w, win_h + 290);

        this.add(jLabel8);
        this.add(jSpinner_1);

        this.add(jLabel1);
        this.add(jSpinner1);

        this.add(jLabel10);
        this.add(jSpinner_mld);

        this.add(jLabel2);
        this.add(jComboBox_otk_strana);
        this.add(jComboBox_otk_gorod);

        this.add(jLabel3);
        this.add(jComboBox_kuda_strana);
        this.add(jComboBox_kuda_gorod);

        this.add(jLabel4);
        this.add(jComboBox_klass_obsl);

        this.add(jButton1);
        this.add(jButton2);
    }

    public void ComBox_otk(String[] strana, String url, String user, String password, ArrayList<String> gorod, ArrayList<String> IATA, ArrayList<String> ICAO) {
        for (int i = 0; i < strana.length; i++) {
            jComboBox_otk_strana.addItem(strana[i]);
        }
        jComboBox_otk_strana.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {

                String flag = (String) jComboBox_otk_strana.getSelectedItem();
                jComboBox_otk_gorod.removeAllItems();
                try (Connection c = DriverManager.getConnection(url, user, password)) {
                    int id = 0;
                    c.setAutoCommit(false);
                    c.setReadOnly(false);
                    try (PreparedStatement ps = c.prepareStatement("SELECT  * FROM `strana` WHERE Nazvanie=?")) {
                        ps.setString(1, flag);
                        try (ResultSet rs = ps.executeQuery()) {
                            while (rs.next()) {
                                id = rs.getInt("Strana_id");
                            }
                        }
                        ps.close();
                    }
                    try (PreparedStatement ps = c.prepareStatement("SELECT  * FROM `ajeroport` WHERE Strana_id=?")) {
                        ps.setInt(1, id);
                        try (ResultSet rs = ps.executeQuery()) {
                            while (rs.next()) {
                                gorod.add(rs.getString("Naznanie"));
                                IATA.add(rs.getString("IATA"));
                                ICAO.add(rs.getString("ICAO"));
                            }
                        }
                        ps.close();
                    }
                    c.commit();
                    c.commit();
                    c.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < gorod.size(); i++) {

                    jComboBox_otk_gorod.addItem(gorod.get(i) + " (" + IATA.get(i) + "," + ICAO.get(i) + ")");
                }
                gorod.removeAll(gorod);
                IATA.removeAll(IATA);
                ICAO.removeAll(ICAO);
            }
        }
        );
    }

    public void Combox_kuda(String[] strana, String url, String user, String password, ArrayList<String> gorod_kuda, ArrayList<String> IATA_kuda, ArrayList<String> ICAO_kuda, ArrayList<String> gorod, ArrayList<String> IATA, ArrayList<String> ICAO) {
        for (int i = 0; i < strana.length; i++) {
            jComboBox_kuda_strana.addItem(strana[i]);
        }
        jComboBox_kuda_strana.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                jComboBox_kuda_gorod.removeAllItems();
                String flag = (String) jComboBox_kuda_strana.getSelectedItem();
                try (Connection c = DriverManager.getConnection(url, user, password)) {
                    int id = 0;
                    c.setAutoCommit(false);
                    c.setReadOnly(false);
                    try (PreparedStatement ps = c.prepareStatement("SELECT  * FROM `strana` WHERE Nazvanie=?")) {
                        ps.setString(1, flag);
                        try (ResultSet rs = ps.executeQuery()) {
                            while (rs.next()) {
                                id = rs.getInt("Strana_id");
                            }
                        }
                        ps.close();
                    }
                    System.out.println("Strana_id: " + id);
                    try (PreparedStatement ps = c.prepareStatement("SELECT  * FROM `ajeroport` WHERE Strana_id=?")) {
                        ps.setInt(1, id);
                        try (ResultSet rs = ps.executeQuery()) {
                            while (rs.next()) {
                                gorod_kuda.add(rs.getString("Naznanie"));
                                IATA_kuda.add(rs.getString("IATA"));
                                ICAO_kuda.add(rs.getString("ICAO"));
                            }
                        }
                        ps.close();
                    }
                    c.commit();
                    c.commit();
                    c.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < gorod_kuda.size(); i++) {

                    jComboBox_kuda_gorod.addItem(gorod_kuda.get(i) + " (" + IATA_kuda.get(i) + "," + ICAO_kuda.get(i) + ")");
                }
                gorod_kuda.removeAll(gorod);
                IATA_kuda.removeAll(IATA);
                ICAO_kuda.removeAll(ICAO);

            }
        }
        );
    }

}
