package Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Add_Product extends JFrame implements ActionListener {
    
	 private JLabel pnameLabel, priceLabel, amountInStockLabel;
	 private JTextField pnameField, priceField, amountInStockField;
	 private JButton addProductButton, customerButton, productButton, invoiceButton,addButton,amendViewButton,deleteButton;
	 
	 
    
    public Add_Product() 
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
                new amendView_Product();
                dispose();
            }
        });
        
        
        
        // Create labels for the input fields
        pnameLabel = new JLabel("Product Name:");
        priceLabel = new JLabel("Price per Unit:");
        amountInStockLabel = new JLabel("Quantity In stock:");
        
        
        // Create text fields for the user to input data
        pnameField = new JTextField(20);
        priceField = new JTextField(20);
        amountInStockField = new JTextField(20);
        
        
        // Create a button to add the new customer
        addProductButton = new JButton("Add Product");
        addProductButton.addActionListener(this);
        
     // Create a panel to hold the navigation buttons
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        navPanel.add(customerButton);
        navPanel.add(productButton);
        navPanel.add(invoiceButton);
        
     // Create a panel to hold the add button
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
        mainPanel.add(pnameLabel, c2);

        c2.gridx = 1;
        mainPanel.add(pnameField, c2);

        c2.gridx = 0;
        c2.gridy = 2;
        mainPanel.add(priceLabel, c2);

        c2.gridx = 1;
        mainPanel.add(priceField, c2);

        c2.gridx = 0;
        c2.gridy = 3;
        mainPanel.add(amountInStockLabel, c2);

        c2.gridx = 1;
        mainPanel.add(amountInStockField, c2);

        
        c2.gridx = 0;
        c2.gridy = 6;
        c2.gridwidth = 2;
        mainPanel.add(addProductButton, c2);

        // Add the main panel to the frame
        add(mainPanel);
        
     // Set the properties of the frame
        setTitle("Add Item");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public Add_Product(int id, String name, double price, int stock) {
		// TODO Auto-generated constructor stub
	}

	public void actionPerformed(ActionEvent e) {
        // Handle the button click event
        if (e.getSource() == addProductButton) {
            // Add the new customer to the database
            String pname = pnameField.getText();
            String price = priceField.getText();
            String amountInStock = amountInStockField.getText();
            
            
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
                String sql = "INSERT INTO product (pname, price, amountInStock) VALUES (?, ?, ?)";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, pname);
                stmt.setString(2, price);
                stmt.setString(3, amountInStock);

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

            // Print the data in the console
            System.out.println("Product Name: " + pname);
            System.out.println("Price: " + price);
            System.out.println("Quantity in Stock: " + amountInStock);

            // Clear the input fields
            pnameField.setText("");
            priceField.setText("");
            amountInStockField.setText("");
        }
    }

    public static void main(String[] args) {
        new Add_Customer();
    }
}
