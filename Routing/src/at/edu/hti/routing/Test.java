/** 
 * Copyright 2013 SSI Schaefer PEEM GmbH. All Rights reserved. 
 * <br /> <br />
 * 
 * $Id$
 * <br /> <br />
 *
 */

package at.edu.hti.routing;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import at.edu.hti.routing.graph.Graph;
import at.edu.hti.routing.route.IRoute;
import at.edu.hti.routing.route.IRouteSearcher;
import at.edu.hti.routing.util.InstanceUtil;
import at.edu.hti.routing.util.StringUtils;

/**
 * Test the routing
 * 
 * @author nickl
 * @version $Revision$
 */

public class Test {
//@formatter:off
  private static final int[][] DATA = new int[][] {new int[] {2, 3, 4, 0, 9, 7, 9, 1, 9, 5,}, 
                                                   new int[] {1, 1, 3, 6, 1, 5, 9, 3, 5, 6,},
                                                   new int[] {2, 1, 0, 1, 4, 4, 5, 8, 7, 5,},
                                                   new int[] {8, 6, 9, 3, 5, 5, 0, 6, 1, 4,},
                                                   new int[] {3, 9, 8, 8, 8, 5, 5, 9, 0, 8,},
                                                   new int[] {6, 1, 8, 2, 7, 5, 7, 2, 3, 5,},
                                                   new int[] {6, 5, 0, 7, 6, 2, 7, 8, 0, 1,},
                                                   new int[] {6, 3, 2, 9, 3, 0, 2, 4, 8, 0,},
                                                   new int[] {9, 7, 4, 5, 6, 2, 7, 0, 6, 3,},
                                                   new int[] {0, 3, 9, 3, 7, 9, 3, 6, 7, 9,}};
//@formatter:on
  public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {

    List<IRouteSearcher> rss = InstanceUtil.returnAvailableRouteSearcherImplementations();
    File f = new File("calculation." + new SimpleDateFormat("dd-hhMMss").format(new Date(System.currentTimeMillis())) + ".txt");
    StringBuilder message = new StringBuilder();

    message.append(testPerformance(rss, 10, 10));
    message.append(testPerformance(rss, 50, 50));
    message.append(testPerformance(rss, 100, 100));
    message.append(testPerformance(rss, 150, 150));

    write(f, message.toString());
  }

  private static String testPerformance(List<IRouteSearcher> rss, int X, int Y) {
    System.err.println(StringUtils.pad("", true, 100, '*'));
    System.err.println("testing route calculation for array [" + X + "][" + Y + "]");
    int[][] testdata = generateTestdata(X, Y);
    Graph p = new Graph(testdata);
    StringBuilder message = new StringBuilder("\nTEST-DATA:\n");
    message.append(printTestData(testdata));
    for (IRouteSearcher rs : rss) {
      IRoute route = rs.calculate(p.getItem(0, 0), p.getItem(X - 1, Y - 1), p);
      String routePrint = route.printRoute();
      String m = rs.getClass().getName() + ": " + route.getCalculationTime() + "ms cost=" + route.getTotalCost() + " hops=" + route.getHops().size();
      System.err.println(m);
      m += "\n" + routePrint + "\n";
      message.append(m);
    }
    message.append(StringUtils.pad("", true, 100, '*'));
    return message.toString();
  }

  public static final boolean write(File file, String content) {
    BufferedWriter writer = null;
    try {
      writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false), "UTF-8"));
      writer.write(content);
      return true;
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (writer != null) {
        try {
          writer.flush();
          writer.close();
        } catch (IOException e) {
          e.printStackTrace();
        }

      }
    }
    return false;
  }

  private static String printTestData(int[][] testdata) {
    StringBuilder sb = new StringBuilder();
    sb.append(StringUtils.pad("", true, (3 * DATA.length), '=') + "\n");
    for (int[] i : testdata) {
      sb.append(Arrays.toString(i) + "\n");
    }
    sb.append(StringUtils.pad("", true, (3 * DATA.length), '=') + "\n");
    return sb.toString();
  }

  private static int[][] generateTestdata(int width, int height) {
    int[][] testdata = new int[height][width];
    Random r = new Random();
    for (int z = 0; z < height; z++) {
      for (int j = 0; j < width; j++) {
        testdata[z][j] = r.nextInt(width);
      }
    }
    return testdata;
  }
}

//---------------------------- Revision History ----------------------------
//$Log$
//
