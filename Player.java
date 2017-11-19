import java.util.ArrayList;
import java.util.Stack;
import java.util.Arrays;
import java.util.Stack;

public abstract class Player{
	protected ArrayList<Card> hand;
	protected static String temp_suit = "";

	public Player(Card[] cards){
		this.hand = new ArrayList<Card>(Arrays.asList(cards));
	}

	public int getSizeOfHand(){
		return hand.size();
	}
	//returns prev or next of current player
	public int getPlayers(ArrayList<Player> players, int player, String deter){
		if(deter.equals("prev")){
			if(player == 0){
				return players.size() - 1;
			}else{
				return player - 1;
			}
		}else if(deter.equals("next")){
			if(player == players.size() - 1){
				return 0;
			}else {
				return player + 1;
			}
		}else {
			return player;
		}
	}

	/* play a card  */
	public abstract boolean play(DiscardPile       discardPile,
	Stack<Card>       drawPile,
	ArrayList<Player> players,
	int player,
	int dir);
	// return true if player wins game by playing last card
	// returns false otherwise
	// side effects: plays a card to top of discard Pile, possibly taking zero
	//               or more cards from the top of the drawPile
	//               card played must be valid card

	public void power_2(DiscardPile discardPile,Stack<Card> drawPile, int player){
		if(drawPile.empty() == false){
			String topPileRank = "" + discardPile.top().getRank();
			if(topPileRank.equals("2") && drawPile.empty() == false){
				System.out.println("POWER CARD: 2 Has been played. Pick up 2.");
				for(int i = 0; i < 2; i++){
					if(drawPile.empty() == false){
						this.hand.add(drawPile.pop());
					}
				}
			}
		}
	}

	public boolean power_4(DiscardPile discardPile,Stack<Card> drawPile,ArrayList<Player> players,int player){
		if(drawPile.empty() == false){
			String topPileSuit = discardPile.top().getSuit().toString();
			if(this.temp_suit.equals(topPileSuit)){
				return(false);
			}
			System.out.println("POWER CARD: 4 has been played. Skip a turn.");
			this.temp_suit = topPileSuit;
			return(true);

		}
		return false;
	}

	public int power_7(DiscardPile discardPile,Stack<Card> drawPile, ArrayList<Player> players, int dir){
		if(drawPile.empty() == false){
			String topPileRank = "" + discardPile.top().getRank();
			if(topPileRank.equals("7")){
				System.out.println("POWER CARD: 7 has been played. Reverse Direction.");
				if(dir == 1){
					return(players.size() - 1);
				}
				else{
					return(1);}
				}
				else if(dir == 1){
					return(1);
				}
				return(players.size() - 1);
			}
			return (dir);
		}

		public String power_8(ArrayList<Card> hand, ArrayList<Player> players, int player, int dir, Stack<Card> drawPile){
			int[] count = new int[4];
			String max_suit = "";
			int max_count = -1;
			int prevPlayer = 0;
			int nextPlayer = 0;
			//prevPlayer = getPlayers(players, player, "prev");
			//prevPlayer = getPlayers(players, player, "next");
			int playerHandSize = hand.size() - 1;
			//System.out.println(players.get(prevPlayer).getSizeOfHand() - 1);

			for(int i = 0; i < playerHandSize - 1; i++) {
				if (drawPile.empty() == false){
					//System.out.println(players.get(prevPlayer).hand.get(i).getSuit());
					if(hand.get(i).getSuit().equals("Diamonds")){
						//counter_d++;
						count[0] += 1;
					}else if(hand.get(i).getSuit().equals("Hearts")){
						//counter_h++;
						count[1] += 1;
					}else if(hand.get(i).getSuit().equals("Clubs")){
						//counter_d++;
						count[2] += 1;
					}else if(hand.get(i).getSuit().equals("Spades")){
						//counter_s++;
						count[3] += 1;
					}
				}
			}
			for(int i=0; i<4; i++){
				if(count[i] > max_count){
					max_count = count[i];
					if(i == 0){
						max_suit = "Diamonds";
					}else if(i == 1){
						max_suit = "Hearts";
					}else if(i == 2){
						max_suit = "Clubs";
					}else{
						max_suit = "Spades";
					}
				}
			}
			//System.out.println("POWER CARD: 8 has been played. Change Suit. to : " + max_suit );

			return (max_suit);
		}
		public int get_Points(ArrayList<Player> players,int winner){
			boolean end_multi_game = false;
			int point_count = 0;
			for(int i=0; i<players.size(); i++){
				if(players.get(i) != players.get(winner) && players.get(i).getSizeOfHand() != 0 ){
					for(int j=0; j < players.get(i).getSizeOfHand(); j++){
						point_count += Points(players.get(i).hand.get(j).getRank());
					}
				}
			}
			return(point_count);
		}

		public int Points(int in_hand){
			if (in_hand==8) {return 50;}
			if (in_hand==2 ||
			in_hand==4){return 25;}
			if (in_hand==7) {return 20;}
			if (in_hand==10||in_hand==11
			||in_hand==12|| in_hand==13){return 10;}
			if (in_hand==9) {return 9;}
			if (in_hand==6) {return 6;}
			if (in_hand==5) {return 5;}
			if (in_hand==3) {return 3;}
			if (in_hand == 14){return 1;}

			else{
				return 0;
			} //if no card exists
		}
	}
