package breakthroughPP.player.ai;

import breakthroughPP.preset.Move;

import java.util.ArrayList;
import java.util.Random;

public class RandomPlayer extends AbstractAI {

    private Random rand = new Random();

    /**
     * Constructor for the RandomPlayer if the board dimensions are known
     */

    public RandomPlayer() {
        super();
    }

    /**
     * Method to get a random move out of all possible moves
     *
     * @return Move that the easy AI wants to go
     */

    @Override
    public Move getMove() {
        ArrayList<Move> now = board.getPossibleMoves(board.getCurrentPlayer());
        int n = rand.nextInt(now.size());
        return now.get(n);
    }

}

