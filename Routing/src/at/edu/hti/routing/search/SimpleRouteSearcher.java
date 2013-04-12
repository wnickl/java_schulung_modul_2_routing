/** 
 * Copyright 2013 SSI Schaefer PEEM GmbH. All Rights reserved. 
 * <br /> <br />
 * 
 * $Id$
 * <br /> <br />
 *
 */

package at.edu.hti.routing.search;

import java.util.concurrent.TimeUnit;

import at.edu.hti.routing.graph.Graph;
import at.edu.hti.routing.graph.GraphDirection;
import at.edu.hti.routing.graph.GraphItem;
import at.edu.hti.routing.route.IRoute;
import at.edu.hti.routing.route.IRouteSearcher;
import at.edu.hti.routing.route.Route;

/**
 * just take always next item in graph with lowest cost <br />
 * 
 * @author nickl
 * @version $Revision$
 */

public class SimpleRouteSearcher implements IRouteSearcher {

  @Override
  public IRoute calculate(GraphItem start, GraphItem end, Graph graph) {
    Route route = new Route(start, end, graph);
    long s1 = System.nanoTime();
    GraphDirection[] directions = start.calculateDirection(end);
    //    System.err.println("distance: " + distance + " direction:" + Arrays.toString(directions));
    route.addHop(start);
    GraphItem current = start;
    while (current != end) {
      current = current.getNeighborWithLowestCost(directions);
      route.addHop(current);
    }
    long s2 = System.nanoTime() - s1;
    route.setCalculationTime(TimeUnit.NANOSECONDS.toMillis(s2));
    return route;
  }
}

//---------------------------- Revision History ----------------------------
//$Log$
//
