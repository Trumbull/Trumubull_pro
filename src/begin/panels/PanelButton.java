package begin.panels;

import frame.Vse_klienti;
import frame.Zakaz;
import frame.Info_zakaz;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JPanel;
import size.windows.SizeWindows;

//Начальная панель
public class PanelButton extends JPanel {

    public PanelButton() {
        SizeWindows size = new SizeWindows();
        this.setBounds(0, 0, size.getW(), size.getH_b());
        //this.setLayout(new BorderLayout());
        this.setLayout(null);
        int win_w = size.getWin_w();
        int win_h = size.getWin_h();
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
            } catch (SQLException | ParseException ex) {
                Logger.getLogger(PanelButton.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        b2.addActionListener((java.awt.event.ActionEvent evt) -> {
            b2ActionPerformed();
        });
        b3.addActionListener((java.awt.event.ActionEvent evt) -> {
            try {
                b3ActionPerformed();
            } catch (SQLException ex) {
                Logger.getLogger(PanelButton.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    private void b1ActionPerformed() throws SQLException, ParseException {
        new Zakaz().setVisible(true);
    }

    private void b2ActionPerformed() {
        new Info_zakaz().setVisible(true);
    }

    private void b3ActionPerformed() throws SQLException {
        new Vse_klienti().setVisible(true);
    }
}
