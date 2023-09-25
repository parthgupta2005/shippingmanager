 /*
The install database class is to first install the db on the user's device
 */
//package ShippingProject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InstallDB
{

  public static void main(String[] args)
  {
    //db name declaration
    String dbName = "shippingDB";
    //object of DB class
    JavaDatabase objDb = new JavaDatabase();
    //creating new db
    objDb.CreateDb(dbName);
    //creating table
    String currentTable = "CREATE TABLE currentTable (orderId varchar(4),"
        + "containerId varchar(4), crateId varchar(7), length double, "
        + "width double, height double, coordA varchar(16), coordB varchar(16),"
        + "coordC varchar(16), coordD varchar(16), coordE varchar(16),"
        + "coordF varchar(16), coordG varchar(16), coordH varchar(16))";
    String archivedTable = "CREATE TABLE archivedTable (orderId varchar(4),"
        + "containerId varchar(4), crateId varchar(7), length double, "
        + "width double, height double, coordA varchar(16), coordB varchar(16),"
        + "coordC varchar(16), coordD varchar(16), coordE varchar(16),"
        + "coordF varchar(16), coordG varchar(16), coordH varchar(16))";
    String efficiencyTable = "CREATE TABLE efficiencyTable (orderId varchar(4), "
        + "efficiency varchar(6))";
    String containerTable = "CREATE TABLE containerTable (orderId varchar(4),"
        + "containerLength int)";
    objDb.CreateTable(currentTable, dbName);
    objDb.CreateTable(archivedTable, dbName);
    objDb.CreateTable(efficiencyTable, dbName);
    objDb.CreateTable(containerTable, dbName);
  }
}
