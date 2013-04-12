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
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import at.edu.hti.routing.util.StringUtils;

/**
 * One item in graph
 * 
 * @author nickl
 * @version $Revision$
 */

public class GraphItem {

  private final int xCoord;
  private final int yCoord;
  private final int cost;
  private final Map<GraphDirection, GraphItem> neighbors = new EnumMap<GraphDirection, GraphItem>(GraphDirection.class);
  private final String name;

  public GraphItem(int xCoord, int yCoord, int cost) {
    this.xCoord = xCoord;
    this.yCoord = yCoord;
    this.cost = cost;
    this.name = xCoord + "-" + yCoord;
  }

  public int getXCoordinate() {
    return xCoord;
  }

  public int getYCoordinate() {
    return yCoord;
  }

  public int getCost() {
    return cost;
  }

  @Override
  public String toString() {
    return StringUtils.center("[x=" + xCoord + ", y=" + yCoord + ", cost=" + cost + "]", 20);
  }

  public String toStringWithNeighbors() {
    int length = 20;
    GraphItem n = neighbors.get(GraphDirection.NORTH);
    GraphItem s = neighbors.get(GraphDirection.SOUTH);
    GraphItem w = neighbors.get(GraphDirection.WEST);
    GraphItem e = neighbors.get(GraphDirection.EAST);
    String delim = StringUtils.pad("", true, 3 * length, '*');
    String strN = StringUtils.center(String.valueOf(n), 3 * length);
    String strS = StringUtils.center(String.valueOf(s), 3 * length);
    String strW = StringUtils.center(String.valueOf(w), length);
    String strE = StringUtils.center(String.valueOf(e), length);
    String _this = StringUtils.center("[x=" + xCoord + ", y=" + yCoord + ", cost=" + cost + "]", length);
    StringBuilder sb = new StringBuilder(200);
    sb.append("\n");
    sb.append(delim);
    sb.append("\n");
    sb.append(strN);
    sb.append("\n");
    sb.append(strW);
    sb.append(_this);
    sb.append(strE);
    sb.append("\n");
    sb.append(strS);
    sb.append("\n");
    sb.append(delim);
    return sb.toString();
  }

  public int calculateDistance(GraphItem item) {
    int xDist = Math.abs(item.getXCoordinate() - getXCoordinate());
    int yDist = Math.abs(item.getYCoordinate() - getYCoordinate());
    return xDist + yDist;
  }

  public GraphDirection[] calculateDirection(GraphItem item) {
    List<GraphDirection> directions = new ArrayList<>();
    if (item.getXCoordinate() > getXCoordinate()) {
      directions.add(GraphDirection.EAST);
    } else if (item.getXCoordinate() < getXCoordinate()) {
      directions.add(GraphDirection.WEST);
    }
    if (item.getYCoordinate() > getYCoordinate()) {
      directions.add(GraphDirection.SOUTH);
    } else if (item.getXCoordinate() < getXCoordinate()) {
      directions.add(GraphDirection.NORTH);
    }
    return directions.toArray(new GraphDirection[directions.size()]);
  }

  public void addNeighbor(GraphDirection direction, GraphItem item) {
    neighbors.put(direction, item);
  }

  public GraphItem getNeighborWithLowestCost(GraphDirection[] directions) {
    GraphItem bestNeighbor = null;
    for (GraphDirection direction : directions) {
      GraphItem item = neighbors.get(direction);
      if (bestNeighbor == null) {
        bestNeighbor = item;
      }
      if (bestNeighbor == null || item == null) {
        continue;
      }
      if (bestNeighbor.getCost() > item.getCost()) {
        bestNeighbor = item;
      }
    }
    return bestNeighbor;
  }

  public List<GraphItem> getNeighbors(GraphDirection[] directions) {
    List<GraphItem> list = new LinkedList<>();
    for (GraphDirection d : directions) {
      GraphItem n = neighbors.get(d);
      if (n != null) {
        list.add(n);
      }
    }
    return list;
  }

  public String getName() {
    return name;
  }
}

//---------------------------- Revision History ----------------------------
//$Log$
//
