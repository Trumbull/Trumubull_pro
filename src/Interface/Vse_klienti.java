/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class Vse_klienti extends javax.swing.JFrame {

    @SuppressWarnings("CallToPrintStackTrace")
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();// определяем размер экрана

    public Vse_klienti() throws SQLException {
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
            String nomer_pasporta[] = new String[500];
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
                        nomer_pasporta[shet] = rs.getString("Nomer_pasporta");
                        data_rog[shet] = rs.getString("Data_rogdeniya");
                        id_flag[shet] = rs.getInt("Strana_id");
                        shet++;
                    }
                }
                ps.close();
            }
            /*
            for (int i = 0; i <= (id - 1); i++) {
                System.out.println("ID: " + id_flag[i]);
            }*/
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            setBounds(100, 100, 280, 500);//размеры окна таблицы

            setTitle("Сведения о клиентах");
//Скролл
            JScrollPane scrollPane = new JScrollPane();
            System.out.println("Ширина: " + screenSize.height);
            scrollPane.setBounds(0, 0, screenSize.width, screenSize.height - 120);

            getContentPane().setLayout(new BorderLayout());
            getContentPane().add(scrollPane);
//Таблица
            JTable table = new JTable();
            scrollPane.setViewportView(table);
            //getContentPane().add(scrollPane, BorderLayout.CENTER);
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
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setExtendedState(MAXIMIZED_BOTH
        );
        setLocationByPlatform(true);
        setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 961, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 329, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowOpened

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowActivated

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Vse_klienti.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Vse_klienti.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Vse_klienti.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Vse_klienti.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Vse_klienti().setVisible(true);

                } catch (SQLException ex) {
                    Logger.getLogger(Vse_klienti.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
