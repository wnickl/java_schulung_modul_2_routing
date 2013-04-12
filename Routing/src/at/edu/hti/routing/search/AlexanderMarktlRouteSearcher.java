/** 
 * Copyright 2013 SSI Schaefer PEEM GmbH. All Rights reserved. 
 * <br /> <br />
 * 
 * $Id$
 * <br /> <br />
 *
 */

package at.edu.hti.routing.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import at.edu.hti.routing.graph.Graph;
import at.edu.hti.routing.graph.GraphDirection;
import at.edu.hti.routing.graph.GraphItem;
import at.edu.hti.routing.route.IRoute;
import at.edu.hti.routing.route.IRouteSearcher;
import at.edu.hti.routing.route.Route;
import at.edu.hti.routing.search.alexmarkt.AlexMarktlGraph;
import at.edu.hti.routing.search.alexmarkt.Dijkstra;
import at.edu.hti.routing.search.alexmarkt.Edge;
import at.edu.hti.routing.search.alexmarkt.IGraph;
import at.edu.hti.routing.search.alexmarkt.IVertex;
import at.edu.hti.routing.search.alexmarkt.Vertex;

/**
 * www.vogella.com based implementation of {@link IRouteSearcher}; converts internal object to
 * needed vogella based objects and re-converts result to internal objects.
 * 
 * @author nickl
 * @version $Revision$
 */

public class AlexanderMarktlRouteSearcher implements IRouteSearcher {

  /** {@inheritDoc} */
  @Override
  public IRoute calculate(GraphItem start, GraphItem end, Graph graph) {
    Route route = new Route(start, end, graph);

    IGraph amg = initRandomGraph(graph.getInitialIntArray());
    //    System.err.println(amg);
    Vertex s = new Vertex(start.getName());
    Vertex z = new Vertex(end.getName());
    //    System.err.println(s + " - " + z);
    Dijkstra dijkstra = new Dijkstra();

    //timing only for calculation - not instantiation
    long s1 = System.nanoTime();

    List<IVertex> path = dijkstra.findShortestPath(amg, s, z);
    //    System.err.println(path);
    for (IVertex v : path) {
      String[] name = v.getName().split("-");
      int x = Integer.valueOf(name[0]).intValue();
      int y = Integer.valueOf(name[1]).intValue();
      route.addHop(graph.getItem(x, y));
    }
    //
    long s2 = System.nanoTime() - s1;
    route.setCalculationTime(TimeUnit.NANOSECONDS.toMillis(s2));
    return route;
  }

  private static final IGraph initRandomGraph(int[][] matrix) {

    IGraph g = new AlexMarktlGraph();

    for (int x = 0; x < matrix.length; x++) {
      for (int y = 0; y < matrix[x].length; y++) {
        IVertex v = new Vertex(x + "-" + y);
        g.addVertex(v);
      }
    }
    for (int x = 0; x < matrix.length; x++) {
      for (int y = 0; y < matrix[x].length; y++) {
        IVertex vertex = g.getVertex(x + "-" + y);
        List<IVertex> list = calculateNeighbors(x, y, matrix.length - 1, matrix[x].length - 1, g);
        for (IVertex v : list) {
          Edge e = new Edge("E-" + x + "-" + y, matrix[x][y], v);
          vertex.addDirectedEdge(e);
        }
      }
    }
    //
    //    /** init all verticles */
    //    for (int i = 0; i < matrix.length; i++) {
    //      IVertex v = new Vertex(i + "");
    //      g.addVertex(v);
    //    }
    //
    //    /** init all edges */
    //    for (int i = 0; i < matrix.length; i++) {
    //      IVertex v = g.getVertex(i + "");
    //
    //      int[] edges = matrix[i];
    //      for (int j = 0; j < edges.length; j++) {
    //        if (edges[j] == 0) {
    //          // if 0 then there is no edge
    //          continue;
    //        }
    //        Edge e = new Edge("E-" + i + "-" + j, edges[j], g.getVertex(j + ""));
    //        v.addDirectedEdge(e);
    //      }
    //    }

    return g;
  }

  private static List<IVertex> calculateNeighbors(int x, int y, int maxX, int maxY, IGraph itemsArray) {
    List<IVertex> v = new ArrayList<>();
    if (x == 0) {
      v.add(itemsArray.getVertex((x + 1) + "-" + y));
    } else if (x == maxX) {
      v.add(itemsArray.getVertex((x - 1) + "-" + y));
    } else {
      v.add(itemsArray.getVertex((x + 1) + "-" + y));
      v.add(itemsArray.getVertex((x - 1) + "-" + y));
    }
    if (y == 0) {
      v.add(itemsArray.getVertex(x + "-" + (y + 1)));
    } else if (y == maxY) {
      v.add(itemsArray.getVertex(x + "-" + (y - 1)));
    } else {
      v.add(itemsArray.getVertex(x + "-" + (y + 1)));
      v.add(itemsArray.getVertex(x + "-" + (y - 1)));
    }
    return v;
  }
}

//---------------------------- Revision History ----------------------------
//$Log$
//
