/*
The db management class is used to archive orders which have been processed 
 */
//package ShippingProject;
//imports

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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class DbManagement extends JFrame implements ActionListener
{

  //declaring frame components
  private JPanel titlePanel;
  private JPanel fieldPanel;
  private JPanel buttonPanel;

  private JLabel messageLabel;
  private JLabel orderLabel;

  private JTextField orderField;

  private JButton deleteButton;
  private JButton homeButton;
  private JButton exitButton;
  //declaring grid layout for panels
  GridLayout fieldLayout = new GridLayout(1, 2);
  //declaring color for panels
  public static final Color BLUE_COLOR = new Color(150, 168, 217);

  //constructor
  public DbManagement(String name)
  {
    //setting frame charateristics
    super(name);
    this.setBounds(100, 50, 800, 300);
    this.getContentPane().setBackground(BLUE_COLOR);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout(85, 85));
    //creating and adding panels
    this.titlePanel = new JPanel(new FlowLayout());
    titlePanel.setBackground(BLUE_COLOR);
    this.add(titlePanel, BorderLayout.NORTH);

    this.fieldPanel = new JPanel(fieldLayout);
    fieldPanel.setBackground(BLUE_COLOR);
    this.add(fieldPanel, BorderLayout.CENTER);

    this.buttonPanel = new JPanel(new FlowLayout());
    this.add(buttonPanel, BorderLayout.SOUTH);
    //creating and adding title label
    this.messageLabel = new JLabel("Please enter the Order ID of the order you"
        + " wish to archive from the current orders database");
    messageLabel.setForeground(Color.WHITE);
    titlePanel.add(messageLabel);
    //creating and adding labels and fields
    this.orderLabel = new JLabel("Order ID: ", SwingConstants.CENTER);
    orderLabel.setForeground(Color.WHITE);
    fieldPanel.add(orderLabel);

    this.orderField = new JTextField(4);
    fieldPanel.add(orderField);
    //creating and adding buttons
    this.deleteButton = new JButton("Archive Order");
    deleteButton.addActionListener(this);
    buttonPanel.add(deleteButton);

    this.homeButton = new JButton("Home");
    homeButton.addActionListener(this);
    buttonPanel.add(homeButton);

    this.exitButton = new JButton("Exit");
    exitButton.addActionListener(this);
    buttonPanel.add(exitButton);

    this.setVisible(true);
  }

  //main method
  public static void main(String[] args)
  {
    //instatiating class
    new DbManagement("DB Management");
  }

  //action listener
  @Override
  public void actionPerformed(ActionEvent e)
  {
    //creating command object
    Object Command = e.getSource();
    //variables for data transfer
    Object[][] data;
    ArrayList<ArrayList<String>> myData = new ArrayList<>();
    String dbName = "shippingDB";
    //making db conn
    JavaDatabase objDb = new JavaDatabase(dbName);
    Connection shippingDbConn = null;
    //database variables
    shippingDbConn = objDb.getDbConn();
    String dbQuery = null;
    String orderId;
    ResultSet rsOrder = null;
    Statement sOrder = null;
    ArrayList<String> existingOrder = new ArrayList<>();
    //transfer variables
    boolean checkOrder = false;
    int i;
    int j;
    int psCounter;
    String orderIdTransfer;
    String containerId;
    String crateId;
    Double length;
    Double width;
    Double height;
    String coordA;
    String coordB;
    String coordC;
    String coordD;
    String coordE;
    String coordF;
    String coordG;
    String coordH;
    String blank = " ";

    //creating column header array 
    String[] columnName =
    {
      "orderId", "containerId", "crateId", "length", "width",
      "height", "coordA", "coordB", "coordC", "coordD", "coordE",
      "coordF", "coordG", "coordH"
    };
    //conditionals for different functions of buttons
    if (Command == deleteButton)
    {
      //getting Order ID value
      orderId = orderField.getText();
      orderField.setText("");
      //checking order ID parameters
      try
      {
        //making db query for receiving order ID values
        dbQuery = "SELECT orderId FROM currentTable";
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
            checkOrder = true;
            //breaking loop
            break;
          }
        }
        //conditional for warning message
        if (checkOrder == false)
        {
          new Warning("Please enter an existing Order ID.");
        }
      }
      catch (SQLException se)
      {
        System.out.println("Error updating data");
        se.printStackTrace(System.err);
      }
      //conditional to proceed if order ID exists
      if (checkOrder == true)
      {
        //getting data
        myData = objDb.getData("currentTable", columnName, orderId);
        //setting class 2d array to parameter 2d array
        data = objDb.to2dArray(myData);
        //try statement
        try
        {
          //setting insert Query
          dbQuery = "INSERT INTO archivedTable VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, "
              + "?, ?, ?, ?, ?)";
          //declaring prepared statement for insertion
          PreparedStatement ps = shippingDbConn.prepareStatement(dbQuery);
          //loop to transfer values to other table
          for (i = 0; i < data.length; i++)
          {
            //loop to go through columns
            for (j = 0; j < columnName.length; j++)
            {
              //setting variable for set statement
              psCounter = j + 1;
              //conditonals to differe ps.set type
              if (psCounter <= 3)
              {
                //conditional to check if blank insertion is needed
                if (String.valueOf(data[i][j]).length() == 0)
                {
                  //setting prepared statement
                  ps.setString(psCounter, blank);
                }
                else
                {
                  //setting prepared statement
                  ps.setString(psCounter, String.valueOf(data[i][j]));
                }
              }
              else if (psCounter >= 4 && psCounter <= 6)
              {
                //setting prepared statement
                ps.setDouble(psCounter, Double.parseDouble(String.valueOf(data[i][j])));
              }
              else if (psCounter > 6)
              {
                //conditional to check if blank insertion is needed
                if (String.valueOf(data[i][j]).length() == 0)
                {
                  //setting prepared statement
                  ps.setString(psCounter, blank);
                }
                else
                {
                  //setting prepared statement
                  ps.setString(psCounter, String.valueOf(data[i][j]));
                }
              }
            }
            ps.executeUpdate();
          }
          //delete query and update
          dbQuery = "DELETE from currentTable WHERE orderId = '" + orderId + "'";
          //making prepared statement 
          PreparedStatement psDelete = shippingDbConn.prepareStatement(dbQuery);
          //executing update
          psDelete.executeUpdate();
          System.out.println("Data archived succesfully");
        }
        //exception catch
        catch (SQLException se)
        {
          System.out.println("Error archiving order");
          se.printStackTrace(System.err);
        }
      }
    }
    else if (Command == homeButton)
    {
      //instantiating class
      new Welcome("Welcome");
      this.dispose();
    }
    else if (Command == exitButton)
    {
      System.exit(0);
    }
  }
}
