
package at.edu.hti.routing.search.vogella;

import java.util.List;

public class VogellaGraph {
  private final List<Vertex> vertexes;
  private final List<Edge> edges;

  public VogellaGraph(List<Vertex> vertexes, List<Edge> edges) {
    this.vertexes = vertexes;
    this.edges = edges;
  }

  public List<Vertex> getVertexes() {
    return vertexes;
  }

  public List<Edge> getEdges() {
    return edges;
  }

}