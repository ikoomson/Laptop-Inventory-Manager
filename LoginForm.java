import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

public class LoginForm extends JFrame {

    private JPanel contentPane;
    private static JTextField textField;
    private static JPasswordField passwordField;
    static CreateRegistrantTable conn2;

    static SignUpForm one = new SignUpForm();
    static String newValue1;
    static String newValue2;
    static LoginForm Loginframe;

    String data = "jdbc:odbc:VideoStore";

    public void login() {
        newValue1 = textField.getText();
        newValue2 = passwordField.getText();

        try {
            //Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            Connection conn = conn2.getDBConnection();
            Statement st = conn.createStatement();
            ResultSet rec = st.executeQuery("Select username, password FROM REGISTRANTS");

            while (rec.next()) {



                if (newValue1.equals(rec.getString("username")) & textField.getText().isEmpty() == false &
                    newValue2.equals(rec.getString("password")) & passwordField.getText().isEmpty() == false) {
                    JOptionPane.showMessageDialog(null, "Welcome, " + newValue1);
                    InventoryManagementForm newForm = new InventoryManagementForm();
                    newForm.setTitle("Laptop Inventory Manager");
                    newForm.setVisible(true);
                    newForm.pack();
                    newForm.setBounds(100, 100, 1275, 598);

                    this.dispose();

                } else if (textField.getText().isEmpty() && passwordField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Enter a username and or password");
                    break;
                } else if (newValue1 != rec.getString("username") && newValue2 != rec.getString("password") &
                    textField.getText() == null & passwordField.getText() == null) {
                    JOptionPane.showMessageDialog(null, "Username and or password is incorrect");
                    textField.setText(" ");
                    passwordField.setText(null);

                }

            }

            st.close();
        } catch (SQLException d) {
            //System.out.println(e.toString());
        }
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Loginframe = new LoginForm();
                    Loginframe.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * Create the frame.
     */
    public LoginForm() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 484, 421);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Login");
        lblNewLabel.setBounds(207, 0, 69, 20);
        contentPane.add(lblNewLabel);

        JLabel lblUsername = new JLabel("Username");
        lblUsername.setBounds(15, 84, 87, 20);
        contentPane.add(lblUsername);

        textField = new JTextField();
        textField.setBounds(110, 81, 305, 26);
        contentPane.add(textField);
        textField.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setBounds(110, 181, 305, 26);
        contentPane.add(passwordField);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setBounds(15, 187, 69, 20);
        contentPane.add(lblPassword);

        JButton login = new JButton("Login");
        login.setBounds(181, 238, 115, 29);
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent v) {
                login();
            }
        });

        contentPane.add(login);

        JButton signUponLoginForm = new JButton("Sign Up");
        signUponLoginForm.setBounds(181, 320, 115, 29);
        signUponLoginForm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent v) {
                Loginframe.dispose();
                //SignUpForm newSignIn = new SignUpForm(); 
                one.setTitle("Sign Up");
                one.setVisible(true);
                one.pack();
                one.setBounds(100, 100, 484, 421);

                //LoginForm.login();

            }
        });
        contentPane.add(signUponLoginForm);

        JLabel lblSelectsignUp = new JLabel("Select 'Sign Up' if you do not have an account.");
        lblSelectsignUp.setBounds(85, 294, 345, 20);
        contentPane.add(lblSelectsignUp);
    }
}
