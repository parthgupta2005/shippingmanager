/*
The warning class is prompted when an error occurs in the user input
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

public class Warning extends JFrame implements ActionListener
{

  //declaring JFrame components
  private JLabel warningLabel;

  private JPanel buttonPanel;

  private JButton returnButton;
  //declaring color
  public static final Color BLUE_COLOR = new Color(150, 168, 217);

  //constructor with warning message
  public Warning(String errorMessage)
  {
    //setting frame characteristics
    super();
    this.setBounds(100, 50, 800, 400);
    this.getContentPane().setBackground(BLUE_COLOR);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());
    //creating and adding label, panel, and button
    this.warningLabel = new JLabel("<html>" + errorMessage + "<html>", SwingConstants.CENTER);
    warningLabel.setForeground(Color.WHITE);
    this.add(warningLabel, BorderLayout.CENTER);

    this.buttonPanel = new JPanel(new FlowLayout());
    this.add(buttonPanel, BorderLayout.SOUTH);

    this.returnButton = new JButton("Return");
    returnButton.addActionListener(this);
    buttonPanel.add(returnButton);

    this.setVisible(true);
  }

  //main method
  public static void main(String[] args)
  {
    //instantiating warning class
    new Warning("Warning Message");
  }

  //action performed
  @Override
  public void actionPerformed(ActionEvent e)
  {
    //creating command object
    Object Command = e.getSource();
    //conditional to dispose frame after button is clicked
    if (Command == returnButton)
    {
      this.dispose();
    }
  }
}
