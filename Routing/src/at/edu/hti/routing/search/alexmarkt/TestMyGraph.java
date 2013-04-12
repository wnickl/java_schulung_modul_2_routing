
package at.edu.hti.routing.search.alexmarkt;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class TestMyGraph {

  public static void main(String[] args) {

    //    int[][] matrix = generateQuadraticArray(10);
    //
    //    for (int i = 0; i < matrix.length; i++) {
    //      System.err.println(Arrays.toString(matrix[i]));
    //    }
    //
    //    System.err.println("-----------------------------------------------");

    //    IGraph g = initRandomGraph(matrix);

    IGraph g = initSimpleDirectedGraph();

    System.err.println(g);

    //    IGraph g = initSimpleDirectedGraph();
    //    Vertex s = new Vertex("0");
    //    Vertex z = new Vertex("9");

    Vertex s = new Vertex("START");
    Vertex z = new Vertex("ZIEL");

    Dijkstra dijkstra = new Dijkstra();

    long start = System.nanoTime();
    List<IVertex> path = dijkstra.findShortestPath(g, s, z);
    System.err.println("TOOK: " + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start) + " ms");

    StringBuilder str = new StringBuilder();
    int i = path.size();
    for (IVertex v : path) {
      str.append(v.getName());
      if (--i > 0) {
        str.append(" > ");
      }
    }

    System.err.println("Shortest Path: " + str.toString());

  }

  private static final int[][] generateQuadraticArray(int size) {
    int[][] matrix = new int[size][size];
    int i = size;
    while (i-- > 0) {
      int[] row = new int[size];
      int j = size;
      while (j-- > 0) {
        if (Double.valueOf(Math.random() * 9).intValue() % 4 == 0) {
          row[j] = Double.valueOf(Math.random() * 9).intValue();
        } else {
          row[j] = 0;
        }
      }
      matrix[i] = row;
    }
    return matrix;
  }

  private static final IGraph initRandomGraph(int[][] matrix) {
    IGraph g = new AlexMarktlGraph();

    /** init all verticles */
    for (int i = 0; i < matrix.length; i++) {
      IVertex v = new Vertex(i + "");
      g.addVertex(v);
    }

    /** init all edges */
    for (int i = 0; i < matrix.length; i++) {
      IVertex v = g.getVertex(i + "");

      int[] edges = matrix[i];
      for (int j = 0; j < edges.length; j++) {
        if (edges[j] == 0) {
          // if 0 then there is no edge
          continue;
        }
        Edge e = new Edge("E-" + i + "-" + j, edges[j], g.getVertex(j + ""));
        v.addDirectedEdge(e);
      }
    }
    return g;
  }

  private static final IGraph initSimpleDirectedGraph() {
    IGraph g = new AlexMarktlGraph();

    Vertex s = new Vertex("START");
    g.addVertex(s);

    Vertex z = new Vertex("ZIEL");
    g.addVertex(z);

    Vertex a = new Vertex("A");
    g.addVertex(a);

    Vertex b = new Vertex("B");
    g.addVertex(b);

    Vertex c = new Vertex("C");
    g.addVertex(c);

    Vertex d = new Vertex("D");
    g.addVertex(d);

    Vertex x = new Vertex("X");
    g.addVertex(x);

    Vertex y = new Vertex("Y");
    g.addVertex(y);

    s.addDirectedEdge(new Edge("E-0", 1, a));
    a.addDirectedEdge(new Edge("E-1", 3, b));
    a.addDirectedEdge(new Edge("E-2", 2, c));
    b.addDirectedEdge(new Edge("E-3", 3, d));
    c.addDirectedEdge(new Edge("E-4", 7, d));
    d.addDirectedEdge(new Edge("E-5", 8, z));
    d.addDirectedEdge(new Edge("E-6", 2, x));

    return g;
  }
}

//---------------------------- Revision History ----------------------------
//$Log$
//
