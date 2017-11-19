import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.Random;

public class MindTheEights_demo{

  public static int dir = 1;

  public static void main(String[] args){
    /* create the deck */
    Card[] deck = new Card[52];
    Card[] eightdeck = new Card[52];


    int index = 0;
    for(int r=2; r<=14; r+=1){
      for(int s=0; s<4; s+=1){
        deck[index++] = new Card(Card.SUITS[s], Card.RANKS[r]);
      }
    }

    int eightIndex = 0;
    for (int i = 0; i<4; i+=1){
      eightdeck[eightIndex++] = new Card(Card.SUITS[i], Card.RANKS[8]);
    }

    /* shuffle the deck */
    Random rnd = new Random();
    Card swap;
    for(int i = deck.length-1; i>=0; i=i-1){
      int pos = rnd.nextInt(i+1);
      swap = deck[pos];
      deck[pos] = deck[i];
      deck[i] = swap;
    }
    Player[] players = new Player[2];

    //description of the demo
    System.out.println("\n");
    System.out.println("This demo will demonstrate the MindTheEights class. "
    + "\n" + "The first player will be Mind the eights "
    + "\n" + "and the next player will be a RandomPlayer. "
    + "\n" + "The Mind the Eights player will have 4 8s with different suits in his hand."
    + "\n" + "Because of the behaviour of Eights player, even though the player has same suit "
    + "the player will not discard 8 in first round but pick a card from draw Pile");
    System.out.println("\n");

    /* START MIND THE EIGHTS */
    players[0] = new MindTheEights( Arrays.copyOfRange(eightdeck, 0, 4) );
    System.out.println("Mind The Eights Player's hand : " + Arrays.toString( Arrays.copyOfRange(eightdeck, 0, 4)));
    players[1] = new RandomPlayer( Arrays.copyOfRange(deck, 5, 10) );
    System.out.println("\n");
    System.out.println("Next Player's hand : " + Arrays.toString( Arrays.copyOfRange(deck, 5, 10)));
    /* END MIND THE EIGHTS */


    /* discard and draw piles */
    DiscardPile discardPile = new DiscardPile();
    Stack<Card> drawPile = new Stack<Card>();
    for(int i=15; i<deck.length; i++){
      drawPile.push(deck[i]);
    }

    deck = null;

    boolean win = false;
    int player = -1;    // start game play with player 0

    ArrayList<Player> people = new ArrayList<Player>(Arrays.asList(players));
    discardPile.add( drawPile.pop());
    while(discardPile.top().getRank() == 2 || discardPile.top().getRank() == 4 ||
    discardPile.top().getRank() == 7 || discardPile.top().getRank() == 8){
      discardPile.add( drawPile.pop());
      discardPile.add( drawPile.pop());
      System.out.println("discard pile in while: " + discardPile.top() );
    }
    System.out.println("\n");
    System.out.println("discard pile : " + discardPile.top() );
    //while( win == false && drawPile.empty() == false){
    player = (player + dir) % players.length;

    win = people.get(player).play(discardPile, drawPile, people, player, dir);
    System.out.println("discard pile after Mind The Eight's first play: " + discardPile.top() );

  }
}
