/** 
 * Copyright 2013 SSI Schaefer PEEM GmbH. All Rights reserved. 
 * <br /> <br />
 * 
 * $Id$
 * <br /> <br />
 *
 */

package at.edu.hti.routing.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import at.edu.hti.routing.route.IRoute;
import at.edu.hti.routing.route.Route;

/**
 * graph containing graph items
 * 
 * @author nickl
 * @version $Revision$
 */

public class Graph {

  private final List<GraphItem> itemList = new ArrayList<>();
  private final GraphItem[][] itemArray;
  private final int maximumXCoordinate;
  private int maximumYCoordinate;

  /** converts int[][] to GraphItem[][]/List */
  public Graph(int[][] arr) {
    if (arr == null) {
      throw new NullPointerException("'array' must not be null");
    }
    maximumXCoordinate = arr.length - 1;
    itemArray = new GraphItem[arr.length][];
    for (int x = 0; x < arr.length; x++) {
      int[] sub = arr[x];
      maximumYCoordinate = sub.length - 1;
      itemArray[x] = new GraphItem[sub.length];
      for (int y = 0; y < sub.length; y++) {
        GraphItem item = new GraphItem(x, y, sub[y]);
        itemArray[x][y] = item;
        getItemsList().add(item);
      }
    }
    for (int x = 0; x < itemArray.length; x++) {
      int[] sub = arr[x];
      for (int y = 0; y < sub.length; y++) {
        GraphItem item = itemArray[x][y];
        Map<GraphDirection, GraphItem> neighbors = calculateNeighbors(x, y, maximumXCoordinate, maximumYCoordinate, itemArray);
        for (Entry<GraphDirection, GraphItem> e : neighbors.entrySet()) {
          item.addNeighbor(e.getKey(), e.getValue());
        }
      }
    }
  }

  public GraphItem getItem(int x, int y) {
    return itemArray[x][y];
  }

  public GraphItem[][] getItemsArray() {
    return itemArray;
  }

  private Map<GraphDirection, GraphItem> calculateNeighbors(int x, int y, int maxX, int maxY, GraphItem[][] itemsArray) {
    Map<GraphDirection, GraphItem> map = new HashMap<GraphDirection, GraphItem>();
    if (x == 0) {
      map.put(GraphDirection.EAST, itemsArray[x + 1][y]);
    } else if (x == maxX) {
      map.put(GraphDirection.WEST, itemsArray[x - 1][y]);
    } else {
      map.put(GraphDirection.EAST, itemsArray[x + 1][y]);
      map.put(GraphDirection.WEST, itemsArray[x - 1][y]);
    }
    if (y == 0) {
      map.put(GraphDirection.SOUTH, itemsArray[x][y + 1]);
    } else if (y == maxY) {
      map.put(GraphDirection.NORTH, itemsArray[x][y - 1]);
    } else {
      map.put(GraphDirection.SOUTH, itemsArray[x][y + 1]);
      map.put(GraphDirection.NORTH, itemsArray[x][y - 1]);
    }
    return map;
  }

  public List<GraphItem> getItemsList() {
    return itemList;
  }

  public IRoute calculateRoute(GraphItem start, GraphItem end) {
    IRoute route = new Route(start, end, this);
    return route;
  }

  public int getMaxX() {
    return maximumXCoordinate;
  }

  public int getMaxY() {
    return maximumYCoordinate;
  }
}

//---------------------------- Revision History ----------------------------
//$Log$
//
