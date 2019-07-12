import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.toedter.calendar.JCalendar;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField.AbstractFormatter;
import com.toedter.calendar.JDateChooser;

import javafx.scene.control.ComboBox;
import net.proteanit.sql.DbUtils;

import java.awt.Panel;
import javax.swing.JSplitPane;
import javax.swing.JTable;


public class InventoryManagementForm extends JFrame {

    private JPanel contentPane;
    static JTextField firstNametext;
    static JTextField middleNametext;
    static JTextField lastNametext;
    //static JTextField textField_3;
    //static JTextField textField_4;
    static String[] laptopModels = {
        "Fujitsu T-732",
        "Fujitsu T-734",
        "Lenovo Yoga 260"
    };
    static String cell, cell2;
    static DefaultTableModel model;
    static JComboBox laptopModelList = new JComboBox(laptopModels);
    static boolean isCleared;
    static CreateRegistrantTable conn4;
    static int selectedRowIndex, row, row2, col;
    /*static JRadioButton rb1 = new JRadioButton("one", false),
    	      rb2 = new JRadioButton("two", false), rb3 = new JRadioButton(
    	          "three", false);*/

    static JDateChooser dateChooser = new JDateChooser();
    static JDateChooser dateChooser1 = new JDateChooser();
    static String value1, value2, value3, value4, timeStamp;

    static JButton checkInButton = new JButton("Check-In");
    static JButton checkOutButton = new JButton("Check-Out");
    static JTextField laptopLabelField;
    static JTable table;
    static Date date1, date2;

    public static void resetEntryFields() {
        dateChooser.setDate(null);
        dateChooser1.setDate(null);
        firstNametext.setText(null);
        middleNametext.setText(null);
        lastNametext.setText(null);
        laptopModelList.setSelectedItem(null);
        laptopLabelField.setText(null);

    }
    public static void getTableRecords() {

        try {
            Connection connection = conn4.getDBConnection();
            String query = "select RECORDID, FIRSTNAME, MIDDLENAME, LASTNAME, CHECKINDATE, CHECKOUTDATE, LAPTOPMODEL, LAPTOPLABELNUMBER FROM Records";

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            table.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
    public static void getRowData(java.awt.event.MouseEvent evt) throws Exception {

        model = (DefaultTableModel) table.getModel();
        //table.setModel(model);

        selectedRowIndex = table.getSelectedRow();

        //date1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US).parse((String)model.getValueAt(selectedRowIndex, 4));
        //dateChooser.setDate(date1);

        date2 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US).parse((String) model.getValueAt(selectedRowIndex, 5));
        dateChooser1.setDate(date2);

        firstNametext.setText(model.getValueAt(selectedRowIndex, 1).toString());
        middleNametext.setText(model.getValueAt(selectedRowIndex, 2).toString());
        lastNametext.setText(model.getValueAt(selectedRowIndex, 3).toString());
        laptopModelList.setSelectedItem(model.getValueAt(selectedRowIndex, 6).toString());
        laptopLabelField.setText(model.getValueAt(selectedRowIndex, 7).toString());
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    InventoryManagementForm frame = new InventoryManagementForm();
                    frame.setVisible(true);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });



    }

    public static void deleteRecord() {
        //Create H2 DB Connection Object
        Connection connection = conn4.getDBConnection();

        try {
            //Set auto commit to false  
            connection.setAutoCommit(false);

            //Create a Statement Object
            Statement statement = connection.createStatement();


            //Execute statement to insert record into WORKERS
            //statement.execute("DELETE FROM RECORDS WHERE RECORDID =" + selectedRowIndex + "");        
            row = table.getSelectedRow();
            col = table.getSelectedColumn();

            cell = table.getModel().getValueAt(row, 0).toString();
            statement.execute("DELETE FROM RECORDS where RECORDID =" + cell);

            statement.close();

            //Commit the record to the DB table
            connection.commit();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

        JOptionPane.showMessageDialog(null, "Record deleted!");

    }
    static class test extends AbstractFormatter {


        private String datePattern = "yyyy-MM-dd";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }

            return "";
        }

    }

    /**
     * Create the frame.
     */
    public InventoryManagementForm() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1275, 598);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblLaptopInventoryManager = new JLabel("Laptop Inventory Manager");
        lblLaptopInventoryManager.setBounds(198, 0, 204, 20);
        contentPane.add(lblLaptopInventoryManager);

        JRadioButton rdbtnCheckoutLaptop = new JRadioButton("Check-Out Laptop", true);

        JRadioButton rdbtnCheckinLaptop = new JRadioButton("Check-In Laptop", false);

        if (rdbtnCheckoutLaptop.isSelected() == true) {
            rdbtnCheckinLaptop.setEnabled(false);
            checkInButton.setEnabled(false);

        }

        rdbtnCheckoutLaptop.setBounds(15, 37, 178, 29);
        rdbtnCheckoutLaptop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent v) {
                /*resetEntryFields();
                firstNametext.setEnabled(true);
                middleNametext.setEnabled(true);
                lastNametext.setEnabled(true);
                laptopModelList.setEnabled(true)*/
                ;

                checkInButton.setEnabled(false);

                if (rdbtnCheckoutLaptop.isSelected() == true) {
                    rdbtnCheckinLaptop.setSelected(false);
                    checkOutButton.setEnabled(true);
                    dateChooser.setEnabled(false);

                    /*if(dateChooser1.isEnabled()==false)
                    {
                    	dateChooser1.setEnabled(true);
                    }*/

                }
            }

        });

        contentPane.add(rdbtnCheckoutLaptop);

        rdbtnCheckinLaptop.setBounds(227, 37, 155, 29);
        rdbtnCheckinLaptop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent v) {
                getTableRecords();

                checkOutButton.setEnabled(false);
                rdbtnCheckoutLaptop.setSelected(false);
                firstNametext.setEditable(false);
                middleNametext.setEditable(false);
                lastNametext.setEditable(false);
                laptopModelList.setEnabled(false);
                laptopLabelField.setEditable(false);

                /*
                try {
                    Connection connection = conn4.getDBConnection();
                	String query="select RECORDID, FIRSTNAME, MIDDLENAME, LASTNAME, CHECKINDATE, CHECKOUTDATE, LAPTOPMODEL, LAPTOPLABELNUMBER FROM Records";
                	
                	Statement stmt=connection.createStatement();
                	ResultSet rs=stmt.executeQuery(query);
                	
                	table.setModel(DbUtils.resultSetToTableModel(rs));
                	
                }
                catch(Exception e)
                {
                	
                	e.printStackTrace();
                }*/



                if (rdbtnCheckinLaptop.isSelected() == true) {
                    checkInButton.setEnabled(true);
                    dateChooser1.setEnabled(false);

                    if (dateChooser.isEnabled() == false) {
                        dateChooser.setEnabled(true);
                    }



                }
            }

        });

        contentPane.add(rdbtnCheckinLaptop);

        JLabel lblFirstName = new JLabel("First Name");
        lblFirstName.setBounds(15, 93, 84, 20);
        contentPane.add(lblFirstName);

        firstNametext = new JTextField();
        firstNametext.setBounds(136, 90, 250, 26);
        contentPane.add(firstNametext);
        firstNametext.setColumns(10);

        JLabel lblMiddleName = new JLabel("Middle Name");
        lblMiddleName.setBounds(15, 139, 99, 20);
        contentPane.add(lblMiddleName);

        JLabel lblLastName = new JLabel("Last Name");
        lblLastName.setBounds(15, 175, 90, 20);
        contentPane.add(lblLastName);

        middleNametext = new JTextField();
        middleNametext.setColumns(10);
        middleNametext.setBounds(136, 132, 250, 26);
        contentPane.add(middleNametext);

        lastNametext = new JTextField();
        lastNametext.setColumns(10);
        lastNametext.setBounds(136, 172, 250, 26);
        contentPane.add(lastNametext);

        /*checkIndate.setVisible(true);
        checkIndate.setBounds(136, 211, 250, 26);
        checkIndate.setLocale(Locale.US);
        contentPane.add(checkIndate);
		
        checkOutdate.setVisible(true);
        checkOutdate.setBounds(136, 253, 250, 26);
        checkOutdate.setLocale(Locale.US);
        checkOutdate.setFor
        contentPane.add(checkOutdate);*/


        JLabel lblLaptop = new JLabel("Laptop Model");
        lblLaptop.setBounds(10, 298, 131, 20);
        contentPane.add(lblLaptop);

        JLabel lblLaptopLabel = new JLabel("Laptop Label #");
        lblLaptopLabel.setBounds(10, 343, 131, 20);
        contentPane.add(lblLaptopLabel);

        checkInButton.setBounds(6, 389, 169, 44);
        checkInButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                //if(rdbtnCheckinLaptop.isSelected()==true)
                //{

                //System.out.print(cell2);


                /*try {
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/

                Connection connection = conn4.getDBConnection();

                try {
                    row2 = table.getSelectedRow();
                    cell2 = table.getModel().getValueAt(row2, 0).toString();

                    getRowData(evt);

                    //Set auto commit to false  
                    connection.setAutoCommit(false);

                    timeStamp = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US).format(Calendar.getInstance().getTime());

                    //Create a Statement Object
                    Statement statement = connection.createStatement();


                    //statement.execute("UPDATE RECORDS checkindate SET CHECKINDATE= +'"timeStamp"'+ WHERE RECORDID ="+ cell);

                    String query4 = "SELECT CHECKINDATE FROM RECORDS\r\n" +
                        "WHERE RECORDID='" + cell2 + "';\r\n" +
                        "update RECORDS SET CHECKINDATE='" + timeStamp + "' where RECORDID='" + cell2 + "'";

                    //String query="select RECORDID, FIRSTNAME, MIDDLENAME, LASTNAME, CHECKOUTDATE, LAPTOPMODEL, LAPTOPLABELNUMBER FROM Records";

                    ResultSet rs = statement.executeQuery(query4);

                    //Statement stmt=connection.createStatement();
                    //ResultSet rs=statement.executeQuery(query4);




                    //table.setModel(DbUtils.resultSetToTableModel(rs));
                    //Close the statement object
                    statement.close();

                    //Commit the record to the DB table
                    connection.commit();

                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }



                if (isCleared == true & rdbtnCheckinLaptop.isSelected() == true &
                    firstNametext.getText().isEmpty() == true & middleNametext.getText().isEmpty() == true &
                    lastNametext.getText().isEmpty() == true) {
                    JOptionPane.showMessageDialog(null, "There is not enough information provided to check this user in");

                } else if (firstNametext.getText().isEmpty() == false & middleNametext.getText().isEmpty() == false & lastNametext.getText().isEmpty() == false ||
                    laptopLabelField.getText().isEmpty() == false & laptopModelList.getSelectedItem() != " " & dateChooser1.getDate() != null) {
                    JOptionPane.showMessageDialog(null, firstNametext.getText() + " " + lastNametext.getText() + " has been checked in " + timeStamp);

                }
                firstNametext.setEditable(true);
                middleNametext.setEditable(true);
                lastNametext.setEditable(true);
                laptopModelList.setEnabled(true);
                laptopModelList.setEditable(true);
                laptopLabelField.setEditable(true);
                dateChooser1.setEnabled(true);
                resetEntryFields();

                // }


            }
        });

        contentPane.add(checkInButton);

        checkOutButton.setBounds(185, 389, 197, 44);
        checkOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent v) {
                Connection connection = conn4.getDBConnection();

                if (rdbtnCheckoutLaptop.isSelected() == true) {
                    try {
                        //Set auto commit to false  
                        connection.setAutoCommit(false);

                        //Create a Statement Object
                        Statement statement = connection.createStatement();

                        value1 = firstNametext.getText();
                        value2 = middleNametext.getText();
                        value3 = lastNametext.getText();
                        value4 = laptopLabelField.getText();
                        String value5 = String.valueOf(laptopModelList.getSelectedItem());
                        date2 = dateChooser1.getDate();

                        //Execute statement to insert record into WORKERS
                        /*statement.execute
                        ("INSERT INTO RECORDS (firstname,middlename,lastname,checkindate,checkoutdate,laptopmodel,laptoplabelnumber) "
                        		+ "VALUES('"+value1+"','"+value2+"','"+value3+"', null, null, dddd, '"+value4+"')");*/

                        //if(rdbtnCheckoutLaptop.isSelected()==true)
                        //{
                        if (firstNametext.getText().isEmpty() == false & middleNametext.getText().isEmpty() == false & lastNametext.getText().isEmpty() == false &
                            laptopLabelField.getText().isEmpty() == false & laptopModelList.getSelectedItem() != null & dateChooser1.getDate() != null) {
                            statement.execute("INSERT INTO RECORDS (firstname, middlename, lastname, checkoutdate, laptopmodel, laptoplabelnumber ) " +
                                "                     VALUES ('" + value1 + "', '" + value2 + "', '" + value3 + "', '" + date2 + "', '" + value5 + "', '" + value4 + "')");

                            JOptionPane.showMessageDialog(null, "A laptop has been checked out to" +
                                " " + firstNametext.getText() + " " + lastNametext.getText() + " on " + date2);

                            resetEntryFields();
                            rdbtnCheckinLaptop.setEnabled(true);
                        } else if (firstNametext.getText().isEmpty() || middleNametext.getText().isEmpty() || lastNametext.getText().isEmpty() ||
                            laptopLabelField.getText().isEmpty() || laptopModelList.getSelectedItem() == " " || dateChooser1.getDate() == null) {
                            JOptionPane.showMessageDialog(null, "All fields are required, please complete the entire form.");

                        }
                        //dateChooser1.setDate(date2);
                        //Close the statement object
                        statement.close();

                        //Commit the record to the DB table
                        connection.commit();

                    } catch (Exception ex) {
                        System.out.println(ex.toString());
                    }





                }
            }
        });
        contentPane.add(checkOutButton);

        laptopModelList.setBounds(136, 295, 250, 26);
        contentPane.add(laptopModelList);
        laptopModelList.setSelectedIndex(2);

        laptopLabelField = new JTextField();
        laptopLabelField.setColumns(10);
        laptopLabelField.setBounds(136, 340, 250, 26);
        contentPane.add(laptopLabelField);

        /*JLabel lblNewLabel = new JLabel("Check In Date:");
        lblNewLabel.setBounds(15, 214, 126, 20);
        contentPane.add(lblNewLabel);*/

        JLabel lblCheckOutDate = new JLabel("Check Out Date:");
        lblCheckOutDate.setBounds(10, 256, 126, 20);
        contentPane.add(lblCheckOutDate);

        /*dateChooser.setBounds(134, 214, 119, 26);
        contentPane.add(dateChooser);*/

        dateChooser1.setBounds(136, 256, 119, 26);
        contentPane.add(dateChooser1);

        /*Object rowData[][] = { { "First Name", "Middle Name", "Last Name", "Check In Date", "Check Out Date", "Laptop Model", "Laptop Number"},
			       };
		Object columnNames[] = { "First Name", "Middle Name", "Last Name", "Check In Date", "Check Out Date", "Laptop Model", "Laptop Number"};*/

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(401, 37, 837, 505);
        contentPane.add(scrollPane);

        //contentPane.add(scrollPane);
        table = new JTable();
        table.setBounds(426, 78, 490, 453);

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {


                try {
                    getRowData(evt);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        scrollPane.setViewportView(table);

        JButton btnViewRecords = new JButton("View All Records");
        btnViewRecords.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                getTableRecords();
                resetEntryFields();

            }
        });
        btnViewRecords.setBounds(195, 449, 155, 44);
        contentPane.add(btnViewRecords);

        JButton deleteRecord = new JButton("Delete a Record");
        deleteRecord.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                deleteRecord();


                //int modelRow = table.convertRowIndexToModel(selectedRowIndex);
                //model.removeRow( modelRow );

            }
        });
        deleteRecord.setBounds(16, 449, 159, 44);
        contentPane.add(deleteRecord);

        JButton btnClearAllFields = new JButton("Reset All Fields");
        btnClearAllFields.setBounds(105, 498, 155, 44);
        btnClearAllFields.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                checkInButton.setEnabled(true);
                checkOutButton.setEnabled(true);
                rdbtnCheckinLaptop.setEnabled(true);
                rdbtnCheckoutLaptop.setEnabled(true);

                firstNametext.setEditable(true);
                middleNametext.setEditable(true);
                lastNametext.setEditable(true);
                laptopModelList.setEnabled(true);
                laptopModelList.setEditable(true);
                laptopLabelField.setEditable(true);
                dateChooser1.setEnabled(true);

                rdbtnCheckinLaptop.setSelected(false);
                rdbtnCheckoutLaptop.setSelected(false);

                dateChooser.setDate(null);
                dateChooser1.setDate(null);
                firstNametext.setText(null);
                middleNametext.setText(null);
                lastNametext.setText(null);
                laptopModelList.setSelectedItem(null);
                laptopLabelField.setText(null);

                isCleared = true;


            }
        });
        contentPane.add(btnClearAllFields);

    }
}
