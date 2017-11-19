import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.Random;

public class Crazy8GameMultiple{
	public static int dir = 1;
	public static void main(String[] args){
		int setGoal = 50;
		boolean isGoal = false;

		Card[] deck = new Card[52];
		int index = 0;
		for(int r=2; r<=14; r+=1){
			for(int s=0; s<4; s+=1){
				deck[index++] = new Card(Card.SUITS[s], Card.RANKS[r]);
			}
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

		Player[] players = new Player[3];
		players[0] = new MindTheEights( Arrays.copyOfRange(deck, 0, 5) );
		System.out.println("0 : " + Arrays.toString( Arrays.copyOfRange(deck, 0, 5)));
		players[1] = new RandomPlayer( Arrays.copyOfRange(deck, 5, 10) );
		System.out.println("1 : " + Arrays.toString( Arrays.copyOfRange(deck, 5, 10)));
		players[2] = new ExtraCards( Arrays.copyOfRange(deck, 10, 15) );
		System.out.println("2 : " + Arrays.toString( Arrays.copyOfRange(deck, 10, 15)));

		int[] total_points = new int[players.length];

		/* create the deck */
		while(isGoal == false){
			deck = new Card[52];
			index = 0;
			for(int r=2; r<=14; r+=1){
				for(int s=0; s<4; s+=1){
					deck[index++] = new Card(Card.SUITS[s], Card.RANKS[r]);
				}
			}

			/* shuffle the deck */
			rnd = new Random();
			for(int i = deck.length-1; i>=0; i=i-1){
				int pos = rnd.nextInt(i+1);
				swap = deck[pos];
				deck[pos] = deck[i];
				deck[i] = swap;
			}

			/* players in the game */
			players = new Player[3];
			players[0] = new MindTheEights( Arrays.copyOfRange(deck, 0, 5) );
			System.out.println("0 : " + Arrays.toString( Arrays.copyOfRange(deck, 0, 5)));
			players[1] = new RandomPlayer( Arrays.copyOfRange(deck, 5, 10) );
			System.out.println("1 : " + Arrays.toString( Arrays.copyOfRange(deck, 5, 10)));
			players[2] = new ExtraCards( Arrays.copyOfRange(deck, 10, 15) );
			System.out.println("2 : " + Arrays.toString( Arrays.copyOfRange(deck, 10, 15)));

			/* discard and draw piles */
			DiscardPile discardPile = new DiscardPile();
			Stack<Card> drawPile = new Stack<Card>();
			for(int i=15; i<deck.length; i++){
				drawPile.push(deck[i]);
			}

			System.out.println("draw pile is : " + Arrays.toString( Arrays.copyOfRange(deck, 15, deck.length) ));
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
			while( win == false && drawPile.empty() == false){
				System.out.println("----------- next turn --------------");
				player = (player + dir) % players.length;


				System.out.println("draw pile : " + drawPile + "\n" );

				if(drawPile.empty() == false){
					win = people.get(player).play(discardPile, drawPile, people, player, dir);
					System.out.println("discard pile : " + discardPile.top() );
					if(drawPile.empty() == false){
						dir = people.get(player).power_7(discardPile, drawPile, people, dir);
					}
				}
			}
			if(drawPile.empty() == true){
				System.out.println("There is no winner");
				System.out.println("\n--------No winner this game, play new game--------\n");
			}
			else{
				total_points[player] += people.get(player).get_Points(people, player);
				if(total_points[player] >= setGoal){
					isGoal = true;
					System.out.println("\n**********winner is player " + player + " with " + total_points[player] + " points!**********\n");
				}
				else{
					System.out.println("\n--------No winner this game on points, play new game--------\n");
				}
			}
		}
	}
	/*public int get_Points(ArrayList<Player> players, int goal,int winner){
		boolean end_multi_game = false;
		int point_count = 0;
		for(int i=0; i<players.size(); i++){
			if(players.get(i) != players.get(winner)){
				for(int j=0; j < players.get(i).getSizeOfHand(); j++){
					point_count += Points(players.get(i).hand.getRank());
				}
			}
		}
		return(point_count);
	}	*/
		/*for(i=winner; i<players.size() ; i++){
			for(j=0; j < players.get(i).getSizeOfHand(); j++){
				total_points[winner] = total_points[winner] + hand.get(j).Points();
			}
		}

	}

	for (i=0; i<players.size(); i++){
		if (total_points[i] >= goal){
			System.out.print ("Player number" + i + "is the winner of the multiples games");
			end_multi_game = true;
		}
		else{end_multi_game = false;}
	}
	return end_multi_game;
}	*/

}
