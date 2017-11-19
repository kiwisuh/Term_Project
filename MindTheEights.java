import java.util.Arrays;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Random;
public class MindTheEights extends Player{
  public MindTheEights(Card[] cards){
    super(cards);
  }

  public boolean play(DiscardPile       discardPile,
  Stack<Card>       drawPile,
  ArrayList<Player> players, int player, int dir)
  {

    int counter8 = 0;
    String topPileSuit = discardPile.top().getSuit().toString();
    String topPileRank = "" + discardPile.top().getRank();

    int prevPlayer = 0;
    int nextPlayer = 0;
    int[] goodIndex = new int[getSizeOfHand()];
    int[] eightIndex = new int[getSizeOfHand()];
    int amount_8 = 0;
    int counter = 0;
    int counter_8 = 0;
    boolean Valid = false;
    boolean eight_Valid = false;

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
    System.out.println("MindTheEight player's hand: " + this.hand);

    for(int i = 0;i < getSizeOfHand();i++){
      if(this.hand.get(i).getRank() == 8){
        amount_8 += 1;
      }
    }
    while (Valid == false && drawPile.empty() == false){
      maxsuit = power_8(players.get(player).hand,players,player,dir, drawPile);
      goodIndex = new int[getSizeOfHand()];
      eightIndex = new int[getSizeOfHand()];
      for(int i = 0;i < getSizeOfHand();i++){
        if(topPileSuit.equals(this.hand.get(i).getSuit()) || topPileRank.equals("" + this.hand.get(i).getRank())){
          if(this.hand.get(i).getRank() == 8){
            eightIndex[counter_8] = i;
            counter_8 += 1;
            eight_Valid = true;
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
        System.out.println("MindTheEight player's hand: " + this.hand);
      }
    }
    int randomI = (int) (Math.random() * counter);
    int random_8 = (int) (Math.random() * counter_8);
    if(eight_Valid == true){
      if(getSmallest(players,player) != player && players.get(getSmallest(players,player)).getSizeOfHand() == amount_8){
        this.hand.remove(eightIndex[random_8]);
        Card played = new Card(maxsuit,"8");
        discardPile.add(played);
        Valid = false;
      }
    }
    if (Valid == true){
      discardPile.add(this.hand.remove(goodIndex[randomI]));
    }
    if( this.hand.size() == 0 ){
      return true;
    }
    return false;
  }

public int getSmallest( ArrayList<Player> players, int player){
int player_position = -1; //storage
int smallest_hand = 15;   //storage//52 because that is the max amount of cards
int hand_size;
int i=0;
 for (i=0; i< players.size(); i++){
   hand_size = players.get(i).getSizeOfHand();
  if ((hand_size < smallest_hand) && (player_position != player)) { smallest_hand = hand_size; player_position = i;}
 }
 if (player_position !=-1){return player_position;}
 else {return player;}
 }
}
