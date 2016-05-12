package Zakaz_panels;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Panel_klient_new extends JPanel {

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();// определяем размер экрана
    JLabel name_label;
    JLabel last_name_label;
    JLabel мiddle_name_label;
    JLabel country_label;
    JLabel passport_num_label;
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

    public Panel_klient_new() {
        this.setBounds(0, 0, screenSize.width, screenSize.height);
        name_label = new JLabel("Имя");
        last_name_label = new JLabel("Фамилия");
        мiddle_name_label = new JLabel("Отчество");
        country_label = new JLabel("Страна");
        passport_num_label = new JLabel("Номер паспорта");
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
        gui();
        cancel_klient.addActionListener((ActionEvent e) -> {
            this.removeAll();
            this.revalidate();
            this.repaint();
            this.updateUI();
            this.add(new Panel_klient_vibor());
        });
        insert_klient.addActionListener((ActionEvent e) -> {

        });
    }

    private void gui() {
        //this.setLayout(new BorderLayout());
        this.setLayout(null);
        int win_w = screenSize.width / 2 - 180;
        int win_h = screenSize.height / 3 - 180;
        int w = 170;
        int w_field = 150;
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

        passport_num_label.setSize(w, h);
        passport_num_label.setLocation(win_w, win_h + h * 4);
        passport_num_field.setSize(w_field, h);
        passport_num_field.setLocation(win_w + 170, win_h + h * 4);

        city_label.setSize(w, h);
        city_label.setLocation(win_w, win_h + h * 5);
        city_field.setSize(w_field, h);
        city_field.setLocation(win_w + 170, win_h + h * 5);

        street_label.setSize(w, h);
        street_label.setLocation(win_w, win_h + h * 6);
        street_field.setSize(w_field, h);
        street_field.setLocation(win_w + 170, win_h + h * 6);

        house_label.setSize(w, h);
        house_label.setLocation(win_w, win_h + h * 7);
        house_field.setSize(w_field, h);
        house_field.setLocation(win_w + 170, win_h + h * 7);

        apartment_label.setSize(w, h);
        apartment_label.setLocation(win_w, win_h + h * 8);
        apartment_field.setSize(w_field, h);
        apartment_field.setLocation(win_w + 170, win_h + h * 8);

        mobile_phone_label.setSize(w, h);
        mobile_phone_label.setLocation(win_w, win_h + h * 9);
        mobile_phone_field.setSize(w_field, h);
        mobile_phone_field.setLocation(win_w + 170, win_h + h * 9);

        email_label.setSize(w, h);
        email_label.setLocation(win_w, win_h + h * 10);
        email_field.setSize(w_field, h);
        email_field.setLocation(win_w + 170, win_h + h * 10);

        cancel_klient.setSize(w, h);
        cancel_klient.setLocation(win_w, win_h + h * 11 + 30);
        insert_klient.setSize(w_field, h);
        insert_klient.setLocation(win_w + 170, win_h + h * 11 + 30);

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

    }

}
