package Zakaz_panels;

import Connect.Conn;
import Interface.table;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
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

public class Panel_oplata extends JPanel {

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();// определяем размер экрана
    int flag = 0;
    private JLabel price_Label;
    private JComboBox<String> vid_oplaty_ComboBox;
    private JTextField price_Field;
    private JLabel kreditnaya_karta_Label;
    private JLabel mili_Label;
    private JTextField kreditnaya_karta_Field;
    private JTextField mili_Field;
    private Zakaz_p p = new Zakaz_p();
    private int element_id = p.getElement_id();
    private int cena = p.getCena();
    private int klient_id = p.getId();
    private int mili = p.getMili();
    private int mili_begin = 0;
    private int cena_rows;
    private JButton b1 = new JButton("Назад");
    private JButton b2 = new JButton("Оплатить");

    public Panel_oplata() {

        this.setLayout(null);
        this.setBounds(0, 0, screenSize.width, screenSize.height);
        JLabel zakaz_nomer_Label;

        JLabel vid_oplaty_Label;

        JTextField zakaz_nomer_Field = new JTextField();
        kreditnaya_karta_Field = new JTextField();
        mili_Field = new JTextField();
        vid_oplaty_ComboBox = new JComboBox<>();
        price_Field = new JTextField();

        zakaz_nomer_Label = new JLabel("Номер заказа");
        vid_oplaty_Label = new JLabel("Вид оплаты");
        kreditnaya_karta_Label = new JLabel("Кредитная карта");
        mili_Label = new JLabel("Милей");
        price_Label = new JLabel("Цена");
        mili_Label = new JLabel("Мили");

        int win_w = screenSize.width / 2 - 200;
        int win_h = screenSize.height / 3 - 180;
        int w = 150;
        int w_field = 150;
        int h = 30;
        int g = 11;

        Conn conn = new Conn();
        String url = conn.getUrl();
        String user = conn.getUser();
        String password = conn.getPassword();

        try (Connection c = DriverManager.getConnection(url, user, password)) {
            c.setAutoCommit(false);
            c.setReadOnly(false);
            try (PreparedStatement ps = c.prepareStatement("SELECT  * FROM `rejs_gorod` WHERE id=1;")) {
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        element_id = rs.getInt("Element_id");
                        cena = rs.getInt("Cena");
                    }
                }
                ps.close();
            }
            try (PreparedStatement ps = c.prepareStatement("SELECT  * FROM `klient_zakaz` WHERE `Element_zakaza_id`=?")) {
                ps.setInt(1, element_id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        klient_id = rs.getInt("Klient_id");
                    }
                }
                ps.close();
            }
            try (PreparedStatement ps = c.prepareStatement("SELECT  * FROM `klient` WHERE `Klient_id`=?")) {
                ps.setInt(1, klient_id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        mili = rs.getInt("Mili");
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

        zakaz_nomer_Label.setSize(w, h);
        zakaz_nomer_Label.setLocation(win_w, win_h);
        zakaz_nomer_Field.setSize(w_field, h);
        zakaz_nomer_Field.setLocation(win_w + 170, win_h);
        zakaz_nomer_Field.setText("" + element_id);

        vid_oplaty_Label.setSize(w, h);
        vid_oplaty_Label.setLocation(win_w, win_h + h);
        vid_oplaty_ComboBox.setSize(w_field, h);
        vid_oplaty_ComboBox.setLocation(win_w + 170, win_h + h);

        vid_oplaty_ComboBox.addItem("Наличные");
        vid_oplaty_ComboBox.addItem("Элект. карта");
        vid_oplaty_ComboBox.addItem("Милями");
        //vid_oplaty_ComboBox.setSelectedIndex(0);
        vid_oplaty_ComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                flag = (int) vid_oplaty_ComboBox.getSelectedIndex();

                switch (flag) {
                    case 0:
                        price_Label.setSize(w, h);
                        price_Label.setLocation(win_w, win_h + h * 2);
                        price_Field.setSize(w_field, h);
                        price_Field.setLocation(win_w + 170, win_h + h * 2);

                        b1.setSize(w, h);
                        b1.setLocation(win_w, win_h + h * 3);
                        b2.setSize(w_field, h);
                        b2.setLocation(win_w + 170, win_h + h * 3);
                        price_Field.setText("" + cena);
                        price_label();

                        kreditnaya_karta_Label.setVisible(false);
                        kreditnaya_karta_Field.setVisible(false);
                        mili_Label.setVisible(false);
                        mili_Field.setVisible(false);

                        break;

                    case 1:
                        kreditnaya_karta_Label.setSize(w, h);
                        kreditnaya_karta_Label.setLocation(win_w, win_h + h * 2);
                        kreditnaya_karta_Field.setSize(w_field, h);
                        kreditnaya_karta_Field.setLocation(win_w + 170, win_h + h * 2);

                        price_Field.setText("" + cena);
                        price_Label.setSize(w, h);
                        price_Label.setLocation(win_w, win_h + h * 3);
                        price_Field.setSize(w_field, h);
                        price_Field.setLocation(win_w + 170, win_h + h * 3);

                        b1.setSize(w, h);
                        b1.setLocation(win_w, win_h + h * 4);
                        b2.setSize(w_field, h);
                        b2.setLocation(win_w + 170, win_h + h * 4);

                        kreditnaya_karta_Label.setVisible(true);
                        kreditnaya_karta_Field.setVisible(true);
                        mili_Label.setVisible(false);
                        mili_Field.setVisible(false);
                        karta_price();
                        break;
                    case 2:
                        mili_Label.setSize(w, h);
                        mili_Label.setLocation(win_w, win_h + h * 2);
                        mili_Field.setSize(w_field, h);
                        mili_Field.setLocation(win_w + 170, win_h + h * 2);

                        cena_rows = cena - (mili / 2);
                        if (cena_rows < 0) {
                            mili_begin = (0 - cena_rows) * 2;
                            mili_Field.setText("" + mili_begin);

                            //cena_rows = 0;
                        } else {
                            mili_Field.setText("0");
                        }
                        price_Field.setText("" + cena_rows);
                        price_Label.setSize(w, h);
                        price_Label.setLocation(win_w, win_h + h * 3);
                        price_Field.setSize(w_field, h);
                        price_Field.setLocation(win_w + 170, win_h + h * 3);

                        b1.setSize(w, h);
                        b1.setLocation(win_w, win_h + h * 4);
                        b2.setSize(w_field, h);
                        b2.setLocation(win_w + 170, win_h + h * 4);

                        kreditnaya_karta_Label.setVisible(false);
                        kreditnaya_karta_Field.setVisible(false);
                        mili_Label.setVisible(true);
                        mili_Field.setVisible(true);
                        mili_price();

                        break;

                }

            }

        }
        );

        this.add(zakaz_nomer_Label);
        this.add(zakaz_nomer_Field);
        this.add(vid_oplaty_Label);
        this.add(vid_oplaty_ComboBox);
        this.add(b1);
        this.add(b2);

        b2.addActionListener((java.awt.event.ActionEvent evt) -> {
            update_vid_oplaty(url, user, password);
            this.removeAll();
            this.revalidate();
            this.repaint();
            this.updateUI();

        });
         b1.addActionListener((java.awt.event.ActionEvent evt) -> {
            update_vid_oplaty(url, user, password);
            this.removeAll();
            this.revalidate();
            this.repaint();
            this.updateUI();

        });

    }

    public void update_vid_oplaty(String url, String user, String password) {
        //model.setValueAt(klient_id.get(i), i, 6);
        try (Connection c = DriverManager.getConnection(url, user, password)) {
            c.setAutoCommit(false);
            c.setReadOnly(false);
            try (PreparedStatement ps2 = c.prepareStatement("UPDATE `element_zakaza` SET `Cena`=? WHERE `Element_zakaza_id`=?")) {
                ps2.setInt(1, cena_rows);
                ps2.setInt(2, element_id);
                ps2.executeUpdate();
            }
            try (PreparedStatement ps2 = c.prepareStatement("UPDATE `klient_zakaz` SET `Vid_oplaty_id`=? WHERE `Element_zakaza_id`=?")) {
                ps2.setInt(1, (flag + 1));
                ps2.setInt(2, element_id);
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
    public void delect(String url, String user, String password) {
        //model.setValueAt(klient_id.get(i), i, 6);
        try (Connection c = DriverManager.getConnection(url, user, password)) {
            c.setAutoCommit(false);
            c.setReadOnly(false);
            try (PreparedStatement ps2 = c.prepareStatement("UPDATE `zakaz` SET `Vid_oplaty_id`=? WHERE `Zakaz_id`=?")) {
                ps2.setInt(1, (flag + 1));
                ps2.setInt(2, element_id);
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

    public void price_label() {
        this.add(price_Label);
        this.add(price_Field);
    }

    public void karta_price() {
        this.add(price_Label);
        this.add(price_Field);
        this.add(kreditnaya_karta_Label);
        this.add(kreditnaya_karta_Field);
    }

    public void mili_price() {
        this.add(mili_Label);
        this.add(mili_Field);
        this.add(price_Label);
        this.add(price_Field);

    }
}
