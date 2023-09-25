/*
The update class is used to update any and all data points
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
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Update extends JFrame implements ActionListener
{

  //JFrame component declarations
  private JLabel messageLabel;
  private JLabel orderIdLabel;
  private JLabel crateIdLabel;
  private JLabel updatedLabel;
  private JLabel lengthLabel;
  private JLabel heightLabel;
  private JLabel widthLabel;
  private JLabel emptyLabel;

  private JPanel wholePanel;
  private JPanel titlePanel;
  private JPanel fieldPanel;
  private JPanel radioPanel;
  private JPanel buttonPanel;

  private JTextField orderIdField;
  private JTextField crateIdField;
  private JTextField updateField;

  private JRadioButton IdButton;
  private JRadioButton widthButton;
  private JRadioButton lengthButton;
  private JRadioButton heightButton;

  private JButton updateButton;
  private JButton homeButton;
  private JButton exitButton;

  private ButtonGroup buttonGroup;

  //declaring grid layouts for panels
  GridLayout wholeLayout = new GridLayout(4, 1);
  GridLayout fieldLayout = new GridLayout(2, 4);
  GridLayout radioLayout = new GridLayout(1, 8);
  //declaring colors
  public static final Color BLUE_COLOR = new Color(150, 168, 217);

  //constructor
  public Update(String name)
  {
    //setting frame characteristics
    super(name);
    this.setBounds(100, 50, 800, 400);
    this.getContentPane().setBackground(Color.LIGHT_GRAY);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());
    //declaring anda adding panels
    this.wholePanel = new JPanel(wholeLayout);
    this.add(wholePanel);

    this.titlePanel = new JPanel(new FlowLayout());
    titlePanel.setBackground(BLUE_COLOR);
    wholePanel.add(titlePanel);

    this.fieldPanel = new JPanel(fieldLayout);
    fieldPanel.setBackground(BLUE_COLOR);
    wholePanel.add(fieldPanel);

    this.radioPanel = new JPanel(radioLayout);
    radioPanel.setBackground(BLUE_COLOR);
    wholePanel.add(radioPanel);

    this.buttonPanel = new JPanel(new FlowLayout());
    wholePanel.add(buttonPanel);
    //declaring and adding labels and fields
    this.messageLabel = new JLabel("<html>Please update the information regarding the crates, "
        + " Remember that the length <br> cannot be greater than the container length, "
        + "the width cannot be greater than 8 feet, <br> and the height cannot be "
        + "greater than 8.5 feet. All measured values must be in increments <br> of 0.5 feet.</html>");
    messageLabel.setForeground(Color.WHITE);
    titlePanel.add(messageLabel);

    this.orderIdLabel = new JLabel("Order ID: ", SwingConstants.RIGHT);
    orderIdLabel.setForeground(Color.WHITE);
    fieldPanel.add(orderIdLabel);

    this.orderIdField = new JTextField(4);
    fieldPanel.add(orderIdField);

    this.crateIdLabel = new JLabel("Crate ID: ", SwingConstants.RIGHT);
    crateIdLabel.setForeground(Color.WHITE);
    fieldPanel.add(crateIdLabel);

    this.crateIdField = new JTextField(7);
    fieldPanel.add(crateIdField);

    this.updatedLabel = new JLabel("Updated Value: ", SwingConstants.RIGHT);
    updatedLabel.setForeground(Color.WHITE);
    fieldPanel.add(updatedLabel);

    this.updateField = new JTextField(7);
    fieldPanel.add(updateField);
    //creating empty label to maintain grid layout
    this.emptyLabel = new JLabel(" ");
    fieldPanel.add(emptyLabel);
    //creating button group for radio buttons
    this.buttonGroup = new ButtonGroup();
    //creating labels and radio buttons for user choice
    this.crateIdLabel = new JLabel("Crate ID", SwingConstants.RIGHT);
    crateIdLabel.setForeground(Color.WHITE);
    radioPanel.add(crateIdLabel);

    this.IdButton = new JRadioButton();
    IdButton.addActionListener(this);
    buttonGroup.add(IdButton);
    radioPanel.add(IdButton);

    this.lengthLabel = new JLabel("Length", SwingConstants.RIGHT);
    lengthLabel.setForeground(Color.WHITE);
    radioPanel.add(lengthLabel);

    this.lengthButton = new JRadioButton();
    lengthButton.addActionListener(this);
    buttonGroup.add(lengthButton);
    radioPanel.add(lengthButton);

    this.widthLabel = new JLabel("Width", SwingConstants.RIGHT);
    widthLabel.setForeground(Color.WHITE);
    radioPanel.add(widthLabel);

    this.widthButton = new JRadioButton();
    widthButton.addActionListener(this);
    buttonGroup.add(widthButton);
    radioPanel.add(widthButton);

    this.heightLabel = new JLabel("Height", SwingConstants.RIGHT);
    heightLabel.setForeground(Color.WHITE);
    radioPanel.add(heightLabel);

    this.heightButton = new JRadioButton();
    heightButton.addActionListener(this);
    buttonGroup.add(heightButton);
    radioPanel.add(heightButton);

    this.updateButton = new JButton("Update");
    updateButton.addActionListener(this);
    buttonPanel.add(updateButton);

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
    new Update("Update");
  }

  //action performed
  @Override
  public void actionPerformed(ActionEvent e)
  {
    //declaring command object
    Object Command = e.getSource();
    //setting condtionals for different button selections
    if (Command == updateButton)
    {
      //initializing db components
      String dbName = "shippingDB";
      String tableName = "currentTable";
      //making db connection object
      JavaDatabase objDb = new JavaDatabase(dbName);
      Connection shippingDbConn = null;
      //declaring insert query values
      String orderId = "";
      String crateId = "";
      String crateIdUpdated = "";
      double updated;
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
      //robustness variables
      ArrayList<Character> alphabet = new ArrayList<>();
      ArrayList<Character> numbers = new ArrayList<>();
      ArrayList<String> existingOrder = new ArrayList<>();
      ArrayList<String> existing = new ArrayList<>();
      //declaring arraylist for computation
      ArrayList<Crate> unsortedCrateList = new ArrayList<>();
      //database data structures
      ArrayList<String> orderIdList = new ArrayList<>();
      ArrayList<String> crateIdList = new ArrayList<>();
      ArrayList<Double> lengthList = new ArrayList<>();
      ArrayList<Double> widthList = new ArrayList<>();
      ArrayList<Double> heightList = new ArrayList<>();
      //conditional variables
      boolean checkOrder = false;
      boolean checkOrder2 = true;
      boolean check = false;
      boolean check1 = false;
      boolean check2 = false;
      boolean check3 = false;
      boolean check4 = true;
      boolean check5 = false;
      boolean check6 = false;
      boolean check7 = false;
      int i;
      int lengthParameter = 0;
      int j;
      char test;
      String test1;
      //adding alphabet to check entered ID
      alphabet.add('A');
      alphabet.add('B');
      alphabet.add('C');
      alphabet.add('D');
      alphabet.add('E');
      alphabet.add('F');
      alphabet.add('G');
      alphabet.add('H');
      alphabet.add('I');
      alphabet.add('J');
      alphabet.add('K');
      alphabet.add('L');
      alphabet.add('M');
      alphabet.add('N');
      alphabet.add('O');
      alphabet.add('P');
      alphabet.add('Q');
      alphabet.add('R');
      alphabet.add('S');
      alphabet.add('T');
      alphabet.add('U');
      alphabet.add('V');
      alphabet.add('W');
      alphabet.add('X');
      alphabet.add('Y');
      alphabet.add('Z');
      //adding digits to check entered ID
      numbers.add('0');
      numbers.add('1');
      numbers.add('2');
      numbers.add('3');
      numbers.add('4');
      numbers.add('5');
      numbers.add('6');
      numbers.add('7');
      numbers.add('8');
      numbers.add('9');

      if (Command == updateButton)
      {
        //getting order ID value & checking int input
        orderId = orderIdField.getText();
        orderIdField.setText("");

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
        if (checkOrder == true)
        {
          //checking that order isnt archived
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
                checkOrder2 = false;
                //warning message
                new Warning("Archived orders cannot be updated");
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
          //proceeding with process if Order ID exists
          if (checkOrder2 == true)
          {
            try
            {
              //setting value
              crateId = crateIdField.getText();
              crateIdField.setText("");
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
                new Warning("Please enter a existing Crate ID to update.");
              }
              //catching sql exception
            }
            catch (SQLException se)
            {
              System.out.println("Error checking crateId");
              se.printStackTrace(System.err);
            }
            //conditonal for radio button
            if (IdButton.isSelected())
            {
              //getting values 
              crateIdUpdated = updateField.getText();
              updateField.setText("");
              //try statement
              try
              {
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
                  if (crateIdUpdated.equals(existing.get(i)))
                  {
                    //setting boolean to true to stop process
                    check = false;
                    //breaking loop
                    break;
                  }
                }
                //sending error if Id not found
                if (check == false)
                {
                  //instatiating warning class
                  new Warning("Please enter a unique Crate ID as updated value.");
                }
                if (check == true)
                {
                  //assigning char variable first string char
                  test = crateIdUpdated.charAt(0);
                  //loop to see if first char is a letter
                  for (j = 0; j < alphabet.size(); j++)
                  {
                    if (test == alphabet.get(j))
                    {
                      check1 = true;
                      break;
                    }
                  }
                  //warning message for invalid input
                  if (check == false)
                  {
                    new Warning("Please enter a valid Updated Crate ID.");
                  }
                  //repeating process for second and third indexes
                  test = crateIdUpdated.charAt(1);
                  //loop to see if second char is a letter
                  for (j = 0; j < alphabet.size(); j++)
                  {
                    if (test == alphabet.get(j))
                    {
                      check2 = true;
                      break;
                    }
                  }
                  //warning message for invalid input
                  if (check2 == false)
                  {
                    new Warning("Please enter a valid Updated Crate ID.");
                  }
                  test = crateIdUpdated.charAt(2);
                  //loop to see if first char is a letter
                  for (j = 0; j < alphabet.size(); j++)
                  {
                    if (test == alphabet.get(j))
                    {
                      check3 = true;
                      break;
                    }
                  }
                  //warning message for invalid input
                  if (check3 == false)
                  {
                    new Warning("Please enter a valid Updated Crate ID.");
                  }
                  //assigning char to variable
                  test = crateIdUpdated.charAt(3);
                  //loop to see if fourth char is number
                  for (j = 0; j < numbers.size(); j++)
                  {
                    if (test == numbers.get(j))
                    {
                      check4 = true;
                      break;
                    }
                  }
                  //warning message for invalid input
                  if (check4 == false)
                  {
                    new Warning("Please enter a valid Updated Crate ID.");
                  }
                  //warning message for invalid input
                  if (check4 == false)
                  {
                    new Warning("Please enter a valid Updated Crate ID.");
                  }
                  //repeating process for remaining chars
                  test = crateIdUpdated.charAt(4);
                  //loop to see if fifth char is number
                  for (j = 0; j < numbers.size(); j++)
                  {
                    if (test == numbers.get(j))
                    {
                      check5 = true;
                      break;
                    }
                  }
                  //warning message for invalid input
                  if (check5 == false)
                  {
                    new Warning("Please enter a valid Updated Crate ID.");
                  }
                  test = crateIdUpdated.charAt(5);
                  //loop to see if sixth char is number
                  for (j = 0; j < numbers.size(); j++)
                  {
                    if (test == numbers.get(j))
                    {
                      check6 = true;
                      break;
                    }
                  }
                  //warning message for invalid input
                  if (check6 == false)
                  {
                    new Warning("Please enter a valid Updated Crate ID.");
                  }
                  test = crateIdUpdated.charAt(6);
                  //loop to see if seventh char is number
                  for (j = 0; j < numbers.size(); j++)
                  {
                    if (test == numbers.get(j))
                    {
                      check7 = true;
                      break;
                    }
                  }
                  //warning message for invalid input
                  if (check7 == false)
                  {
                    new Warning("Please enter a valid Updated Crate ID.");
                  }
                  //conditional checking format for crateId
                  if (check1 == true && check2 && check3 == true && check4 == true
                      && check5 == true && check6 == true && check7 == true)
                  {
                    try
                    {
                      //making update query
                      dbQuery = "UPDATE currentTable SET crateId = ? WHERE orderId = ? AND"
                          + " crateId = ?";
                      //declaring prepared statement
                      PreparedStatement ps = shippingDbConn.prepareStatement(dbQuery);
                      //setting prepared statement values
                      ps.setString(1, crateIdUpdated);
                      ps.setString(2, orderId);
                      ps.setString(3, crateId);

                      System.out.println(crateIdUpdated);
                      //executing query
                      ps.executeUpdate();
                      System.out.println("Data updated succesfully");
                    }
                    catch (SQLException se)
                    {
                      System.out.println("Error updating data");
                      se.printStackTrace(System.err);
                    }
                  }
                }
              }
              //sql exception catch
              catch (SQLException se)
              {
                System.out.println("Error updating data");
                se.printStackTrace(System.err);
              }
            }
            //conditional for radio button
            else if (lengthButton.isSelected())
            {
              //getting values for length
              updated = Double.parseDouble(updateField.getText());
              updateField.setText("");
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
              System.out.println(lengthParameter);
              //checking parameters for dimension
              if (updated > 0 && updated <= lengthParameter && updated % 0.5 == 0)
              {
                check = true;
              }
              else
              {
                check = false;
                new Warning("Please input the updated value correctly.");
              }
              //update sequence
              if (check == true)
              {
                try
                {
                  //declaring db Query 
                  dbQuery = "UPDATE currentTable SET length = ? WHERE orderId = ? "
                      + "AND crateId = ?";
                  //declaring ps
                  PreparedStatement ps = shippingDbConn.prepareStatement(dbQuery);
                  //placing values in statement
                  ps.setDouble(1, updated);
                  ps.setString(2, orderId);
                  ps.setString(3, crateId);
                  //executing update
                  ps.executeUpdate();
                  System.out.println("Data updated succesfully");
                }
                catch (SQLException se)
                {
                  System.out.println("Error updating values");
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
            }
            else if (widthButton.isSelected())
            {
              //getting values for width
              updated = Double.parseDouble(updateField.getText());
              updateField.setText("");
              //checking parameters for dimension
              if (updated > 0 && updated <= 8.0 && updated % 0.5 == 0)
              {
                check = true;
              }
              else
              {
                check = false;
                new Warning("Please input the updated value correctly.");
              }
              //update sequence
              if (check == true)
              {
                try
                {
                  //declaring db Query 
                  dbQuery = "UPDATE currentTable SET width = ? WHERE orderId = ? "
                      + "AND crateId = ?";
                  //declaring ps
                  PreparedStatement ps = shippingDbConn.prepareStatement(dbQuery);
                  //placing values in statement
                  ps.setDouble(1, updated);
                  ps.setString(2, orderId);
                  ps.setString(3, crateId);

                  ps.executeUpdate();
                  System.out.println("Data updated succesfully");
                }
                catch (SQLException se)
                {
                  System.out.println("Error updating values");
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
            }
            else if (heightButton.isSelected())
            {
              //getting values for height
              updated = Double.parseDouble(updateField.getText());
              updateField.setText("");
              //checking parameters for dimension
              if (updated > 0 && updated <= 8.5 && updated % 0.5 == 0)
              {
                check = true;
              }
              else
              {
                check = false;
                new Warning("Please input the updated value correctly.");
              }
              //update sequence
              if (check == true)
              {
                try
                {
                  //declaring db Query 
                  dbQuery = "UPDATE currentTable SET height = ? WHERE orderId = ? "
                      + "AND crateId = ?";
                  //declaring ps
                  PreparedStatement ps = shippingDbConn.prepareStatement(dbQuery);
                  //placing values in statement
                  ps.setDouble(1, updated);
                  ps.setString(2, orderId);
                  ps.setString(3, crateId);
                  //executing update
                  ps.executeUpdate();
                  System.out.println("Data updated succesfully");
                }
                catch (SQLException se)
                {
                  System.out.println("Error updating values");
                  se.printStackTrace(System.err);
                }
              }
            }
          }
        }
      }
    }
    else if (Command == homeButton)
    {
      //instatiating welcome class
      new Welcome("Welcome");
      this.dispose();
    }
    else if (Command == exitButton)
    {
      //exiting
      System.exit(0);
    }
  }
}
