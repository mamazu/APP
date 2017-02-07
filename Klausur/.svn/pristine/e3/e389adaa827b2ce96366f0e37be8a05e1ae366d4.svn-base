package breakthroughPP.player;

import breakthroughPP.preset.Requestable;
import breakthroughPP.preset.Move;
import breakthroughPP.preset.PresetException;

/**
 * Layover for AbstractPlayer to allow human interaction
 */
public class HumanPlayer extends AbstractPlayer {
    private Requestable ui;

    /**
     * Creates an object for human game interaction
     * @param user Requestable bject to request moves from
     */
    public HumanPlayer(Requestable user) {
        super();
        ui = user;
    }
	
    /**
     * Returns the move the player made
     * @return Returns the {@link Move} a player has made
     */
    @Override
    public Move getMove() throws PresetException{
        try{
        	return ui.deliver();
        }catch(Exception e){
        	throw new PresetException(e.getMessage());
        }
    }
}
