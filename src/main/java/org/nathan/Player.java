package org.nathan;

import java.util.HashSet;

/**
 * Contains functionality for a Player in the Marco Polo Audio Game.
 * @author (Nathan Tao)
 * @version (2/16/2024)
 */
public class Player {
    private double x, y;
    private final AudioListener listener;
    private Board board;
    private final static String WALL_EFFECT_PATH = "src/main/java/org/nathan/wall_hit.wav";
    private final static String WIN_EFFECT_PATH = "src/main/java/org/nathan/win.wav";
    private HashSet<double[]> visited;

    /**
     * Creates a new Player and pseudorandomly sets the Player position.
     * @param listener an AudioListener that will listen to the target AudioSource and other sound effects
     * @param board a Board representing this Player's Game Instance's board
     */
    public Player(AudioListener listener, Board board) {
        x=0;
        y=0;
        this.listener = listener;
        this.board = board;
        visited = new HashSet<>();
        setRandomPos();
    }

    /**
     * Sets the position of the Player to a random empty tile.
     */
    public void setRandomPos() {
        x=Math.random()*board.NUM_COLS;
        y=Math.random()*board.NUM_ROWS;
        if (board.getPos(x, y)!=' ') setRandomPos();
        else tick(0, 0);
    }

    /**
     * Returns the position of this Player instance.
     * @returns an array of two integers [x, y] representing this Player's x and y coordinates.
     */
    public int[] getPos() {
        return new int[]{(int) x, (int) y};
    }

    /**
     * Runs one tick for this Player instance, updating this Player's position and this Player instance's AudioListener's position. Evaluates collisions and wins. Plays sound effects corresponding to events such as wall collisions and winning.
     * @param vx the change in x (relative to the board) position for this Player instance over this tick
     * @param vy the change in y (relative to the board) position for this Player instance over this tick
     * @returns true if this Player has landed on the target tile (and has, therefore, won the game), false otherwise.
     */
    public boolean tick(double vx, double vy) {
        board.setPos(x, y, ' ');
        x = Math.min(Math.max(0, x+vx), board.NUM_COLS);
        y = Math.min(Math.max(0, y+vy), board.NUM_ROWS);
        if (board.getPos(x,y)=='x') {
            AudioSource s = new AudioSource();
            s.setPosition((float)x, (float)y);
            s.setVolume(0.069f);
            s.play(listener.getBuffer(WALL_EFFECT_PATH));
            x-=vx;
            y-=vy;
        } else if (board.getPos(x,y)=='T') {
            AudioSource s = new AudioSource();
            s.setPosition((float)x, (float)y);
            s.setVolume(0.69f);
            s.play(listener.getBuffer(WIN_EFFECT_PATH));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        }
        board.setPos(x, y, 'P');
//        listener.setVelocity((float)vx, (float)vy, 0);
        listener.setPosition((float)x, (float)y);
        visited.add(new double[]{x, y});
//        System.out.println("Player pos: (" + x + ", " + y + ")");
        return false;
    }

    /**
     * Returns the positions this player has visited.
     * @return a set representing the positions this player has visited, each position is an array of doubles in the form [x, y].
     */
    public HashSet<double[]> getVisited() {
        return visited;
    }
}
