
package at.edu.hti.routing.search.alexmarkt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AlexMarktlGraph implements IGraph {

  private List<IVertex> verticles = new ArrayList<IVertex>();

  /** {@inheritDoc} */
  public IGraph addVertex(IVertex v) {
    if (v == null) {
      throw new NullPointerException("'v' must not be null");
    }
    verticles.add(v);
    return this;
  }

  /** {@inheritDoc} */
  public List<IVertex> getVerticles() {
    return Collections.unmodifiableList(verticles);
  }

  /** {@inheritDoc} */
  public IVertex getVertex(String name) {
    for (IVertex v : verticles) {
      if (v.getName().compareToIgnoreCase(name) == 0) {
        return v;
      }
    }
    throw new IllegalStateException("No such vertex [" + name + "]");
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append("<graph>");
    for (IVertex v : verticles) {
      s.append(v);
    }
    s.append("</graph>");
    return s.toString();
  }

}

//---------------------------- Revision History ----------------------------
//$Log$
//
