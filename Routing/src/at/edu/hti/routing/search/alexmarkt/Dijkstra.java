
package at.edu.hti.routing.search.alexmarkt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Dijkstra {

  private final List<ExtendedVertex> verticles = new ArrayList<Dijkstra.ExtendedVertex>();
  private final List<ExtendedVertex> unsettled = new ArrayList<Dijkstra.ExtendedVertex>();

  public List<IVertex> findShortestPath(IGraph g, Vertex start, Vertex destination) {
    if (g == null) {
      throw new NullPointerException("'g' must not be null");
    }
    if (start == null) {
      throw new NullPointerException("'start' must not be null");
    }
    if (destination == null) {
      throw new NullPointerException("'destination' must not be null");
    }

    if (!g.getVerticles().contains(start)) {
      throw new IllegalStateException("Start Vertex [" + start.getName() + "] is not part of Graph");
    }

    if (!g.getVerticles().contains(destination)) {
      throw new IllegalStateException("Destination Vertex [" + destination.getName() + "] is not part of Graph");
    }

    /**
     * 1) Weise allen Knoten die beiden Eigenschaften „Distanz“ und „Vorgaenger“ zu. Initialisiere
     * die Distanz im Startknoten mit 0 und in allen anderen Knoten mit INFINITY.<br/>
     * 2) Solange es noch unbesuchte Knoten gibt, <br/>
     * wähle darunter denjenigen mit minimaler Distanz aus und Speichere, dass dieser Knoten schon
     * besucht wurde <br/>
     * 2.1) Berechne für alle noch unbesuchten Nachbarknoten die Summe des jeweiligen
     * Kantengewichtes und der Distanz im aktuellen Knoten. Ist dieser Wert für einen Knoten kleiner
     * als die dort gespeicherte Distanz, aktualisiere sie und setze den aktuellen Knoten als
     * Vorgänger.
     */

    /** 1 */
    init0(g, start);

    /** 2 */
    while (!unsettled.isEmpty()) {
      ExtendedVertex evx = getMininum();
      unsettled.remove(evx);
      update(evx);
    }

    /** 3 -> get path */
    return getPath(destination);
  }

  private List<IVertex> getPath(Vertex destination) {
    List<IVertex> path = new ArrayList<IVertex>();
    ExtendedVertex step = getExtendedVertex(destination);

    if (step.getPredecessor() == null) {
      return Collections.emptyList();
    }
    IVertex v = step.getVertex();
    path.add(v);

    while (getExtendedVertex(v).getPredecessor() != null) {
      step = getExtendedVertex(v);
      v = step.getPredecessor();
      path.add(v);
    }

    // Put it into the correct order
    Collections.reverse(path);

    return path;
  }

  /** 2.1 */
  private void update(ExtendedVertex evx) {
    IVertex v = evx.getVertex();
    double distance = evx.getDistance();

    for (Edge e : v.getEdges()) {
      double costs = e.getCost();

      double d = distance + costs;

      ExtendedVertex n = getExtendedVertex(e.getDestination());
      if (n.getDistance() > d) {
        n.setDistance(d);
        n.setPredecessor(v);
      }
    }
  }

  private ExtendedVertex getExtendedVertex(IVertex v) {
    for (ExtendedVertex evx : verticles) {
      if (evx.getVertex().compareTo(v) == 0) {
        return evx;
      }
    }
    throw new IllegalStateException();
  }

  private ExtendedVertex getMininum() {
    Collections.sort(unsettled, new Comparator<ExtendedVertex>() {
      @Override
      public int compare(ExtendedVertex v1, ExtendedVertex v2) {

        double tmp = v1.getDistance() - v2.getDistance();
        return tmp == 0 ? 0 : (tmp < 0 ? -1 : 1);

      }
    });
    return unsettled.get(0);
  }

  private void init0(IGraph g, Vertex start) {
    List<IVertex> verticles = g.getVerticles();
    for (IVertex v : verticles) {
      ExtendedVertex evx = new ExtendedVertex(v);
      if (v.compareTo(start) == 0) {
        evx.setDistance(0);
      }
      unsettled.add(evx);
      this.verticles.add(evx);
    }
  }

  private static class ExtendedVertex {
    final IVertex v;
    double distance = Double.POSITIVE_INFINITY;
    IVertex predecessor = null;

    /**
     * @param v2
     */
    public ExtendedVertex(IVertex v2) {
      if (v2 == null) {
        throw new NullPointerException("'v' must not be null");
      }
      this.v = v2;
    }

    public IVertex getVertex() {
      return v;
    }

    /**
     * @return the distance
     */
    public double getDistance() {
      return distance;
    }

    /**
     * @param distance the distance to set
     */
    public void setDistance(double distance) {
      this.distance = distance;
    }

    /**
     * @return the predecessor
     */
    public IVertex getPredecessor() {
      return predecessor;
    }

    /**
     * @param v the predecessor to set
     */
    public void setPredecessor(IVertex v) {
      this.predecessor = v;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
      return "<extendedVertex vertex=\"" + v.getName() + "\" distance=\"" + distance + "\" predecessor=\""
        + (predecessor != null ? predecessor.getName() : "null") + "\" />";
    }

  }

}

//---------------------------- Revision History ----------------------------
//$Log$
//
