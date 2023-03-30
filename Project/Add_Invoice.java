package Project;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.*;

public class Add_Invoice<Product> extends JFrame {
	
    private ArrayList<Product> products = new ArrayList<>();
    private ArrayList<Integer> customerIds = new ArrayList<>();
    private JComboBox<String> customerComboBox, productComboBox;
    private JSpinner quantitySpinner;
    private JButton addOrderButton,addProductButton, customerButton, productButton, invoiceButton,addButton,amendViewButton,deleteButton;

    private JTable table;

    public Add_Invoice() {

        // Create a combo box to select the customer
        customerComboBox = new JComboBox<String>();
        loadCustomers();

        // Create a combo box to select the product
        productComboBox = new JComboBox<String>();
        loadProducts();

        // Create a spinner to select the quantity of the product
        SpinnerModel spinnerModel = new SpinnerNumberModel(1, 1, 999, 1);
        quantitySpinner = new JSpinner(spinnerModel);
        
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
        
     // Initialize addOrderButton
        addOrderButton = new JButton("Add Order");
        addOrderButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addOrder(); // Call the addOrder() method
            }
        });
        
        // Create a panel to hold the navigation buttons
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        navPanel.add(customerButton);
        navPanel.add(productButton);
        navPanel.add(invoiceButton);
        
        

        // Create the input panel and the table
        JPanel inputPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        inputPanel.add(new JLabel("Customer:"));
        inputPanel.add(customerComboBox);
        inputPanel.add(new JLabel("Product:"));
        inputPanel.add(productComboBox);
        inputPanel.add(new JLabel("Quantity:"));
        inputPanel.add(quantitySpinner);
        inputPanel.add(addOrderButton);
        
        // Create the table
        table = new JTable();
        table.setAutoCreateRowSorter(true);
        JScrollPane scrollPane = new JScrollPane(table);

        // Create the main panel with a BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(navPanel, BorderLayout.NORTH);
        mainPanel.add(inputPanel, BorderLayout.WEST);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Add the input panel and the table to the main panel
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Create a panel to hold the navigation and input panels
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(navPanel, BorderLayout.NORTH);
        northPanel.add(inputPanel, BorderLayout.CENTER);

        // Add the northPanel to the main panel
        mainPanel.add(northPanel, BorderLayout.NORTH);

        // Add the main panel to the frame
        add(mainPanel);

        // Set the properties of the frame
        setTitle("Add Invoice");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadCustomers() {
        // Connect to the database and load the customers into the combo box
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

            while (rs.next()) {
                customerIds.add(rs.getInt("customerID"));
                customerComboBox.addItem(rs.getString("name") + " " + rs.getString("surname"));
            }
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


    private void loadProducts() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            // Connect to the database
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/project";
            String user = "root";
            String password = "";
            conn = DriverManager.getConnection(url, user, password);

            // Load the data from the "product" table into the "productComboBox"
            stmt = conn.prepareStatement("SELECT * FROM product");
            rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("productID");
                String name = rs.getString("pname");
                double price = rs.getDouble("price");
                int stock = rs.getInt("amountInStock");
                products.add(new Product(id, name, price, stock));
                productComboBox.addItem(name + " - €" + price);
            }
        } catch (ClassNotFoundException | SQLException ex) {
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
    private void addOrder() {
    	
        // Get the selected customer and product
        int customerIndex = customerComboBox.getSelectedIndex();
        int productIndex = productComboBox.getSelectedIndex();
        if (customerIndex == -1 || productIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select a customer and product.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int customerId = customerIds.get(customerIndex);
        Product p = products.get(productIndex);
        
     // Get the customer details
        String customerName = (String) customerComboBox.getSelectedItem();
        String[] nameParts = customerName.split(" ");
        String name = nameParts[0];
        String surname = nameParts[1];

        // Get the customer address and email using customerId
        String address = "";
        String email = "";

        // Get the quantity and calculate the total price
        int quantity = (int) quantitySpinner.getValue();
        double totalPrice = quantity * p.getPrice();
        
        // Insert the order into the database
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/project";
            String user = "root";
            String password = "";
            conn = DriverManager.getConnection(url, user, password);
            
            PreparedStatement customerStmt = conn.prepareStatement("SELECT * FROM customer WHERE customerID = ?");
            customerStmt.setInt(1, customerId);
            ResultSet customerRs = customerStmt.executeQuery();

            if (customerRs.next()) {
                address = customerRs.getString("address");
                email = customerRs.getString("email");
            }

            // Insert the order into the invoice table
            stmt = conn.prepareStatement("INSERT INTO invoice (customerID, name, surname, address, email, productID, pname, price, quantity, totalPrice) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            stmt.setInt(1, customerId);
            stmt.setString(2, name);
            stmt.setString(3, surname);
            stmt.setString(4, address);
            stmt.setString(5, email);
            stmt.setInt(6, p.getId());
            stmt.setString(7, p.getName());
            stmt.setDouble(8, p.getPrice());
            stmt.setInt(9, quantity);
            stmt.setDouble(10, totalPrice);
            stmt.executeUpdate();

            // Show a success message and clear the selection
            JOptionPane.showMessageDialog(this, "Order added successfully.");
            customerComboBox.setSelectedIndex(-1);
            productComboBox.setSelectedIndex(-1);
           
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
    class Product {
        private int id;
        private String name;
        private double price;
        private int stock;

        public Product(int id, String name, double price, int stock) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.stock = stock;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        public int getStock() {
            return stock;
        }
    }
}

