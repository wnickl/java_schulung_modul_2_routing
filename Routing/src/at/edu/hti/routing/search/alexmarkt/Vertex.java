
package at.edu.hti.routing.search.alexmarkt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Vertex implements IVertex {

  private final List<Edge> edges = new ArrayList<Edge>();
  private final String name;

  public Vertex(String name) {
    if (name == null) {
      throw new NullPointerException("'name' must not be null");
    }
    if (name.trim().length() == 0) {
      throw new IllegalArgumentException("'name' must not be empty");
    }
    this.name = name;
  }

  /** {@inheritDoc} */
  @Override
  public IVertex addDirectedEdge(Edge e) {
    if (e == null) {
      throw new NullPointerException("'e' must not be null");
    }
    edges.add(e);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public IVertex addUnidirectedEdge(Edge e) {
    addDirectedEdge(e);
    IVertex v = e.getDestination();
    v.addDirectedEdge(new Edge(e.getName() + "-reverse", e.getCost(), this));
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public int compareTo(IVertex v) {
    if (v == null) {
      throw new NullPointerException("'o' must not be null");
    }
    //IgnoreCase
    return name.compareTo(v.getName());
  }

  /** {@inheritDoc} */
  @Override
  public List<Edge> getEdges() {
    return Collections.unmodifiableList(edges);
  }

  /** {@inheritDoc} */
  @Override
  public String getName() {
    return name;
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append("<vertex name=\"" + name + "\">");
    for (Edge e : edges) {
      s.append(e);
    }
    s.append("</vertex>");
    return s.toString();
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof IVertex)) {
      return false;
    }
    if (obj == this) {
      return true;
    }
    return name.equals(((IVertex) obj).getName());
    //    return name.compareTo(((IVertex) obj).getName()) == 0;
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + name.hashCode();
    return result;
  }

}

//---------------------------- Revision History ----------------------------
//$Log$
//
