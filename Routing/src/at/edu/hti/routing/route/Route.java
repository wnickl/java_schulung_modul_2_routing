/** 
 * Copyright 2013 SSI Schaefer PEEM GmbH. All Rights reserved. 
 * <br /> <br />
 * 
 * $Id$
 * <br /> <br />
 *
 */

package at.edu.hti.routing.route;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import at.edu.hti.routing.graph.Graph;
import at.edu.hti.routing.graph.GraphItem;
import at.edu.hti.routing.util.StringUtils;

/**
 * basic implementation of {@link IRoute}
 * 
 * @author nickl
 * @version $Revision$
 */

public class Route implements IRoute {

  private final GraphItem start;
  private final GraphItem end;
  private final Graph graph;
  private final List<GraphItem> hops = new LinkedList<>();
  private long calculationTime;

  /**
   * costructor
   * 
   * @param start the start item
   * @param end the end item
   * @param graph the graph to use
   */
  public Route(GraphItem start, GraphItem end, Graph graph) {
    this.start = start;
    this.end = end;
    this.graph = graph;
  }

  /** {@inheritDoc} */
  @Override
  public void addHop(GraphItem hop) {
    hops.add(hop);
  }

  /** {@inheritDoc} */
  @Override
  public List<GraphItem> getHops() {
    return Collections.unmodifiableList(hops);
  }

  /** {@inheritDoc} */
  @Override
  public int getTotalCost() {
    int cost = 0;
    for (GraphItem hop : hops) {
      cost += hop.getCost();
    }
    return cost;
  }

  /** {@inheritDoc} */
  @Override
  public GraphItem getStart() {
    return start;
  }

  /** {@inheritDoc} */
  @Override
  public GraphItem getEnd() {
    return end;
  }

  /** {@inheritDoc} */
  @Override
  public Graph getGraph() {
    return graph;
  }

  /** {@inheritDoc} */
  @Override
  public String printRoute() {
    int maxX = getGraph().getMaxX();
    int maxY = getGraph().getMaxY();
    char[][] testdata2 = new char[maxX + 1][];
    for (int i = 0; i < maxX + 1; i++) {
      testdata2[i] = new char[maxY + 1];
      Arrays.fill(testdata2[i], ' ');
    }
    for (GraphItem i : hops) {
      testdata2[i.getXCoordinate()][i.getYCoordinate()] = 'X';
    }
    StringBuilder sb = new StringBuilder("\n");
    sb.append(StringUtils.pad("", true, (maxX * 3) + 3, '=') + "\n");
    String heading = getCalculationTime() + "ms cost=" + getTotalCost() + " hops=" + getHops().size() + " (=" + (getTotalCost() + hops.size()) + ")";
    sb.append(StringUtils.center(heading, (maxX * 3) + 3) + "\n");
    sb.append(StringUtils.pad("", true, (maxX * 3) + 3, '=') + "\n");
    for (char[] c : testdata2) {
      sb.append(Arrays.toString(c));
      sb.append("\n");
    }
    sb.append(StringUtils.pad("", true, (maxX * 3) + 3, '=') + "\n");
    return sb.toString();
  }

  /** {@inheritDoc} */
  @Override
  public long getCalculationTime() {
    return calculationTime;
  }

  /** {@inheritDoc} */
  @Override
  public void setCalculationTime(long calculationTime) {
    this.calculationTime = calculationTime;
  }
}

//---------------------------- Revision History ----------------------------
//$Log$
//
