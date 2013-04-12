/** 
 * Copyright 2013 SSI Schaefer PEEM GmbH. All Rights reserved. 
 * <br /> <br />
 * 
 * $Id$
 * <br /> <br />
 *
 */

package at.edu.hti.routing.search;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import at.edu.hti.routing.graph.Graph;
import at.edu.hti.routing.graph.GraphDirection;
import at.edu.hti.routing.graph.GraphItem;
import at.edu.hti.routing.route.IRoute;
import at.edu.hti.routing.route.IRouteSearcher;
import at.edu.hti.routing.route.Route;

/**
 * just take always next item in graph with lowest cost and consider seconds item following item
 * currently in hand<br />
 * 
 * @author nickl
 * @version $Revision$
 */

public class SimpleRouteSearcherConsideringTwoHops implements IRouteSearcher {

  private static final boolean debug = false;

  /** {@inheritDoc} */
  @Override
  public IRoute calculate(GraphItem start, GraphItem end, Graph graph) {
    Route route = new Route(start, end, graph);
    long s1 = System.nanoTime();
    GraphDirection[] directions = start.calculateDirection(end);
    if (debug) {
      System.err.println("directions:" + Arrays.toString(directions));
    }
    route.addHop(start);
    if (directions.length == 0) {
      return route;
    }
    GraphItem currentHop = start;
    while (currentHop != end) {
      List<GraphItem> neighborItems = currentHop.getNeighbors(directions);
      GraphItem bestNextHop = null;
      int cost = 0;
      if (debug) {
        System.err.println(currentHop + " > " + neighborItems);
      }
      for (GraphItem neighborItem : neighborItems) {
        if (bestNextHop == null) {
          bestNextHop = neighborItem;
          GraphItem neighborWithLowestCost = neighborItem.getNeighborWithLowestCost(directions);
          cost = neighborItem.getCost();
          if (neighborWithLowestCost != null) {
            cost += neighborWithLowestCost.getCost();
          }
          if (debug) {
            System.err.println("\t neighbor with additional cost " + cost + ": " + bestNextHop);
          }
          continue;
        }
        int thisCost = neighborItem.getCost() + neighborItem.getNeighborWithLowestCost(directions).getCost();
        if (debug) {
          System.err.println("\t neighbor with additional cost " + thisCost + ": " + neighborItem);
        }
        if (cost > thisCost) {
          if (debug) {
            System.err.println("\t use " + neighborItem + " instead of " + bestNextHop + " because cost of " + thisCost + " is less than " + cost);
          }
          bestNextHop = neighborItem;
          cost = thisCost;
        } else {
          if (debug) {
            System.err.println("\t use " + bestNextHop + " and not " + neighborItem + " because cost of " + cost + " is less than " + thisCost);
          }
        }

      }
      if (debug) {
        System.err.println("\t: choosen: " + bestNextHop);
      }
      currentHop = bestNextHop;
      route.addHop(currentHop);
    }
    long s2 = System.nanoTime() - s1;
    route.setCalculationTime(TimeUnit.NANOSECONDS.toMillis(s2));
    return route;
  }
}

//---------------------------- Revision History ----------------------------
//$Log$
//
