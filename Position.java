/*
The position class will be used to find the position of the crates
 */
//package ShippingProject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class Position
{

  //arraylist declaration
  private ArrayList<Crate> unsortedCrates = new ArrayList<>();
  private ArrayList<Crate> containerList = new ArrayList<>();
  private ArrayList<ArrayList<Crate>> sortedCrates = new ArrayList<>();
  private ArrayList<ArrayList<Crate>> sortedCratesReturn;
  private ArrayList<Crate> readList = new ArrayList<>();
  //variable declaration
  private String crateId;
  private int length;
  int i;
  int index = 0;
  int j;
  double orderVolume;
  double crateVolume;
  double orderEfficiency;
  boolean go = true;
  //initializing db components
  String dbName = "shippingDB";
  String tableName = "currentTable";
  String orderId;
  String orderEfficiencyFormat;
  //making db connection object
  JavaDatabase objDb = new JavaDatabase(dbName);
  Connection shippingDbConn = null;
  //query elements
  String dbQuery = null;

  //blank constructor
  public Position()
  {
    this.unsortedCrates = new ArrayList<>();
    this.length = 0;
  }

  //overloading constructor
  public Position(ArrayList<Crate> unsortedCrates, int parameterLength)
  {
    //setting parameter values to local arrayLists
    this.unsortedCrates = unsortedCrates;
    this.sortedCrates = new ArrayList<>();
    this.sortedCratesReturn = new ArrayList<>();
    //setting db connection
    shippingDbConn = objDb.getDbConn();
    this.length = parameterLength;

    //instantiating default area class
    Area defaultArea = new Area();
    //setting values/coordinates of area
    defaultArea.setLength(length);
    defaultArea.setWidth(8);
    defaultArea.setCoordAX("0");
    defaultArea.setCoordAY("0");
    defaultArea.setCoordBY("8");
    defaultArea.setCoordCX(Integer.toString(parameterLength));

    //loop to go through all containers
    while (go)
    {
      sortedCratesReturn.clear();
      //defaulting boolean to false
      go = false;
      //calling method for one container
      sortedCratesReturn = findPosition(unsortedCrates, containerList,
          defaultArea, index, 'z');

      //setting index
      sortedCrates.add(sortedCratesReturn.get(0));

      for (i = 0; i < sortedCrates.get(index).size(); i++)
      {
        readList.add(sortedCrates.get(index).get(i));
      }

      //getting new unsorted list
      unsortedCrates = sortedCratesReturn.get(1);

      //conditional to continue if crates remain
      if (unsortedCrates.size() > 0)
      {
        //incrementing container index
        index++;
        //setting boolean true
        go = true;
        containerList.clear();
      }
    }

    //loop to continue until all objects updated
    for (i = 0; i < readList.size(); i++)
    {
      try
      {
        //declaring db Query 
        dbQuery = "UPDATE currentTable SET containerId = ?, coordA = ?, coordB = ?,"
            + " coordC = ?, coordD = ?, coordE = ?, coordF =?, coordG = ?, coordH = ?"
            + " WHERE orderId = ? AND crateId = ?";
        //declaring ps
        PreparedStatement ps = shippingDbConn.prepareStatement(dbQuery);
        //placing values in statement
        ps.setString(1, readList.get(i).getContainerId());
        ps.setString(2, readList.get(i).getCoordA());
        ps.setString(3, readList.get(i).getCoordB());
        ps.setString(4, readList.get(i).getCoordC());
        ps.setString(5, readList.get(i).getCoordD());
        ps.setString(6, readList.get(i).getCoordE());
        ps.setString(7, readList.get(i).getCoordF());
        ps.setString(8, readList.get(i).getCoordG());
        ps.setString(9, readList.get(i).getCoordH());
        ps.setString(10, readList.get(i).getOrderId());
        ps.setString(11, readList.get(i).getCrateId());
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
    //finding efficiency for total order
    orderVolume = (index + 1) * parameterLength * 8;
    System.out.println(orderVolume);

    //looping through readlist to add internal volume
    for (i = 0; i < readList.size(); i++)
    {
      crateVolume = crateVolume + readList.get(i).getLength() * readList.get(i).getWidth();
    }

    System.out.println(crateVolume);
    //initializing orderId
    orderId = readList.get(0).getOrderId();
    System.out.println(orderId);
    //getting efficiency value
    orderEfficiency = (crateVolume / orderVolume) * 100;
    orderEfficiencyFormat = String.format("%.1f", orderEfficiency) + "%";

    System.out.println(orderEfficiencyFormat);

    //try statement for db insert 
    try
    {
      //updating db query for set
      dbQuery = "UPDATE efficiencyTable SET efficiency = ? WHERE orderId = ?";
      //declaring prepared statement for insertion
      PreparedStatement ps = shippingDbConn.prepareStatement(dbQuery);
      //placing values in statement
      ps.setString(1, orderEfficiencyFormat);
      ps.setString(2, orderId);
      //executing query
      ps.executeUpdate();
      System.out.println("Order Created Succesfully");
    }
    catch (SQLException se)
    {
      System.out.println("Error creating new order.");
      se.printStackTrace(System.err);
    }
  }

  //find position method
  public ArrayList<ArrayList<Crate>> findPosition(ArrayList<Crate> unsortedCrates,
                                                  ArrayList<Crate> innerCrate, Area givenArea,
                                                  int index, char marker)
  {
    //variable declaration
    int i;
    int finalIndex = 0;
    int unsortedSize;
    int removeIndex;
    double volumeTotal;
    double futureVolume;
    double areaAVolume;
    double areaBVolume;
    double areaCVolume;
    double areaDVolume;
    double efficiencyMultiplier;
    double volumeCrate;
    double efficiency = 0.0;
    double efficiencyA = 0.0;
    double efficiencyB = 0.0;
    double efficiencyC = 0.0;
    double efficiencyD = 0.0;
    double bothEfficiency1 = 0.0;
    double bothEfficiency2 = 0.0;
    double finalEfficiency = 0.0;
    double lengthDifference = 0.0;
    double widthDifference = 0.0;
    String containerId;
    String objHeight;
    String xCoordTemp;
    String yCoordTemp;
    String xA;
    String yA;
    String yB;
    String xC;
    boolean check = true;
    boolean finalCheck = true;
    ArrayList<Crate> containerList = innerCrate;
    ArrayList<ArrayList<Crate>> returnList = new ArrayList<>();
    ArrayList<Crate> getList1 = new ArrayList<>();
    ArrayList<Crate> getList2 = new ArrayList<>();
    ArrayList<Crate> unsortedEfficiency = new ArrayList<>();
    ArrayList<Crate> copyList = new ArrayList<>();
    //declaring object variables
    Crate objBest = new Crate();
    Area areaA = new Area();
    Area areaB = new Area();
    Area areaC = new Area();
    Area areaD = new Area();
    Area efficientCrate = new Area();
    EfficiencyContainer areaAEfficiency = new EfficiencyContainer();
    EfficiencyContainer areaBEfficiency = new EfficiencyContainer();
    EfficiencyContainer areaCEfficiency = new EfficiencyContainer();
    EfficiencyContainer areaDEfficiency = new EfficiencyContainer();

    System.out.println(marker);

    //initializing unsorted size
    unsortedSize = unsortedCrates.size();

    //initializing total volume
    volumeTotal = givenArea.getLength() * givenArea.getWidth();

    //sorting routine to find best fit
    for (i = 0; i < unsortedCrates.size(); i++)
    {
      //checking if crate fits
      if ((unsortedCrates.get(i).getLength() <= givenArea.getLength()
          && unsortedCrates.get(i).getWidth() <= givenArea.getWidth())
          || (unsortedCrates.get(i).getWidth() <= givenArea.getLength()
          && unsortedCrates.get(i).getLength() <= givenArea.getWidth()))
      {
        //finding efficiency
        volumeCrate = unsortedCrates.get(i).getLength() * unsortedCrates.get(i).getWidth();
        //conditional to switch length and width to see fitting
        if ((unsortedCrates.get(i).getLength() <= givenArea.getLength()
            && unsortedCrates.get(i).getWidth() <= givenArea.getWidth())
            || (unsortedCrates.get(i).getLength() <= givenArea.getLength()
            && unsortedCrates.get(i).getWidth() <= givenArea.getWidth()
            && unsortedCrates.get(i).getWidth() <= givenArea.getLength()
            && unsortedCrates.get(i).getLength() <= givenArea.getWidth()))
        {
          //assigning value to efficiency;
          efficiency = (volumeCrate / volumeTotal) * 100;
          //assigning boolean true to indicate normal fitting
          check = true;
        }
        //alternative when width becomes length and vice versa
        else if (unsortedCrates.get(i).getWidth() <= givenArea.getLength()
            && unsortedCrates.get(i).getLength() <= givenArea.getWidth())
        {
          //assigning value to efficiency;
          efficiency = (volumeCrate / volumeTotal) * 100;
          //assigning boolean true to indicate normal fitting
          check = false;
        }
        //setting efficiency as greatest if is greater than previous greatest
        if (efficiency > finalEfficiency)
        {
          finalEfficiency = efficiency;
          objBest = unsortedCrates.get(i);
          finalCheck = check;
          finalIndex = i;
        }
      }
    }

    System.out.println(finalCheck);

    //getting double value for x and y coordinates of area object
    xA = givenArea.getCoordAX();
    yA = givenArea.getCoordAY();
    yB = givenArea.getCoordBY();
    xC = givenArea.getCoordCX();

    //setting difference values 
    if (finalCheck == true)
    {
      //setting remaing length and width values
      lengthDifference = Double.parseDouble(xC) - (Double.parseDouble(xA) + objBest.getLength());
      widthDifference = Double.parseDouble(yB) - (Double.parseDouble(yA) + objBest.getWidth());
    }
    else if (finalCheck == false)
    {
      //setting difference values where L is W and W is L
      lengthDifference = Double.parseDouble(xC) - (Double.parseDouble(xA) + objBest.getWidth());
      widthDifference = Double.parseDouble(yB) - (Double.parseDouble(yA) + objBest.getLength());
    }

    System.out.println(lengthDifference);
    System.out.println(widthDifference);

    //conditional to check for alternation
    if (finalCheck == true)
    {
      //initializing temp values for set
      xCoordTemp = formatCoord(Double.parseDouble(xA));
      yCoordTemp = formatCoord(Double.parseDouble(yA));
      objHeight = formatCoord(objBest.getHeight());

      //setting coordinate values A/E
      objBest.setCoordA("(" + xCoordTemp + "," + yCoordTemp + ",00.0)");
      objBest.setCoordE("(" + xCoordTemp + "," + yCoordTemp + "," + objHeight + ")");

      System.out.println(xCoordTemp);
      System.out.println(yCoordTemp);

      //initializing temp values for set
      xCoordTemp = formatCoord(Double.parseDouble(xA));
      yCoordTemp = formatCoord(Double.parseDouble(yA) + objBest.getWidth());

      //setting coordinate values B/F
      objBest.setCoordB("(" + xCoordTemp + "," + yCoordTemp + ",00.0)");
      objBest.setCoordF("(" + xCoordTemp + "," + yCoordTemp + "," + objHeight + ")");

      //initializing temp values for set
      xCoordTemp = formatCoord(Double.parseDouble(xA) + objBest.getLength());
      yCoordTemp = formatCoord(Double.parseDouble(yA));

      //setting coordinate values C/G
      objBest.setCoordC("(" + xCoordTemp + "," + yCoordTemp + ",00.0)");
      objBest.setCoordG("(" + xCoordTemp + "," + yCoordTemp + "," + objHeight + ")");

      //initializing temp values for set
      xCoordTemp = formatCoord(Double.parseDouble(xA) + objBest.getLength());
      yCoordTemp = formatCoord(Double.parseDouble(yA) + objBest.getWidth());

      //setting coordinate values D/H
      objBest.setCoordD("(" + xCoordTemp + "," + yCoordTemp + ",00.0)");
      objBest.setCoordH("(" + xCoordTemp + "," + yCoordTemp + "," + objHeight + ")");

      System.out.println(xCoordTemp);
      System.out.println(yCoordTemp);
    }
    else if (finalCheck == false)
    {
      //initializing temp values for set
      xCoordTemp = formatCoord(Double.parseDouble(xA));
      yCoordTemp = formatCoord(Double.parseDouble(yA));
      objHeight = formatCoord(objBest.getHeight());

      //setting coordinate values A/E
      objBest.setCoordA("(" + xCoordTemp + "," + yCoordTemp + ",00.0)");
      objBest.setCoordE("(" + xCoordTemp + "," + yCoordTemp + "," + objHeight + ")");

      System.out.println(xCoordTemp);
      System.out.println(yCoordTemp);

      //initializing temp values for set
      xCoordTemp = formatCoord(Double.parseDouble(xA));
      yCoordTemp = formatCoord(Double.parseDouble(yA) + objBest.getLength());

      //setting coordinate values B/F
      objBest.setCoordB("(" + xCoordTemp + "," + yCoordTemp + ",00.0)");
      objBest.setCoordF("(" + xCoordTemp + "," + yCoordTemp + "," + objHeight + ")");

      //initializing temp values for set
      xCoordTemp = formatCoord(Double.parseDouble(xA) + objBest.getWidth());
      yCoordTemp = formatCoord(Double.parseDouble(yA));

      //setting coordinate values C/G
      objBest.setCoordC("(" + xCoordTemp + "," + yCoordTemp + ",00.0)");
      objBest.setCoordG("(" + xCoordTemp + "," + yCoordTemp + "," + objHeight + ")");

      //initializing temp values for set
      xCoordTemp = formatCoord(Double.parseDouble(xA) + objBest.getWidth());
      yCoordTemp = formatCoord(Double.parseDouble(yA) + objBest.getLength());

      //setting coordinate values D/H
      objBest.setCoordD("(" + xCoordTemp + "," + yCoordTemp + ",00.0)");
      objBest.setCoordH("(" + xCoordTemp + "," + yCoordTemp + "," + objHeight + ")");

      System.out.println(xCoordTemp);
      System.out.println(yCoordTemp);
    }

    //assigning container ID
    objBest.setContainerId(String.format("%04d", index + 1));

    if (finalEfficiency == 0)
    {
      returnList.add(containerList);
      returnList.add(unsortedCrates);
      return returnList;
    }

    //adding best fitting crate to sorted array 
    containerList.add(objBest);
    unsortedCrates.remove(finalIndex);
    //adding container list and unsorted list to return
    returnList.add(containerList);
    returnList.add(unsortedCrates);

    //conditional to only set if area remains
    if (widthDifference > 0)
    {
      if (finalCheck == true)
      {
        //setting ax value for area 
        areaA.setCoordAX(xA);

        //setting ay value for area 
        areaA.setCoordAY(Double.toString(Double.parseDouble(yA) + objBest.getWidth()));

        //setting by value for area
        areaA.setCoordBY(yB);

        //setting cx value for area
        areaA.setCoordCX(Double.toString(Double.parseDouble(xA) + objBest.getLength()));

        //setting area values for length and width
        areaA.setLength(objBest.getLength());
        areaA.setWidth(widthDifference);

        //repeating process for area c
        //setting ax value for area 
        areaC.setCoordAX(xA);

        //setting ay value for area
        areaC.setCoordAY(Double.toString(Double.parseDouble(yA) + objBest.getWidth()));

        //setting by value for area
        areaC.setCoordBY(yB);

        //setting cx value for area
        areaC.setCoordCX(xC);

        //setting length and width values 
        areaC.setLength(givenArea.getLength());
        areaC.setWidth(widthDifference);
      }
      else if (finalCheck == false)
      {
        //setting ax value for area 
        areaA.setCoordAX(xA);

        //setting ay value for area 
        areaA.setCoordAY(Double.toString(Double.parseDouble(yA) + objBest.getLength()));

        //setting by value for area
        areaA.setCoordBY(yB);

        //setting cx value for area
        areaA.setCoordCX(Double.toString(Double.parseDouble(xA) + objBest.getWidth()));

        //setting area values for length and width
        areaA.setLength(objBest.getWidth());
        areaA.setWidth(widthDifference);

        //repeating process for area c
        //setting ax value for area 
        areaC.setCoordAX(xA);

        //setting ay value for area
        areaC.setCoordAY(Double.toString(Double.parseDouble(yA) + objBest.getLength()));

        //setting by value for area
        areaC.setCoordBY(yB);

        //setting cx value for area
        areaC.setCoordCX(xC);

        //setting length and width values 
        areaC.setLength(givenArea.getWidth());
        areaC.setWidth(widthDifference);
      }
    }

    //condtional to only do if length remains
    if (lengthDifference > 0)
    {
      //repeating process for area b
      if (finalCheck == true)
      {
        //setting ax value for area
        areaB.setCoordAX(Double.toString(Double.parseDouble(xA) + objBest.getLength()));

        //setting ay value for area
        areaB.setCoordAY(yA);

        //setting by value for area
        areaB.setCoordBY(yB);

        //setting cx value for area
        areaB.setCoordCX(xC);

        //setting length and width values for area b
        areaB.setLength(lengthDifference);
        areaB.setWidth(givenArea.getWidth());

        //repeating process for area d
        //setting ax value for area
        areaD.setCoordAX(Double.toString(Double.parseDouble(xA) + objBest.getLength()));

        //setting ay value for area
        areaD.setCoordAY(yA);

        //setting by value for area
        areaD.setCoordBY(Double.toString(Double.parseDouble(yA) + objBest.getWidth()));

        //setting cx value for area
        areaD.setCoordCX(xC);

        //setting values for length and width of area
        areaD.setLength(lengthDifference);
        areaD.setWidth(objBest.getWidth());
      }
      else if (finalCheck == false)
      {
        //setting ax value for area
        areaB.setCoordAX(Double.toString(Double.parseDouble(xA) + objBest.getWidth()));

        //setting ay value for area
        areaB.setCoordAY(yA);

        //setting by value for area
        areaB.setCoordBY(yB);

        //setting cx value for area
        areaB.setCoordCX(xC);

        //setting length and width values for area b
        areaB.setLength(lengthDifference);
        areaB.setWidth(givenArea.getWidth());

        //repeating process for area d
        //setting ax value for area
        areaD.setCoordAX(Double.toString(Double.parseDouble(xA) + objBest.getWidth()));

        //setting ay value for area
        areaD.setCoordAY(yA);

        //setting by value for area
        areaD.setCoordBY(Double.toString(Double.parseDouble(yA) + objBest.getLength()));

        //setting cx value for area
        areaD.setCoordCX(xC);

        //setting values for length and width of area
        areaD.setLength(lengthDifference);
        areaD.setWidth(objBest.getWidth());
      }
    }

    //setting volumes for respective areas
    areaAVolume = areaA.getLength() * areaA.getWidth();
    areaBVolume = areaB.getLength() * areaB.getWidth();
    areaCVolume = areaC.getLength() * areaC.getWidth();
    areaDVolume = areaD.getLength() * areaD.getWidth();
    //setting total volume
    futureVolume = areaAVolume + areaBVolume;

    unsortedEfficiency = new ArrayList<>(unsortedCrates);

    //getting efficiency values per area
    areaAEfficiency = findEfficiency(unsortedEfficiency, areaA);
    //setting multiplier for area 
    efficiencyMultiplier = areaAVolume / futureVolume;
    //getting efficency for area 
    efficiencyA = (areaAEfficiency.getEfficiency()) * efficiencyMultiplier;
    //changing array to remove sorted object
    if (efficiencyA > 0)
    {
      copyList.add(unsortedEfficiency.get(areaAEfficiency.getIndex()));
      unsortedEfficiency.remove(areaAEfficiency.getIndex());
    }

    //repeating process for efficiency b
    areaBEfficiency = findEfficiency(unsortedEfficiency, areaB);
    //setting multiplier for area 
    efficiencyMultiplier = areaBVolume / futureVolume;
    //getting efficiency for area
    efficiencyB = (areaBEfficiency.getEfficiency()) * efficiencyMultiplier;

    if (efficiencyA > 0)
    {
      unsortedEfficiency.add(copyList.get(0));
    }

    //resetting arraylist for other permutation
    areaCEfficiency = findEfficiency(unsortedEfficiency, areaC);
    //setting multiplier for area 
    efficiencyMultiplier = areaCVolume / futureVolume;
    //getting efficiency for area
    efficiencyC = (areaCEfficiency.getEfficiency()) * efficiencyMultiplier;
    //changing array to remove sorted object
    if (efficiencyC > 0)
    {
      copyList.add(unsortedEfficiency.get(areaCEfficiency.getIndex()));
      unsortedEfficiency.remove(areaCEfficiency.getIndex());
    }

    //repeating process for efficiency d
    areaDEfficiency = findEfficiency(unsortedEfficiency, areaD);
    //setting multiplier for area 
    efficiencyMultiplier = areaDVolume / futureVolume;
    //getting efficiency for area
    efficiencyD = (areaDEfficiency.getEfficiency()) * efficiencyMultiplier;

    //readding removed object to arraylist
    if (efficiencyC > 0)
    {
      unsortedEfficiency.add(copyList.get(0));
    }
    //combining efficiencies by permutations
    bothEfficiency1 = efficiencyA + efficiencyB;
    bothEfficiency2 = efficiencyC + efficiencyD;

    //conditional to change return based on change in lists
    if (unsortedSize != unsortedCrates.size() && unsortedCrates.size() != 0)
    {
      if (efficiencyC == 0 && efficiencyB > 0)
      {
        //recursively calling method
        return findPosition(unsortedCrates, containerList, areaB, index, 'b');
      }
      else if (efficiencyB == 0 && efficiencyC > 0)
      {
        //recursively calling method
        return findPosition(unsortedCrates, containerList, areaC, index, 'c');
      }
      else if (efficiencyB > 0 && efficiencyC > 0)
      {
        //conditional to change return based on higher efficiency
        if (Math.floor(bothEfficiency1) >= Math.floor(bothEfficiency2))
        {
          //getting two arraylists
          returnList = findPosition(unsortedCrates, containerList, areaA, index, 'a');
          //updating sorted/unsorted crates
          containerList = returnList.get(0);
          unsortedCrates = returnList.get(1);
          //getting second return
          returnList = findPosition(unsortedCrates, containerList, areaB, index, 'b');
        }
        else if (Math.floor(bothEfficiency2) > Math.floor(bothEfficiency1))
        {
          //getting two arraylists
          returnList = findPosition(unsortedCrates, containerList, areaC, index, 'c');
          //updating sorted/unsorted crates
          containerList = returnList.get(0);
          unsortedCrates = returnList.get(1);
          //getting second return
          returnList = findPosition(unsortedCrates, containerList, areaD, index, 'd');
        }
        return returnList;
      }
      else
      {
        return returnList;
      }
    }
    else
    {
      //return
      return returnList;
    }
  }

//local efficiency method for container sub areas
  public EfficiencyContainer findEfficiency(ArrayList<Crate> unsortedCrates, Area givenArea)
  {
    //variable declaration
    int i;
    int index;
    int bestIndex = 0;
    double volumeTotal;
    double volumeCrate;
    double efficiency = 0.0;
    double finalEfficiency = 0.0;
    //return object declaration
    EfficiencyContainer efficiencyFinal = new EfficiencyContainer();

    //initializing total volume
    volumeTotal = givenArea.getLength() * givenArea.getWidth();

    //sorting routine to find best fit
    for (i = 0; i < unsortedCrates.size(); i++)
    {
      //checking if crate fits
      if ((unsortedCrates.get(i).getLength() <= givenArea.getLength()
          && unsortedCrates.get(i).getWidth() <= givenArea.getWidth())
          || (unsortedCrates.get(i).getWidth() <= givenArea.getLength()
          && unsortedCrates.get(i).getLength() <= givenArea.getWidth()))
      {
        //finding efficiency
        volumeCrate = unsortedCrates.get(i).getLength() * unsortedCrates.get(i).getWidth();

        //finding volume efficiency of crate
        efficiency = (volumeCrate / volumeTotal) * 100;
        //saving index
        index = i;
        //setting efficiency as greatest if is greater than previous greatest
        if (efficiency > finalEfficiency)
        {
          //assigning index effiency to return if is more efficient
          finalEfficiency = efficiency;
          bestIndex = index;
        }
      }
    }
    //setting object attributes
    efficiencyFinal.setEfficiency(finalEfficiency);
    efficiencyFinal.setIndex(bestIndex);
    //return statement
    return efficiencyFinal;
  }

  //method to format string
  public static String formatCoord(Double coordinate)
  {
    //conditional to see change formatting based on coordinate size
    if (coordinate < 10)
    {
      //return
      return String.format("0%.1f", coordinate);
    }
    //returning parameter
    return Double.toString(coordinate);
  }
}
