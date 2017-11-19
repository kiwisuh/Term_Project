import java.util.Arrays;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Random;
public class RandomPlayer extends Player{
  public RandomPlayer(Card[] cards){
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

    prevPlayer = getPlayers(players, player, "prev");
    nextPlayer = getPlayers(players, player,"next");
    String maxsuit = power_8(players.get(prevPlayer).hand,players,player,dir, drawPile);
    /*checks if top discard pile is a power card */
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

    System.out.println("RandomPlayer's hand: " + this.hand);

    /* saves index*/
    int[] goodIndex = new int[getSizeOfHand()];
    int counter = 0;
    boolean Valid = false;
    while (Valid == false  && drawPile.empty() == false){
      maxsuit = power_8(players.get(player).hand,players,player,dir, drawPile);
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
        System.out.println("RandomPlayer's hand: " + this.hand);
      }
    }
    int randomI = (int) (Math.random() * counter);
    //System.out.println(this.hand.get(goodIndex[randomI]));
    if (Valid == true){
      //System.out.println("Card found, Placing card: " + this.hand.get(goodIndex[randomI]));
      if (this.hand.get(goodIndex[randomI]).getRank() == 8){
        this.hand.remove(goodIndex[randomI]);
        Card played = new Card(maxsuit,"8");
        discardPile.add(played);
      }
      else{
        discardPile.add(this.hand.remove(goodIndex[randomI]));
      }
    }

  //System.out.println("RandomPlayer " + player + ":" + this.hand);
  System.out.println("");

  // Win or keep going game.
  if( this.hand.size() == 0 ){
    return true;
  }
  return false;
}

}
