/*
The delete class is to delete any crates that the user may wish to remove
 */
//package ShippingProject;
//imports

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Delete extends JFrame implements ActionListener
{

  //declaring frame components
  private JPanel titlePanel;
  private JPanel fieldPanel;
  private JPanel buttonPanel;

  private JLabel messageLabel;
  private JLabel orderLabel;
  private JLabel crateLabel;
  private JLabel emptyLabel;

  private JTextField orderField;
  private JTextField crateField;

  private JButton deleteButton;
  private JButton homeButton;
  private JButton exitButton;
  //declaring grid layout 
  GridLayout fieldLayout = new GridLayout(1, 4);
  //declaring color for panel
  public static final Color BLUE_COLOR = new Color(150, 168, 217);

  //constructor
  public Delete(String name)
  {
    //setting frame charateristics
    super(name);
    this.setBounds(100, 50, 800, 220);
    this.getContentPane().setBackground(BLUE_COLOR);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout(50, 50));
    //creating and adding panels
    this.titlePanel = new JPanel(new FlowLayout());
    titlePanel.setBackground(BLUE_COLOR);
    this.add(titlePanel, BorderLayout.NORTH);

    this.fieldPanel = new JPanel(fieldLayout);
    fieldPanel.setBackground(BLUE_COLOR);
    this.add(fieldPanel, BorderLayout.CENTER);

    this.buttonPanel = new JPanel(new FlowLayout());
    this.add(buttonPanel, BorderLayout.SOUTH);
    //creating message label
    this.messageLabel = new JLabel("Please enter the Crate ID of the container "
        + "you wish to delete from the database");
    messageLabel.setForeground(Color.WHITE);
    titlePanel.add(messageLabel);
    //creating labels and fields for input
    this.orderLabel = new JLabel("Order ID: ", SwingConstants.CENTER);
    orderLabel.setForeground(Color.WHITE);
    fieldPanel.add(orderLabel);

    this.orderField = new JTextField(4);
    fieldPanel.add(orderField);

    this.crateLabel = new JLabel("Crate ID: ", SwingConstants.CENTER);
    crateLabel.setForeground(Color.WHITE);
    fieldPanel.add(crateLabel);

    this.crateField = new JTextField(7);
    fieldPanel.add(crateField);
    //creating and adding buttons
    this.deleteButton = new JButton("Delete");
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
    //instantiating class
    new Delete("Delete");
  }

  //action listener
  @Override
  public void actionPerformed(ActionEvent e)
  {
    //creating command object
    Object Command = e.getSource();
    //initializing db componenets
    String dbName = "shippingDB";
    String tableName = "currentTable";
    //making db connection object
    JavaDatabase objDb = new JavaDatabase(dbName);
    Connection shippingDbConn = null;
    //declaring insert query values
    String orderId;
    String crateId = "";
    //declaring query and connection
    shippingDbConn = objDb.getDbConn();
    String dbQuery = null;
    ResultSet rs = null;
    ResultSet rsOrder = null;
    ResultSet rsOrder1 = null;
    ResultSet rsCrate = null;
    ResultSet rsLength = null;
    ResultSet rsWidth = null;
    ResultSet rsHeight = null;
    Statement s = null;
    Statement sOrder = null;
    Statement sOrder1 = null;
    Statement sCrate = null;
    Statement sLength = null;
    Statement sWidth = null;
    Statement sHeight = null;
    //robustness variables
    ArrayList<String> existingOrder = new ArrayList<>();
    ArrayList<String> existing = new ArrayList<>();
    boolean check = false;
    boolean checkOrder = false;
    int i;
    int lengthParameter = 0;
    //declaring arraylist for computation
    ArrayList<Crate> unsortedCrateList = new ArrayList<>();
    //database data structures
    ArrayList<String> orderIdList = new ArrayList<>();
    ArrayList<String> crateIdList = new ArrayList<>();
    ArrayList<Double> lengthList = new ArrayList<>();
    ArrayList<Double> widthList = new ArrayList<>();
    ArrayList<Double> heightList = new ArrayList<>();
    //conditionals for button selections
    if (Command == deleteButton)
    {
      //receiving value for Order ID
      orderId = orderField.getText();
      orderField.setText("");
      //checking orderId parameters
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
      //conditional to proceed if Order ID exists
      try
      {
        //setting value
        crateId = crateField.getText();
        crateField.setText("");
        //checking if Id exists
        dbQuery = "SELECT crateId FROM currentTable WHERE orderId = '" + orderId + "'";
        s = objDb.getDbConn().createStatement();
        rs = s.executeQuery(dbQuery);
        //transferring result set to array list
        while (rs.next())
        {
          existing.add(rs.getString("crateId"));
        }
        //loop to check presence of crate Id
        for (i = 0; i < existing.size(); i++)
        {
          if (crateId.equals(existing.get(i)))
          {
            //setting boolean to true to stop process
            check = true;
            //breaking loop
            break;
          }
        }
        //sending error if Id not found
        if (check == false)
        {
          //instatiating warning class
          new Warning("Please enter a existing Crate ID to delete.");
        }
      }
      //catching sql exception
      catch (SQLException se)
      {
        System.out.println("Error checking crateId");
        se.printStackTrace(System.err);
      }
      //deletion process
      if (check == true && checkOrder == true)
      {
        try
        {
          //setting query
          dbQuery = "DELETE from currentTable WHERE orderId = ? AND crateId = ?";
          //making prepared statement
          PreparedStatement ps = shippingDbConn.prepareStatement(dbQuery);
          //settign strings in ps
          ps.setString(1, orderId);
          ps.setString(2, crateId);
          //executing update
          ps.executeUpdate();
          System.out.println("Data deleted succesfully");
        }
        //exception catch
        catch (SQLException se)
        {
          System.out.println("Error checking crateId");
          se.printStackTrace(System.err);
        }
        try
        {
          //setting db query and getting result set
          dbQuery = "SELECT orderId FROM currentTable WHERE orderId = '" + orderId + "'";
          sOrder1 = objDb.getDbConn().createStatement();
          rsOrder1 = s.executeQuery(dbQuery);
          //transferring from rs to arraylist
          while (rsOrder1.next())
          {
            orderIdList.add(rsOrder1.getString("orderId"));
          }

          //setting db query and getting result set
          dbQuery = "SELECT crateId FROM currentTable WHERE orderId = '" + orderId + "'";
          sCrate = objDb.getDbConn().createStatement();
          rsCrate = s.executeQuery(dbQuery);
          //transferring from rs to arraylist
          while (rsCrate.next())
          {
            crateIdList.add(rsCrate.getString("crateId"));
          }

          //setting db query and getting result set
          dbQuery = "SELECT length FROM currentTable WHERE orderId = '" + orderId + "'";
          sLength = objDb.getDbConn().createStatement();
          rsLength = s.executeQuery(dbQuery);
          //transferring from rs to arraylist
          while (rsLength.next())
          {
            lengthList.add(rsLength.getDouble("length"));
          }

          //setting db query and getting result set
          dbQuery = "SELECT width FROM currentTable WHERE orderId = '" + orderId + "'";
          sWidth = objDb.getDbConn().createStatement();
          rsWidth = s.executeQuery(dbQuery);
          //transferring from rs to arraylist
          while (rsWidth.next())
          {
            widthList.add(rsWidth.getDouble("width"));
          }

          //setting db query and getting result set
          dbQuery = "SELECT height FROM currentTable WHERE orderId = '" + orderId + "'";
          sHeight = objDb.getDbConn().createStatement();
          rsHeight = s.executeQuery(dbQuery);
          //transferring from rs to arraylist
          while (rsHeight.next())
          {
            heightList.add(rsHeight.getDouble("height"));
          }
        }
        //catch statement for sql exception
        catch (SQLException se)
        {
          System.out.println("Error getting objects of crates");
          se.printStackTrace(System.err);
        }
        //try statement for length parameter
        try
        {
          //getting length parameter from table
          dbQuery = "SELECT containerLength FROM containerTable WHERE "
              + "orderId = '" + orderId + "'";
          sOrder = objDb.getDbConn().createStatement();
          rsOrder = sOrder.executeQuery(dbQuery);
          //transferring values to arraylist
          while (rsOrder.next())
          {
            existingOrder.add(rsOrder.getString("containerLength"));
          }
          //setting length parameter value
          lengthParameter = Integer.parseInt(existingOrder.get(existingOrder.size() - 1));
        }
        //catching sql exception
        catch (SQLException se)
        {
          System.out.println("Error checking length parameter");
          se.printStackTrace(System.err);
        }
        //creating objects for calculation
        for (i = 0; i < orderIdList.size(); i++)
        {
          //creating objects in ArrayList
          unsortedCrateList.add(new Crate(orderIdList.get(i), crateIdList.get(i),
              lengthList.get(i), widthList.get(i), heightList.get(i)));
        }
        System.out.println(unsortedCrateList.size());
        //calling comptutation class
        new Position(unsortedCrateList, lengthParameter);
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
