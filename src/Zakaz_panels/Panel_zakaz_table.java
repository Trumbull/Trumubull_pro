package Zakaz_panels;

import Connect.Conn;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Panel_zakaz_table extends JPanel {

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();// определяем размер экрана

    public Panel_zakaz_table() {
        Conn conn = new Conn();
        String url = conn.getUrl();
        String user = conn.getUser();
        String password = conn.getPassword();
        int id = 0;
        ArrayList<Integer> element_zakaza_id = new ArrayList<>();
        //ArrayList<Integer> rejs_id = new ArrayList<>();
        ArrayList<Integer> klient_id = new ArrayList<>();
        ArrayList<Integer> kolichestvo = new ArrayList<>();
        ArrayList<Integer> cena = new ArrayList<>();
        ArrayList<String> klient_F = new ArrayList<>();
        ArrayList<String> klient_I = new ArrayList<>();
        ArrayList<String> klient_O = new ArrayList<>();
        ArrayList<String> nomer_rejs = new ArrayList<>();
        try (Connection c = DriverManager.getConnection(url, user, password)) {
            c.setAutoCommit(false);
            c.setReadOnly(false);

            try (PreparedStatement ps = c.prepareStatement("SELECT * FROM `element_zakaza`")) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        element_zakaza_id.add(rs.getInt("Element_zakaza_id"));
                        kolichestvo.add(rs.getInt("Kolychestvo"));
                        //rejs_id.add(rs.getInt("Rejs_id"));
                        cena.add(rs.getInt("Cena"));
                        id++;
                    }
                }
                ps.close();
            }
            for (int i = 0; i < element_zakaza_id.size(); i++) {
                try (PreparedStatement ps = c.prepareStatement("SELECT * FROM `klient_zakaz` WHERE `Element_zakaza_id`=?")) {
                    ps.setInt(1, element_zakaza_id.get(i));
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            klient_id.add(rs.getInt("Klient_id"));
                        }
                    }
                    ps.close();
                }
            }
            for (int i = 0; i < klient_id.size(); i++) {
                try (PreparedStatement ps = c.prepareStatement("SELECT * FROM `klient` WHERE `Klient_id`=?")) {
                    ps.setInt(1, klient_id.get(i));
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            klient_F.add(rs.getString("Familiya"));
                            klient_I.add(rs.getString("Imja"));
                            klient_O.add(rs.getString("Otshestvo"));
                        }
                    }
                    ps.close();
                }
            }
            //System.out.println("ID: " + id);
            try (PreparedStatement ps = c.prepareStatement("SELECT  * FROM `rejs`")) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        nomer_rejs.add(rs.getString("Nomer"));
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
        /*
            for (int i = 0; i <= (id - 1); i++) {
                System.out.println("ID: " + id_flag[i]);
            }*/

//Скролл
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 0, screenSize.width, screenSize.height - 120);
        this.setBounds(0, 0, screenSize.width, screenSize.height);

        this.setLayout(null);
        this.add(scrollPane);
//Таблица
        JTable table = new JTable();
        scrollPane.setViewportView(table);
        //getContentPane().add(scrollPane, BorderLayout.CENTER);
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        //table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        //setBounds(0, 0, screenSize.width, screenSize.height);
        //model.addColumn("id");
        model.addColumn("Номер");
        model.addColumn("Номер");
        model.addColumn("Количество клиент");
        model.addColumn("Главный клиент");
        model.addColumn("Цена");
        
        table.setRowHeight(60);
        //выравнивание таблицы
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);

        //размеры столбцов            
        /*
            TableColumnModel colsize = table.getColumnModel();
            colsize.getColumn(0).setPreferredWidth(100);
            colsize.getColumn(1).setPreferredWidth(80);
            colsize.getColumn(2).setPreferredWidth(100);
            colsize.getColumn(3).setPreferredWidth(80);
            colsize.getColumn(4).setPreferredWidth(65);
            colsize.getColumn(5).setPreferredWidth(180);
         */
        //Строки
        for (int i = 0; i <= (id - 1); i++) {

            model.addRow(new Object[0]);
            //model.setValueAt(i + 1, i, 0);
            model.setValueAt(element_zakaza_id.get(i), i, 0);
            model.setValueAt(nomer_rejs.get(i), i, 1);
            model.setValueAt(kolichestvo.get(i), i, 2);
            model.setValueAt("<html><center>" + klient_F.get(i) + "<br>" + klient_I.get(i) + "<br>" + klient_O.get(i) + "<br></center></html>", i, 3);
            model.setValueAt(cena.get(i), i, 4);
        }

    }

}
