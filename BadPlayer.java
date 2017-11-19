import java.util.Arrays;
import java.util.ArrayList;
import java.util.Stack;

public class BadPlayer extends Player{

	public BadPlayer(Card[] cards){this.hand = new ArrayList<Card>(Arrays.asList(cards));}

  /* play a card */
	public boolean play(DiscardPile       discardPile,
	                    Stack<Card>       drawPile,
											ArrayList<Player> players, int player, int dir)
	{
		String topPileSuit = discardPile.top().getSuit().toString();
    String topPileRank = "" + discardPile.top().getRank();
		System.out.println("Player" + player + ":" + this.hand);
		if(topPileRank.equals("8")){
			topPileSuit = power_8(players.get((player + (players.size() - dir)) % players.size()).hand,players,player,dir);
			discardPile.add(new Card(topPileRank,topPileSuit));
			System.out.println("8 has been played ----------------------------------------------------------------------");
			System.out.println("Top pile suit: " + power_8(players.get((player + (players.size() - dir)) % players.size()).hand,players,player,dir));
		}

		//System.out.print(this.hand.);

		discardPile.add(this.hand.remove(0));
		if( this.hand.size() == 0 ){return true;}
		System.out.println("Player" + player + ":" + this.hand);

		return false;
	}




}
