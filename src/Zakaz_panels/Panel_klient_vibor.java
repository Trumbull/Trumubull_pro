package Zakaz_panels;

import Interface.Zakaz;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

//Таблица с клиентами и кнопками 
public class Panel_klient_vibor extends JPanel {

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();// определяем размер экрана
    int flag;
    private javax.swing.JLabel jLabel1;
    public JButton b3;

    public Panel_klient_vibor() {
        JButton b1 = new JButton("Внести");;
        JButton b2 = new JButton("Добавить нового клиента");
        b3 = new JButton("Отмена");
        Map<Integer, String> codes = new HashMap<>();
        String url = "jdbc:mysql://localhost:3306/mydb", user = "root", password = "";
        try (Connection c = DriverManager.getConnection(url, user, password)) {
            c.setAutoCommit(false);
            c.setReadOnly(false);
            int shet = 0; //счетчик
            int id = 0;
            String imja[] = new String[500];
            String familiya[] = new String[500];
            String otchestvo[] = new String[500];
            String mili[] = new String[500];
            //String nomer_pasporta[] = new String[500];
            String data_rog[] = new String[500];
            int id_flag[] = new int[500]; // id стран
            try (PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM `klient`")) {
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        id = rs.getInt("COUNT(*)");
                    }
                }
                ps.close();
            }
            try (PreparedStatement ps = c.prepareStatement("SELECT * FROM `strana`")) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        int strana_id = rs.getInt("Strana_id");
                        String nazv = rs.getString("Nazvanie");

                        codes.put(strana_id, nazv);
                    }
                }
                ps.close();
            }

            //System.out.println("ID: " + id);
            try (PreparedStatement ps = c.prepareStatement("SELECT  * FROM `klient`")) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        imja[shet] = rs.getString("Imja");
                        familiya[shet] = rs.getString("Familiya");
                        otchestvo[shet] = rs.getString("Otshestvo");
                        mili[shet] = rs.getString("Mili");
                        //nomer_pasporta[shet] = rs.getString("Nomer_pasporta");
                        data_rog[shet] = rs.getString("Data_rogdeniya");
                        id_flag[shet] = rs.getInt("Strana_id");
                        shet++;
                    }
                }
                ps.close();
            }

            JScrollPane scrollPane = new JScrollPane();
            int h = screenSize.height / 2 + 340; //высота скролла
            scrollPane.setBounds(0, 0, screenSize.width, h);
            setBounds(0, 0, screenSize.width, screenSize.height);
            //getContentPane().setLayout(new BorderLayout());
            setLayout(new BorderLayout());
            add(scrollPane);
            b1.setBounds(0, h + 5, screenSize.width, 30);
            b2.setBounds(0, h + 5 + 30, screenSize.width, 30);
            b3.setBounds(0, h + 5 + 60, screenSize.width, 30);
            JButton b4 = new JButton("Бред");
            add(b1);
            add(b2);
            add(b3);
            add(b4);//заглушка
            b4.setVisible(false); // не мешала

            JTable table = new JTable();
            scrollPane.setViewportView(table);
            DefaultTableModel model = (DefaultTableModel) table.getModel();

            //table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
            //setBounds(0, 0, screenSize.width, screenSize.height);
            //model.addColumn("id");
            model.addColumn("Фамилия");
            model.addColumn("Имя");
            model.addColumn("Отчество");
            model.addColumn("Дата рождения");
            model.addColumn("Мили");
            model.addColumn("Страна");

            //выравнивание таблицы
            DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
            DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
            rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
            leftRenderer.setHorizontalAlignment(JLabel.LEFT);
            table.getColumnModel().getColumn(0).setCellRenderer(leftRenderer);
            table.getColumnModel().getColumn(1).setCellRenderer(leftRenderer);
            table.getColumnModel().getColumn(2).setCellRenderer(leftRenderer);
            table.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
            table.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
            for (int i = 0; i <= (id - 1); i++) {

                model.addRow(new Object[0]);
                //model.setValueAt(i + 1, i, 0);
                model.setValueAt(familiya[i], i, 0);
                model.setValueAt(imja[i], i, 1);
                model.setValueAt(otchestvo[i], i, 2);
                model.setValueAt(data_rog[i], i, 3);
                model.setValueAt(mili[i], i, 4);
                model.setValueAt(codes.get(id_flag[i]), i, 5);

            }

            c.commit();
            c.commit();
            c.close();
        } catch (SQLException se) {
        } catch (Exception e) {
        }
        b2.addActionListener((ActionEvent e) -> {
            this.removeAll();
            this.revalidate();
            this.repaint();
            this.updateUI();
            this.add(new Panel_klient_new());
        });

        b3.addActionListener((ActionEvent e) -> {
            System.out.println("Работает!");
            this.removeAll();
            this.revalidate();
            this.repaint();
            this.updateUI();
            try {
                this.add(new Panel_begin());
            } catch (SQLException ex) {
                Logger.getLogger(Panel_klient_vibor.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

}
