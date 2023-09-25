/*
The java database class contains all of the database methods
 */
//package ShippingProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class JavaDatabase
{

  //variable declaration
  private String dbName;
  private Connection dbConn;
  private ArrayList<ArrayList<String>> data;

  //constructor for new db
  public JavaDatabase()
  {
    dbName = "";
    dbConn = null;
    data = null;
  }

  public JavaDatabase(String dbName)
  {
    setDbName(dbName);
    //Java to DB Connection
    setDbConn();
    data = null;
  }

  //mutators and acessors for DB name/connection
  public String getDbName()
  {
    return dbName;
  }

  public void setDbName(String dbName)
  {
    this.dbName = dbName;
  }

  public Connection getDbConn()
  {
    return dbConn;
  }

  public void setDbConn()
  {
    String connectionURL = "jdbc:derby:" + this.dbName;
    this.dbConn = null;
    try
    {
      //finding library
      Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
      //connection from db to java
      this.dbConn = DriverManager.getConnection(connectionURL);
    }
    //library not found
    catch (ClassNotFoundException ex)
    {
      System.out.println("Class For Name not found, check library");
      ex.printStackTrace(System.err);
    }
    //error in connection to SQL
    catch (SQLException se)
    {
      System.out.println("SQL Connection error!");
      se.printStackTrace(System.err);
    }
  }

  //method to close db connection
  public void CloseDbConn()
  {
    try
    {
      this.dbConn.close();
    }
    catch (Exception err)
    {
      System.out.println("DB Closing Error");
    }
  }

  //method to receive db contents in 2d array
  public ArrayList<ArrayList<String>> getData(String tableName,
                                              String[] tableHeaders)
  {
    //variable declaration
    int columnCount = tableHeaders.length;
    Statement s = null;
    ResultSet rs = null;
    String dbQuery = "SELECT * FROM " + tableName;
    this.data = new ArrayList<>();

    try
    {
      //query to receive data
      s = this.dbConn.createStatement();
      rs = s.executeQuery(dbQuery);

      //reading data into array list
      while (rs.next())
      {
        ArrayList<String> row = new ArrayList<>();
        for (int i = 0; i < columnCount; i++)
        {
          row.add(rs.getString(tableHeaders[i]));
        }
        this.data.add(row);
      }
      //random SQL error
    }
    catch (SQLException se)
    {
      System.out.println("SQL Error: Not able to get data");
      se.printStackTrace(System.err);
    }
    return data;
  }

  //mutator for data array list
  public void setData(ArrayList<ArrayList<String>> data)
  {
    this.data = data;
  }

  //method to receive db contents in 2d array
  public ArrayList<ArrayList<String>> getData(String tableName,
                                              String[] tableHeaders, String orderId)
  {
    //variable declaration
    int columnCount = tableHeaders.length;
    Statement s = null;
    ResultSet rs = null;
    String dbQuery = "SELECT * FROM " + tableName + " WHERE orderId = '" + orderId + "'";;
    this.data = new ArrayList<>();

    try
    {
      //query to receive data
      s = this.dbConn.createStatement();
      rs = s.executeQuery(dbQuery);

      //reading data into array list
      while (rs.next())
      {
        ArrayList<String> row = new ArrayList<>();
        for (int i = 0; i < columnCount; i++)
        {
          row.add(rs.getString(tableHeaders[i]));
        }
        this.data.add(row);
      }
      //random SQL error
    }
    catch (SQLException se)
    {
      System.out.println("SQL Error: Not able to get data");
      se.printStackTrace(System.err);
    }
    return data;
  }

  //method to create db
  public void CreateDb(String newDbName)
  {
    setDbName(newDbName);
    String connectionURL = "jdbc:derby:" + this.dbName
        + ";create=true";
    this.dbConn = null;
    try
    {
      //finding driver
      Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
      //creating db
      this.dbConn = DriverManager.getConnection(connectionURL);
      System.out.println("New Database " + this.dbName + " created!");
    }
    catch (ClassNotFoundException ex)
    {
      System.out.println("Class For Name not found, check your library");
      ex.printStackTrace(System.err);
    }
    catch (SQLException se)
    {
      System.out.println("Database not created, check your SQL or db connection");
      se.printStackTrace(System.err);
    }
  }

  public void CreateTable(String newTable, String dnName)
  {
    System.out.println(newTable);
    setDbName(dbName);
    setDbConn();
    //SQL statement
    Statement s;
    try
    {
      s = this.dbConn.createStatement();
      //creating new table
      s.execute(newTable);
      System.out.println("New table created!");
    }
    catch (SQLException se)
    {
      System.out.println("Table not created, check SQL or db connection!");
      se.printStackTrace(System.err);
    }
  }

  //convert 2d ArrayList to 2d array for jtable
  public Object[][] to2dArray(ArrayList<ArrayList<String>> data)
  {
    int columnCount = data.get(0).size();
    Object[][] dataList = new Object[data.size()][columnCount];
    for (int i = 0; i < data.size(); i++)
    {
      ArrayList<String> row = data.get(i);
      for (int j = 0; j < columnCount; j++)
      {
        dataList[i][j] = row.get(j);
      }
    }
    return dataList;
  }

  public static void main(String[] args)
  {
    //inserting dummy values for efficiency table
    String dbName = "shippingDb";
    String tableName = "efficiencyTable";
    //column name array
    String[] columnName =
    {
      "orderId", "efficiency"
    };
    //declaring db connection
    JavaDatabase objDb = new JavaDatabase(dbName);
    Connection shippingDbConn = null;
    //declaring insert components
    String orderId = "0001";
    String dummyEfficiency = "84.8%";

    shippingDbConn = objDb.getDbConn();
    //insert query)";
    String dbQuery = "INSERT INTO efficiencyTable VALUES (?, ?)";
    try
    {
      //creating prepared statement
      PreparedStatement ps = shippingDbConn.prepareStatement(dbQuery);
      //passing values for PS
      ps.setString(1, orderId);
      ps.setString(2, dummyEfficiency);

      //executing update
      ps.executeUpdate();
      System.out.println("Data inserted succesfully");
    }
    //catching SQL exceptions
    catch (SQLException se)
    {
      System.out.println("Error inserting data");
      se.printStackTrace(System.err);
    }
  }
}
