/*
Welcome class is the first frame the user sees and helps the user navigate through the program
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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Welcome extends JFrame implements ActionListener
{

  //declaring all JFrame Attributes
  private JButton insertButton;
  private JButton deleteButton;
  private JButton displayButton;
  private JButton efficiencyButton;
  private JButton dbButton;
  private JButton exitButton;

  private JPanel buttonPanel;
  private JPanel titlePanel;

  private JMenuBar mainBar;
  private JMenu helpMenu;
  private JMenuItem howItem;
  private JMenu orderMenu;
  private JMenuItem addItem;
  private JMenuItem displayItem;

  private JLabel welcomeLabel;
  //Declaring grid layout for panel
  GridLayout ButtonLayout = new GridLayout(2, 3);
  //declaring color
  public static final Color BLUE_COLOR = new Color(150, 168, 217);

  //constructor
  public Welcome(String name)
  {
    //setting frame characteristics
    super(name);
    this.setBounds(100, 50, 800, 600);
    this.getContentPane().setBackground(BLUE_COLOR);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());
    //creating panels
    this.titlePanel = new JPanel(new FlowLayout());
    this.add(titlePanel, BorderLayout.NORTH);

    this.buttonPanel = new JPanel(ButtonLayout);
    this.add(buttonPanel, BorderLayout.SOUTH);
    //creating title label
    this.welcomeLabel = new JLabel("Welcome to the Shipping Management System!");
    titlePanel.add(welcomeLabel);

    //creating menu bar and items
    this.mainBar = new JMenuBar();
    this.setJMenuBar(mainBar);

    orderMenu = new JMenu("Orders");
    mainBar.add(orderMenu);

    addItem = new JMenuItem("Add Order");
    addItem.addActionListener(this);
    orderMenu.add(addItem);

    displayItem = new JMenuItem("Display Orders");
    displayItem.addActionListener(this);
    orderMenu.add(displayItem);

    helpMenu = new JMenu("Help");
    mainBar.add(helpMenu);

    howItem = new JMenuItem("How To Operate");
    howItem.addActionListener(this);
    helpMenu.add(howItem);
    //declaring and adding all buttons
    this.insertButton = new JButton("Insert Containers");
    insertButton.addActionListener(this);
    buttonPanel.add(insertButton);

    this.deleteButton = new JButton("Delete Containers");
    deleteButton.addActionListener(this);
    buttonPanel.add(deleteButton);

    this.displayButton = new JButton("Display Organization");
    displayButton.addActionListener(this);
    buttonPanel.add(displayButton);

    this.efficiencyButton = new JButton("Program Efficiency");
    efficiencyButton.addActionListener(this);
    buttonPanel.add(efficiencyButton);

    this.dbButton = new JButton("Manage Database");
    dbButton.addActionListener(this);
    buttonPanel.add(dbButton);

    this.exitButton = new JButton("Exit");
    exitButton.addActionListener(this);
    buttonPanel.add(exitButton);

    this.setVisible(true);
  }

  //main method
  public static void main(String[] args)
  {
    //instatiating class
    new Welcome("Welcome");
  }

  //action performed
  @Override
  public void actionPerformed(ActionEvent e)
  {
    //declaring command object
    Object command = e.getSource();
    //local button declaration
    JButton dummyButton;
    //conditionals to open each respective frame
    if (command == insertButton)
    {
      new Insert("Insert");
      this.dispose();
    }
    else if (command == deleteButton)
    {
      new Delete("Delete");
      this.dispose();
    }
    else if (command == efficiencyButton)
    {
      String[] columnName =
      {
        "orderId", "efficiency"
      };
      //instantiating class
      Display objDisplay = new Display("shippingDB", "efficiencyTable", columnName, "efficiency");
      this.dispose();
      //customizing display
      dummyButton = objDisplay.getUpdateButton();
      objDisplay.setButton(dummyButton);
      //setting dummy as other button
      dummyButton = objDisplay.getAllButton();
      objDisplay.setButton(dummyButton);
    }
    else if (command == displayButton)
    {
      //creating column header array 
      String[] columnName =
      {
        "orderId", "containerId", "crateId", "length", "width",
        "height", "coordA", "coordB", "coordC", "coordD", "coordE",
        "coordF", "coordG", "coordH"
      };
      //instantiating class
      new Display("shippingDB", "currentTable", columnName, "current");
      this.dispose();
    }
    else if (command == dbButton)
    {
      //instantiating class
      new DbManagement("Database Management");
      this.dispose();
    }
    else if (command == addItem)
    {
      //instantiating class
      new AddOrder("Add Order");
      this.dispose();
    }
    else if (command == displayItem)
    {
      //creating column header array 
      String[] columnName =
      {
        "orderId", "containerLength"
      };
      //instantiating class
      Display objDisplay = new Display("shippingDB", "containerTable", columnName, "order");
      //changing instantiated frame
      dummyButton = objDisplay.getUpdateButton();
      objDisplay.setButton(dummyButton);
      //repeating process for other buttons
      dummyButton = objDisplay.getAllButton();
      objDisplay.setButton(dummyButton);
      dummyButton = objDisplay.getExportButton();
      objDisplay.setButton(dummyButton);
      this.dispose();
    }
    else if (command == howItem)
    {
      //instantiating class
      new Help("Help");
      this.dispose();
    }
    else if (command == exitButton)
    {
      System.exit(0);
    }
  }
}
