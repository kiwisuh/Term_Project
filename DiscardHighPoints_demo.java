import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.Random;

public class DiscardHighPoints_demo{

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
    System.out.println("This demo will demonstrate the DiscardHighPoints class. "
    + "\n" + "The first player will be DiscardHighPoints "
    + "\n" + "and the next player will be a random player with one card left. "
    + "\n" + "Because of the behaviour of HamperLeader player "
    + "\n" + "if the next player is the leader and theres no power card"
    + "\n" + "the player will continue to pick up"
    + "\n" +  "from draw pile until there is a power card");
    System.out.println("\n");

    /* START MIND THE EIGHTS */
    players[0] = new DiscardHighPoints( Arrays.copyOfRange(deck, 0, 5) );
    System.out.println("ExtraCards's hand : " + Arrays.toString( Arrays.copyOfRange(deck, 0, 5)));
    System.out.println("\n");
    players[1] = new RandomPlayer( Arrays.copyOfRange(deck, 5, 10) );
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
    System.out.println("\n");
    System.out.println("discard pile : " + discardPile.top() );
    //while( win == false && drawPile.empty() == false){
    player = (player + dir) % players.length;

    win = people.get(player).play(discardPile, drawPile, people, player, dir);
    //System.out.println("RandomPlayer's hand : " + players[0].hand);
    System.out.println("discard pile after HamperLeader's first play: " + discardPile.top() );

  }
}
