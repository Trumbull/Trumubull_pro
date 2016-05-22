package Zakaz_panels;

import Connect.Conn;
import Interface.Info_zakaz;
import java.awt.BorderLayout;
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
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class Panel_vibor_klienta extends JPanel {

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();// определяем размер экрана

    private JTextField filterText;
    ListSelectionModel listSelectionModel;
    JTable table_klient = new JTable();
    int sort_id = 0;
    private int id_row = 0;
    int id_row_flag = 0;
    DefaultTableModel model = (DefaultTableModel) table_klient.getModel();
    private TableRowSorter<DefaultTableModel> sorter;

    public Panel_vibor_klienta() {
        sorter = new TableRowSorter<>(model);
        JLabel l1 = new JLabel("Найти:");
        JButton b1 = new JButton("Далее");
        JButton b2 = new JButton("Добавить новый");
        JButton b3 = new JButton("Назад");
        Map<Integer, String> codes = new HashMap<>();
        Conn conn = new Conn();
        String url = conn.getUrl();
        String user = conn.getUser();
        String password = conn.getPassword();
        int shet = 0; //счетчик
        int id = 0;
        ArrayList<Integer> klient_id = new ArrayList<>();
        ArrayList<String> imja = new ArrayList<>();
        ArrayList<String> familiya = new ArrayList<>();
        ArrayList<String> otchestvo = new ArrayList<>();
        ArrayList<String> mili = new ArrayList<>();
        //String nomer_pasporta[] = new String[500];
        ArrayList<String> data_rog = new ArrayList<>();
        int id_flag[] = new int[500]; // id стран
        try (Connection c = DriverManager.getConnection(url, user, password)) {
            c.setAutoCommit(false);
            c.setReadOnly(false);

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
                        klient_id.add(rs.getInt("Klient_id"));
                        imja.add(rs.getString("Imja"));
                        familiya.add(rs.getString("Familiya"));
                        otchestvo.add(rs.getString("Otshestvo"));
                        mili.add(rs.getString("Mili"));
                        //nomer_pasporta[shet] = rs.getString("Nomer_pasporta");
                        data_rog.add(rs.getString("Data_rogdeniya"));
                        id_flag[shet] = rs.getInt("Strana_id");
                        shet++;
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

//Скролл
        JScrollPane scrollPane = new JScrollPane();
        int h = screenSize.height / 2 + 220; //высота скролла
        scrollPane.setBounds(0, 0, screenSize.width, h - 30);
        this.setBounds(0, 0, screenSize.width, screenSize.height);
        this.setLayout(null);
        this.add(scrollPane);

        l1.setBounds(0, h - 30, 40, 30);
        filterText = new JTextField();
        filterText.setBounds(40, h - 30, screenSize.width - 40, 30);
        l1.setLabelFor(filterText);

        filterText.getDocument().addDocumentListener(
                new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                newFilter();
            }

            public void insertUpdate(DocumentEvent e) {
                newFilter();
            }

            public void removeUpdate(DocumentEvent e) {
                newFilter();
            }
        });
        table_klient.setRowSorter(sorter);
        listSelectionModel = table_klient.getSelectionModel();
        listSelectionModel
                .addListSelectionListener(new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent e) {
                        ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                        /*
                        int firstIndex = e.getFirstIndex();
                        int lastIndex = e.getLastIndex();
                        boolean isAdjusting = e.getValueIsAdjusting();

                        System.out.println("Строчка " + firstIndex + " - " + lastIndex
                                + "; " + isAdjusting + "; selected indexes:");*/
                        int id2 = table_klient.getSelectedRow();
                        sort_id = table_klient.getRowSorter().convertRowIndexToModel(id2);
                        if (lsm.isSelectionEmpty()) {
                            System.out.println(" <none>");
                        } else {
                            // Find out which indexes are selected.
                            int minIndex = lsm.getMinSelectionIndex();
                            int maxIndex = lsm.getMaxSelectionIndex();
                            for (int i = minIndex; i <= maxIndex; i++) {
                                if (lsm.isSelectedIndex(i)) {
                                    //System.out.println("" + i); //Строчка 
                                    id_row = i;
                                    id_row = sort_id;
                                }
                            }
                        }
                    }
                }
                );
        table_klient.setSelectionModel(listSelectionModel);
        b1.setBounds(0, h + 5, screenSize.width, 30);
        b2.setBounds(0, h + 5 + 30, screenSize.width, 30);
        b3.setBounds(0, h + 5 + 60, screenSize.width, 30);

        this.add(l1);
        this.add(filterText);
        this.add(b1);
        this.add(b2);
        this.add(b3);

//Таблица
        scrollPane.setViewportView(table_klient);
        //getContentPane().add(scrollPane, BorderLayout.CENTER);

        //table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        //setBounds(0, 0, screenSize.width, screenSize.height);
        //model.addColumn("id");
        model.addColumn("Фамилия");
        model.addColumn("Имя");
        model.addColumn("Отчество");
        model.addColumn("Дата рождения");
        model.addColumn("Мили");
        model.addColumn("Страна");
        //model.addColumn("id");

        //выравнивание таблицы
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        leftRenderer.setHorizontalAlignment(JLabel.LEFT);
        table_klient.getColumnModel().getColumn(0).setCellRenderer(leftRenderer);
        table_klient.getColumnModel().getColumn(1).setCellRenderer(leftRenderer);
        table_klient.getColumnModel().getColumn(2).setCellRenderer(leftRenderer);
        table_klient.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
        table_klient.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);

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
            model.setValueAt(familiya.get(i), i, 0);
            model.setValueAt(imja.get(i), i, 1);
            model.setValueAt(otchestvo.get(i), i, 2);
            model.setValueAt(data_rog.get(i), i, 3);
            model.setValueAt(mili.get(i), i, 4);
            model.setValueAt(codes.get(id_flag[i]), i, 5);

        }

        b1.addActionListener((ActionEvent e) -> {
            System.out.println(id_row + 1);
            insert_klient(url, user, password);
            this.removeAll();
            this.revalidate();
            this.repaint();
            this.updateUI();
            this.add(new Panel_zakaz_end());

        });
        b2.addActionListener((ActionEvent e) -> {
            this.removeAll();
            this.revalidate();
            this.repaint();
            this.updateUI();
            try {
                this.add(new Panel_klient_new());
            } catch (SQLException ex) {
                Logger.getLogger(Panel_vibor_klienta.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(Panel_vibor_klienta.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void insert_klient(String url, String user, String password) {
        //model.setValueAt(klient_id.get(i), i, 6);
        int vozrast = 0;
       // int id_klient
        int id_zakaz_klient = 0;
        int id_element_zakaza = 0;
        int id_klass_obls = 0;
        int id_rejs = 0;
        String nomer_rejs = null;
        int id_mesto = 0;
        System.out.println("Работает!");
        try (Connection c = DriverManager.getConnection(url, user, password)) {
            c.setAutoCommit(false);
            c.setReadOnly(false);
            try (PreparedStatement ps = c.prepareStatement("SELECT * FROM `element_zakaza`")) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        id_element_zakaza = rs.getInt("Element_zakaza_id");
                    }
                }

                ps.close();
            }

            System.out.println(id_element_zakaza);
            try (PreparedStatement ps = c.prepareStatement("SELECT * FROM `rejs_gorod` WHERE id=1")) {
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        vozrast = rs.getInt("Vozrast");
                        id_klass_obls = rs.getInt("klass_obsluzhivanija_id");
                        nomer_rejs = rs.getString("Rejs_nomer");

                    }
                }

                ps.close();
            }
            try (PreparedStatement ps = c.prepareStatement("SELECT * FROM `klient_zakaz` WHERE `Element_zakaza_id`=? AND `Vozrast`=? AND `Klient_id`=?")) {
                ps.setInt(1, (id_element_zakaza + 1));
                ps.setInt(2, vozrast);
                ps.setInt(3, (id_row + 1));
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        id_zakaz_klient = rs.getInt("Klient_zakaz_id");

                    }
                }

                ps.close();
            }
            

            if (id_zakaz_klient <= 0) {
                try (PreparedStatement ps = c.prepareStatement("SELECT * FROM `klient_zakaz`")) {
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            id_zakaz_klient = rs.getInt("klient_zakaz_id");
                        }
                    }

                    ps.close();
                }

                try (PreparedStatement ps = c.prepareStatement("INSERT INTO `klient_zakaz` (`Klient_zakaz_id`, `Klient_id`, `Element_zakaza_id`,`Vid_oplaty_id`,`Vozrast`) VALUES (?, ?, ?, ?, ?);")) {
                    ps.setInt(1, (id_zakaz_klient + 1));
                    ps.setInt(2, (id_row + 1));
                    ps.setInt(3, (id_element_zakaza + 1));
                    ps.setInt(4, 0);
                    ps.setInt(5, vozrast);
                    ps.executeUpdate();
                    ps.close();
                }
            } else {
                try (PreparedStatement ps = c.prepareStatement("UPDATE `mydb`.`klient_zakaz` SET `Klient_id` = ?, `Element_zakaza_id` = ?, `Nomer` = ?, `Vozrast` = ? WHERE `klient_zakaz`.`Klient_zakaz_id` = ?;")) {
                    ps.setInt(1, (id_row + 1));
                    ps.setInt(2, (id_element_zakaza + 1));
                    ps.setInt(3, 0);
                    ps.setInt(4, vozrast);
                    ps.setInt(5, id_zakaz_klient);
                    ps.executeUpdate();
                    ps.close();
                }
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

    private void newFilter() {
        RowFilter<DefaultTableModel, Object> rf = null;
        try {
            rf = RowFilter.regexFilter(filterText.getText(), 0);
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        sorter.setRowFilter(rf);
        table_klient.updateUI();
    }
}
