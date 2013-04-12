/** 
 * Copyright 2013 SSI Schaefer PEEM GmbH. All Rights reserved. 
 * <br /> <br />
 * 
 * $Id$
 * <br /> <br />
 *
 */

package at.edu.hti.routing.route;

import at.edu.hti.routing.graph.Graph;
import at.edu.hti.routing.graph.GraphItem;

/**
 * find best route
 * 
 * @author nickl
 * @version $Revision$
 */

public interface IRouteSearcher {
  IRoute calculate(GraphItem start, GraphItem end, Graph graph);
}

//---------------------------- Revision History ----------------------------
//$Log$
//
