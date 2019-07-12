import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

public class SignUpForm extends JFrame {

    private JPanel contentPane;
    private static JTextField textField;
    private static JPasswordField passwordField;
    static CreateRegistrantTable conn;
    static String value1;
    static String value2;
    static boolean signedUp;
    static SignUpForm newSignInForm = new SignUpForm();
    static LoginForm newForm = new LoginForm();
    static SignUpForm frame;



    public static void insertRecordInTableUsingStatement() {
        //Create H2 DB Connection Object
        value1 = textField.getText();
        value2 = passwordField.getText();

        try {
            Connection connection = conn.getDBConnection();

            //Set auto commit to false  
            connection.setAutoCommit(false);

            //Create a Statement Object
            Statement statement = connection.createStatement();



            //Execute statement to insert record into WORKERS
            statement.execute("INSERT INTO REGISTRANTS (username, password) " +
                "                     VALUES ('" + value1 + "', '" + value2 + "')");



            //Close the statement object
            statement.close();



            //Commit the record to the DB table
            connection.commit();

            if (textField.getText().isEmpty() == false && passwordField.getText().isEmpty() == false) {
                JOptionPane.showMessageDialog(null, "Sign up successful!");
                signedUp = true;
                newSignInForm.dispose();

                LoginForm logIn = new LoginForm();
                logIn.setTitle("Login");
                logIn.setVisible(true);
                logIn.pack();
                logIn.setBounds(100, 100, 484, 421);

            } else if (textField.getText().isEmpty() == true && passwordField.getText().isEmpty() == true) {
                JOptionPane.showMessageDialog(null, "Please enter a username and or passsword.");
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }





    }
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    frame = new SignUpForm();
                    frame.setVisible(true);



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

    /**
     * Create the frame.
     */
    public SignUpForm() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 484, 421);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Sign Up");
        lblNewLabel.setBounds(187, 0, 69, 20);
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

        JButton signUp = new JButton("Sign Up");
        signUp.setBounds(180, 239, 115, 29);
        signUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent v) {
                insertRecordInTableUsingStatement();



                if (signedUp == true) {
                    frame.dispose();
                    //signedUp=false;
                }
            }
        });


        contentPane.add(signUp);

        JButton btnLogin = new JButton("Login");
        btnLogin.setBounds(180, 320, 115, 29);
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent v) {

                //LoginForm logIn2 = new LoginForm(); 
                newForm.setTitle("Login");
                newForm.setVisible(true);
                newForm.pack();
                newForm.setBounds(100, 100, 484, 421);

                //LoginForm.login();

            }
        });


        contentPane.add(btnLogin);

        JLabel lblSelectloginIf = new JLabel("Select 'Login' if you already have an account");
        lblSelectloginIf.setBounds(104, 298, 311, 20);
        contentPane.add(lblSelectloginIf);
    }
}
