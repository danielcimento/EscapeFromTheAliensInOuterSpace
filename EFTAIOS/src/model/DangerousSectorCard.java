package model;
import java.util.Stack;
import java.util.Collections;


public class DangerousSectorCard{
    private CardType aType;
    private static Stack<DangerousSectorCard> drawnCards = new Stack<>();
    private static Stack<DangerousSectorCard> deckOfCards = new Stack<>();

    //this method should probably never need to be used, save for tests
    public static Stack<DangerousSectorCard> getDeckInstance(){
    	return deckOfCards;
    }
    
    static{
    	initializeDeck();
    }
    
    private static void initializeDeck(){
        for(int i = 0; i < 5; i++){
            deckOfCards.push(new DangerousSectorCard(CardType.SILENCE));
        }

        for(int i = 0; i < 10; i++){
            deckOfCards.push(new DangerousSectorCard(CardType.NOISE_IN_SECTOR));
        }

        for(int i = 0; i < 10; i++){
            deckOfCards.push(new DangerousSectorCard(CardType.NOISE_IN_OTHER_SECTOR));
        }

        Collections.shuffle(deckOfCards);
    }
    
    //used for testing
    private static void reset(){
    	deckOfCards = new Stack<>();
    	drawnCards = new Stack<>();
    	initializeDeck();
    }

    public CardType getType(){
        return aType;
    }

    private static void refillDeck(){
        for(DangerousSectorCard c : drawnCards){
            deckOfCards.push(c);
        }
        drawnCards = new Stack<>();
        Collections.shuffle(deckOfCards);
    }

    public static DangerousSectorCard draw(){
        if(deckOfCards.isEmpty()){
            refillDeck();    
        }
        DangerousSectorCard card = deckOfCards.pop();
        drawnCards.push(card);
        return card;
    }

    private DangerousSectorCard(CardType pType){
        aType = pType;
    } 

}
