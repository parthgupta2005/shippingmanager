/*
The insert class is used to insert data regarding crates
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Insert extends JFrame implements ActionListener
{

  //declaring frame components
  private JPanel titlePanel;
  private JPanel fieldPanel;
  private JPanel buttonPanel;

  private JLabel messageLabel;
  private JLabel orderIdLabel;
  private JLabel widthLabel;
  private JLabel lengthLabel;
  private JLabel heightLabel;
  private JLabel numberOfLabel;

  private JTextField orderIdField;
  private JTextField crateIdField;
  private JTextField widthField;
  private JTextField lengthField;
  private JTextField heightField;
  private JTextField numberOfField;

  private JButton insertButton;
  private JButton homeButton;
  private JButton exitButton;

  //declaring grid layout for panel
  GridLayout fieldLayout = new GridLayout(3, 4);
  //declaring color for frame
  public static final Color BLUE_COLOR = new Color(150, 168, 217);

  //constructor
  public Insert(String name)
  {
    //setting frame charateristics
    super(name);
    this.setBounds(100, 50, 800, 450);
    this.getContentPane().setBackground(BLUE_COLOR);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout(100, 100));
    //creating and adding panels
    this.titlePanel = new JPanel(new FlowLayout());
    titlePanel.setBackground(BLUE_COLOR);
    this.add(titlePanel, BorderLayout.NORTH);

    this.fieldPanel = new JPanel(fieldLayout);
    fieldPanel.setBackground(BLUE_COLOR);
    this.add(fieldPanel, BorderLayout.CENTER);

    this.buttonPanel = new JPanel(new FlowLayout());
    this.add(buttonPanel, BorderLayout.SOUTH);
    //creating and addind title label
    this.messageLabel = new JLabel("<html>Please insert the information regarding "
        + "the containers. The length cannot be greater than the container length, <br> the width cannot "
        + "be greater than 8 feet, and the height cannot be greater than 8.5 feet. Your Order ID"
        + " should be in the <br> following format starting with 0001. A Crate ID will be generated "
        + "for each container. All measured values must be <br> in increments of 0.5 feet.</html>");
    messageLabel.setForeground(Color.WHITE);
    titlePanel.add(messageLabel);
    //creating and addine labels and fields
    this.orderIdLabel = new JLabel("Order ID: ", SwingConstants.RIGHT);
    orderIdLabel.setForeground(Color.WHITE);
    fieldPanel.add(orderIdLabel);

    this.orderIdField = new JTextField(4);
    fieldPanel.add(orderIdField);

    this.lengthLabel = new JLabel("Length: ", SwingConstants.RIGHT);
    lengthLabel.setForeground(Color.WHITE);
    fieldPanel.add(lengthLabel);

    this.lengthField = new JTextField(4);
    fieldPanel.add(lengthField);

    this.widthLabel = new JLabel("Width: ", SwingConstants.RIGHT);
    widthLabel.setForeground(Color.WHITE);
    fieldPanel.add(widthLabel);

    this.widthField = new JTextField(3);
    fieldPanel.add(widthField);

    this.heightLabel = new JLabel("Height: ", SwingConstants.RIGHT);
    heightLabel.setForeground(Color.WHITE);
    fieldPanel.add(heightLabel);

    this.heightField = new JTextField(3);
    fieldPanel.add(heightField);

    this.numberOfLabel = new JLabel("Number Of: ", SwingConstants.RIGHT);
    numberOfLabel.setForeground(Color.WHITE);
    fieldPanel.add(numberOfLabel);

    this.numberOfField = new JTextField(2);
    fieldPanel.add(numberOfField);
    //creating and adding buttons
    this.insertButton = new JButton("Insert");
    insertButton.addActionListener(this);
    buttonPanel.add(insertButton);

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
    new Insert("Insert");
  }

  //action listener
  @Override
  public void actionPerformed(ActionEvent e)
  {
    //creating command object
    Object Command = e.getSource();
    //initializing db components
    String dbName = "shippingDB";
    String tableName = "currentTable";
    //making db connection object
    JavaDatabase objDb = new JavaDatabase(dbName);
    Connection shippingDbConn = null;
    //declaring insert query values
    String orderId;
    String crateId = "";
    int lengthParameter = 0;
    double length;
    double width;
    double height;
    int numberOf;
    String blank = "";
    //creating statements and query
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
    //randomId variables
    ArrayList<String> alphabet = new ArrayList<>();
    ArrayList<String> existing = new ArrayList<>();
    ArrayList<String> existingOrder = new ArrayList<>();
    int[] lastFour = new int[4];
    String[] firstThree = new String[3];
    int i;
    int generated;
    //robustness booleans
    boolean check = true;
    boolean checkOrder = false;
    boolean check1 = true;
    boolean check2 = true;
    boolean check3 = true;
    boolean check4 = false;
    boolean check5 = false;

    //making alphabet
    alphabet.add("A");
    alphabet.add("B");
    alphabet.add("C");
    alphabet.add("D");
    alphabet.add("E");
    alphabet.add("F");
    alphabet.add("G");
    alphabet.add("H");
    alphabet.add("I");
    alphabet.add("J");
    alphabet.add("K");
    alphabet.add("L");
    alphabet.add("M");
    alphabet.add("N");
    alphabet.add("O");
    alphabet.add("P");
    alphabet.add("Q");
    alphabet.add("R");
    alphabet.add("S");
    alphabet.add("T");
    alphabet.add("U");
    alphabet.add("V");
    alphabet.add("W");
    alphabet.add("X");
    alphabet.add("Y");
    alphabet.add("Z");
    //declaring arraylist for computation
    ArrayList<Crate> unsortedCrateList = new ArrayList<>();
    //database data structures
    ArrayList<String> orderIdList = new ArrayList<>();
    ArrayList<String> crateIdList = new ArrayList<>();
    ArrayList<Double> lengthList = new ArrayList<>();
    ArrayList<Double> widthList = new ArrayList<>();
    ArrayList<Double> heightList = new ArrayList<>();

    //creating conditionals to perform functions of buttons
    if (Command == insertButton)
    {
      //storing user entered information
      orderId = orderIdField.getText();
      orderIdField.setText("");

      length = Double.parseDouble(lengthField.getText());
      lengthField.setText("");

      width = Double.parseDouble(widthField.getText());
      widthField.setText("");

      height = Double.parseDouble(heightField.getText());
      heightField.setText("");

      numberOf = Integer.parseInt(numberOfField.getText());
      numberOfField.setText("");
      try
      {
        //checking if Id exists
        dbQuery = "SELECT orderId FROM containerTable";
        sOrder = objDb.getDbConn().createStatement();
        rsOrder = sOrder.executeQuery(dbQuery);
        //transferring result set to array list
        while (rsOrder.next())
        {
          existingOrder.add(rsOrder.getString("orderId"));
        }
        //loop to check presence of crate Id
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
        //sending error if Id not found
        if (checkOrder == false)
        {
          //instatiating warning class
          new Warning("Please add crates to an existing order.");
        }
        //catching sql exception
      }
      catch (SQLException se)
      {
        System.out.println("Error checking orderId");
        se.printStackTrace(System.err);
      }
      //conditional to proceed
      if (checkOrder == true)
      {
        try
        {
          //reading crateId column into result set
          dbQuery = "SELECT crateId FROM currentTable WHERE orderId = '" + orderId + "'";
          s = objDb.getDbConn().createStatement();
          rs = s.executeQuery(dbQuery);
          //checking db for crateId
          while (rs.next())
          {
            existing.add(rs.getString("crateId"));
          }
          //loop to check if id exists
          for (i = 0; i < existing.size(); i++)
          {
            if (crateId.equals(existing.get(i)))
            {
              //setting boolean to false to stop process
              check = false;
              //warning message
              new Warning("Please re-enter your values, as the automatically "
                  + "generated ID alredy exists");
              //breaking loop
              break;
            }
          }
        }
        //catch statement for sql exception
        catch (SQLException se)
        {
          System.out.println("Error checking crateId");
          se.printStackTrace(System.err);
        }
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
        //checking parameters for input
        if (length > 0 && length <= lengthParameter && length % 0.5 == 0
            && width > 0 && width <= 8 && width % 0.5 == 0 && height > 0
            && height <= 8.5 && height % 0.5 == 0)
        {
          check1 = true;
        }
        else
        {
          check1 = false;
          new Warning("Please make sure to enter multiples of 0.5 feet, and follow the "
              + "dimensional limitations");
        }
        //check orderId input parameter
        if (orderId.length() == 4)
        {
          check2 = true;
        }
        else
        {
          check2 = false;
          //instantiating warning class
          new Warning("Please format your order ID correctly.");
        }
        //checking that crates arent added to archived order 
        try
        {
          //reading order ID column into result set
          dbQuery = "SELECT orderId FROM archivedTable WHERE orderId = '" + orderId + "'";
          s = objDb.getDbConn().createStatement();
          rs = s.executeQuery(dbQuery);
          //checking db for order ID
          while (rs.next())
          {
            existing.add(rs.getString("orderId"));
          }
          //loop to check if id exists
          for (i = 0; i < existing.size(); i++)
          {
            if (orderId.equals(existing.get(i)))
            {
              //setting boolean to false to stop process
              check3 = false;
              //warning message
              new Warning("Crates cannot be added to archived orders.");
              //breaking loop
              break;
            }
          }
        }
        //catch statement for sql exception
        catch (SQLException se)
        {
          System.out.println("Error checking order ID");
          se.printStackTrace(System.err);
        }

        //conditional to make sure all conditions are met
        if (check == true && check1 == true && check2 == true && check3 == true)
        {
          while (numberOf > 0)
          {
            //generating first three letters of Id
            for (i = 0; i < 3; i++)
            {
              generated = (int) (Math.floor(Math.random() * 26));
              firstThree[i] = alphabet.get(generated);
            }
            //generating last four number of Id
            for (i = 0; i < 4; i++)
            {
              generated = (int) (Math.floor(Math.random() * 10));
              lastFour[i] = generated;
            }
            //reading arrays into Id String
            for (i = 0; i < 3; i++)
            {
              crateId = crateId + firstThree[i];
            }
            for (i = 0; i < 4; i++)
            {
              crateId = crateId + Integer.toString(lastFour[i]);
            }

            try
            {
              //updating db query for insert
              dbQuery = "INSERT INTO currentTable VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, "
                  + "?, ?, ?, ?, ?)";
              //declaring prepared statement for insertion
              PreparedStatement ps = shippingDbConn.prepareStatement(dbQuery);
              //placing values in statement
              ps.setString(1, orderId);
              ps.setString(2, blank);
              ps.setString(3, crateId);
              ps.setDouble(4, length);
              ps.setDouble(5, width);
              ps.setDouble(6, height);
              ps.setString(7, blank);
              ps.setString(8, blank);
              ps.setString(9, blank);
              ps.setString(10, blank);
              ps.setString(11, blank);
              ps.setString(12, blank);
              ps.setString(13, blank);
              ps.setString(14, blank);
              //executing query
              ps.executeUpdate();
              System.out.println("Data inserted succesfully");
            }
            catch (SQLException se)
            {
              System.out.println("Error inserting data");
              se.printStackTrace(System.err);
            }
            //resetting crate id and iterating loop again
            crateId = "";
            numberOf--;
          }
        }
      }
      //try statement
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
      //creating objects for calculation
      for (i = 0; i < orderIdList.size(); i++)
      {
        //creating objects in ArrayList
        unsortedCrateList.add(new Crate(orderIdList.get(i), crateIdList.get(i),
            lengthList.get(i), widthList.get(i), heightList.get(i)));
      }
      //calling comptutation class
      new Position(unsortedCrateList, lengthParameter);
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
