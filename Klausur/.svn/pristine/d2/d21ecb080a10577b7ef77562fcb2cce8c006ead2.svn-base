package breakthroughPP;

import breakthroughPP.gui.ConsoleBoardUI;
import breakthroughPP.gui.GUI;
import breakthroughPP.map.Board;
import breakthroughPP.player.AbstractPlayer;
import breakthroughPP.player.HumanPlayer;
import breakthroughPP.player.StonedNetPlayer;
import breakthroughPP.player.ai.EasyAI;
import breakthroughPP.player.ai.HardAI;
import breakthroughPP.player.ai.RandomPlayer;
import breakthroughPP.preset.Exceptions.InvalidOrderException;
import breakthroughPP.preset.Exceptions.StatusException;
import breakthroughPP.preset.*;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Game {
    public static final int PLAY_FINISHED = 0;
    public static final int PLAY_ERROR = 122;
    public static final int PLAY_INVALID = 123;

    public final static int PLAYER_ISNOPLAYER = 1337;
    public final static int PLAYER_CONSOLE = 4200;
    public final static int PLAYER_GRAPHIC = 4201;
    public final static int PLAYER_AIRANDOM = 4210;
    public final static int PLAYER_AIEASY = 4211;
    public final static int PLAYER_AIHARD = 4212;


    private static final Status statusIllegal = new Status(Setting.ILLEGAL);
    public final int height, width;
    private final Object lock = new Object(), lockHost = new Object();
    private Board board;
    private Player redPlayer, bluePlayer;
    private ConsoleBoardUI cbui;
    private GUI gui;
    private int aiTurnDelay = 0;
    private boolean forcedShutdown = false;
    private boolean shutdownCalled = false;

    /**
     * Initializes a new game instance
     *
     * @param createUI Whether or not to create a GUI, if false Stdout is used
     * @param width    Width of the game
     * @param height   Height of the game
     * @param red      Player type for redPlayer, must be value of Game.PLAYER_*
     * @param blue     Player type for bluePlayer, must be value of Game.PLAYER_*
     */
    public Game(boolean createUI, int width, int height, int red, int blue) throws Exception {
        this(createUI, width, height);
        if (createUI) waitForUI();
        redPlayer = createPlayer(red);
        redPlayer.init(width, height, Setting.RED);
        bluePlayer = createPlayer(blue);
        bluePlayer.init(width, height, Setting.BLUE);
    }

    /**
     * Initializes a new game instance with NetPlayer initialization ability
     *
     * @param createUI Whether or not to create a GUI, if false Stdout is used
     * @param width    Width of the game
     * @param height   Height of the game
     * @param red      Player type for redPlayer, must be value of Game.PLAYER_*
     * @param blue     Player type for bluePlayer, must be value of Game.PLAYER_*
     * @param rmiHost  RMI host to use for network players, pass null for local game
     * @param redRMI   RMI path to use if redPlayer is a network player, network mode ignored when null
     * @param blueRMI  RMI path to use if bluePlayer is a network player, network mode  ignored when null
     * @param hostRed  True if this game instance should host redPlayer player, ignored when any network name is null
     */
    public Game(boolean createUI, int width, int height, int red, int blue, String rmiHost, String redRMI, String blueRMI, boolean hostRed) throws Exception {
        this(createUI, width, height);
        if (createUI) waitForUI();
        if (redRMI == null || blueRMI == null)
            rmiHost = null;
        if (rmiHost == null || hostRed) {
            redPlayer = createPlayer(red);
            redPlayer.init(width, height, Setting.RED);
        }
        if (rmiHost == null || !hostRed) {
            bluePlayer = createPlayer(blue);
            bluePlayer.init(width, height, Setting.BLUE);
        }
        try {
            if (rmiHost != null) {
                if (hostRed) {
                    this.redPlayer = new StonedNetPlayer((AbstractPlayer) this.redPlayer, null, this, rmiHost + "/" + redRMI);
                    rmiOffer(((StonedNetPlayer) this.redPlayer).address, this.redPlayer);
                    if (gui != null) {
                        gui.setText("Waiting for connection");
                        gui.paintImmediately();
                    }
                    {//Host new game
                        final Game gameinstance = this;
                        final String rmiPath = rmiHost + "/" + blueRMI;
                        new Thread() {
                            @Override
                            public void run() {
                                gameinstance.bluePlayer = null;
                                try {
                                    Thread.sleep(50);
                                } catch (InterruptedException e) {
                                    System.err.println("Could not sleep at game hosting");
                                }
                                //Waiting 30 seconds
                                for (int i = 300; i >= 0; i--) {
                                    gameinstance.bluePlayer = rmiFindIgnore(rmiPath);
                                    if (gameinstance.bluePlayer != null)
                                        break;
                                    try {
                                        Thread.sleep(100);
                                    } catch (InterruptedException iex) {
                                        //ignore
                                    }
                                }
                                synchronized (lockHost) {
                                    lockHost.notify();
                                }
                            }
                        }.start();
                        try {
                            synchronized (lockHost) {
                                lockHost.wait();
                            }
                        } catch (InterruptedException e) {
                            throw new Exception("Game hosting was interrupted");
                        }
                        if (gameinstance.bluePlayer == null)
                            throw new Exception("Game hosting timed out");
                    }
                    if (gui != null) gui.setText();
                    this.bluePlayer.init(width, height, Setting.BLUE);
                } else {
                    this.redPlayer = rmiFind(rmiHost + "/" + redRMI);
                    this.bluePlayer = new StonedNetPlayer((AbstractPlayer) this.bluePlayer, gui, this, rmiHost + "/" + blueRMI);
                    rmiOffer(((StonedNetPlayer) this.bluePlayer).address, this.bluePlayer);
                }
            }
        } catch (Exception e) {
            if (gui != null) gui.gameEnded("Network error");
            shutdown();
            throw e;
        }
    }

    /**
     * Basic game initialization, private use only
     *
     * @param createUI Create new GUI
     * @param w        Width
     * @param h        Height
     */
    private Game(boolean createUI, int w, int h) throws PresetException {
        board = new Board(h, w);//Size min/max handled in board
        width = board.WIDTH;
        height = board.HEIGHT;
        if (SplinterTheOmniscientRat.DEBUG)
            board.setDebug(true);
        final Viewer view = board.getViewer();
        cbui = new ConsoleBoardUI(view);
        if (createUI)
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        System.err.println("Could not sleep");
                    }
                    gui = new GUI(view);
                    synchronized (lock) {
                        lock.notify();
                    }
                }
            }.start();
        else gui = null;
    }

    /**
     * Finds a remote player
     *
     * @param address Location of remote player
     * @return Returns a {@link Player} object
     */
    public static Player rmiFind(String address) throws Exception {
        Player p;
        try {
            p = (Player) Naming.lookup("rmi://" + address);
            if (SplinterTheOmniscientRat.DEBUG)
                System.out.println("Player at " + address + " found");
        } catch (Exception ex) {
            if (SplinterTheOmniscientRat.DEBUG)
                System.err.println("Player at " + address + " not found: " + ex.getMessage());
            throw new Exception("Could not find player at " + address);
        }
        return p;
    }

    /**
     * Tries to find a remote player and ignores exceptions
     *
     * @param address Location of remote player
     * @return Returns a {@link Player} object or null
     */
    public static Player rmiFindIgnore(String address) {
        try {
            Player p = (Player) Naming.lookup("rmi://" + address);
            if (SplinterTheOmniscientRat.DEBUG)
                System.out.println("Player at " + address + " found");
            return p;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Waits for lock to be notified
     */
    private void waitForUI() {
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                System.err.println("Game initialization: wait was interrupted");
            }
        }
    }

    /**
     * Set AI turn delay
     *
     * @param ms Min delay between AI turns in milliseconds
     */
    public void setAiTurnDelay(int ms) {
        aiTurnDelay = ms;
    }

    /**
     * Initializes specified player object
     *
     * @param which Player type, instance of PLAYER_* constant
     */
    private Player createPlayer(int which) throws PresetException {
        switch (which) {
            case PLAYER_CONSOLE:
                return new HumanPlayer(cbui);
            case PLAYER_GRAPHIC:
                if (gui == null)
                    throw new PresetException("No GUI to create graphical player");
                return new HumanPlayer(gui);
            case PLAYER_AIRANDOM:
                return new RandomPlayer();
            case PLAYER_AIEASY:
                return new EasyAI();
            case PLAYER_AIHARD:
                return new HardAI();
            case PLAYER_ISNOPLAYER:
                if (gui != null) gui.gameEnded("Error");
                throw new PresetException("PLAYER_ISNOPLAYER was supplied to createPlayer");
            default:
                if (gui != null) gui.gameEnded("Error");
                throw new PresetException("Unknown player type requested");
        }
    }

    /**
     * Offers a remote player
     *
     * @param address Location the player should be offered at
     * @param player  Player object to offer
     */
    private void rmiOffer(String address, Player player) throws Exception {
        try {
            Naming.rebind("rmi://" + address, player);
            if (SplinterTheOmniscientRat.DEBUG)
                System.out.println("Player at " + address + " ready");
        } catch (MalformedURLException | RemoteException ex) {
            if (SplinterTheOmniscientRat.DEBUG)
                System.err.println("Player hosted at " + address + " encountered an error: " + ex.getMessage());
            throw new Exception("Could not host player at " + address);
        }
    }

    /**
     * Play rounds Rounds
     *
     * @param rounds Number of rounds to play, -1 for indefinite
     * @return If the game is finished or invalid.
     */
    public int play(int rounds) throws PresetException {
        if (rounds < -1 || rounds == 0)
            return PLAY_INVALID;
        while (--rounds != 0 && board.checkStatus().isOk()) {
            Player turn, opposite;
            if (board.getCurrentPlayer() == Setting.RED) {
                turn = redPlayer;
                opposite = bluePlayer;
            } else {
                turn = bluePlayer;
                opposite = redPlayer;
            }
            try {
                forcedShutdown = true;
                Move move;
                boolean execmove;
                long waitUntil = System.currentTimeMillis() + aiTurnDelay;
                for (int counter = 10; counter >= 0; counter--) {
                    execmove = true;
                    if (counter == 0) {
                        System.err.println("Player " + Setting.colorString[board.getCurrentPlayer()] + " supplied too many invalid moves and was forced to surrender");
                        move = null;
                    } else {
                        move = turn.request();
                        if (move == null && gui == null) {
                            Scanner scanner = new Scanner(System.in);
                            try {
                                System.out.print("Are you sure you want to surrender and loose this game? (y)es/(n)o\n> ");
                                String s = scanner.nextLine().toLowerCase();
                                switch (s) {
                                    case "y":
                                    case "yes":
                                    case "j":
                                    case "ja":
                                        break;
                                    case "n":
                                    case "no":
                                    case "nein":
                                    default: //everything else continues game
                                        execmove = false;
                                        break;
                                }
                            } catch (Exception e) {
                                if (SplinterTheOmniscientRat.DEBUG)
                                    e.printStackTrace();
                                System.err.println("Error: Scanner error. Is STDIN still alive?");
                                return PLAY_ERROR;
                            }
                        }
                    }

                    if (execmove && board.executeMove(move)) {
                        {//Turn delay, especially for AI
                            long diff = waitUntil - System.currentTimeMillis();
                            if (diff > 0) Thread.sleep(diff);
                        }
                        Status status = board.checkStatus();
                        turn.confirm(status);
                        opposite.update(move, status);
                        if (gui != null) gui.refresh();
                        break;
                    } else {
                        if (SplinterTheOmniscientRat.DEBUG)
                            System.err.println("Invalid move: " + move);
                        turn.confirm(statusIllegal);
                    }
                    if (gui == null)
                        cbui.draw();
                }
                forcedShutdown = false;
            } catch (InvalidOrderException e) {
                e.printStackTrace(System.err);
                System.err.println("THIS WAS PROGRAMMED FUCKING WRONG");
                return PLAY_ERROR;
            } catch (RemoteException e) {
                if (SplinterTheOmniscientRat.DEBUG)
                    e.printStackTrace(System.err);
                else System.err.println("A remote error occurred during the game :/");
                return PLAY_ERROR;
            } catch (StatusException e) {
                if (SplinterTheOmniscientRat.DEBUG)
                    e.printStackTrace(System.err);
                else System.err.println("Status of the board doesn't match with players :/");
                return PLAY_ERROR;
            } catch (Exception e) {
                if (SplinterTheOmniscientRat.DEBUG)
                    e.printStackTrace(System.err);
                else System.err.println("An error occurred during the game :/");
                return PLAY_ERROR;
            }
        }
        if (!board.checkStatus().isOk())
            shutdown();
        return PLAY_FINISHED;
    }

    /**
     * Does game postprocessing and displays end game state
     */
    public void shutdown() {
        if (shutdownCalled) return;
        shutdownCalled = true;
        if (redPlayer instanceof StonedNetPlayer)
            ((StonedNetPlayer) redPlayer).freeAddress();
        redPlayer = null;
        if (bluePlayer instanceof StonedNetPlayer)
            ((StonedNetPlayer) bluePlayer).freeAddress();
        bluePlayer = null;
        String gameOver;
        Status stat = board.checkStatus();
        gameOver = "Game over - " + Setting.statusString[stat.getStatus()];
        if (gui == null) {
            if (!forcedShutdown)
                cbui.draw();
            System.out.println(gameOver);
        } else {
            gui.gameEnded(gameOver);
            gui.refresh();
        }
    }
}
