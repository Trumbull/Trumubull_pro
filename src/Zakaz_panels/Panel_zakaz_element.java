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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Panel_zakaz_element extends JPanel {

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();// определяем размер экрана
    JLabel nomer_label;
    JLabel kolichestvo_label;
    JLabel cena_label;
    JLabel mili_label;
    JLabel klass_obsl_label;
    JTextField nomer_field;
    JTextField kolichestvo_field;
    JTextField cena_field;
    JComboBox<String> klass_obsl_combox;
    JTextField mili_field;

    JTextField street_field;
    JTextField house_field;
    JTextField apartment_field;
    JTextField mobile_phone_field;
    JTextField email_field;
    JButton insert_klient;
    JButton cancel_klient;
    private Zakaz_p rejs_p = new Zakaz_p();
    private int mest_ekonom = rejs_p.getMest_ekonom();
    private int mest_bizness = rejs_p.getMest_bizness();
    private int mest_pervyj = rejs_p.getMest_pervyj();
    private int adult = rejs_p.getAdult();
    private int child = rejs_p.getChild();
    private int baby = rejs_p.getBaby();
    private int cena_rows_begin = 0;
    private String nomer_rejs = rejs_p.getNomer_rejs();
    private String kolich_mili = rejs_p.getKolich_mil();
    private int id_marshrut = rejs_p.getID_marshrut();
    private int flag = 0;
    private int multiplier[] = new int[3];
    private int cena_rows[] = new int[3];
    private int nakop_mili = 0;

    public Panel_zakaz_element() {
        this.setBounds(0, 0, screenSize.width, screenSize.height);
        nomer_label = new JLabel("Номер рейса");
        kolichestvo_label = new JLabel("Количество");
        cena_label = new JLabel("Цена");
        mili_label = new JLabel("Накопительные мили");
        klass_obsl_label = new JLabel("Класс обслуживания");
        nomer_field = new JTextField();
        kolichestvo_field = new JTextField();
        cena_field = new JTextField();
        mili_field = new JTextField();
        klass_obsl_combox = new JComboBox<>();
        cancel_klient = new JButton("Назад");
        insert_klient = new JButton("Добавить");
        Conn conn = new Conn();
        String url = conn.getUrl();
        String user = conn.getUser();
        String password = conn.getPassword();

        try (Connection c = DriverManager.getConnection(url, user, password)) {

            c.setAutoCommit(false);
            c.setReadOnly(false);

            try (PreparedStatement ps = c.prepareStatement("SELECT * FROM `rejs_gorod` WHERE id=?")) {
                ps.setInt(1, 1);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        cena_rows_begin = rs.getInt("Cena");
                        id_marshrut = rs.getInt("Marshrut_id");
                        nomer_rejs = rs.getString("Rejs_nomer");
                        adult = rs.getInt("Adult");
                        child = rs.getInt("Child");
                        baby = rs.getInt("Baby");

                    }
                }
                ps.close();
            }
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
            try (PreparedStatement ps = c.prepareStatement("SELECT * FROM `marshrut` WHERE Marshrut_id=?")) {
                ps.setInt(1, id_marshrut);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        kolich_mili = rs.getString("Kolichestvo_mil");

                    }
                }

                ps.close();
            }
            try (PreparedStatement ps = c.prepareStatement("SELECT * FROM `klass_obsluzhivanija`")) {
                try (ResultSet rs = ps.executeQuery()) {
                    int r = 0;
                    while (rs.next()) {
                        multiplier[r] = rs.getInt("Mnozhitel");
                        r++;
                    }
                }

                ps.close();
            }
            System.out.println(multiplier[1]);
            c.commit();
            c.commit();
            c.close();
        } catch (SQLException se) {
        } catch (Exception e) {
        }

        this.setLayout(null);
        int win_w = screenSize.width / 2 - 180;
        int win_h = screenSize.height / 3 - 180;
        int w = 170;
        int w_field = 150;
        int h = 30;

        nomer_label.setSize(w, h);
        nomer_label.setLocation(win_w, win_h);
        nomer_field.setSize(w_field, h);
        nomer_field.setLocation(win_w + 170, win_h);
        nomer_field.setText(nomer_rejs);
        nomer_field.setHorizontalAlignment(JTextField.RIGHT);
        nomer_field.setEditable(false);

        kolichestvo_label.setSize(w, h);
        kolichestvo_label.setLocation(win_w, win_h + h);
        kolichestvo_field.setSize(w_field, h);
        kolichestvo_field.setLocation(win_w + 170, win_h + h);
        kolichestvo_field.setText("" + (adult + child + baby));
        kolichestvo_field.setHorizontalAlignment(JTextField.RIGHT);
        kolichestvo_field.setEditable(false);

        klass_obsl_label.setSize(w, h);
        klass_obsl_label.setLocation(win_w, win_h + h * 2);
        if (mest_ekonom != 0) {
            klass_obsl_combox.addItem("Эконом");
        }
        if (mest_bizness != 0) {
            klass_obsl_combox.addItem("Бизнес");
        }
        if (mest_pervyj != 0) {
            klass_obsl_combox.addItem("Первый");
        }
        klass_obsl_combox.setSize(w_field, h);
        klass_obsl_combox.setLocation(win_w + 170, win_h + h * 2);
        klass_obsl_combox.setSelectedIndex(0);
        cena_rows[0] = (cena_rows_begin * adult + (((cena_rows_begin / 100) * 10)) * child + (((cena_rows_begin / 100) * 74)) * baby) * multiplier[0];
        cena_field.setText("" + cena_rows[0]);
        //cena_rows[0] = 0;
        nakop_mili = (Integer.valueOf(kolich_mili) * 0);
        mili_field.setText("" + nakop_mili);
        klass_obsl_combox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                flag = (int) klass_obsl_combox.getSelectedIndex();
                if (flag == 0) {
                    //Эконом
                    cena_rows[0] = (cena_rows_begin * adult + (((cena_rows_begin / 100) * 10)) * child + (((cena_rows_begin / 100) * 74)) * baby) * multiplier[0];
                    cena_field.setText("" + cena_rows[0]);
                    //cena_rows[0] = 0;
                    nakop_mili = (Integer.valueOf(kolich_mili) * 0);
                    mili_field.setText("" + nakop_mili);
                }
                if (flag == 1) {
//Цена бизнес
                    cena_rows[1] = (cena_rows_begin * adult + (((cena_rows_begin / 100) * 10)) * child + (((cena_rows_begin / 100) * 74)) * baby) * multiplier[1];
                    cena_field.setText("" + cena_rows[1]);
                    //cena_rows[1] = 0;
                    nakop_mili = ((Integer.valueOf(kolich_mili) / 100) * 25) * adult;
                    mili_field.setText("" + nakop_mili);
                }
                if (flag == 2) {
//Цена первый
                    cena_rows[2] = (cena_rows_begin * adult + (((cena_rows_begin / 100) * 10)) * child + (((cena_rows_begin / 100) * 74)) * baby) * multiplier[2];
                    cena_field.setText("" + cena_rows[2]);
                    //cena_rows[2] = 0;
                    nakop_mili = ((Integer.valueOf(kolich_mili) / 100) * 45) * adult;
                    mili_field.setText("" + nakop_mili);
                }
            }
        });

        cena_label.setSize(w, h);
        cena_label.setLocation(win_w, win_h + h * 3);
        cena_field.setSize(w_field, h);
        cena_field.setLocation(win_w + 170, win_h + h * 3);
        cena_field.setHorizontalAlignment(JTextField.RIGHT);
        cena_field.setEditable(false);

        mili_label.setSize(w, h);
        mili_label.setLocation(win_w, win_h + h * 4);
        mili_field.setSize(w_field, h);
        mili_field.setLocation(win_w + 170, win_h + h * 4);

        mili_field.setHorizontalAlignment(JTextField.RIGHT);
        mili_field.setEditable(false);

        cancel_klient.setSize(w, h);
        cancel_klient.setLocation(win_w, win_h + h * 5 + 30);
        insert_klient.setSize(w_field, h);
        insert_klient.setLocation(win_w + 170, win_h + h * 5 + 30);

        this.add(nomer_label);
        this.add(nomer_field);

        this.add(kolichestvo_label);
        this.add(kolichestvo_field);

        this.add(klass_obsl_label);
        this.add(klass_obsl_combox);

        this.add(cena_label);
        this.add(cena_field);

        this.add(mili_label);
        this.add(mili_field);

        this.add(cancel_klient);
        this.add(insert_klient);
        cancel_klient.addActionListener((ActionEvent e) -> {
            this.removeAll();
            this.revalidate();
            this.repaint();
            this.updateUI();
            try {
                this.add(new Panel_vibor_rejs());
            } catch (SQLException ex) {
                Logger.getLogger(Panel_klient_new.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        insert_klient.addActionListener((ActionEvent e) -> {
            update_rejs(url, user, password);
            this.removeAll();
            this.revalidate();
            this.repaint();
            this.updateUI();
            this.add(new Panel_zakaz_end());
        });
    }

    public void update_rejs(String url, String user, String password) {
        //model.setValueAt(klient_id.get(i), i, 6);
        try (Connection c = DriverManager.getConnection(url, user, password)) {
            c.setAutoCommit(false);
            c.setReadOnly(false);
            try (PreparedStatement ps2 = c.prepareStatement("UPDATE `rejs_gorod` SET `klass_obsluzhivanija_id`=?, `Cena`=?, `Mili`=? WHERE `id`=1")) {
                ps2.setInt(1, (flag + 1));
                ps2.setInt(2, cena_rows[flag]);
                ps2.setInt(3, nakop_mili);
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
