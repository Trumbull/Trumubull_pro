package zakaz.panels;

import conn.Conn;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

//Таблица с клиентами и кнопками 
public class PanelViborRejs extends JPanel {

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();// определяем размер экрана
    private javax.swing.JLabel jLabel1;
    ListSelectionModel listSelectionModel;
    int id_row = 0;
    int id_marshrut = 0;
    int id_rejs = 0;

    public PanelViborRejs() throws SQLException {
        JButton b1 = new JButton("Далее");
        JButton b2 = new JButton("Назад");
        ArrayList<String> data_otk = new ArrayList<>();
        ArrayList<String> time_otk = new ArrayList<>();
        ArrayList<String> data_kuda = new ArrayList<>();
        ArrayList<String> time_kuda = new ArrayList<>();
        ArrayList<String> time_fly = new ArrayList<>();
        ArrayList<String> rejs_nomer = new ArrayList<>();
        ArrayList<Integer> cena = new ArrayList<>();
        ArrayList<Integer> mest_ekonom = new ArrayList<>();
        ArrayList<Integer> mest_bizness = new ArrayList<>();
        ArrayList<Integer> mest_pervyj = new ArrayList<>();
        int cena_rows[] = new int[3];
        int multiplier[] = new int[3]; //множитель
        ZakazP rejs_p = new ZakazP();
        String ajeroport_otk = rejs_p.getAjeroport_otk();
        String ajeroport_kuda = rejs_p.getAjeroport_kuda();
        String date_sql = rejs_p.getDate_sql();
        /*
        String ICAO_otk = rejs_p.getICAO_otk();
        String ICAO_kuda = rejs_p.getICAO_kuda();
         */
        String IATA_otk = rejs_p.getIATA_otk();
        String IATA_kuda = rejs_p.getIATA_kuda();
        int id = 0;
        int id_gorod_otk = rejs_p.getID_gorod_otk();
        int id_gorod_kuda = rejs_p.getID_gorod_kuda();

        int adult = rejs_p.getAdult();
        int child = rejs_p.getChild();
        int baby = rejs_p.getBaby();

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
                        ajeroport_otk = rs.getString("Gorod_otk");
                        ajeroport_kuda = rs.getString("Gorod_kuda");
                        adult = rs.getInt("Adult");
                        child = rs.getInt("Child");
                        baby = rs.getInt("Baby");
                        date_sql = rs.getString("Daty");

                    }
                }
                ps.close();
            }

            try (PreparedStatement ps = c.prepareStatement("SELECT * FROM `ajeroport` WHERE Naznanie=?")) {
                ps.setString(1, ajeroport_otk);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        id_gorod_otk = rs.getInt("Ajeroport_id");
                        IATA_otk = rs.getString("IATA");
                        //ICAO_otk = rs.getString("ICAO");
                    }
                }

                ps.close();
            }

            try (PreparedStatement ps = c.prepareStatement("SELECT * FROM `ajeroport` WHERE Naznanie=?")) {
                ps.setString(1, ajeroport_kuda);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        id_gorod_kuda = rs.getInt("Ajeroport_id");
                        IATA_kuda = rs.getString("IATA");
                        //ICAO_kuda = rs.getString("ICAO");
                    }
                }

                ps.close();
            }

            try (PreparedStatement ps = c.prepareStatement("SELECT * FROM `marshrut` WHERE Ajeropor_otpravleniya_id=? AND Ajeroport_pribitiya_id=?")) {
                ps.setInt(1, id_gorod_otk);
                ps.setInt(2, id_gorod_kuda);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        id_marshrut = rs.getInt("Marshrut_id");

                    }
                }

                ps.close();
            }
            System.out.println(id_marshrut);
            try (PreparedStatement ps = c.prepareStatement("SELECT * FROM `rejs` WHERE Marshrut_id=? AND Data_vsleta=?")) {
                ps.setInt(1, id_marshrut);
                ps.setString(2, date_sql);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {

                        data_otk.add(rs.getString("Data_vsleta"));
                        time_otk.add(rs.getString("Vremya_vsleta"));

                        data_kuda.add(rs.getString("Data_posadki"));
                        time_kuda.add(rs.getString("Vremya_posadki"));
                        time_fly.add(rs.getString("Vremya_poleta"));

                        rejs_nomer.add(rs.getString("Nomer"));
                        cena.add(rs.getInt("Cena"));

                        mest_ekonom.add(rs.getInt("Mest_ekonom"));
                        mest_bizness.add(rs.getInt("Mest_bizness"));
                        mest_pervyj.add(rs.getInt("Mest_pervyj"));
                        id++;
                    }
                }
                ps.close();
            }
            try (PreparedStatement ps = c.prepareStatement("SELECT * FROM `klass_obsluzhivanija`")) {
                try (ResultSet rs = ps.executeQuery()) {
                    int i = 0;
                    while (rs.next()) {
                        multiplier[i] = rs.getInt("Mnozhitel");
                        i++;
                        //ICAO_kuda = rs.getString("ICAO");
                    }
                }

                ps.close();
            }
            c.commit();
            c.commit();
            c.close();
        } catch (SQLException se) {
        } catch (Exception e) {
        }

        /*
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
         */
        JScrollPane scrollPane = new JScrollPane();
        int h = screenSize.height / 2 + 220; //высота скролла
        scrollPane.setBounds(0, 0, screenSize.width, h);
        this.setBounds(0, 0, screenSize.width, screenSize.height);
        //getContentPane().setLayout(new BorderLayout());
        this.setLayout(null);
        this.add(scrollPane);
        b1.setBounds(0, h + 5, screenSize.width, 45);
        b2.setBounds(0, h + 5 + 45, screenSize.width, 45);

        this.add(b1);
        this.add(b2);

        JTable table = new JTable();
        scrollPane.setViewportView(table);
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        //table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        //setBounds(0, 0, screenSize.width, screenSize.height);
        //model.addColumn("id");
        model.addColumn("Отправление");
        model.addColumn("Прибытия");
        model.addColumn("Информация о рейсе");
        model.addColumn("Общее время в пути");
        model.addColumn("Эконом");
        model.addColumn("Бизнес");
        model.addColumn("Первый");

        for (int i = 0; i < id; i++) {
            model.addRow(new Object[0]);

            model.setValueAt("<html><center>" + IATA_otk + "<br>" + time_otk.get(i) + "<br>" + data_otk.get(i) + "<br></center></html>", i, 0);
//model.setValueAt(IATA_otk + "" + time_otk.get(0) + "\n" + data_otk.get(0), 0, 0);
            model.setValueAt("<html><center>" + IATA_kuda + "<br>" + time_kuda.get(i) + "<br>" + data_kuda.get(i) + "<br></center></html>", i, 1);
            model.setValueAt(rejs_nomer.get(i) + " | " + IATA_otk + " в " + IATA_kuda, i, 2);
            model.setValueAt(time_fly.get(i), i, 3);

            //Цена эконома
            cena_rows[0] = (cena.get(i) * adult + (((cena.get(i) / 100) * 10)) * child + (((cena.get(i) / 100) * 74)) * baby) * multiplier[0];

            //Цена бизнес
            cena_rows[1] = (cena.get(i) * adult + (((cena.get(i) / 100) * 10)) * child + (((cena.get(i) / 100) * 74)) * baby) * multiplier[1];

            //Цена первый
            cena_rows[2] = (cena.get(i) * adult + (((cena.get(i) / 100) * 10)) * child + (((cena.get(i) / 100) * 74)) * baby) * multiplier[2];

            model.setValueAt(cena_rows[0] + " RUB", i, 4);
            model.setValueAt(cena_rows[1] + " RUB", i, 5);
            model.setValueAt(cena_rows[2] + " RUB", i, 6);
        }
        table.setRowHeight(60);

        //выравнивание таблицы
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        leftRenderer.setHorizontalAlignment(JLabel.LEFT);
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        /*
        table.getColumnModel().getColumn(0).setHeaderRenderer(centerRenderer1);
        table.getColumnModel().getColumn(1).setHeaderRenderer(centerRenderer1);
        
        table.getColumnModel().getColumn(1).setHeaderRenderer(centerRenderer);
        table.getColumnModel().getColumn(2).setHeaderRenderer(centerRenderer);
        table.getColumnModel().getColumn(3).setHeaderRenderer(centerRenderer);
        table.getColumnModel().getColumn(4).setHeaderRenderer(centerRenderer);
        table.getColumnModel().getColumn(5).setHeaderRenderer(centerRenderer);
        table.getColumnModel().getColumn(6).setHeaderRenderer(centerRenderer);
         */
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        //table.getColumnModel().getColumn(2).setCellRenderer(leftRenderer);

        // table.getColumnModel().getColumn(1).setCellRenderer(textRenderer);
        //table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        //table.getColumnModel().getColumn(0).setCellRenderer(leftRenderer);
        //table.getColumnModel().getColumn(1).setCellRenderer(leftRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);

        listSelectionModel = table.getSelectionModel();
        listSelectionModel
                .addListSelectionListener(new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent e) {
                        ListSelectionModel lsm = (ListSelectionModel) e.getSource();

                        //int firstIndex = e.getFirstIndex();
                        // int lastIndex = e.getLastIndex();
                        // boolean isAdjusting = e.getValueIsAdjusting();
                        /*
                        System.out.println("Первая ячейка " + firstIndex + " - " + lastIndex
                                + "; " + isAdjusting + "; selected indexes:");
                         */
                        if (lsm.isSelectionEmpty()) {
                            System.out.println(" <none>");
                        } else {
                            // Find out which indexes are selected.
                            int minIndex = lsm.getMinSelectionIndex();
                            int maxIndex = lsm.getMaxSelectionIndex();
                            for (int i = minIndex; i <= maxIndex; i++) {
                                if (lsm.isSelectedIndex(i)) {
                                    System.out.println("" + i); //Строчка 
                                    id_row = i;
                                }
                            }
                        }
                    }
                }
                );
        table.setSelectionModel(listSelectionModel);

        b1.addActionListener((ActionEvent e) -> {
            if (mest_ekonom.get(id_row) == 0 && mest_bizness.get(id_row) == 0 && mest_pervyj.get(id_row) == 0) {
                JOptionPane.showMessageDialog(this,
                        "Нет мест!");
            } else {
                insert_vibor(url, user, password, cena, rejs_nomer);
                this.removeAll();
                this.revalidate();
                this.repaint();
                this.updateUI();
                this.add(new PanelZakazElement());
            }

        });

        b2.addActionListener((ActionEvent e) -> {
            System.out.println("Работает!");
            this.removeAll();
            this.revalidate();
            this.repaint();
            this.updateUI();
            try {
                this.add(new PanelBegin());
            } catch (SQLException ex) {
                Logger.getLogger(PanelViborRejs.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(PanelViborRejs.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    public void insert_vibor(String url, String user, String password, ArrayList<Integer> cena, ArrayList<String> rejs_nomer) {
        try (Connection c = DriverManager.getConnection(url, user, password)) {
            c.setAutoCommit(false);
            c.setReadOnly(false);
            try (PreparedStatement ps2 = c.prepareStatement("UPDATE `mydb`.`rejs_gorod` SET `Marshrut_id` = ?, `Rejs_nomer` = ?, `Cena` = ? WHERE `rejs_gorod`.`id` = 1;")) {
                ps2.setInt(1, id_marshrut);
                ps2.setString(2, rejs_nomer.get(id_row));
                ps2.setInt(3, cena.get(id_row));
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
