//check who has lowest cards
//if person next to you
//( true) check if you have power card
//if you dont play regular card\

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class HamperLeader extends Player{
  public boolean Valid = false;
  public boolean PowerValid = false;
  public HamperLeader(Card[] cards){
    super(cards);
  }

  // public int getSizeOfHand(){ return hand.size();}


  public int getSmallest( ArrayList<Player> players, int player){
    int player_position = player; //storage
    int smallest_hand = players.get(player).getSizeOfHand();   //storage//52 because that is the max amount of cards
    int hand_size;
    int i=0;
    for (i=0; i< players.size(); i++){
      hand_size = players.get(i).getSizeOfHand();
      if (hand_size < smallest_hand) {
        smallest_hand = hand_size;
        player_position = i;
      }
    }
    return(player_position);
  }

  //play power card if you have 8, 2, 4 or 7, apply Player.power_x
  //if that is not true, play first card in your hand

  @Override
  public boolean play (DiscardPile discardPile, Stack<Card> drawPile, ArrayList<Player> players, int player, int dir){
    Valid = false;
    PowerValid = false;
    String topPileSuit = discardPile.top().getSuit().toString();
    String topPileRank = "" + discardPile.top().getRank();

    int prevPlayer = 0;
    int nextPlayer = 0;
    boolean nextPlayerOne = false;
    int[] goodIndex = new int[getSizeOfHand()];
    int[] powerIndex = new int[getSizeOfHand()];
    int powerCounter = 0;
    int powerNumber = 0;
    int counter = 0;
    boolean played_before;
    prevPlayer = getPlayers(players, player, "prev");
    nextPlayer = getPlayers(players, player,"next");
    String maxsuit = power_8(players.get(prevPlayer).hand,players,player,dir, drawPile);
    if(topPileRank.equals("2")){
      power_2(discardPile, drawPile, player);
    }
    if(topPileRank.equals("4")){
      if(power_4(discardPile, drawPile, players, player) == true){

        return(false);
      }
    }
    if(topPileRank.equals("8")){
      System.out.println("POWER CARD: 8 has been played.");
      Card please = new Card(maxsuit,topPileRank);
      discardPile.add(please);
    }
    System.out.println("HamperLeader's hand: " + this.hand);
    int leader = getSmallest (players, player);
    System.out.println(leader);
    // //playing power card start
    if (leader != player){
      for(int i = 0; i < getSizeOfHand(); i++){
        if(this.hand.get(i).getRank() == 8){
          discardPile.add(this.hand.remove(i));
        }else if(this.hand.get(i).getRank() == 2){
          discardPile.add(this.hand.remove(i));
        }else if(this.hand.get(i).getRank() == 4){
          discardPile.add(this.hand.remove(i));
        }else if(this.hand.get(i).getRank() == 7){
          discardPile.add(this.hand.remove(i));
        }
      }
    }
    while (Valid == false  && drawPile.empty() == false){
      maxsuit = power_8(players.get(prevPlayer).hand,players,player,dir, drawPile);
      goodIndex = new int[getSizeOfHand()];
      //System.out.println("Hand Size: " + getSizeOfHand());
      for(int i = 0;i < getSizeOfHand();i++){
        if(topPileSuit.equals(this.hand.get(i).getSuit())
        || topPileRank.equals("" + this.hand.get(i).getRank())
        || this.hand.get(i).getRank() == (8)){
          goodIndex[counter] = i;
          counter++;
          Valid = true;
        }
      }
      if (Valid == false && drawPile.empty() == false){
        System.out.println("No cards, Picked up: " + drawPile.peek());
        this.hand.add(drawPile.pop());
        System.out.println("HamperLeader's hand: " + this.hand);
      }
    }
    int randomI = (int) (Math.random() * counter);
    //System.out.println(this.hand.get(goodIndex[randomI]));
    if (Valid == true){
      System.out.println("Card found, Placing card: " + this.hand.get(goodIndex[randomI]));
      if (this.hand.get(goodIndex[randomI]).getRank() == 8){
        this.hand.remove(goodIndex[randomI]);
        Card played = new Card(maxsuit,"8");
        discardPile.add(played);
      }
      else{
        discardPile.add(this.hand.remove(goodIndex[randomI]));
      }
    }
    System.out.println("");

    // Win or keep going game.
    if( this.hand.size() == 0 ){
      return true;
    }
    return false;
  }
}//end of Hamper Leader Player
