
package at.edu.hti.routing.search.alexmarkt;

import java.util.List;

/**
 * Copyright 2013 SSI Schaefer PEEM GmbH. All Rights reserved. <br />
 * <br />
 * $Id$ <br />
 * <br />
 * TODO ADD/Complete JavaDoc! IVertex provides methods to TODO xxx. IVertex is (not) immutable and
 * may (not) be freely exchanged between Threads. Calls to methods of IVertex are (not) thread safe.
 * Usage: TODO add some usage examples.
 * 
 * @author marktl
 * @version $Revision$
 */

public interface IVertex extends Comparable<IVertex> {

  IVertex addDirectedEdge(Edge e);

  IVertex addUnidirectedEdge(Edge e);

  String getName();

  List<Edge> getEdges();

}

//---------------------------- Revision History ----------------------------
//$Log$
//
