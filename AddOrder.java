/*
The Add Order Class is to add the order initially
 */
//package ShippingProject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class AddOrder extends JFrame implements ActionListener
{

  //declaring frame components
  private JPanel titlePanel;
  private JPanel orderPanel;
  private JPanel optionPanel;
  private JPanel buttonPanel;
  private JPanel wholePanel;

  private JTextField orderField;

  private JLabel titleLabel;
  private JLabel orderIdLabel;
  private JLabel typeALabel;
  private JLabel typeBLabel;
  private JLabel typeCLabel;

  private JRadioButton typeAButton;
  private JRadioButton typeBButton;
  private JRadioButton typeCButton;

  private JButton addButton;
  private JButton homeButton;
  private JButton exitButton;

  private ButtonGroup buttonGroup;
  //declaring grid layout for panel
  GridLayout wholeLayout = new GridLayout(3, 1);
  GridLayout fieldLayout = new GridLayout(2, 3);

  //declaring color for frame
  public static final Color BLUE_COLOR = new Color(150, 168, 217);

  //frame constructor
  public AddOrder(String name)
  {
    //setting frame charateristics
    super(name);
    this.setBounds(100, 50, 800, 450);
    this.getContentPane().setBackground(BLUE_COLOR);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout(100, 100));

    //creating and adding panels
    this.wholePanel = new JPanel(wholeLayout);
    wholePanel.setBackground(BLUE_COLOR);
    this.add(wholePanel);

    this.titlePanel = new JPanel(new FlowLayout());
    titlePanel.setBackground(BLUE_COLOR);
    wholePanel.add(titlePanel);

    this.orderPanel = new JPanel(new FlowLayout());
    orderPanel.setBackground(BLUE_COLOR);
    wholePanel.add(orderPanel);

    this.optionPanel = new JPanel(fieldLayout);
    optionPanel.setBackground(BLUE_COLOR);
    wholePanel.add(optionPanel);

    this.buttonPanel = new JPanel(new FlowLayout());
    this.add(buttonPanel, BorderLayout.SOUTH);

    //creating button group
    this.buttonGroup = new ButtonGroup();

    //creating and adding title label
    this.titleLabel = new JLabel("Please add the Order ID of your desired order, "
        + "and select which container dimension will be used.");
    titleLabel.setForeground(Color.WHITE);
    titlePanel.add(titleLabel);

    //creating and adding order field components
    this.orderIdLabel = new JLabel("Order ID: ", SwingConstants.CENTER);
    orderIdLabel.setForeground(Color.WHITE);
    orderPanel.add(orderIdLabel);

    this.orderField = new JTextField(4);
    orderPanel.add(orderField);

    //creating and adding option field components
    this.typeALabel = new JLabel("Container Type A (10, 8, 8.5)", SwingConstants.CENTER);
    typeALabel.setForeground(Color.WHITE);
    optionPanel.add(typeALabel);

    this.typeBLabel = new JLabel("Container Type B (20, 8, 8.5)", SwingConstants.CENTER);
    typeBLabel.setForeground(Color.WHITE);
    optionPanel.add(typeBLabel);

    this.typeCLabel = new JLabel("Container Type C (40, 8, 8.5)", SwingConstants.CENTER);
    typeCLabel.setForeground(Color.WHITE);
    optionPanel.add(typeCLabel);

    //Radio button creation
    this.typeAButton = new JRadioButton();
    typeAButton.addActionListener(this);
    typeAButton.setHorizontalAlignment(SwingConstants.CENTER);
    buttonGroup.add(typeAButton);
    optionPanel.add(typeAButton);

    this.typeBButton = new JRadioButton();
    typeBButton.addActionListener(this);
    typeBButton.setHorizontalAlignment(SwingConstants.CENTER);
    buttonGroup.add(typeBButton);
    optionPanel.add(typeBButton);

    this.typeCButton = new JRadioButton();
    typeCButton.addActionListener(this);
    typeCButton.setHorizontalAlignment(SwingConstants.CENTER);
    buttonGroup.add(typeCButton);
    optionPanel.add(typeCButton);

    //constructing JButtons
    this.addButton = new JButton("Add Order");
    addButton.addActionListener(this);
    buttonPanel.add(addButton);

    this.homeButton = new JButton("Home");
    homeButton.addActionListener(this);
    buttonPanel.add(homeButton);

    this.exitButton = new JButton("Exit");
    exitButton.addActionListener(this);
    buttonPanel.add(exitButton);

    this.setVisible(true);
  }

  public static void main(String[] args)
  {
    new AddOrder("Add Order");
  }

  @Override
  public void actionPerformed(ActionEvent e)
  {
    //declaring command object
    Object Command = e.getSource();
    //declaring variables
    int orderIdInt;
    String orderId = "";
    boolean check = false;
    boolean checkOrder = true;
    int i;
    int length = 0;
    //connection variables
    String dbName = "shippingDB";
    String tableName = "currentTable";
    //making db connection object
    JavaDatabase objDb = new JavaDatabase(dbName);
    Connection shippingDbConn = null;
    //database variables and data structures
    shippingDbConn = objDb.getDbConn();
    String dbQuery = null;
    ResultSet rsOrder = null;
    Statement sOrder = null;
    //arraylist to cross reference 
    ArrayList<String> existingOrder = new ArrayList<>();

    //conditionals for button selections
    if (Command == addButton)
    {
      try
      {
        //initializing orderId
        orderIdInt = Integer.parseInt(orderField.getText());
        orderField.setText("");

        try
        {
          //converting back to string
          //conditionals to see integer length
          if (Integer.toString(orderIdInt).length() == 1)
          {
            //setting order Id string
            orderId = "000" + Integer.toString(orderIdInt);
          }
          else if (Integer.toString(orderIdInt).length() == 2)
          {
            //setting order Id string
            orderId = "00" + Integer.toString(orderIdInt);
          }
          else if (Integer.toString(orderIdInt).length() == 3)
          {
            //setting order Id string
            orderId = "0" + Integer.toString(orderIdInt);
          }
          else if (Integer.toString(orderIdInt).length() == 4)
          {
            //setting order Id string
            orderId = Integer.toString(orderIdInt);
          }
          //making db query for receiving order ID values
          dbQuery = "SELECT orderId FROM containerTable";
          sOrder = objDb.getDbConn().createStatement();
          rsOrder = sOrder.executeQuery(dbQuery);
          while (rsOrder.next())
          {
            existingOrder.add(rsOrder.getString("orderId"));
          }
          //loop to check presence of order Id
          for (i = 0; i < existingOrder.size(); i++)
          {
            if (orderId.equals(existingOrder.get(i)))
            {
              //setting boolean to true to stop process
              checkOrder = false;
              //breaking loop
              break;
            }
          }
          //conditional for warning message
          if (checkOrder == false)
          {
            new Warning("Please enter a unique Order ID.");
          }
          //condtional to proceed
          if (checkOrder == true)
          {
            if (typeAButton.isSelected())
            {
              length = 10;
            }
            else if (typeBButton.isSelected())
            {
              length = 20;
            }
            else if (typeCButton.isSelected())
            {
              length = 40;
            }
            //updating db query for insert
            dbQuery = "INSERT INTO containerTable VALUES (?, ?)";
            //declaring prepared statement for insertion
            PreparedStatement ps = shippingDbConn.prepareStatement(dbQuery);
            //placing values in statement
            ps.setString(1, orderId);
            ps.setInt(2, length);
            //executing query
            ps.executeUpdate();
            System.out.println("Order Created Succesfully");
          }
        }
        //sql exception
        catch (SQLException se)
        {
          System.out.println("Error creating new order.");
          se.printStackTrace(System.err);
        }
        //command to add default values for efficiency
        try
        {
          if (checkOrder == true)
          {
            //updating db query for insert
            dbQuery = "INSERT INTO efficiencyTable VALUES (?, ?)";
            //declaring prepared statement for insertion
            PreparedStatement ps = shippingDbConn.prepareStatement(dbQuery);
            //placing values in statement
            ps.setString(1, orderId);
            ps.setString(2, "EMPTY");
            //executing query
            ps.executeUpdate();
            System.out.println("Order Efficiency Set Succesfully");
          }
        }
        catch (SQLException se)
        {
          System.out.println("Error creating new order.");
          se.printStackTrace(System.err);
        }
      }
      //number format exception
      catch (NumberFormatException nfe)
      {
        //calling warning frame
        new Warning("Please enter a number for the Order ID");
        //resetting fields
        orderField.setText("");
      }
    }
    else if (Command == homeButton)
    {
      new Welcome("Welcome");
      this.dispose();
    }
    else if (Command == exitButton)
    {
      System.exit(0);
    }

  }
}
