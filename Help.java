/*
The help class is used to guide the user through the program
 */
//package ShippingProject;
//imports

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Help extends JFrame implements ActionListener
{

  //declaring frame components
  private JLabel helpLabel;

  private JButton returnButton;

  private JPanel buttonPanel;
  //declaring color
  public static final Color BLUE_COLOR = new Color(150, 168, 217);

  //constructor
  public Help(String name)
  {
    //setting frame characteristics
    super(name);
    this.setBounds(100, 50, 800, 400);
    this.getContentPane().setBackground(BLUE_COLOR);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());
    //creating main help label
    this.helpLabel = new JLabel("<html>1) Begin by navigating to the order button in the tool bar. <br>"
        + "2) Add crates to that order by clicking the insert button on the home page. <br>"
        + "3) You will now be able to update or delete containers. "
        + "You will also be able to archive orders, view crate organizations-archived and current-"
        + "and see the program efficiency. <br>"
        + "4) To export the displayed fields to a spreadsheet, go to the display tab "
        + "and click export. Current Orders are named current_orders.xlsx, Archived Orders"
        + "are named archived_orders.xlsx, and Program Efficiency is named order_efficiency.xlsx.<html>");
    helpLabel.setForeground(Color.WHITE);
    this.add(helpLabel, BorderLayout.CENTER);
    //creating and adding buttons 
    this.buttonPanel = new JPanel(new FlowLayout());
    this.add(buttonPanel, BorderLayout.SOUTH);

    this.returnButton = new JButton("Return");
    returnButton.addActionListener(this);
    buttonPanel.add(returnButton, BorderLayout.SOUTH);

    this.setVisible(true);
  }

  //main method
  public static void main(String[] args)
  {
    //instantiating class
    new Help("Help");
  }

  //action listener
  @Override
  public void actionPerformed(ActionEvent e)
  {
    //creating command object
    Object Command = e.getSource();
    //conditional to perform button function
    if (Command == returnButton)
    {
      new Welcome("Welcome");
      this.dispose();
    }
  }
}
