package Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Add_Customer extends JFrame implements ActionListener {
    
	 private JLabel nameLabel, surnameLabel, addressLabel, emailLabel, phoneLabel;
	 private JTextField nameField, surnameField, addressField, emailField, phoneField;
	 private JButton addCustomerButton, customerButton, productButton, invoiceButton,addButton,amendViewButton,deleteButton;
	 
	 
    
    public Add_Customer() 
    	{
    	
    	// Create buttons to navigate to the customer, product, and invoice sections
        customerButton = new JButton("Customers");
        customerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Add_Customer();
                dispose();
            }
        });
        
        productButton = new JButton("Products");
        productButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Add_Product();
                dispose();
            }
        });
        
        invoiceButton = new JButton("Orders");
        invoiceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Add_Invoice();
                dispose();
            }
        });
        
        
        amendViewButton = new JButton("View/Edit");
        amendViewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new amendView_Customer();
                dispose();
            }
        });
        

        
        // Create labels for the input fields
        nameLabel = new JLabel("Name:");
        surnameLabel = new JLabel("Surname:");
        addressLabel = new JLabel("Address:");
        emailLabel = new JLabel("Email:");
        phoneLabel = new JLabel("Phone:");
        
        // Create text fields for the user to input data
        nameField = new JTextField(20);
        surnameField = new JTextField(20);
        addressField = new JTextField(20);
        emailField = new JTextField(20);
        phoneField = new JTextField(20);
        
        // Create a button to add the new customer
        addCustomerButton = new JButton("Add Customer");
        addCustomerButton.addActionListener(this);
        
     // Create a panel to hold the navigation buttons
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        navPanel.add(customerButton);
        navPanel.add(productButton);
        navPanel.add(invoiceButton);
        
     // Create a panel to hold the add,view,delete buttons
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        buttonPanel.add(amendViewButton);


        // Create the main panel with a GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());

        // Add the navigation buttons to the main panel
        GridBagConstraints c1 = new GridBagConstraints();
        c1.gridx = 0;
        c1.gridy = 0;
        c1.anchor = GridBagConstraints.WEST;
        c1.insets = new Insets(5, 5, 5, 5);
        mainPanel.add(navPanel, c1);
        
        //Add buttonPanel to the main pane;
        GridBagConstraints c3 = new GridBagConstraints();
        c3.gridx = 8;
        c3.gridy = 8;
        c3.anchor = GridBagConstraints.WEST;
        c3.insets = new Insets(3,1,5,5);
        mainPanel.add(buttonPanel, c3);

        // Add the input fields and button to the main panel
        GridBagConstraints c2 = new GridBagConstraints();
        c2.gridx = 0;
        c2.gridy = 1;
        c2.anchor = GridBagConstraints.WEST;
        c2.insets = new Insets(5, 5, 5, 5);
        mainPanel.add(nameLabel, c2);

        c2.gridx = 1;
        mainPanel.add(nameField, c2);

        c2.gridx = 0;
        c2.gridy = 2;
        mainPanel.add(surnameLabel, c2);

        c2.gridx = 1;
        mainPanel.add(surnameField, c2);

        c2.gridx = 0;
        c2.gridy = 3;
        mainPanel.add(addressLabel, c2);

        c2.gridx = 1;
        mainPanel.add(addressField, c2);

        c2.gridx = 0;
        c2.gridy = 4;
        mainPanel.add(phoneLabel, c2);
        
        c2.gridx = 1;
        mainPanel.add(phoneField, c2);
        
        c2.gridx = 0;
        c2.gridy = 5;
        mainPanel.add(emailLabel, c2);
        
        c2.gridx = 1;
        mainPanel.add(emailField, c2);
        
        c2.gridx = 0;
        c2.gridy = 6;
        c2.gridwidth = 2;
        mainPanel.add(addCustomerButton, c2);

        // Add the main panel to the frame
        add(mainPanel);
        
     // Set the properties of the frame
        setTitle("Add Customer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        // Handle the button click event
        if (e.getSource() == addCustomerButton) {
            // Add the new customer to the database
            String name = nameField.getText();
            String surname = surnameField.getText();
            String address = addressField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            
         // Establish a connection to the database
            Connection conn = null;
            PreparedStatement stmt = null;
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                String url = "jdbc:mysql://localhost:3306/project";
                String user = "root";
                String password = "";
                conn = DriverManager.getConnection(url, user, password);

                // Create a prepared statement to insert the data into the customer table
                String sql = "INSERT INTO customer (name, surname, address, email, phone) VALUES (?, ?, ?, ?, ?)";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, name);
                stmt.setString(2, surname);
                stmt.setString(3, address);
                stmt.setString(4, email);
                stmt.setString(5, phone);

                // Execute the statement
                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    System.out.println(rows + " row(s) inserted.");
                } else {
                    System.out.println("No rows inserted.");
                }
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                // Close the statement and connection objects
                try {
                    if (stmt != null) stmt.close();
                    if (conn != null) conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

            // print the data in teh console
            System.out.println("Name: " + name);
            System.out.println("Surname: " + surname);
            System.out.println("Address: " + address);
            System.out.println("Email: " + email);
            System.out.println("Phone: " + phone);

            // Clear the input fields
            nameField.setText("");
            surnameField.setText("");
            addressField.setText("");
            emailField.setText("");
            phoneField.setText("");
        }
    }

    public static void main(String[] args) {
        new Add_Customer();
    }
}
