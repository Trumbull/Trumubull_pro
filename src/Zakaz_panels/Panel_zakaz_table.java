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
        model.addColumn("Цена");

        //выравнивание таблицы
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        leftRenderer.setHorizontalAlignment(JLabel.LEFT);
        table.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);

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
            model.setValueAt(cena.get(i), i, 3);;
        }

    }

}
