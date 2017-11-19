//check who has lowest cards
//if person next to you
//( true) check if you have power card
//if you dont play regular card\

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class test extends Player{

  public boolean PowerValid = false;
  public test(Card[] cards){
    super(cards);
  }
  // public int getSizeOfHand(){ return hand.size();}

public int IdentifyLeader( ArrayList<Player> players, int player){
//returns position of smallest_hand_player if it is next to current card
//otherwise returns the position of the current player
//USEFULL BECAUSE: since leader = IdentifyLeader
//if player is not leader, you can play your power cards
//if player is leader you cannot play your power cards
//this works because before returning the value smallest_hand_player
//we check to see if it is the next player if not, smallest hand is
//player therefore, hold onto power cards


//Atributes
    int hand_size;
    int smallest_hand = 52;   //max had size
    int i =0;
 int smallest_hand_player = player;
 int HP_next_player = getPlayers(players, player, "next");


    for (i=0; i< players.size(); i++){
      hand_size = players.get(i).getSizeOfHand();
   if (hand_size < smallest_hand){

        smallest_hand = hand_size;
        smallest_hand_player = i;
      }
   }
if (HP_next_player == smallest_hand_player){return smallest_hand_player;}
else{return player;}
    }
  //play power card if you have 8, 2, 4 or 7, apply Player.power_x
  //if that is not true, play first card in your hand

  @Override
  public boolean play (DiscardPile discardPile, Stack<Card> drawPile, ArrayList<Player> players, int player, int dir){
    boolean Valid = false;
    boolean power_Valid = false;
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
    int counter_p = 0;
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
    System.out.println("test's hand: " + this.hand);
    int leader = IdentifyLeader(players, player);
    System.out.println(leader);
    // //playing power card start

    while (Valid == false  && drawPile.empty() == false){
      maxsuit = power_8(players.get(prevPlayer).hand,players,player,dir, drawPile);
      goodIndex = new int[getSizeOfHand()];
      powerIndex = new int[getSizeOfHand()];
      //System.out.println("Hand Size: " + getSizeOfHand());
      for(int i = 0;i < getSizeOfHand();i++){

        if(topPileSuit.equals(this.hand.get(i).getSuit())
        || topPileRank.equals(this.hand.get(i).getRank())){
        if(check(this.hand.get(i))){
          powerIndex[counter_p] = i;
          counter_p += 1;
          power_Valid = true;
        }else{
          goodIndex[counter] = i;
          counter++;
          Valid = true;
        }
      }
    }
      if (Valid == false && drawPile.empty() == false){
        System.out.println("No cards, Picked up: " + drawPile.peek());
        this.hand.add(drawPile.pop());
        System.out.println("HamperLeader player's hand: " + this.hand);
      }
    }
    int randomI = (int) (Math.random() * counter);
    int random_p = (int) (Math.random() * counter_p);
    //System.out.println(this.hand.get(goodIndex[randomI]));

    if(power_Valid == true){
      if (leader != player){
        discardPile.add(this.hand.remove(powerIndex[random_p]));
      }
      Valid = false;
    }

    if(leader == player || drawPile.empty() == true){
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
  }
    System.out.println("");

    // Win or keep going game.
    if( this.hand.size() == 0 ){
      return true;
    }
    return false;

  }
  public boolean check(Card other){
    if(other.getRank() == 2 || other.getRank() == 4 || other.getRank() == 7 || other.getRank() == 8){
      return true;
    }
    else{
      return false;
    }
  }
}//end of Hamper Leader Player
