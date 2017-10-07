package model.map

// Represents an undirected graph.
// Due to the context of the application, it's not likely that any search will have a
// distance of > 2, so the path search algorithms should only be used with short distances
case class Graph[T](vertices: Set[T], edges: Set[Edge[T]]) {
  // Returns the nodes where the predicate is true
  def findVertex(predicate: T => Boolean): Set[T] = {
    vertices.filter(predicate(_))
  }

  def updateVertex(oldVertex: T, newVertex: T): Graph[T] = {
    val newVertexSet = vertices.map { v =>
      if (v == oldVertex) newVertex else v
    }
    val newEdgeSet = edges.map {
      case Edge(a, b) if a == oldVertex => Edge(newVertex, b)
      case Edge(a, b) if b == oldVertex => Edge(a, newVertex)
      case other => other
    }
    new Graph(newVertexSet, newEdgeSet)
  }

  def removeVertex(oldVertex: T): Graph[T] = {
    val newVertexSet = vertices.filterNot(_ == oldVertex)
    val newEdgeSet = edges.filterNot(_.contains(oldVertex))
    new Graph(newVertexSet, newEdgeSet)
  }

  // Passing a vertex to the graph returns a set of its neighbors
  // Due to the restriction given by the filter, Edge.apply should never
  // return `None`
  def apply(a: T): Set[T] = {
    edges.filter(_.contains(a)).map(e => e(a).get)
  }

  // Passing two vertices to the graph returns whether the two are adjacent
  def apply(a: T, b: T): Boolean = {
    val equivalentEdge = Edge(a, b)
    edges.contains(equivalentEdge)
  }

  // Passing two vertices to the graph with a distance parameter returns whether there is
  // an a-b path of length `distance` or less
  def apply(a: T, b: T, distance: Int): Boolean = {
    if(distance == 0) false
    else if(distance == 1) this.apply(a, b)
    else this.apply(a, b) || apply(a, b, distance - 1)
  }

  override def equals(obj: Any): Boolean = obj match {
    case g: Graph[T] => g.vertices == vertices && g.edges == edges
    case _ => false
  }
}

case class Edge[T](u: T, v: T) {
  // Edges are symmetric
  override def equals(obj: Any): Boolean = {
    obj match {
      case Edge(a, b) => (u == a && v == b) || (u == b && v == a)
      case _ => false
    }
  }

  def contains(element: T): Boolean = {
    element == u || element == v
  }

  // Passing a vertex to an edge returns the other vertex
  def apply(a: T): Option[T] = {
    if(a == u) Some(v)
    else if(a == v) Some(u)
    else None
  }
}
