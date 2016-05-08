package Zakaz_panels;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Panel_begin extends JPanel {

    private javax.swing.JButton jButton1;
    public javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    public javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JTextField jTextField3;
    public javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();// определяем размер экрана

    public Panel_begin() {
        setBounds(0, 0, screenSize.width, screenSize.height);
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jSpinner1 = new javax.swing.JSpinner();
        jTextField3 = new javax.swing.JTextField();
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton1.setBounds(screenSize.width - 68 / 2, (screenSize.height - 68) / 2, 68, 68);

        jLabel1.setText("Количество");

        jLabel2.setText("Цена");

        jLabel3.setText("Рейс");

        jLabel4.setText("Вид билета");

        jLabel5.setText("Класс обслуживания");

        jButton4.setText("Добавить");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));

        jLabel8.setText("Место");

        jTextField7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // jTextField7ActionPerformed(evt);
            }
        });

        jLabel9.setText("Вид оплаты");

        jLabel10.setText("Клиент");

        jTextField9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // jTextField9ActionPerformed(evt);
            }
        });

        jButton1.setText("Добавить");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        gui();
    }

    public void gui() {
        this.setLayout(new BorderLayout());
        int win_w = screenSize.width / 2 - 180;
        int win_h = screenSize.height / 3 - 180;
        int w = 170;
        int w_field = 150;
        int h = 30;

        //Место
        jLabel8.setSize(w, h);
        jLabel8.setLocation(win_w, win_h);
        jTextField3.setSize(w_field, h);
        jTextField3.setLocation(win_w + 170, win_h);

        //Количество
        jLabel1.setSize(w, h);
        jLabel1.setLocation(win_w, win_h + 30);
        jSpinner1.setSize(w_field, h);
        jSpinner1.setLocation(win_w + 170, win_h + 30);

        //Клиент
        jLabel10.setSize(w, h);
        jLabel10.setLocation(win_w, win_h + 60);
        jTextField7.setSize(w_field, h);
        jTextField7.setLocation(win_w + 170, win_h + 60);
        jButton1.setSize(w_field - 30, h);
        jButton1.setLocation(win_w + 340, win_h + 60);

        //Цена
        jLabel2.setSize(w, h);
        jLabel2.setLocation(win_w, win_h + 90);
        jTextField8.setSize(w_field, h);
        jTextField8.setLocation(win_w + 170, win_h + 90);

        //Рейс
        jLabel3.setSize(w, h);
        jLabel3.setLocation(win_w, win_h + 120);
        jComboBox1.setSize(w_field, h);
        jComboBox1.setLocation(win_w + 170, win_h + 120);

        //Вид билета
        jLabel4.setSize(w, h);
        jLabel4.setLocation(win_w, win_h + 150);
        jComboBox2.setSize(w_field, h);
        jComboBox2.setLocation(win_w + 170, win_h + 150);

        //Класс обслуживания
        jLabel5.setSize(w, h);
        jLabel5.setLocation(win_w, win_h + 180);
        jComboBox3.setSize(w_field, h);
        jComboBox3.setLocation(win_w + 170, win_h + 180);
        jButton4.setSize(w_field, h);
        jButton4.setLocation(win_w + 170, win_h + 230);
        
        this.add(jLabel8);
        this.add(jTextField3);

        this.add(jLabel1);
        this.add(jSpinner1);

        this.add(jLabel10);
        this.add(jTextField7);
        this.add(jButton1);

        this.add(jLabel2);
        this.add(jTextField8);

        this.add(jLabel3);
        this.add(jComboBox1);

        this.add(jLabel4);
        this.add(jComboBox2);

        this.add(jLabel5);
        this.add(jComboBox3);
        this.add(jButton4);
        JButton ggg = new JButton();
        jLabel1.setSize(w, h);
        this.add(ggg);
        ggg.setVisible(false);

    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        System.out.println("Работает!");
        this.removeAll();
        this.revalidate();
        this.repaint();
        this.updateUI();
        this.add(new Panel_1());

    }
}
