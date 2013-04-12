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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import at.edu.hti.routing.graph.Graph;
import at.edu.hti.routing.graph.GraphDirection;
import at.edu.hti.routing.graph.GraphItem;
import at.edu.hti.routing.route.IRoute;
import at.edu.hti.routing.route.IRouteSearcher;
import at.edu.hti.routing.route.Route;
import at.edu.hti.routing.search.vogella.DijkstraAlgorithm;
import at.edu.hti.routing.search.vogella.Edge;
import at.edu.hti.routing.search.vogella.Vertex;
import at.edu.hti.routing.search.vogella.VogellaGraph;

/**
 * www.vogella.com based implementation of {@link IRouteSearcher}; converts internal object to
 * needed vogella based objects and re-converts result to internal objects.
 * 
 * @author nickl
 * @version $Revision$
 */

public class DijkstraRouteSearcher implements IRouteSearcher {

  /** {@inheritDoc} */
  @Override
  public IRoute calculate(GraphItem start, GraphItem end, Graph graph) {
    Route route = new Route(start, end, graph);

    List<Vertex> vertexes = new ArrayList<>();
    List<Edge> edges = new ArrayList<>();
    Map<String, Vertex> kurzzeitGedaechtnis = new HashMap<>();
    for (GraphItem g : graph.getItemsList()) {
      Vertex vertex = new Vertex(g.getXCoordinate() + "-" + g.getYCoordinate(), g.getXCoordinate() + "-" + g.getYCoordinate());
      vertexes.add(vertex);
      kurzzeitGedaechtnis.put(vertex.getId(), vertex);
    }
    for (GraphItem g : graph.getItemsList()) {
      Vertex _g = kurzzeitGedaechtnis.get(g.getXCoordinate() + "-" + g.getYCoordinate());
      for (GraphItem n : g.getNeighbors(GraphDirection.values())) {
        Vertex _n = kurzzeitGedaechtnis.get(n.getXCoordinate() + "-" + n.getYCoordinate());
        edges.add(new Edge(_g.getId() + ">" + _n.getId(), _g, _n, n.getCost()));
      }
    }
    //timing only for calculation - not instantiation
    long s1 = System.nanoTime();
    VogellaGraph vogellaGraph = new VogellaGraph(vertexes, edges);
    DijkstraAlgorithm algo = new DijkstraAlgorithm(vogellaGraph);
    algo.execute(kurzzeitGedaechtnis.get(start.getXCoordinate() + "-" + start.getYCoordinate()));
    LinkedList<Vertex> path = algo.getPath(kurzzeitGedaechtnis.get(end.getXCoordinate() + "-" + end.getYCoordinate()));
    for (Vertex v : path) {
      String[] xy = v.getId().split("-");
      int x = Integer.valueOf(xy[0]).intValue();
      int y = Integer.valueOf(xy[1]).intValue();
      GraphItem g = graph.getItemsArray()[x][y];
      route.addHop(g);
    }
    //
    long s2 = System.nanoTime() - s1;
    route.setCalculationTime(TimeUnit.NANOSECONDS.toMillis(s2));
    return route;
  }
}

//---------------------------- Revision History ----------------------------
//$Log$
//
