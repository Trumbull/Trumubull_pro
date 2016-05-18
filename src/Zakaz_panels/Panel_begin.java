package Zakaz_panels;

import Connect.Conn;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.MaskFormatter;

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
    private JLabel datу_label;
    private JFormattedTextField date_rog_formatted_field;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JSpinner jSpinner_1;
    private javax.swing.JSpinner jSpinner_mld;
    private javax.swing.JComboBox<String> jComboBox_otk_gorod;
    private javax.swing.JTextField jTextField9;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();// определяем размер экрана
    private javax.swing.JComboBox<String> jComboBox_otk_strana;
    private javax.swing.JComboBox<String> jComboBox_kuda_strana;
    private String otk_gorod;
    private String kuda_gorod;
    private Zakaz_p p = new Zakaz_p();
    private String date_sql = p.getDate_sql();
    private int adult;
    private int child;
    private int baby;
    int gorod_begin = 0;

    public Panel_begin() throws SQLException, ParseException {
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
        datу_label = new JLabel();
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
        datу_label.setText("Дата отправления");
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

        jButton2.addActionListener((ActionEvent e) -> {
            String data = date_rog_formatted_field.getText();
            date_sql_formatted(data);
            if (otk_gorod == null || kuda_gorod == null || adult == 0) {
                JOptionPane.showMessageDialog(this,
                        "Ошибка неполные данные!");
            } else {

                insert_gorods(url, user, password);
                insert_vid_bileta(url, user, password);
                this.removeAll();
                this.revalidate();
                this.repaint();
                this.updateUI();
                try {
                    this.add(new Panel_vibor_rejs());
                } catch (SQLException ex) {
                    Logger.getLogger(Panel_begin.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        );

        gui(strana, url, user, password);

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

    public void gui(String[] strana, String url, String user, String password) throws ParseException {
        this.setLayout(null);
        ArrayList<String> gorod = new ArrayList<>(); //динамический массив
        ArrayList<String> IATA = new ArrayList<>();
        ArrayList<String> ICAO = new ArrayList<>();

        ArrayList<String> gorod_kuda = new ArrayList<>(); //динамический массив
        ArrayList<String> IATA_kuda = new ArrayList<>();
        ArrayList<String> ICAO_kuda = new ArrayList<>();
        int win_w = screenSize.width / 2 - 210;
        int win_h = screenSize.height / 3 - 180;
        int w = 200;
        int w_field = 200;
        int h = 30;
        //Взрослый

        jLabel8.setSize(w, h);
        jLabel8.setLocation(win_w, win_h);
        jSpinner_1.setSize(w_field, h);
        jSpinner_1.setLocation(win_w + w, win_h);

        ChangeListener listener = new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JSpinner js = (JSpinner) e.getSource();
                //System.out.println("Взрослый: " + js.getValue());
                adult = (int) js.getValue();
            }

        };
        jSpinner_1.addChangeListener(listener);

        //Ребенок
        jLabel1.setSize(w, h);
        jLabel1.setLocation(win_w, win_h + 30);
        jSpinner1.setSize(w_field, h);
        jSpinner1.setLocation(win_w + w, win_h + 30);
        ChangeListener listener1 = new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JSpinner js = (JSpinner) e.getSource();
                child = (int) js.getValue();
                //System.out.println("Ребенок: " + js.getValue());

            }
        };
        jSpinner1.addChangeListener(listener1);

        //Младенец
        jLabel10.setSize(w, h);
        jLabel10.setLocation(win_w, win_h + 60);
        jSpinner_mld.setSize(w_field, h);
        jSpinner_mld.setLocation(win_w + w, win_h + 60);

        ChangeListener listener2 = new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JSpinner js = (JSpinner) e.getSource();
                baby = (int) js.getValue();
            }
        };
        jSpinner_mld.addChangeListener(listener2);

        //Откуда
        jLabel2.setSize(w, h);
        jLabel2.setLocation(win_w, win_h + 90);
        ComBox_otk(strana, url, user, password, gorod, IATA, ICAO);
        jComboBox_otk_strana.setSize(w, h);
        jComboBox_otk_strana.setLocation(win_w, win_h + 120);
        jComboBox_otk_strana.setSelectedIndex(161);
        jComboBox_otk_gorod.setSize(260, h);
        jComboBox_otk_gorod.setLocation(win_w + w, win_h + 120);

        //выбираем первый без нажатия
        otk_gorod = (String) jComboBox_otk_gorod.getSelectedItem();

        //выбираем после нажатия
        jComboBox_otk_gorod.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                otk_gorod = (String) jComboBox_otk_gorod.getSelectedItem();

            }
        });

        //Куда
        jLabel3.setSize(w, h);
        jLabel3.setLocation(win_w, win_h + 150);
        Combox_kuda(strana, url, user, password, gorod_kuda, IATA_kuda, ICAO_kuda, gorod, IATA, ICAO);
        jComboBox_kuda_strana.setSize(w, h);
        jComboBox_kuda_strana.setLocation(win_w, win_h + 180);
        jComboBox_kuda_gorod.setSize(260, h);
        jComboBox_kuda_gorod.setLocation(win_w + w, win_h + 180);

        //выбираем первый без нажатия
        kuda_gorod = (String) jComboBox_kuda_gorod.getSelectedItem();

        //выбираем после нажатия
        jComboBox_kuda_gorod.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                kuda_gorod = (String) jComboBox_kuda_gorod.getSelectedItem();

            }
        });

        //Класс обслуживания
        /* 
        jLabel4.setSize(w, h);
        jLabel4.setLocation(win_w, win_h + 210);
        jComboBox_klass_obsl.setSize(w_field, h);
        jComboBox_klass_obsl.setLocation(win_w + w, win_h + 210);
        jComboBox_klass_obsl.addItem("Эконом");
        jComboBox_klass_obsl.addItem("Бизнес");
        jComboBox_klass_obsl.addItem("Первый");
         */
        datу_label.setSize(w, h);
        datу_label.setLocation(win_w, win_h + 210);

        MaskFormatter mf = new MaskFormatter("##-##-####");
        date_rog_formatted_field = new JFormattedTextField(mf);
        mf.setPlaceholderCharacter('_');

        date_rog_formatted_field.setSize(80, h);
        date_rog_formatted_field.setLocation(win_w + w, win_h + 210);
        date_rog_formatted_field.setHorizontalAlignment(JFormattedTextField.RIGHT);
        //Кнопки
        //Отмена
        jButton1.setSize(w, h);
        jButton1.setLocation(win_w, win_h + 240);
        //Добавить
        jButton2.setSize(w_field, h);
        jButton2.setLocation(win_w + w, win_h + 240);

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
        /*
        this.add(jLabel4);
        this.add(jComboBox_klass_obsl);
         */
        this.add(datу_label);
        this.add(date_rog_formatted_field);

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

                    //jComboBox_otk_gorod.addItem(gorod.get(i) + " (" + IATA.get(i) + ", " + ICAO.get(i) + ")");
                    jComboBox_otk_gorod.addItem(gorod.get(i));
                }
                gorod.removeAll(gorod);
                //IATA.removeAll(IATA);
                //ICAO.removeAll(ICAO);
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
                    //jComboBox_kuda_gorod.addItem(gorod_kuda.get(i) + " (" + IATA_kuda.get(i) + ", " + ICAO_kuda.get(i) + ")");
                    jComboBox_kuda_gorod.addItem(gorod_kuda.get(i));
                }
                gorod_kuda.removeAll(gorod);
                //IATA_kuda.removeAll(IATA);
                //ICAO_kuda.removeAll(ICAO);

            }
        }
        );
    }

    public void insert_gorods(String url, String user, String password) {

        try (Connection c = DriverManager.getConnection(url, user, password)) {
            c.setAutoCommit(false);
            c.setReadOnly(false);
            try (PreparedStatement ps2 = c.prepareStatement("UPDATE `rejs_gorod` SET `Gorod_otk`=?, `Gorod_kuda`=?, `Daty`=?  WHERE id=?")) {
                ps2.setString(1, otk_gorod);
                ps2.setString(2, kuda_gorod);
                ps2.setString(3, date_sql);
                ps2.setInt(4, 1);
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

    public void insert_vid_bileta(String url, String user, String password) {

        try (Connection c = DriverManager.getConnection(url, user, password)) {
            c.setAutoCommit(false);
            c.setReadOnly(false);
            try (PreparedStatement ps2 = c.prepareStatement("UPDATE `mydb`.`rejs_gorod` SET `Adult` = ?, `Child` = ?, `Baby` = ? WHERE `rejs_gorod`.`id` = 1;")) {
                ps2.setInt(1, adult);
                ps2.setInt(2, child);
                ps2.setInt(3, baby);
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
