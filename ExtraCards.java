import java.util.Arrays;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Random;
public class ExtraCards extends Player{


  public ExtraCards(Card[] cards){
    super(cards);
  }
  public boolean play(DiscardPile       discardPile,
  Stack<Card>       drawPile,
  ArrayList<Player> players, int player, int dir)
  {
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
    boolean Valid = false;


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
    System.out.println("ExtraCard's hand: " + this.hand);
    while (Valid == false && (players.get(nextPlayer).getSizeOfHand() > 1) && drawPile.empty() == false){
      goodIndex = new int[getSizeOfHand()];
      maxsuit = power_8(players.get(player).hand,players,player,dir, drawPile);
      //counter = 0;


      for(int i = 0;i < getSizeOfHand();i++){
        if(topPileSuit.equals(this.hand.get(i).getSuit())  || topPileRank.equals("" + this.hand.get(i).getRank()) || this.hand.get(i).getRank() == (8)){
          goodIndex[counter] = i;
          counter++;
          Valid = true;

        }
      //  System.out.println("this is inside for loop Valid :" + Valid);
      }
    //  System.out.println("Valid :" + Valid);
      if (Valid == false && drawPile.empty() == false){
        System.out.println("No cards, Picked up: " + drawPile.peek());
        this.hand.add(drawPile.pop());
        System.out.println("ExtraCard's hand: " + this.hand);
      }
    }

    int randomI = (int) (Math.random() * counter);
    //int randomI = (int) counter;
    if (Valid == true && (players.get(nextPlayer).getSizeOfHand() > 1 )){
      System.out.println("Card found, Placing card: " + this.hand.get(goodIndex[randomI]));
      //System.out.println("Non-Placing Power Move: " +  this.hand.get(goodIndex[randomI]));
      if (this.hand.get(goodIndex[randomI]).getRank() == 8){
        this.hand.remove(goodIndex[randomI]);
        Card played = new Card(maxsuit,"8");
        discardPile.add(played);
      }
      else{
        discardPile.add(this.hand.remove(goodIndex[randomI]));
      }
    }
    // END - EVERYTHING BEFORE NEXT PLAYER HAS ONE CARD LEFT //

    while (Valid == false && (players.get(nextPlayer).getSizeOfHand() == 1) && drawPile.empty() == false){
      //System.out.println("Must Use a power card!");
      goodIndex = new int[getSizeOfHand()];
      maxsuit = power_8(players.get(player).hand,players,player,dir, drawPile);
    //  counter = 0;


      for(int i = 0;i < getSizeOfHand();i++){
        if(topPileSuit.equals(this.hand.get(i).getSuit()) || topPileRank.equals("" + this.hand.get(i).getRank()) || this.hand.get(i).getRank() == (8)){
          if (check(this.hand.get(i)) == true){
            powerNumber = i;
            //System.out.println("Found a valid power card: " + this.hand.get(powerNumber) );
            Valid = true;
            break;
          }
        }
      }
      if (Valid == false){
        System.out.println("No power cards, Picked up: " + drawPile.peek());
        this.hand.add(drawPile.pop());
      }
    }
    if (Valid == true && (players.get(nextPlayer).getSizeOfHand() == 1 )){
      System.out.println("Card found, placing card: " + this.hand.get(powerNumber));
      //System.out.println("Placing Power Move: " +  this.hand.get(powerNumber));
      if (this.hand.get(powerNumber).getRank() == 8){
              this.hand.remove(powerNumber);
              Card played = new Card(maxsuit,"8");
              discardPile.add(played);
            }
            else{
              discardPile.add(this.hand.remove(powerNumber));
            }
    }

    /*
    // now i need to use a power move.
    while (Valid == false && (players.get(player+1).getSizeOfHand() == 1)){
    for(int i = 0; i < getSizeOfHand();i++){
    // If there is a power card in the hand. exit the loop.
    if(topPileSuit.equals(this.hand.get(i).getSuit()) ||
    topPileRank.equals(this.hand.get(i).getRank()) &&
    check(this.hand.get(i)) == true){
    powerIndex[powerCounter] = i;
    powerNumber = i;
    powerCounter++;
    System.out.println("Found power card: " + this.hand.get(powerIndex[powerNumber]) );
    Valid = true;
    PowerValid = true;
  }
}

// If we cant find a power card pick up.
if (Valid == false){
System.out.println("Must find a power card: " + drawPile.peek());
this.hand.add(drawPile.pop());
}
}



if (PowerValid == true && (players.get(player+1).getSizeOfHand() == 1) ){
System.out.println("Placing Power Move: " +  this.hand.get(powerIndex[powerNumber]));
discardPile.add(this.hand.remove(powerIndex[powerCounter]));
}
*/
//System.out.println("Player ExtraCards " + player + ":" + this.hand);
System.out.println("");

if( this.hand.size() == 0 ){
  return true;
}
return false;
}



//checks if theres ANY power card
public boolean check(Card other){
  if(other.getRank() == 2 || other.getRank() == 4 || other.getRank() == 7 || other.getRank() == 8){
    return true;
  }
  else{
    return false;
  }
}

}
