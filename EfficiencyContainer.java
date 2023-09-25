/*
The efficiency container class is to find the efficiency of areas when evaluating them
 */
//package ShippingProject;

public class EfficiencyContainer
{
  //attribute declaration
  private int index;
  private double efficiency;

  //blank constructor
  public EfficiencyContainer()
  {
    this.index = 0;
    this.efficiency = 0;
  }

  //overloading constructor
  public EfficiencyContainer(int index, double efficiency)
  {
    this.efficiency = efficiency;
    this.index = index;
  }

  //set methods
  public void setIndex(int index)
  {
    this.index = index;
  }

  public void setEfficiency(double efficiency)
  {
    this.efficiency = efficiency;
  }

  //get methods
  public int getIndex()
  {
    return this.index;
  }

  public double getEfficiency()
  {
    return this.efficiency;
  }
}
