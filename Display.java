/*
Display class displays all of the crate positions in a table
 */
//package ShippingProject;
//imports

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.table.JTableHeader;
import java.sql.Connection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Display extends JFrame implements ActionListener
{

  //declaring dataSheet structures for JTable
  private Object[][] data;
  private ArrayList<ArrayList<String>> myData;
  private JTable resultTable;
  private JScrollPane resultPane;
  private JTableHeader header;
  //declaring frame components
  private JPanel buttonPanel;

  private JButton exportButton;
  private JButton allTimeButton;
  private JButton exitButton;
  private JButton homeButton;
  private JButton updateButton;
  //declaring color
  public static final Color BLUE_COLOR = new Color(150, 168, 217);

  String markerParameter;

  //constructor
  public Display(String dbName, String tableName, String[] columnName, String markerParameter)
  {
    //seting frame characteristics
    super();
    this.setBounds(100, 50, 1500, 450);
    this.getContentPane().setBackground(BLUE_COLOR);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());
    //getting db connection
    JavaDatabase objDb = new JavaDatabase(dbName);
    Connection myDbConn = objDb.getDbConn();
    myData = objDb.getData(tableName, columnName);
    //setting class 2d array to parameter 2d array
    data = objDb.to2dArray(myData);
    //creating JTable
    resultTable = new JTable(data, columnName);
    //setting table characteristics
    resultTable.setFont(new Font("Calibri", 100, 13));
    resultTable.setBackground(BLUE_COLOR);
    resultTable.setForeground(Color.WHITE);
    resultTable.setGridColor(Color.WHITE);

    resultTable.setRowHeight(20);
    //creating scroll pane and adding table
    resultPane = new JScrollPane();
    resultPane.getViewport().add(resultTable);
    resultTable.setFillsViewportHeight(true);
    //formatting table header
    header = resultTable.getTableHeader();
    header.setFont(new Font("Calibri", 100, 15));
    header.setBackground(BLUE_COLOR);
    header.setForeground(Color.WHITE);
    //adding pane
    this.add(resultPane, BorderLayout.CENTER);

    this.buttonPanel = new JPanel(new FlowLayout());
    this.add(buttonPanel, BorderLayout.SOUTH);
    //creating and adding buttons
    this.exportButton = new JButton("Export to Spreadsheet");
    exportButton.addActionListener(this);
    buttonPanel.add(exportButton);

    this.allTimeButton = new JButton("See Archived Orders");
    allTimeButton.addActionListener(this);
    buttonPanel.add(allTimeButton);

    this.updateButton = new JButton("Update Values");
    updateButton.addActionListener(this);
    buttonPanel.add(updateButton);

    this.homeButton = new JButton("Home");
    homeButton.addActionListener(this);
    buttonPanel.add(homeButton);

    this.exitButton = new JButton("Exit");
    exitButton.addActionListener(this);
    buttonPanel.add(exitButton);

    this.markerParameter = markerParameter;

    this.setVisible(true);
  }

  //main method
  public static void main(String[] args)
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
  }

  //getters and setters for button visibility
  public JButton getUpdateButton()
  {
    return this.updateButton;
  }

  public JButton getAllButton()
  {
    return this.allTimeButton;
  }

  public JButton getExportButton()
  {
    return this.exportButton;
  }

  public void setButton(JButton button)
  {
    button.setVisible(false);
  }

  //action listener
  @Override
  public void actionPerformed(ActionEvent e)
  {
    //creating command object
    String command = e.getActionCommand();
    //loop variables
    int i;
    int j;
    //conditionals for button functions
    if (command.equals("See Archived Orders"))
    {
      //creating column header array 
      String[] columnName =
      {
        "orderId", "containerId", "crateId", "length", "width",
        "height", "coordA", "coordB", "coordC", "coordD", "coordE",
        "coordF", "coordG", "coordH"
      };
      //instantiating class
      Display objDisplay = new Display("shippingDB", "archivedTable", columnName, "archived");
      this.dispose();
      //changing labels
      objDisplay.updateButton.setVisible(false);
      objDisplay.allTimeButton.setText("See Current Orders");
    }

    if (command.equals("See Current Orders"))
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
    if (command.equals("Export to Spreadsheet"));
    {
      if (markerParameter.equals("current"))
      {
        //Blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();

        //Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("Current Orders");

        //This dataSheet needs to be written (Object[])
        Map<String, Object[]> dataSheet = new TreeMap<String, Object[]>();
        dataSheet.put("1", new Object[]
        {
          "Order ID", "Container ID", "Crate ID", "Length", "Width", "Height",
          "Coordinate A", "Coordinate B", "Coordinate C", "Coordinate D",
          "Coordinate E", "Coordinate F", "Coordinate G", "Coordinate H"
        });
        //outer loop for rows
        for (i = 0; i < this.data.length; i++)
        {
          dataSheet.put(Integer.toString(i + 2), new Object[]
          {
            this.data[i][0], this.data[i][1], this.data[i][2], this.data[i][3],
            this.data[i][4], this.data[i][5], this.data[i][6], this.data[i][7],
            this.data[i][8], this.data[i][9], this.data[i][10], this.data[i][11],
            this.data[i][12], this.data[i][13]
          });
        }

        //Iterate over dataSheet and write to sheet
        Set<String> keyset = dataSheet.keySet();

        int rownum = 0;
        for (String key : keyset)
        {
          //create a row of excelsheet
          org.apache.poi.ss.usermodel.Row row = sheet.createRow(rownum++);

          //get object array of prerticuler key
          Object[] objArr = dataSheet.get(key);

          int cellnum = 0;

          for (Object obj : objArr)
          {
            Cell cell = row.createCell(cellnum++);
            if (obj instanceof String)
            {
              cell.setCellValue((String) obj);
            }
            else if (obj instanceof Integer)
            {
              cell.setCellValue((Integer) obj);
            }
          }
        }
        try
        {
          //Write the workbook in file system
          FileOutputStream out = new FileOutputStream(new File("current_orders.xlsx"));
          workbook.write(out);
          out.close();
          System.out.println("Current Orders written successfully on disk.");
        }
        catch (Exception g)
        {
          g.printStackTrace();
        }
      }
      else if (markerParameter.equals("archived"))
      {
        //Blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();

        //Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("Archived Orders");

        //This dataSheet needs to be written (Object[])
        Map<String, Object[]> dataSheet = new TreeMap<String, Object[]>();
        dataSheet.put("1", new Object[]
        {
          "Order ID", "Container ID", "Crate ID", "Length", "Width", "Height",
          "Coordinate A", "Coordinate B", "Coordinate C", "Coordinate D",
          "Coordinate E", "Coordinate F", "Coordinate G", "Coordinate H"
        });
        //outer loop for rows
        for (i = 0; i < this.data.length; i++)
        {
          dataSheet.put(Integer.toString(i + 2), new Object[]
          {
            this.data[i][0], this.data[i][1], this.data[i][2], this.data[i][3],
            this.data[i][4], this.data[i][5], this.data[i][6], this.data[i][7],
            this.data[i][8], this.data[i][9], this.data[i][10], this.data[i][11],
            this.data[i][12], this.data[i][13]
          });
        }

        //Iterate over dataSheet and write to sheet
        Set<String> keyset = dataSheet.keySet();

        int rownum = 0;
        for (String key : keyset)
        {
          //create a row of excelsheet
          org.apache.poi.ss.usermodel.Row row = sheet.createRow(rownum++);

          //get object array of prerticuler key
          Object[] objArr = dataSheet.get(key);

          int cellnum = 0;

          for (Object obj : objArr)
          {
            Cell cell = row.createCell(cellnum++);
            if (obj instanceof String)
            {
              cell.setCellValue((String) obj);
            }
            else if (obj instanceof Integer)
            {
              cell.setCellValue((Integer) obj);
            }
          }
        }
        try
        {
          //Write the workbook in file system
          FileOutputStream out = new FileOutputStream(new File("archived_orders.xlsx"));
          workbook.write(out);
          out.close();
          System.out.println("Archived Orders written successfully on disk.");
        }
        catch (Exception g)
        {
          g.printStackTrace();
        }
      }
      else if (markerParameter.equals("efficiency"))
      {
        //Blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();

        //Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("Order Efficiency");

        //This dataSheet needs to be written (Object[])
        Map<String, Object[]> dataSheet = new TreeMap<String, Object[]>();
        dataSheet.put("1", new Object[]
        {
          "Order ID", "Efficiency"
        });
        //outer loop for rows
        for (i = 0; i < this.data.length; i++)
        {
          dataSheet.put(Integer.toString(i + 2), new Object[]
          {
            this.data[i][0], this.data[i][1]
          });
        }

        //Iterate over dataSheet and write to sheet
        Set<String> keyset = dataSheet.keySet();

        int rownum = 0;
        for (String key : keyset)
        {
          //create a row of excelsheet
          org.apache.poi.ss.usermodel.Row row = sheet.createRow(rownum++);

          //get object array of prerticuler key
          Object[] objArr = dataSheet.get(key);

          int cellnum = 0;

          for (Object obj : objArr)
          {
            Cell cell = row.createCell(cellnum++);
            if (obj instanceof String)
            {
              cell.setCellValue((String) obj);
            }
            else if (obj instanceof Integer)
            {
              cell.setCellValue((Integer) obj);
            }
          }
        }
        try
        {
          //Write the workbook in file system
          FileOutputStream out = new FileOutputStream(new File("order_efficiency.xlsx"));
          workbook.write(out);
          out.close();
          System.out.println("Order Efficiency written successfully on disk.");
        }
        catch (Exception g)
        {
          g.printStackTrace();
        }
      }
    }
    if (command.equals("Update Values"))
    {
      //instantiating class
      new Update("Update");
      this.dispose();
    }
    if (command.equals("Home"))
    {
      //instantiating class
      new Welcome("Welcome");
      this.dispose();
    }
    if (command.equals("Exit"))
    {
      System.exit(0);
    }
  }
}
