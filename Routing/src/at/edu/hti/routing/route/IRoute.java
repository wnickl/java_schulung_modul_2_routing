/** 
 * Copyright 2013 SSI Schaefer PEEM GmbH. All Rights reserved. 
 * <br /> <br />
 * 
 * $Id$
 * <br /> <br />
 *
 */

package at.edu.hti.routing.route;

import java.util.List;

import at.edu.hti.routing.graph.Graph;
import at.edu.hti.routing.graph.GraphItem;

/**
 * This is the class header. The first sentence (ending with "."+SPACE) is important, because it is
 * used summary in the package overview pages.<br />
 * <br />
 * 
 * @author nickl
 * @version $Revision$
 */

public interface IRoute {

  List<GraphItem> getHops();

  void addHop(GraphItem item);

  int getTotalCost();

  String printRoute();

  long getCalculationTime();

  void setCalculationTime(long calculationTime);

  GraphItem getStart();

  GraphItem getEnd();

  Graph getGraph();

}

//---------------------------- Revision History ----------------------------
//$Log$
//
