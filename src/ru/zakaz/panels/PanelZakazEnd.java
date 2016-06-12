package ru.zakaz.panels;

import ru.conn.Conn;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PanelZakazEnd extends JPanel {

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();// определяем размер экрана
    private int flag = 0;
    private ZakazP p = new ZakazP();
    private int adult = p.getAdult();
    private int child = p.getChild();
    private int baby = p.getBaby();
    private int id = p.getId();
    private int cena_rows[] = new int[2];
    private String nomer_rejs = p.getNomer_rejs();
    private int klass_obsluzhivanija_id = p.getKlass_obsluzhivanija_id();
    private int cena = p.getCena();
    private JTextField price_field = new JTextField();
    private int adult_id[] = new int[5];
    private int child_id[] = new int[5];
    private int vid_bileta = 0;
    private int mili_begin = 0;
    private JTextField person_adult_1_field;
    private JTextField person_adult_2_field;
    private JTextField person_adult_3_field;
    private JTextField person_adult_4_field;
    private JTextField person_adult_5_field;
    private int mili[] = new int[5];
    private JTextField person_child_1_field;
    private JTextField person_child_2_field;
    private JTextField person_child_3_field;
    private JTextField person_child_4_field;
    private JTextField person_child_5_field;

    public PanelZakazEnd() {
        this.setBounds(0, 0, screenSize.width, screenSize.height);
        this.setLayout(null);
        JLabel nomer_zakaza_label;
        JLabel rejs_label;
        JLabel kolichestvo_biletov_label;
        JLabel klass_obsluzhivanija_label;
        JLabel vid_bileta_label;
        JLabel price_label;
        int id_element_zakaza_id = 0;
        int adult_klient_id[] = new int[5];
        int child_klient_id[] = new int[5];

        String familiya_adult[] = new String[5];
        String otche_adult[] = new String[5];
        String name_adult[] = new String[5];
        String familiya_child[] = new String[5];
        String name_child[] = new String[5];
        String otche_child[] = new String[5];

        JLabel person_adult_1_label;
        JLabel person_adult_2_label;
        JLabel person_adult_3_label;
        JLabel person_adult_4_label;
        JLabel person_adult_5_label;

        JLabel person_child_1_label;
        JLabel person_child_2_label;
        JLabel person_child_3_label;
        JLabel person_child_4_label;
        JLabel person_child_5_label;

        person_adult_1_field = new JTextField();
        person_adult_2_field = new JTextField();
        person_adult_3_field = new JTextField();
        person_adult_4_field = new JTextField();
        person_adult_5_field = new JTextField();

        person_child_1_field = new JTextField();
        person_child_2_field = new JTextField();
        person_child_3_field = new JTextField();
        person_child_4_field = new JTextField();
        person_child_5_field = new JTextField();

        JTextField nomer_zakaz_field = new JTextField();
        JTextField rejs_field = new JTextField();
        JTextField kolichestvo_biletov_field = new JTextField();
        JTextField klass_obsluzhivanija_field = new JTextField();
        JComboBox<String> vid_bileta_box = new JComboBox<>();

        JButton b1 = new JButton("Добавить");
        JButton b2 = new JButton("Добавить");
        JButton b3 = new JButton("Добавить");
        JButton b4 = new JButton("Добавить");
        JButton b5 = new JButton("Добавить");
        JButton b6 = new JButton("Добавить");
        JButton b7 = new JButton("Добавить");
        JButton b8 = new JButton("Добавить");
        JButton b9 = new JButton("Добавить");
        JButton b10 = new JButton("Добавить");
        JButton drop = new JButton("Удалить");
        JButton next = new JButton("Далее");
        JButton cancel = new JButton("Отмена");

        nomer_zakaza_label = new JLabel("Номер заказа");
        rejs_label = new JLabel("Номер рейса");
        kolichestvo_biletov_label = new JLabel("Количество билетов");
        klass_obsluzhivanija_label = new JLabel("Класс обслуживания");
        vid_bileta_label = new JLabel("Вид билета");
        price_label = new JLabel("Итоговая цена");

        person_adult_1_label = new JLabel("Взрослый №1");
        person_adult_2_label = new JLabel("Взрослый №2");
        person_adult_3_label = new JLabel("Взрослый №3");
        person_adult_4_label = new JLabel("Взрослый №4");
        person_adult_5_label = new JLabel("Взрослый №5");

        person_child_1_label = new JLabel("Ребенок №1");
        person_child_2_label = new JLabel("Ребенок №2");
        person_child_3_label = new JLabel("Ребенок №3");
        person_child_4_label = new JLabel("Ребенок №4");
        person_child_5_label = new JLabel("Ребенок №5");

        Conn conn = new Conn();
        String url = conn.getUrl();
        String user = conn.getUser();
        String password = conn.getPassword();

        try (Connection c = DriverManager.getConnection(url, user, password)) {
            c.setAutoCommit(false);
            c.setReadOnly(false);
            try (PreparedStatement ps = c.prepareStatement("SELECT  * FROM `rejs_gorod` WHERE id=1")) {
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        adult = rs.getInt("Adult");
                        child = rs.getInt("Child");
                        baby = rs.getInt("Baby");
                        nomer_rejs = rs.getString("Rejs_nomer");
                        klass_obsluzhivanija_id = rs.getInt("klass_obsluzhivanija_id");
                        cena = rs.getInt("Cena");
                        mili_begin = rs.getInt("Mili");
                    }
                }
                ps.close();
            }
            try (PreparedStatement ps = c.prepareStatement("SELECT  * FROM `element_zakaza`")) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        id = rs.getInt("Element_zakaza_id");

                    }
                }

                ps.close();
            }
            try (PreparedStatement ps = c.prepareStatement("SELECT  * FROM `klient_zakaz` WHERE `Element_zakaza_id`=?")) {
                ps.setInt(1, (id + 1));
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        id_element_zakaza_id = rs.getInt("Element_zakaza_id");

                    } else {
                        id_element_zakaza_id = 0;
                    }
                }

                ps.close();
            }
            if (id_element_zakaza_id > 0) {
                try (PreparedStatement ps = c.prepareStatement("SELECT  * FROM `klient_zakaz` WHERE `Vozrast`= ? AND `Element_zakaza_id`= ?")) {
                    ps.setInt(1, 1);
                    ps.setInt(2, id_element_zakaza_id);
                    int sheet = 0;
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            adult_klient_id[sheet] = rs.getInt("Klient_id");
                            adult_id[sheet] = rs.getInt("Klient_zakaz_id");
                            sheet++;
                        }
                    }
                    ps.close();
                }
                try (PreparedStatement ps = c.prepareStatement("SELECT  * FROM `klient_zakaz` WHERE `Vozrast`=? AND `Element_zakaza_id`= ?")) {
                    ps.setInt(1, 2);
                    ps.setInt(2, id_element_zakaza_id);
                    int sheet1 = 0;
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            child_klient_id[sheet1] = rs.getInt("Klient_id");
                            child_id[sheet1] = rs.getInt("Klient_zakaz_id");
                            sheet1++;
                        }
                    }

                    ps.close();
                }
                for (int i = 0; i < adult_klient_id.length; i++) {
                    try (PreparedStatement ps = c.prepareStatement("SELECT  * FROM `klient` WHERE `Klient_id`=?")) {
                        ps.setInt(1, adult_klient_id[i]);
                        try (ResultSet rs = ps.executeQuery()) {
                            if (rs.next()) {
                                name_adult[i] = rs.getString("Imja");
                                familiya_adult[i] = rs.getString("Familiya");
                                otche_adult[i] = rs.getString("Otshestvo");
                                mili[i] = rs.getInt("Mili");
                            }
                        }

                        ps.close();
                    }
                }

                for (int i = 0; i < child_klient_id.length; i++) {
                    try (PreparedStatement ps = c.prepareStatement("SELECT  * FROM `klient` WHERE `Klient_id`=?")) {
                        ps.setInt(1, child_klient_id[i]);
                        try (ResultSet rs = ps.executeQuery()) {
                            if (rs.next()) {
                                name_child[i] = rs.getString("Imja");
                                familiya_child[i] = rs.getString("Familiya");
                                otche_child[i] = rs.getString("Otshestvo");
                            }
                        }

                        ps.close();
                    }
                }
            }

            c.commit();
            c.commit();
            c.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        int win_w = screenSize.width / 2 - 200;
        int win_h = screenSize.height / 3 - 180;
        int w = 150;
        int w_field = 150;
        int h = 30;
        int g = 11;
        vid_bileta_box.addItem("Обычный");
        vid_bileta_box.addItem("Ветеран");
        vid_bileta_box.setSelectedIndex(0);
        cena_rows[0] = cena;;
        price_field.setText("" + cena_rows[0]);
        //cena_rows[0] = 0;
        vid_bileta = 0;
        vid_bileta_box.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                flag = (int) vid_bileta_box.getSelectedIndex();
                if (flag == 0) {
                    //Обычный
                    cena_rows[0] = cena;
                    price_field.setText("" + cena_rows[0]);
                    //cena_rows[0] = 0;
                    vid_bileta = 0;
                }
                if (flag == 1) {
//Бизнес
                    cena_rows[1] = cena - ((cena / 100) * 20);
                    price_field.setText("" + cena_rows[1]);
                    //cena_rows[1] = 0;
                    vid_bileta = 0;
// mili_field.setText(String.format("%8.2f", nakop_mili).replace(',', '.'));
                }
            }
        });
        nomer_zakaz_field.setText("" + (id + 1));
        rejs_field.setText("" + nomer_rejs);
        kolichestvo_biletov_field.setText("" + (adult + child + baby));
        if (klass_obsluzhivanija_id == 1) {
            klass_obsluzhivanija_field.setText("Эконом");
        }
        if (klass_obsluzhivanija_id == 2) {
            klass_obsluzhivanija_field.setText("Бизнес");
        }
        if (klass_obsluzhivanija_id == 3) {
            klass_obsluzhivanija_field.setText("Первый");
        }

        b1.addActionListener((java.awt.event.ActionEvent evt) -> {
            insert_klient_adult(url, user, password);
            this.removeAll();
            this.revalidate();
            this.repaint();
            this.updateUI();
            this.add(new PanelViborKlienta());

        });
        b2.addActionListener((java.awt.event.ActionEvent evt) -> {
            insert_klient_adult(url, user, password);
            this.removeAll();
            this.revalidate();
            this.repaint();
            this.updateUI();
            this.add(new PanelViborKlienta());

        });
        b3.addActionListener((java.awt.event.ActionEvent evt) -> {
            insert_klient_adult(url, user, password);
            this.removeAll();
            this.revalidate();
            this.repaint();
            this.updateUI();
            this.add(new PanelViborKlienta());

        });
        b4.addActionListener((java.awt.event.ActionEvent evt) -> {
            insert_klient_adult(url, user, password);
            this.removeAll();
            this.revalidate();
            this.repaint();
            this.updateUI();
            this.add(new PanelViborKlienta());

        });
        b5.addActionListener((java.awt.event.ActionEvent evt) -> {
            insert_klient_adult(url, user, password);
            this.removeAll();
            this.revalidate();
            this.repaint();
            this.updateUI();
            this.add(new PanelViborKlienta());

        });

        b6.addActionListener((java.awt.event.ActionEvent evt) -> {
            insert_klient_child(url, user, password);
            this.removeAll();
            this.revalidate();
            this.repaint();
            this.updateUI();
            this.add(new PanelViborKlienta());

        });
        b7.addActionListener((java.awt.event.ActionEvent evt) -> {
            insert_klient_child(url, user, password);
            this.removeAll();
            this.revalidate();
            this.repaint();
            this.updateUI();
            this.add(new PanelViborKlienta());

        });
        b8.addActionListener((java.awt.event.ActionEvent evt) -> {
            insert_klient_child(url, user, password);
            this.removeAll();
            this.revalidate();
            this.repaint();
            this.updateUI();
            this.add(new PanelViborKlienta());

        });
        b9.addActionListener((java.awt.event.ActionEvent evt) -> {
            insert_klient_child(url, user, password);
            this.removeAll();
            this.revalidate();
            this.repaint();
            this.updateUI();
            this.add(new PanelViborKlienta());

        });
        b10.addActionListener((java.awt.event.ActionEvent evt) -> {
            insert_klient_child(url, user, password);
            this.removeAll();
            this.revalidate();
            this.repaint();
            this.updateUI();
            this.add(new PanelOplata());

        });
        drop.addActionListener((java.awt.event.ActionEvent evt) -> {
            drop_klient(url, user, password);

        });

        cancel.addActionListener((java.awt.event.ActionEvent evt) -> {
            this.removeAll();
            this.revalidate();
            this.repaint();
            this.updateUI();
            this.add(new PanelZakazElement());

        });

        next.addActionListener((java.awt.event.ActionEvent evt) -> {
            zakaz_insert(url, user, password);
            this.removeAll();
            this.revalidate();
            this.repaint();
            this.updateUI();
            this.add(new PanelOplata());
        });

        nomer_zakaza_label.setSize(w, h);
        nomer_zakaza_label.setLocation(win_w, win_h);
        nomer_zakaz_field.setSize(w_field, h);
        nomer_zakaz_field.setLocation(win_w + 170, win_h);

        rejs_label.setSize(w, h);
        rejs_label.setLocation(win_w, win_h + h);
        rejs_field.setSize(w_field, h);
        rejs_field.setLocation(win_w + 170, win_h + h);

        kolichestvo_biletov_label.setSize(w, h);
        kolichestvo_biletov_label.setLocation(win_w, win_h + h * 2);
        kolichestvo_biletov_field.setSize(w_field, h);
        kolichestvo_biletov_field.setLocation(win_w + 170, win_h + h * 2);

        klass_obsluzhivanija_label.setSize(w, h);
        klass_obsluzhivanija_label.setLocation(win_w, win_h + h * 3);
        klass_obsluzhivanija_field.setSize(w_field, h);
        klass_obsluzhivanija_field.setLocation(win_w + 170, win_h + h * 3);

        vid_bileta_label.setSize(w, h);
        vid_bileta_label.setLocation(win_w, win_h + h * 4);
        vid_bileta_box.setSize(w_field, h);
        vid_bileta_box.setLocation(win_w + 170, win_h + h * 4);

        price_label.setSize(w, h);
        price_label.setLocation(win_w, win_h + h * 5);
        price_field.setSize(w_field, h);
        price_field.setLocation(win_w + 170, win_h + h * 5);

        person_adult_1_label.setSize(w, h);
        person_adult_1_label.setLocation(win_w, win_h + h * 6);
        person_adult_1_field.setSize(w_field, h);
        person_adult_1_field.setLocation(win_w + 170, win_h + h * 6);
        b1.setSize(w_field, h);
        b1.setLocation(win_w + 340, win_h + h * 6);

        person_adult_2_label.setSize(w, h);
        person_adult_2_label.setLocation(win_w, win_h + h * 7);
        person_adult_2_field.setSize(w_field, h);
        person_adult_2_field.setLocation(win_w + 170, win_h + h * 7);
        b2.setSize(w_field, h);
        b2.setLocation(win_w + 340, win_h + h * 7);

        person_adult_3_label.setSize(w, h);
        person_adult_3_label.setLocation(win_w, win_h + h * 8);
        person_adult_3_field.setSize(w_field, h);
        person_adult_3_field.setLocation(win_w + 170, win_h + h * 8);
        b3.setSize(w_field, h);
        b3.setLocation(win_w + 340, win_h + h * 8);

        person_adult_4_label.setSize(w, h);
        person_adult_4_label.setLocation(win_w, win_h + h * 9);
        person_adult_4_field.setSize(w_field, h);
        person_adult_4_field.setLocation(win_w + 170, win_h + h * 9);
        b4.setSize(w_field, h);
        b4.setLocation(win_w + 340, win_h + h * 9);

        person_adult_5_label.setSize(w, h);
        person_adult_5_label.setLocation(win_w, win_h + h * 10);
        person_adult_5_field.setSize(w_field, h);
        person_adult_5_field.setLocation(win_w + 170, win_h + h * 10);
        b5.setSize(w_field, h);
        b5.setLocation(win_w + 340, win_h + h * 10);

        this.add(nomer_zakaza_label);
        this.add(nomer_zakaz_field);

        this.add(rejs_label);
        this.add(rejs_field);

        this.add(kolichestvo_biletov_label);
        this.add(kolichestvo_biletov_field);

        this.add(klass_obsluzhivanija_label);
        this.add(klass_obsluzhivanija_field);

        this.add(vid_bileta_label);
        this.add(vid_bileta_box);

        this.add(price_label);
        this.add(price_field);
        if (adult == 1) {
            if (familiya_adult[0] == null || otche_adult[0] == null) {
                person_adult_1_field.setText("");
            } else {
                person_adult_1_field.setText("" + familiya_adult[0] + " " + name_adult[0].charAt(0) + "." + otche_adult[0].charAt(0) + ".");
            }
            this.add(person_adult_1_label);
            this.add(person_adult_1_field);
            this.add(b1);
            g = g - 4;
        } else {
            if (adult == 2) {
                if (familiya_adult[0] == null || otche_adult[0] == null) {
                    person_adult_1_field.setText("");
                } else {
                    person_adult_1_field.setText("" + familiya_adult[0] + " " + name_adult[0].charAt(0) + "." + otche_adult[0].charAt(0) + ".");
                }
                if (familiya_adult[1] == null || otche_adult[1] == null) {
                    person_adult_2_field.setText("");
                } else {
                    person_adult_2_field.setText("" + familiya_adult[1] + " " + name_adult[1].charAt(0) + "." + otche_adult[1].charAt(0) + ".");
                }
                this.add(person_adult_1_label);
                this.add(person_adult_1_field);
                this.add(b1);
                this.add(person_adult_2_label);
                this.add(person_adult_2_field);
                this.add(b2);
                g = g - 3;
            }
            if (adult == 3) {
                if (familiya_adult[0] == null || otche_adult[0] == null) {
                    person_adult_1_field.setText("");
                } else {
                    person_adult_1_field.setText("" + familiya_adult[0] + " " + name_adult[0].charAt(0) + "." + otche_adult[0].charAt(0) + ".");
                }
                if (familiya_adult[1] == null || otche_adult[1] == null) {
                    person_adult_2_field.setText("");
                } else {
                    person_adult_2_field.setText("" + familiya_adult[1] + " " + name_adult[1].charAt(0) + "." + otche_adult[1].charAt(0) + ".");
                }
                if (familiya_adult[2] == null || otche_adult[2] == null) {
                    person_adult_3_field.setText("");
                } else {
                    person_adult_3_field.setText("" + familiya_adult[2] + " " + name_adult[2].charAt(0) + "." + otche_adult[2].charAt(0) + ".");
                }
                this.add(person_adult_1_label);
                this.add(person_adult_1_field);
                this.add(b1);
                this.add(person_adult_2_label);
                this.add(person_adult_2_field);
                this.add(b2);
                this.add(person_adult_3_label);
                this.add(person_adult_3_field);
                this.add(b3);
                g = g - 2;
            }
            if (adult == 4) {
                if (familiya_adult[0] == null || otche_adult[0] == null) {
                    person_adult_1_field.setText("");
                } else {
                    person_adult_1_field.setText("" + familiya_adult[0] + " " + name_adult[0].charAt(0) + "." + otche_adult[0].charAt(0) + ".");
                }
                if (familiya_adult[1] == null || otche_adult[1] == null) {
                    person_adult_2_field.setText("");
                } else {
                    person_adult_2_field.setText("" + familiya_adult[1] + " " + name_adult[1].charAt(0) + "." + otche_adult[1].charAt(0) + ".");
                }
                if (familiya_adult[2] == null || otche_adult[2] == null) {
                    person_adult_3_field.setText("");
                } else {
                    person_adult_3_field.setText("" + familiya_adult[2] + " " + name_adult[2].charAt(0) + "." + otche_adult[2].charAt(0) + ".");
                }
                if (familiya_adult[3] == null || otche_adult[3] == null) {
                    person_adult_4_field.setText("");
                } else {
                    person_adult_4_field.setText("" + familiya_adult[3] + " " + name_adult[3].charAt(0) + "." + otche_adult[3].charAt(0) + ".");
                }
                this.add(person_adult_1_label);
                this.add(person_adult_1_field);
                this.add(b1);
                this.add(person_adult_2_label);
                this.add(person_adult_2_field);
                this.add(b2);
                this.add(person_adult_3_label);
                this.add(person_adult_3_field);
                this.add(b3);
                this.add(person_adult_4_label);
                this.add(person_adult_4_field);
                this.add(b4);
                g = g - 1;
            }
            if (adult == 5) {
                if (familiya_adult[0] == null || otche_adult[0] == null) {
                    person_adult_1_field.setText("");
                } else {
                    person_adult_1_field.setText("" + familiya_adult[0] + " " + name_adult[0].charAt(0) + "." + otche_adult[0].charAt(0) + ".");
                }
                if (familiya_adult[1] == null || otche_adult[1] == null) {
                    person_adult_2_field.setText("");
                } else {
                    person_adult_2_field.setText("" + familiya_adult[1] + " " + name_adult[1].charAt(0) + "." + otche_adult[1].charAt(0) + ".");
                }
                if (familiya_adult[2] == null || otche_adult[2] == null) {
                    person_adult_3_field.setText("");
                } else {
                    person_adult_3_field.setText("" + familiya_adult[2] + " " + name_adult[2].charAt(0) + "." + otche_adult[2].charAt(0) + ".");
                }
                if (familiya_adult[3] == null || otche_adult[3] == null) {
                    person_adult_4_field.setText("");
                } else {
                    person_adult_4_field.setText("" + familiya_adult[3] + " " + name_adult[3].charAt(0) + "." + otche_adult[3].charAt(0) + ".");
                }
                if (familiya_adult[4] == null || otche_adult[4] == null) {
                    person_adult_5_field.setText("");
                } else {
                    person_adult_5_field.setText("" + familiya_adult[4] + " " + name_adult[4].charAt(0) + "." + otche_adult[4].charAt(0) + ".");
                }
                this.add(person_adult_1_label);
                this.add(person_adult_1_field);
                this.add(b1);
                this.add(person_adult_2_label);
                this.add(person_adult_2_field);
                this.add(b2);
                this.add(person_adult_3_label);
                this.add(person_adult_3_field);
                this.add(b3);
                this.add(person_adult_4_label);
                this.add(person_adult_4_field);
                this.add(b4);
                this.add(person_adult_5_label);
                this.add(person_adult_5_field);
                this.add(b5);
                g = 11;

            }
        }
        switch (child) {
            case 0:
                next.setSize(w_field, h);
                next.setLocation(win_w + 170, win_h + h * (g));
                cancel.setSize(w, h);
                cancel.setLocation(win_w, win_h + h * (g));
                drop.setSize(w, h);
                drop.setLocation(win_w, win_h + h * (g + 1));
                break;
            case 1:
                if (familiya_child[0] == null || otche_child[0] == null) {
                    person_child_1_field.setText("");
                } else {
                    person_child_1_field.setText("" + familiya_child[0] + " " + name_child[0].charAt(0) + "." + otche_child[0].charAt(0) + ".");
                }
                person_child_1_label.setSize(w, h);
                person_child_1_label.setLocation(win_w, win_h + h * (g));
                person_child_1_field.setSize(w_field, h);
                person_child_1_field.setLocation(win_w + 170, win_h + h * (g));
                b6.setSize(w_field, h);
                b6.setLocation(win_w + 340, win_h + h * (g));
                next.setSize(w_field, h);
                next.setLocation(win_w + 170, win_h + h * (g + 1));
                cancel.setSize(w, h);
                cancel.setLocation(win_w, win_h + h * (g + 1));
                drop.setSize(w, h);
                drop.setLocation(win_w, win_h + h * (g + 2));
                this.add(person_child_1_label);
                this.add(person_child_1_field);
                this.add(b6);
                break;
            default:
                if (child == 2) {
                    if (familiya_child[0] == null || otche_child[0] == null) {
                        person_child_1_field.setText("");
                    } else {
                        person_child_1_field.setText("" + familiya_child[0] + " " + name_child[0].charAt(0) + ". " + otche_child[0].charAt(0) + ".");
                    }
                    if (familiya_child[1] == null || otche_child[1] == null) {
                        person_child_2_field.setText("");
                    } else {
                        person_child_2_field.setText("" + familiya_child[1] + " " + name_child[1].charAt(0) + "." + otche_child[1].charAt(0) + ".");
                    }
                    person_child_1_label.setSize(w, h);
                    person_child_1_label.setLocation(win_w, win_h + h * (g));
                    person_child_1_field.setSize(w_field, h);
                    person_child_1_field.setLocation(win_w + 170, win_h + h * (g));
                    b6.setSize(w_field, h);
                    b6.setLocation(win_w + 340, win_h + h * (g));

                    person_child_2_label.setSize(w, h);
                    person_child_2_label.setLocation(win_w, win_h + h * (g + 1));
                    person_child_2_field.setSize(w_field, h);
                    person_child_2_field.setLocation(win_w + 170, win_h + h * (g + 1));
                    b7.setSize(w_field, h);
                    b7.setLocation(win_w + 340, win_h + h * (g + 1));

                    next.setSize(w_field, h);
                    next.setLocation(win_w + 170, win_h + h * (g + 2));
                    cancel.setSize(w, h);
                    cancel.setLocation(win_w, win_h + h * (g + 2));
                    drop.setSize(w, h);
                    drop.setLocation(win_w, win_h + h * (g + 3));

                    this.add(person_child_1_label);
                    this.add(person_child_1_field);
                    this.add(b6);
                    this.add(person_child_2_label);
                    this.add(person_child_2_field);
                    this.add(b7);

                }
                if (child == 3) {
                    if (familiya_child[0] == null || otche_child[0] == null) {
                        person_child_1_field.setText("");
                    } else {
                        person_child_1_field.setText("" + familiya_child[0] + " " + name_child[0].charAt(0) + "." + otche_child[0].charAt(0) + ".");
                    }
                    if (familiya_child[1] == null || otche_child[1] == null) {
                        person_child_2_field.setText("");
                    } else {
                        person_child_2_field.setText("" + familiya_child[1] + " " + name_child[1].charAt(0) + "." + otche_child[1].charAt(0) + ".");
                    }
                    if (familiya_child[2] == null || otche_child[2] == null) {
                        person_child_3_field.setText("");
                    } else {
                        person_child_3_field.setText("" + familiya_child[2] + " " + name_child[2].charAt(0) + "." + otche_child[2].charAt(0) + ".");
                    }
                    person_child_1_label.setSize(w, h);
                    person_child_1_label.setLocation(win_w, win_h + h * (g));
                    person_child_1_field.setSize(w_field, h);
                    person_child_1_field.setLocation(win_w + 170, win_h + h * (g));
                    b6.setSize(w_field, h);
                    b6.setLocation(win_w + 340, win_h + h * (g));

                    person_child_2_label.setSize(w, h);
                    person_child_2_label.setLocation(win_w, win_h + h * (g + 1));
                    person_child_2_field.setSize(w_field, h);
                    person_child_2_field.setLocation(win_w + 170, win_h + h * (g + 1));
                    b7.setSize(w_field, h);
                    b7.setLocation(win_w + 340, win_h + h * (g + 1));

                    person_child_3_label.setSize(w, h);
                    person_child_3_label.setLocation(win_w, win_h + h * (g + 2));
                    person_child_3_field.setSize(w_field, h);
                    person_child_3_field.setLocation(win_w + 170, win_h + h * (g + 2));
                    b8.setSize(w_field, h);
                    b8.setLocation(win_w + 340, win_h + h * (g + 2));

                    next.setSize(w_field, h);
                    next.setLocation(win_w + 170, win_h + h * (g + 3));
                    cancel.setSize(w, h);
                    cancel.setLocation(win_w, win_h + h * (g + 3));
                    drop.setSize(w, h);
                    drop.setLocation(win_w, win_h + h * (g + 4));

                    this.add(person_child_1_label);
                    this.add(person_child_1_field);
                    this.add(b6);
                    this.add(person_child_2_label);
                    this.add(person_child_2_field);
                    this.add(b7);
                    this.add(person_child_3_label);
                    this.add(person_child_3_field);
                    this.add(b8);

                }
                if (child == 4) {
                    if (familiya_child[0] == null || otche_child[0] == null) {
                        person_child_1_field.setText("");
                    } else {
                        person_child_1_field.setText("" + familiya_child[0] + " " + name_child[0].charAt(0) + "." + otche_child[0].charAt(0) + ".");
                    }
                    if (familiya_child[1] == null || otche_child[1] == null) {
                        person_child_2_field.setText("");
                    } else {
                        person_child_2_field.setText("" + familiya_child[1] + " " + name_child[1].charAt(0) + "." + otche_child[1].charAt(0) + ".");
                    }
                    if (familiya_child[2] == null || otche_child[2] == null) {
                        person_child_3_field.setText("");
                    } else {
                        person_child_3_field.setText("" + familiya_child[2] + " " + name_child[2].charAt(0) + "." + otche_child[2].charAt(0) + ".");
                    }
                    if (familiya_child[3] == null || otche_child[3] == null) {
                        person_child_4_field.setText("");
                    } else {
                        person_child_4_field.setText("" + familiya_child[3] + " " + name_child[3].charAt(0) + "." + otche_child[3].charAt(0) + ".");
                    }

                    person_child_1_label.setSize(w, h);
                    person_child_1_label.setLocation(win_w, win_h + h * (g));
                    person_child_1_field.setSize(w_field, h);
                    person_child_1_field.setLocation(win_w + 170, win_h + h * (g));
                    b6.setSize(w_field, h);
                    b6.setLocation(win_w + 340, win_h + h * (g));

                    person_child_2_label.setSize(w, h);
                    person_child_2_label.setLocation(win_w, win_h + h * (g + 1));
                    person_child_2_field.setSize(w_field, h);
                    person_child_2_field.setLocation(win_w + 170, win_h + h * (g + 1));
                    b7.setSize(w_field, h);
                    b7.setLocation(win_w + 340, win_h + h * (g + 1));

                    person_child_3_label.setSize(w, h);
                    person_child_3_label.setLocation(win_w, win_h + h * (g + 2));
                    person_child_3_field.setSize(w_field, h);
                    person_child_3_field.setLocation(win_w + 170, win_h + h * (g + 2));
                    b8.setSize(w_field, h);
                    b8.setLocation(win_w + 340, win_h + h * (g + 2));

                    person_child_4_label.setSize(w, h);
                    person_child_4_label.setLocation(win_w, win_h + h * (g + 3));
                    person_child_4_field.setSize(w_field, h);
                    person_child_4_field.setLocation(win_w + 170, win_h + h * (g + 3));
                    b9.setSize(w_field, h);
                    b9.setLocation(win_w + 340, win_h + h * (g + 3));

                    next.setSize(w_field, h);
                    next.setLocation(win_w + 170, win_h + h * (g + 4));
                    cancel.setSize(w, h);
                    cancel.setLocation(win_w, win_h + h * (g + 4));
                    drop.setSize(w, h);
                    drop.setLocation(win_w, win_h + h * (g + 5));

                    this.add(person_child_1_label);
                    this.add(person_child_1_field);
                    this.add(b6);
                    this.add(person_child_2_label);
                    this.add(person_child_2_field);
                    this.add(b7);
                    this.add(person_child_3_label);
                    this.add(person_child_3_field);
                    this.add(b8);
                    this.add(person_child_4_label);
                    this.add(person_child_4_field);
                    this.add(b9);

                }
                if (child == 5) {
                    if (familiya_child[0] == null || otche_child[0] == null) {
                        person_child_1_field.setText("");
                    } else {
                        person_child_1_field.setText("" + familiya_child[0] + " " + name_child[0].charAt(0) + "." + otche_child[0].charAt(0) + ".");
                    }
                    if (familiya_child[1] == null || otche_child[1] == null) {
                        person_child_2_field.setText("");
                    } else {
                        person_child_2_field.setText("" + familiya_child[1] + " " + name_child[1].charAt(0) + "." + otche_child[1].charAt(0) + ".");
                    }
                    if (familiya_child[2] == null || otche_child[2] == null) {
                        person_child_3_field.setText("");
                    } else {
                        person_child_3_field.setText("" + familiya_child[2] + " " + name_child[2].charAt(0) + "." + otche_child[2].charAt(0) + ".");
                    }
                    if (familiya_child[3] == null || otche_child[3] == null) {
                        person_child_4_field.setText("");
                    } else {
                        person_child_4_field.setText("" + familiya_child[3] + " " + name_child[3].charAt(0) + "." + otche_child[3].charAt(0) + ".");
                    }
                    if (familiya_child[4] == null || otche_child[4] == null) {
                        person_child_5_field.setText("");
                    } else {
                        person_child_5_field.setText("" + familiya_child[4] + " " + name_child[4].charAt(0) + "." + otche_child[4].charAt(0) + ".");
                    }

                    person_child_1_label.setSize(w, h);
                    person_child_1_label.setLocation(win_w, win_h + h * (g));
                    person_child_1_field.setSize(w_field, h);
                    person_child_1_field.setLocation(win_w + 170, win_h + h * (g));
                    b6.setSize(w_field, h);
                    b6.setLocation(win_w + 340, win_h + h * (g));

                    person_child_2_label.setSize(w, h);
                    person_child_2_label.setLocation(win_w, win_h + h * (g + 1));
                    person_child_2_field.setSize(w_field, h);
                    person_child_2_field.setLocation(win_w + 170, win_h + h * (g + 1));
                    b7.setSize(w_field, h);
                    b7.setLocation(win_w + 340, win_h + h * (g + 1));

                    person_child_3_label.setSize(w, h);
                    person_child_3_label.setLocation(win_w, win_h + h * (g + 2));
                    person_child_3_field.setSize(w_field, h);
                    person_child_3_field.setLocation(win_w + 170, win_h + h * (g + 2));
                    b8.setSize(w_field, h);
                    b8.setLocation(win_w + 340, win_h + h * (g + 2));

                    person_child_4_label.setSize(w, h);
                    person_child_4_label.setLocation(win_w, win_h + h * (g + 3));
                    person_child_4_field.setSize(w_field, h);
                    person_child_4_field.setLocation(win_w + 170, win_h + h * (g + 3));
                    b9.setSize(w_field, h);
                    b9.setLocation(win_w + 340, win_h + h * (g + 3));

                    person_child_5_label.setSize(w, h);
                    person_child_5_label.setLocation(win_w, win_h + h * (g + 4));
                    person_child_5_field.setSize(w_field, h);
                    person_child_5_field.setLocation(win_w + 170, win_h + h * (g + 4));
                    b10.setSize(w_field, h);
                    b10.setLocation(win_w + 340, win_h + h * (g + 4));

                    next.setSize(w_field, h);
                    next.setLocation(win_w + 170, win_h + h * (g + 5));
                    cancel.setSize(w, h);
                    cancel.setLocation(win_w, win_h + h * (g + 5));
                    drop.setSize(w, h);
                    drop.setLocation(win_w, win_h + h * (g + 6));

                    this.add(person_child_1_label);
                    this.add(person_child_1_field);
                    this.add(b6);
                    this.add(person_child_2_label);
                    this.add(person_child_2_field);
                    this.add(b7);
                    this.add(person_child_3_label);
                    this.add(person_child_3_field);
                    this.add(b8);
                    this.add(person_child_4_label);
                    this.add(person_child_4_field);
                    this.add(b9);
                    this.add(person_child_5_label);
                    this.add(person_child_5_field);
                    this.add(b10);

                }
                break;
        }
        this.add(cancel);
        this.add(drop);
        this.add(next);
    }

    public void insert_klient_adult(String url, String user, String password) {
        //model.setValueAt(klient_id.get(i), i, 6);
        try (Connection c = DriverManager.getConnection(url, user, password)) {
            c.setAutoCommit(false);
            c.setReadOnly(false);

            try (PreparedStatement ps2 = c.prepareStatement("UPDATE `rejs_gorod` SET `Vozrast`=? WHERE id=1")) {
                ps2.setInt(1, 1);
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

    public void insert_klient_child(String url, String user, String password) {
        //model.setValueAt(klient_id.get(i), i, 6);
        try (Connection c = DriverManager.getConnection(url, user, password)) {
            c.setAutoCommit(false);
            c.setReadOnly(false);
            try (PreparedStatement ps2 = c.prepareStatement("UPDATE `rejs_gorod` SET `Vozrast`=? WHERE id=1")) {
                ps2.setInt(1, 2);
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

    public void drop_klient(String url, String user, String password) {
        //model.setValueAt(klient_id.get(i), i, 6);
        System.out.println("ID" + adult_id[0]);
        try (Connection c = DriverManager.getConnection(url, user, password)) {
            c.setAutoCommit(false);
            c.setReadOnly(false);
            for (int i = 0; i < 5; i++) {
                if (adult_id[i] != 0) {
                    person_adult_1_field.setText("");
                    person_adult_2_field.setText("");
                    person_adult_3_field.setText("");
                    person_adult_4_field.setText("");
                    person_adult_5_field.setText("");
                    try (PreparedStatement ps2 = c.prepareStatement("DELETE FROM `mydb`.`klient_zakaz` WHERE `klient_zakaz`.`Klient_zakaz_id` = ?;")) {
                        ps2.setInt(1, adult_id[i]);
                        ps2.executeUpdate();
                    }
                }

                if (child_id[i] != 0) {
                    person_child_1_field.setText("");
                    person_child_2_field.setText("");
                    person_child_3_field.setText("");
                    person_child_4_field.setText("");
                    person_child_5_field.setText("");
                    try (PreparedStatement ps2 = c.prepareStatement("DELETE FROM `mydb`.`klient_zakaz` WHERE `klient_zakaz`.`Klient_zakaz_id` = ?;")) {
                        ps2.setInt(1, child_id[i]);
                        ps2.executeUpdate();
                    }
                }
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

    public void zakaz_insert(String url, String user, String password) {
        //model.setValueAt(klient_id.get(i), i, 6);
        int id_zakaz = 0;
        int kolich_milli = 0;
        int id_rejs = 0;
        try (Connection c = DriverManager.getConnection(url, user, password)) {
            c.setAutoCommit(false);
            c.setReadOnly(false);
            try (PreparedStatement ps = c.prepareStatement("SELECT * FROM `zakaz` WHERE `Zakaz_id` in (select max(`Zakaz_id`) from `zakaz`)")) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        id_zakaz = rs.getInt("Zakaz_id");
                    }
                }

                ps.close();
            }
            try (PreparedStatement ps = c.prepareStatement("SELECT * FROM `rejs_gorod` WHERE id=1")) {
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        kolich_milli = rs.getInt("Mili");
                        //nomer_rejs = rs.getString("Rejs_nomer");

                    }
                }

                ps.close();
            }
            try (PreparedStatement ps = c.prepareStatement("SELECT * FROM `rejs` WHERE `Nomer`=?")) {
                ps.setString(1, nomer_rejs);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        id_rejs = rs.getInt("Rejs_id");
                        //nomer_rejs = rs.getString("Rejs_nomer");

                    }
                }

                ps.close();
            }
            System.out.println("ID: " + id_zakaz);
            try (PreparedStatement ps = c.prepareStatement("INSERT INTO `mydb`.`zakaz` (`Zakaz_id`,  `Klient_id`) VALUES (?, ?);")) {
                ps.setInt(1, (id_zakaz + 1));
                //ps.setInt(2, 0);
                ps.setInt(2, 1);
                ps.executeUpdate();
                ps.close();
            }
            try (PreparedStatement ps = c.prepareStatement("INSERT INTO `mydb`.`element_zakaza` (`Element_zakaza_id`, `Kolychestvo`, `Cena`, `Rejs_id`, `Vid_bileta_id`, `Klass_obsluzhivanija_id`, `Zakaz_id`, `Nakopitelnye_mili`) VALUES (?, ?, ?, ?, ?, ?, ?, ?);")) {
                ps.setInt(1, (id + 1));
                ps.setInt(2, (adult + child + baby));
                ps.setInt(3, (cena_rows[vid_bileta]));
                ps.setInt(4, id_rejs);
                ps.setInt(5, (vid_bileta + 1));
                ps.setInt(6, klass_obsluzhivanija_id);
                ps.setInt(7, (id_zakaz + 1));
                ps.setInt(8, kolich_milli);
                ps.executeUpdate();
                ps.close();
            }
            try (PreparedStatement ps = c.prepareStatement("UPDATE `rejs_gorod` SET `Klient_id` = ? WHERE `id` = 1;")) {
                ps.setInt(1, (id + 1));
                ps.executeUpdate();
                ps.close();
            }
            try (PreparedStatement ps = c.prepareStatement("UPDATE `klient` SET `Mili` = ? WHERE `Klient_id` = ?;")) {
                ps.setInt(1, (mili_begin + mili[0]));
                ps.setInt(2, adult_id[0]);
                ps.executeUpdate();
                ps.close();
            }
            int mest_ekonom = 0;
            int mest_bizness = 0;
            int mest_pervyj = 0;

            try (PreparedStatement ps = c.prepareStatement("SELECT  * FROM `rejs` WHERE `Nomer`=?")) {
                ps.setString(1, nomer_rejs);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        mest_ekonom = rs.getInt("Mest_ekonom");
                        mest_bizness = rs.getInt("Mest_bizness");
                        mest_pervyj = rs.getInt("Mest_pervyj");

                    }
                }
                ps.close();
            }
            int vse = adult + child + baby;
            switch (klass_obsluzhivanija_id) {
                case 1:
                    mest_ekonom = mest_ekonom - vse;
                    break;

                case 2:
                    mest_bizness = mest_bizness - vse;
                    break;

                case 3:
                    mest_pervyj = mest_pervyj - vse;
                    break;
            }

            try (PreparedStatement ps2 = c.prepareStatement("UPDATE `rejs` SET `Mest_ekonom`=?, `Mest_bizness`=?, `Mest_pervyj`=?  WHERE `Nomer`=?")) {
                ps2.setInt(1, mest_ekonom);
                ps2.setInt(2, mest_bizness);
                ps2.setInt(3, mest_pervyj);
                ps2.setString(4, nomer_rejs);
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
