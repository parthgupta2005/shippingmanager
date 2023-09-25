/*
The area class is to instantiate objects of area for the computation
 */
//package ShippingProject;

public class Area
{

  //variable declarations
  private double length;
  private double width;
  private String coordAX;
  private String coordAY;
  private String coordBY;
  private String coordCX;

  //blank constructor
  public Area()
  {
    this.length = 0.0;
    this.width = 0.0;
    this.coordAX = "";
    this.coordAY = "";
    this.coordBY = "";
    this.coordCX = "";
  }

  //overloading the constructor
  public Area(double length, double width, String coordAX, String coordAY,
              String coordBY, String coordCX)
  {
    this.length = length;
    this.width = width;
    this.coordAX = coordAX;
    this.coordAY = coordAY;
    this.coordBY = coordBY;
    this.coordCX = coordCX;
  }

  //setters
  public void setLength(double length)
  {
    //setting parameter to local
    this.length = length;
  }

  public void setWidth(double width)
  {
    //setting parameter to local
    this.width = width;
  }

  public void setCoordAX(String coordAX)
  {
    //setting parameter to local
    this.coordAX = coordAX;
  }

  public void setCoordAY(String coordAY)
  {
    //setting parameter to local
    this.coordAY = coordAY;
  }

  public void setCoordBY(String coordBY)
  {
    //setting parameter to local
    this.coordBY = coordBY;
  }

  public void setCoordCX(String coordCX)
  {
    //setting parameter to local
    this.coordCX = coordCX;
  }

  //getters
  public double getLength()
  {
    //returning local variable
    return this.length;
  }

  public double getWidth()
  {
    //returning local variable
    return this.width;
  }

  public String getCoordAX()
  {
    //returning local variable
    return this.coordAX;
  }

  public String getCoordAY()
  {
    //returning local variable
    return this.coordAY;
  }

  public String getCoordBY()
  {
    //returning local variable
    return this.coordBY;
  }

  public String getCoordCX()
  {
    //returning local variable
    return this.coordCX;
  }
}
