package model.cards

import model.cards._

import scala.util.Random

case class DangerousSectorCard(cardType: CardType)

object DangerousSectorCard {
  val initialDeck: Deck[DangerousSectorCard] = initializeDeck()

  private def initializeDeck(): Deck[DangerousSectorCard] = {
    val silenceCard = new DangerousSectorCard(Silence)
    val silenceCards = List.fill(5)(silenceCard)
    val noiseCard = new DangerousSectorCard(NoiseInSector)
    val noiseCards = List.fill(10)(noiseCard)
    val otherNoiseCard = new DangerousSectorCard(NoiseInOtherSector)
    val otherNoiseCards = List.fill(10)(otherNoiseCard)

    val deck = Random.shuffle(silenceCards ++ noiseCards ++ otherNoiseCards)
    Deck(deck, List())
  }
}
