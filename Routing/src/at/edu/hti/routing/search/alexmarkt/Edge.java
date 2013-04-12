
package at.edu.hti.routing.search.alexmarkt;

public class Edge {
  private final double cost;
  private final IVertex destination;
  private String name;

  /**
   * @param name
   * @param cost
   * @param destination
   */
  public Edge(String name, double cost, IVertex destination) {
    if (destination == null) {
      throw new NullPointerException("'destination' must not be null");
    }
    if (cost < 0) {
      throw new IllegalArgumentException("Costs must not be less than 0");
    }
    if (name == null) {
      throw new NullPointerException("'name' must not be null");
    }
    if (name.trim().length() == 0) {
      throw new IllegalArgumentException("'name' must not be empty");
    }
    this.name = name;
    this.cost = cost;
    this.destination = destination;
  }

  /**
   * @return the destination
   */
  public IVertex getDestination() {
    return destination;
  }

  /**
   * @return the cost
   */
  public double getCost() {
    return cost;
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return "<edge name=\"" + name + "\" cost=\"" + cost + "\" destination=\"" + destination.getName() + "\"/>";
  }

  public String getName() {
    return name;
  }

}

//---------------------------- Revision History ----------------------------
//$Log$
//
