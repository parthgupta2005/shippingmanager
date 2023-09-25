/*
This is the OOP class that instantiates crates
 */
//package ShippingProject;

public class Crate
{

  //private attribute declarating
  private String crateId;
  private String containerId;
  private String orderId;
  private double length;
  private double width;
  private double height;
  private String coordA;
  private String coordB;
  private String coordC;
  private String coordD;
  private String coordE;
  private String coordF;
  private String coordG;
  private String coordH;

  //blank constructor
  public Crate()
  {
    this.orderId = "";
    this.containerId = "";
    this.crateId = "";
    this.height = 0.0;
    this.width = 0.0;
    this.height = 0.0;
    this.coordA = "";
    this.coordB = "";
    this.coordC = "";
    this.coordD = "";
    this.coordE = "";
    this.coordF = "";
    this.coordG = "";
    this.coordH = "";
  }

  //overloading constructor
  public Crate(String orderId, String crateId, double length, double width, double height)
  {
    this.orderId = orderId;
    this.crateId = crateId;
    this.length = length;
    this.width = width;
    this.height = height;
    this.containerId = "";
    this.coordA = "";
    this.coordB = "";
    this.coordC = "";
    this.coordD = "";
    this.coordE = "";
    this.coordF = "";
    this.coordG = "";
    this.coordH = "";
  }

  //setters
  public void setOrderId(String orderId)
  {
    this.orderId = orderId;
  }

  public void setContainerId(String containerId)
  {
    this.containerId = containerId;
  }

  public void setCrateId(String crateId)
  {
    this.crateId = crateId;
  }

  public void setLength(Double length)
  {
    this.length = length;
  }

  public void setWidth(Double width)
  {
    this.width = width;
  }

  public void setHeight(Double height)
  {
    this.height = height;
  }

  public void setCoordA(String coordA)
  {
    this.coordA = coordA;
  }

  public void setCoordB(String coordB)
  {
    this.coordB = coordB;
  }

  public void setCoordC(String coordC)
  {
    this.coordC = coordC;
  }

  public void setCoordD(String coordD)
  {
    this.coordD = coordD;
  }

  public void setCoordE(String coordE)
  {
    this.coordE = coordE;
  }

  public void setCoordF(String coordF)
  {
    this.coordF = coordF;
  }

  public void setCoordG(String coordG)
  {
    this.coordG = coordG;
  }

  public void setCoordH(String coordH)
  {
    this.coordH = coordH;
  }

  //getters
  public String getOrderId()
  {
    return this.orderId;
  }

  public String getContainerId()
  {
    if (this.containerId == "")
    {
      return "EMPTY";
    }
    else
    {
      return this.containerId;
    }
  }

  public String getCrateId()
  {
    return this.crateId;
  }

  public Double getLength()
  {
    return this.length;
  }

  public Double getWidth()
  {
    return this.width;
  }

  public Double getHeight()
  {
    return this.height;
  }

  public String crateId()
  {
    return this.crateId;
  }

  public String getCoordA()
  {
    return this.coordA;
  }

  public String getCoordB()
  {
    return this.coordB;
  }

  public String getCoordC()
  {
    return this.coordC;
  }

  public String getCoordD()
  {
    return this.coordD;
  }

  public String getCoordE()
  {
    return this.coordE;
  }

  public String getCoordF()
  {
    return this.coordF;
  }

  public String getCoordG()
  {
    return this.coordG;
  }

  public String getCoordH()
  {
    return this.coordH;
  }
}
