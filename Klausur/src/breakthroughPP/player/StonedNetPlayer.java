package breakthroughPP.player;

import breakthroughPP.Game;
import breakthroughPP.SplinterTheOmniscientRat;
import breakthroughPP.gui.GUI;
import breakthroughPP.preset.Move;
import breakthroughPP.preset.NetPlayer;
import breakthroughPP.preset.Player;
import breakthroughPP.preset.Status;

import java.rmi.Naming;
import java.rmi.RemoteException;

/**
 * A game of stones Network player
 * This class is a wrapper for the Player class and provides additional networking functionality with fixed GUI
 *
 * @author mamazu
 */
public class StonedNetPlayer extends NetPlayer {
    public final String address;
    private GUI gui;
    private Game game;
    private AbstractPlayer aplayer;
    private boolean addressFreed = false;

    /**
     * Constructor for the StonedNetPlayer
     *
     * @param player  Raw {@link Player} object
     * @param gui     User interface for refreshing purposes
     * @param game    {@link Game} Object
     * @param address RMI address  @throws RemoteException if a remote error occurs
     */
    public StonedNetPlayer(AbstractPlayer player, GUI gui, Game game, String address) throws RemoteException {
        super(player);
        aplayer = player;
        this.gui = gui;
        this.game = game;
        this.address = address;
    }

    /**
     * Updates the players board
     *
     * @param opponentMove Move of the opponent
     * @param boardStatus  Status of the board
     * @throws Exception       if an error
     * @throws RemoteException if a remote error
     */
    public void update(Move opponentMove, Status boardStatus) throws Exception {
        super.update(opponentMove, boardStatus);
        if (gui != null) gui.refresh();
        if (!boardStatus.isOk())
            endgame(boardStatus);
    }

    /**
     * Confirms the board status
     *
     * @param boardStatus Status of the board
     * @throws Exception       if an error
     * @throws RemoteException if a remote error occurs
     */
    public void confirm(Status boardStatus) throws Exception {
        super.confirm(boardStatus);
        if (gui != null) gui.refresh();
        if (!boardStatus.isOk())
            endgame(boardStatus);
    }

    /**
     * Initialises the player
     *
     * @param dimX  width of the board
     * @param dimY  height of the board
     * @param color Color of the player
     * @throws Exception       if an error
     * @throws RemoteException if a remote error
     */
    public void init(int dimX, int dimY, int color) throws Exception {
        super.init(dimX, dimY, color);
        if (gui != null) gui.setViewer(aplayer.getViewer());
    }

    /**
     * Attempts to end game
     *
     * @param status Supplied board status
     */
    public void endgame(Status status) {
        freeAddress();
        //TODO shutdown frees this object. working?
        //remote Game being garbage collected?
        if (game != null)
            game.shutdown();
        else if (gui != null)
            gui.gameEnded("Game Over: " + String.valueOf(status));
    }

    /**
     * Attempts to unbind address from RMI host
     */
    public void freeAddress() {
        if (addressFreed) return;
        addressFreed = true;
        try {
            Naming.unbind("rmi://" + address);
        } catch (Exception e) {
            if (SplinterTheOmniscientRat.DEBUG)
                System.err.println("Unbinding failed: " + e.getMessage());
        }
    }
}

