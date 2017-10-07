package model.cards

import scala.util.Random

case class Deck[T](drawPile: List[T], discardPile: List[T]) {
  def reshuffle(): Deck[T] = Deck(Random.shuffle(drawPile ++ discardPile), List())

  def draw(): (T, Deck[T]) = {
    val drawnCard = drawPile.headOption
    drawnCard match {
      case Some(element) => (element, Deck(drawPile.tail, element :: discardPile))
      case None => reshuffle().draw()
    }
  }
}
