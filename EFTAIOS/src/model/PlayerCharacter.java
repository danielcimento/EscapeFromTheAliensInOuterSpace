package model;
import java.util.LinkedList;
import java.util.Scanner;

import map.MapNode;
import map.NodeType;

public class PlayerCharacter{
    private MapNode currentPosition;
    private CharacterType aType;
    private boolean dead;
    private boolean escaped;
    public static LinkedList<PlayerCharacter> gameCharacters = new LinkedList();


    public static void main(String[] args){
        PlayerCharacter tom = new PlayerCharacter(CharacterType.HUMAN, MapNode.get("a3"));
        System.out.println(tom.currentPosition.getName());
        System.out.println(tom.currentPosition.getName());
        PlayerCharacter jeff = new PlayerCharacter(CharacterType.ALIEN, MapNode.get("a3"));
        System.out.println(jeff.isValidMove(MapNode.get("b3")));
        System.out.println(jeff.isValidMove(MapNode.get("c3")));
    }

    public PlayerCharacter(CharacterType pType, MapNode startLoc){
        currentPosition = startLoc;
        aType = pType;
        dead = false;
        escaped = false;
    }


    public MapNode getCurrentLocation(){
    	return currentPosition;
    }
    
    public void attack(){
        if(aType == CharacterType.HUMAN){
            //humans can't attack
            return;
        }

        if(aType == CharacterType.ALIEN){
            for( PlayerCharacter p : gameCharacters ){
                if(p.currentPosition == this.currentPosition && p != this){ 
                    p.dead = true;
                }
            }
        }
    }
   
    private void handleCard(DangerousSectorCard pCard){
        if(pCard.getType() == CardType.SILENCE){
            System.out.println("SILENCE IN ALL SECTORS");
        }else if(pCard.getType() == CardType.NOISE_IN_SECTOR){
            System.out.println("When you press enter, all players will see that there was a noise in your sector");
            Scanner s = new Scanner(System.in);
            s.nextLine();
            System.out.println("NOISE IN SECTOR " + this.currentPosition.getName());
        }else if(pCard.getType() == CardType.NOISE_IN_OTHER_SECTOR){
            System.out.print("Please type a sector. All players will hear a noise in that sector: ");
            String output = "";
            Scanner s = new Scanner(System.in);
            while(!MapNode.hasNode(output)){
                output = s.nextLine();
            }
            System.out.println("NOISE IN SECTOR " + output);
        }
    }


    public void move(MapNode m){
        if(isValidMove(m)){
            currentPosition = m;
        }
        if(currentPosition.getType() == NodeType.DANGEROUS){
            handleCard(DangerousSectorCard.draw());
        }else if(currentPosition.getType() == NodeType.ESCAPE_HATCH){
            escaped = true;
        }
    }


    public boolean isValidMove(MapNode dest){
        for(MapNode n : currentPosition.getAdjacencies()){
            if(aType == CharacterType.ALIEN){
                for(MapNode n2 : n.getAdjacencies()){
                    if(n2 == dest){
                        if(n2.getType() != NodeType.BLOCKED){
                            return true;
                        }
                    }
                }
            }
            if(n == dest){
                if(n.getType() != NodeType.BLOCKED){
                    return true;
                }
            }
        }
        return false;
    }
}
