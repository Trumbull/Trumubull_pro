package zakaz.panels;

import conn.Conn;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

public class PanelKlientNew extends JPanel {

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();// определяем размер экрана

    private ZakazP p = new ZakazP();
    private String name = p.getName();
    private String last_name = p.getLastname();
    private String мiddle_name = p.getМiddle_name();
    private int id_country = p.getCountry();
    private String passport_num = p.getPassport_num();
    private String city = p.getCity();
    private String street = p.getStreet();
    private String house = p.getHouse();
    private String apartment = p.getApartment();
    private String mobile_phone = p.getMobile_phone();
    private String email = p.getEmail();
    private String date_sql = p.getDate_sql();
    private int id = p.getId();

    public PanelKlientNew() throws SQLException, ParseException {
        JLabel name_label;
        JLabel last_name_label;
        JLabel мiddle_name_label;
        JLabel country_label;
        JLabel passport_num_label;
        JLabel datу_rog_label;
        JLabel city_label;
        JLabel street_label;
        JLabel house_label;
        JLabel apartment_label;
        JLabel mobile_phone_label;
        JLabel email_label;
        JTextField name_field;
        JTextField last_name_field;
        JTextField мiddle_name_field;
        JComboBox<String> country_combox;
        JTextField passport_num_field;
        JTextField city_field;
        JTextField street_field;
        JTextField house_field;
        JTextField apartment_field;
        JTextField mobile_phone_field;
        JTextField email_field;
        JButton insert_klient;
        JButton cancel_klient;
        String country_id[] = new String[239];

        this.setBounds(0, 0, screenSize.width, screenSize.height);
        name_label = new JLabel("Имя");
        last_name_label = new JLabel("Фамилия");
        мiddle_name_label = new JLabel("Отчество");
        country_label = new JLabel("Страна");
        passport_num_label = new JLabel("Номер паспорта");
        datу_rog_label = new JLabel("Дата рождения");
        city_label = new JLabel("Город");
        street_label = new JLabel("Улица");
        house_label = new JLabel("Дом");
        apartment_label = new JLabel("Квартира");
        mobile_phone_label = new JLabel("Моб. телефон");
        email_label = new JLabel("E-mail");
        name_field = new JTextField();
        last_name_field = new JTextField();
        мiddle_name_field = new JTextField();
        country_combox = new JComboBox<>();
        passport_num_field = new JTextField();
        city_field = new JTextField();
        street_field = new JTextField();
        house_field = new JTextField();
        apartment_field = new JTextField();
        mobile_phone_field = new JTextField();
        email_field = new JTextField();
        cancel_klient = new JButton("Отмена");
        insert_klient = new JButton("Добавить");
        Conn conn = new Conn();
        String url = conn.getUrl();
        String user = conn.getUser();
        String password = conn.getPassword();

        try (Connection c = DriverManager.getConnection(url, user, password)) {
            int id = 0;
            c.setAutoCommit(false);
            c.setReadOnly(false);
            try (PreparedStatement ps = c.prepareStatement("SELECT  * FROM `strana`")) {
                try (ResultSet rs = ps.executeQuery()) {
                    int r = 0;
                    while (rs.next()) {
                        country_id[r] = rs.getString("Nazvanie");
                        r++;
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

        this.setLayout(null);
        int win_w = screenSize.width / 2 - 180;
        int win_h = screenSize.height / 3 - 180;
        int w = 170;
        int w_field = 200;
        int h = 30;

        name_label.setSize(w, h);
        name_label.setLocation(win_w, win_h);
        name_field.setSize(w_field, h);
        name_field.setLocation(win_w + 170, win_h);

        last_name_label.setSize(w, h);
        last_name_label.setLocation(win_w, win_h + h);
        last_name_field.setSize(w_field, h);
        last_name_field.setLocation(win_w + 170, win_h + h);

        мiddle_name_label.setSize(w, h);
        мiddle_name_label.setLocation(win_w, win_h + h * 2);
        мiddle_name_field.setSize(w_field, h);
        мiddle_name_field.setLocation(win_w + 170, win_h + h * 2);

        country_label.setSize(w, h);
        country_label.setLocation(win_w, win_h + h * 3);
        country_combox.setSize(w_field, h);

        country_combox.setLocation(win_w + 170, win_h + h * 3);
        for (int i = 0; i < country_id.length; i++) {
            country_combox.addItem(country_id[i]);
        }

        passport_num_label.setSize(w, h);
        passport_num_label.setLocation(win_w, win_h + h * 4);
        passport_num_field.setSize(w_field, h);
        passport_num_field.setLocation(win_w + 170, win_h + h * 4);

        datу_rog_label.setSize(w, h);
        datу_rog_label.setLocation(win_w, win_h + h * 5);

        MaskFormatter mf = new MaskFormatter("##-##-####");
        JFormattedTextField date_rog_formatted_field = new JFormattedTextField(mf);
        mf.setPlaceholderCharacter('_');

        date_rog_formatted_field.setSize(80, h);
        date_rog_formatted_field.setLocation(win_w + 170, win_h + h * 5);
        date_rog_formatted_field.setHorizontalAlignment(JFormattedTextField.RIGHT);

        city_label.setSize(w, h);

        city_label.setLocation(win_w, win_h + h * 6);
        city_field.setSize(w_field, h);

        city_field.setLocation(win_w + 170, win_h + h * 6);

        street_label.setSize(w, h);
        street_label.setLocation(win_w, win_h + h * 7);
        street_field.setSize(w_field, h);
        street_field.setLocation(win_w + 170, win_h + h * 7);

        house_label.setSize(w, h);
        house_label.setLocation(win_w, win_h + h * 8);
        house_field.setSize(w_field, h);
        house_field.setLocation(win_w + 170, win_h + h * 8);

        apartment_label.setSize(w, h);
        apartment_label.setLocation(win_w, win_h + h * 9);
        apartment_field.setSize(w_field, h);
        apartment_field.setLocation(win_w + 170, win_h + h * 9);

        mobile_phone_label.setSize(w, h);
        mobile_phone_label.setLocation(win_w, win_h + h * 10);
        mobile_phone_field.setSize(w_field, h);
        mobile_phone_field.setLocation(win_w + 170, win_h + h * 10);

        email_label.setSize(w, h);
        email_label.setLocation(win_w, win_h + h * 11);
        email_field.setSize(w_field, h);
        email_field.setLocation(win_w + 170, win_h + h * 11);

        cancel_klient.setSize(w, h);
        cancel_klient.setLocation(win_w, win_h + h * 12 + 30);
        insert_klient.setSize(w_field, h);
        insert_klient.setLocation(win_w + 170, win_h + h * 12 + 30);

        this.add(name_label);
        this.add(name_field);
        this.add(last_name_label);
        this.add(last_name_field);

        this.add(мiddle_name_label);
        this.add(мiddle_name_field);
        this.add(country_label);
        this.add(country_combox);

        this.add(passport_num_label);
        this.add(passport_num_field);
        this.add(datу_rog_label);
        this.add(date_rog_formatted_field);

        this.add(city_label);
        this.add(city_field);
        this.add(street_label);
        this.add(street_field);

        this.add(house_label);
        this.add(house_field);
        this.add(apartment_label);
        this.add(apartment_field);

        this.add(mobile_phone_label);
        this.add(mobile_phone_field);
        this.add(email_label);
        this.add(email_field);

        this.add(cancel_klient);
        this.add(insert_klient);

        cancel_klient.addActionListener(
                (ActionEvent e) -> {
                    this.removeAll();
                    this.revalidate();
                    this.repaint();
                    this.updateUI();
                    this.add(new PanelViborKlienta());
                });
        insert_klient.addActionListener((ActionEvent e) -> {

            name = name_field.getText();
            last_name = last_name_field.getText();
            мiddle_name = мiddle_name_field.getText();
            passport_num = passport_num_field.getText();
            city = city_field.getText();
            street = street_field.getText();
            house = house_field.getText();
            apartment = apartment_field.getText();
            mobile_phone = mobile_phone_field.getText();
            email = email_field.getText();
            id_country = (int) country_combox.getSelectedIndex();
            String data = date_rog_formatted_field.getText();
            date_sql_formatted(data);
            insert_new_klient(url, user, password);
            this.removeAll();
            this.revalidate();
            this.repaint();
            this.updateUI();
            this.add(new PanelViborKlienta());
            /*
            System.out.println(date_sql);
            System.out.println(name);
            System.out.println(last_name);
            System.out.println(мiddle_name);
            System.out.println(id_country+1);
            System.out.println(passport_num);
            System.out.println(city);
            System.out.println(street);
            System.out.println(house);
            System.out.println(apartment);
            System.out.println(mobile_phone);
            System.out.println(email);
             */
        }
        );
    }

    public void date_sql_formatted(String data) {
        String year;
        String month;
        String date;
        year = "" + data.charAt(6) + data.charAt(7) + data.charAt(8) + data.charAt(9);
        month = "" + data.charAt(3) + data.charAt(4);
        date = "" + data.charAt(0) + data.charAt(1);
        date_sql = year + "-" + month + "-" + date;
    }

    public void insert_new_klient(String url, String user, String password) {
        try (Connection c = DriverManager.getConnection(url, user, password)) {
            c.setAutoCommit(false);
            c.setReadOnly(false);
            try (PreparedStatement ps = c.prepareStatement("SELECT  * FROM `klient`")) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        id = rs.getInt("Klient_id");

                    }
                }
                ps.close();
            }

            try (PreparedStatement ps = c.prepareStatement("INSERT INTO `klient` (`Klient_id`, `Imja`, `Otshestvo`, `Familiya`, `Mili`, `Nomer_pasporta`, `Data_rogdeniya`, `Strana_id`) VALUES (?, ?, ?, ?, ?, ?, ?, ?);")) {
                ps.setInt(1, id + 1);
                ps.setString(2, name);
                ps.setString(3, мiddle_name);
                ps.setString(4, last_name);
                ps.setInt(5, 0);
                ps.setString(6, passport_num);
                ps.setString(7, date_sql);
                ps.setInt(8, id_country + 1);
                ps.executeUpdate();

                ps.close();
            }
            try (PreparedStatement ps2 = c.prepareStatement("UPDATE `rejs_gorod` SET `Klient_id` = ? WHERE `rejs_gorod`.`id` = 1;")) {
                ps2.setInt(1, id + 1);

                ps2.executeUpdate();
            }
            c.commit();
            c.commit();
            c.close();

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
