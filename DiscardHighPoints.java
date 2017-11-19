import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class DiscardHighPoints extends Player{
    
 public final static String[] RANKS = { "None", "None",
    "2", "3", "4", "5", "6", "7", "8", "9", "10",
    "Jack", "Queen", "King", "Ace"};

 protected ArrayList<Card> hand;

 public DiscardHighPoints (Card[] cards) { this.hand = new ArrayList<Card>(Arrays.asList(cards)); }

 public int getSizeOfHand(){ return hand.size();}
 
 @Override
 public boolean play (DiscardPile discardPile, Stack<Card> drawPile, ArrayList<Player> players, int player, int dir){
   
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
  
  String topPileSuit = discardPile.top().getSuit().toString();
  
  int card_points = 0;
  
  int topPileRank = discardPile.top().getRank();
  int card_point_top = Points(topPileRank);
 
  //Attributes
  int HighCard = 0; //Temporary Valiable
  int HighCard_MAX = 0; //To store the BEST choice 
  String Suit_high = "None"; //to use later to define your best high card
  int Rank_high =0;  //to use later to define your best high card
  
  //Attributes 
  int ChangeSuit = 0; //Temporary Valiable
  int ChangeSuit_MAX = 0; //To store the BEST choice
  String Suit_suit = "None";  //to use later to define your best suit card
  int Rank_suit = 0;    //to use later to define your best suit card

//Start: CODE TO CHECK FOR HIGEST CARD OR CHANGE SUIT ---------------------------------  
for (int i = 0;i < getSizeOfHand();i++){ 
   int card_point_player = Points(players.get(player).hand.get(i).getRank()); 
   
  //card must also be same suit as the top pile card so the play is valid
    if ((card_point_player > card_point_top) && (this.hand.get(i).getSuit().equals(topPileSuit))){
      HighCard = card_point_player;  
      if (HighCard > HighCard_MAX){ 
        HighCard_MAX = HighCard ;
        Suit_high = hand.get(i).getSuit();
        Rank_high = hand.get(i).getRank();
      }
    }
  }

  for (int i = 0;i < getSizeOfHand();i++){ 
     if (this.hand.get(i).getSuit().equals(topPileSuit)){ 
      ChangeSuit = Points(players.get(player).hand.get(i).getRank()) ;
       if (ChangeSuit > ChangeSuit_MAX){
        ChangeSuit_MAX = ChangeSuit;
        Suit_suit = hand.get(i).getSuit();
        Rank_suit= hand.get(i).getRank();
       }
     }
  }
  
  //defining our best cards!
  Card high = new Card (RANKS[Rank_high], Suit_high);
  Card suit = new Card (RANKS[Rank_suit], Suit_suit);
  
  //if there is a card that can be played
  //following code will determine which
  //will be efficiently more point discarding 
  if (HighCard_MAX != 0 || ChangeSuit_MAX !=0){
    if (HighCard_MAX >= ChangeSuit_MAX){
      this.hand.remove(high);
      discardPile.add(high);
    } 
    else{
      this.hand.remove(suit);
      discardPile.add(suit);
    }
   }
      while (Valid == false  && drawPile.empty() == false){
      maxsuit = power_8(players.get(prevPlayer).hand,players,player,dir, drawPile);
      goodIndex = new int[getSizeOfHand()];
      //System.out.println("Hand Size: " + getSizeOfHand());
      for(int i = 0;i < getSizeOfHand();i++){
        if(topPileSuit.equals(this.hand.get(i).getSuit())
        || (topPileRank == this.hand.get(i).getRank())
        || this.hand.get(i).getRank() == (8)){
          goodIndex[counter] = i;
          counter++;
          Valid = true;
        }
      }
      if (Valid == false && drawPile.empty() == false){
        System.out.println("No cards, Picked up: " + drawPile.peek());
        this.hand.add(drawPile.pop());
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
 

}//end of overrriten method
}//end of DiscardHighPoints Player