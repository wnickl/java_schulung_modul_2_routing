
package at.edu.hti.routing.search.alexmarkt;

import java.util.List;

public interface IGraph {

  public abstract IGraph addVertex(IVertex v);

  public abstract List<IVertex> getVerticles();

  public abstract IVertex getVertex(String name);

}

//---------------------------- Revision History ----------------------------
//$Log$
//
