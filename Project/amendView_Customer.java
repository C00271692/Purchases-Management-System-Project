package Project;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;

public class amendView_Customer extends JFrame{
	
	 private JButton addCustomerButton, customerButton, productButton, invoiceButton,addButton,amendViewButton,deleteButton;
	 
    private JTable table;

    public amendView_Customer() {
    	
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
        
        invoiceButton = new JButton("Invoices");
        invoiceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Add_Invoice();
                dispose();
            }
        });
        
        // Create buttons to navigate to the add, amend/view, and delete sections
        addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Add_Customer();
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
        
        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get the selected row index
                int selectedRow = table.getSelectedRow();
                
                // Check if a row is selected
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(amendView_Customer.this, "Please select a row to delete.");
                    return;
                }
                
                // Get the ID of the selected row
                int customerID = (int) table.getValueAt(selectedRow, 0);
                
                // Connect to the database and delete the record
                Connection conn = null;
                PreparedStatement stmt = null;
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    String url = "jdbc:mysql://localhost:3306/project";
                    String user = "root";
                    String password = "";
                    conn = DriverManager.getConnection(url, user, password);

                    stmt = conn.prepareStatement("DELETE FROM customer WHERE customerID = ?");
                    stmt.setInt(1, customerID);
                    int rowsDeleted = stmt.executeUpdate();

                    if (rowsDeleted == 1) {
                        JOptionPane.showMessageDialog(amendView_Customer.this, "Record deleted successfully.");
                        
                        // Refresh the table after deleting the record
                        loadData();
                    } else {
                        JOptionPane.showMessageDialog(amendView_Customer.this, "Error deleting record.");
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
            }
        });
        
        
        
        // Create a panel to hold the navigation buttons
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        navPanel.add(customerButton);
        navPanel.add(productButton);
        navPanel.add(invoiceButton);
        
        // Create the table
        table = new JTable();
        table.setAutoCreateRowSorter(true);
        JScrollPane scrollPane = new JScrollPane(table);

        // Create a panel to hold the add,view,delete buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 5, 5));
        buttonPanel.add(addButton);
        buttonPanel.add(amendViewButton);
        buttonPanel.add(deleteButton);
        
        // Create the main panel with a BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Add the navigation buttons and the table to the main panel
        mainPanel.add(navPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add the main panel to the frame
        add(mainPanel);
        
        // Set up the GUI
        setTitle("Edit/View Customer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setVisible(true);

        // Load the data from the database into the table
        loadData();
    }

    
		
	

	private void loadData() {
        // Connect to the database and load the data into the table
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
        	 Class.forName("com.mysql.cj.jdbc.Driver");
             String url = "jdbc:mysql://localhost:3306/project";
             String user = "root";
             String password = "";
             conn = DriverManager.getConnection(url, user, password);

            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM customer");

            // Create the table model with the data from the result set
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID");
            model.addColumn("Name");
            model.addColumn("Surname");
            model.addColumn("Address");
            model.addColumn("Email");
            model.addColumn("Phone");
            while (rs.next()) {
                Object[] row = new Object[6];
                row[0] = rs.getInt("customerID");
                row[1] = rs.getString("name");
                row[2] = rs.getString("surname");
                row[3] = rs.getString("address");
                row[4] = rs.getString("email");
                row[5] = rs.getString("phone");
                model.addRow(row);
            }

            // Set the table model and show the table
            table.setModel(model);
            setVisible(true);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            // Close the result set, statement, and connection objects
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        amendView_Customer gui = new amendView_Customer();
    }
}
