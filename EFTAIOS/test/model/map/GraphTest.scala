package model.map

import org.scalatest._

class GraphTest extends FlatSpec with Matchers with GraphTestHelper {
  "updateVertex" should "replace A with D" in {
    val updatedGraph = simpleCycle.updateVertex(A, D)
    // There should be no remaining neighbors to A
    updatedGraph(A) shouldEqual Set()
    // The neighbors of A should become neighbors of D
    updatedGraph(D) shouldEqual Set(B, C)
    updatedGraph(D, C) shouldBe true
    updatedGraph(A, C) shouldBe false
  }

  it should "do nothing when the old vertex isn't in the vertex set" in {
    val updatedGraph = simpleCycle.updateVertex(D, E)
    updatedGraph shouldEqual simpleCycle
  }

  "apply" should "work with a distance parameter" in {
    simpleTree(A, E, 2) shouldBe true
  }

  it should "work when a shorter path exists" in {
    simpleTree(A, C, 2) shouldBe true
  }

  it should "fail to find a 1 distance path" in {
    simpleTree(A, E, 1) shouldBe false
    simpleTree(A, E) shouldBe false
  }
}

trait GraphTestHelper {
  val simpleCycle: Graph[DummyObject] =
    new Graph(
      Set(A, B, C),
      Set(
        Edge(A, B),
        Edge(B, C),
        Edge(C, A)
      )
    )

  val simpleTree: Graph[DummyObject] =
    new Graph(
      Set(A, B, C, D, E),
      Set(
        Edge(A, B),
        Edge(A, C),
        Edge(C, D),
        Edge(C, E)
      )
    )
}

sealed trait DummyObject
object A extends DummyObject
object B extends DummyObject
object C extends DummyObject
object D extends DummyObject
object E extends DummyObject
