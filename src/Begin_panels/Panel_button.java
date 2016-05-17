package Begin_panels;

import Interface.Vse_klienti;
import Interface.Zakaz;
import Interface.table;
import Zakaz_panels.Panel_zakaz_element;
import Zakaz_panels.Panel_vibor_klienta;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JPanel;

//Начальная панель
public class Panel_button extends JPanel {

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();// определяем размер экрана

    public Panel_button() {

        this.setBounds(0, 0, screenSize.width, screenSize.height / 2 + 300);
        //this.setLayout(new BorderLayout());
        this.setLayout(null);
        int win_w = screenSize.width / 2 - 80;
        int win_h = screenSize.height / 3 - 180;
        int w = 170;
        int h = 30;
        JButton b1 = new JButton();
        JButton b2 = new JButton();
        JButton b3 = new JButton();
        JButton b4 = new JButton();

        b1.setText("Добавить заказ");
        b1.setSize(w, h);
        b1.setLocation(win_w, win_h);
        b2.setText("Информация о заказах");
        b2.setSize(w, h);
        b2.setLocation(win_w, win_h + 32);
        b3.setText("Информация о клиентах");
        b3.setSize(w, h);
        b3.setLocation(win_w, win_h + 64);
        b4.setSize(w, h);

        this.add(b1);
        this.add(b2);
        this.add(b3);
        this.add(b4); //заглушка
        b4.setVisible(false);
        b1.addActionListener((java.awt.event.ActionEvent evt) -> {
            try {
                b1ActionPerformed();
            } catch (SQLException ex) {
                Logger.getLogger(Panel_button.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        b2.addActionListener((java.awt.event.ActionEvent evt) -> {
            b2ActionPerformed();
        });
        b3.addActionListener((java.awt.event.ActionEvent evt) -> {
            try {
                b3ActionPerformed();
            } catch (SQLException ex) {
                Logger.getLogger(Panel_button.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    private void b1ActionPerformed() throws SQLException {
        new Zakaz().setVisible(true);
    }

    private void b2ActionPerformed() {
        this.removeAll();
        this.revalidate();
        this.repaint();
        this.updateUI();
        new table().setVisible(true);
    }

    private void b3ActionPerformed() throws SQLException {
        new Vse_klienti().setVisible(true);
    }
}
