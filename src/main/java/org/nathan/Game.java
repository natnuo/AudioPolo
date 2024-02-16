package org.nathan;

import java.util.HashSet;

/**
 * Contains functionality for displaying and playing the Marco Polo Audio Game.
 * @author (Nathan Tao)
 * @version (2/16/2024)
 */
public class Game {
    private Board board;
    private Player player;
    private int[] initialPlayerPos;
    private Target target;
    private AudioListener listener;
    private int turns;
    private static final String TARGET_AUDIO = "src/main/java/org/nathan/bounce.wav";

    private int rows;
    private int cols;
    private double wallDensity;

    /**
     * Creates and starts a new Game, including the Game's board and sound.
     * @param rows an int representing the rows of the new Game's board.
     * @param cols an int representing the columns of the new Game's board.
     * @param wallDensity an int representing the targeted density of walls in the board, from 0-0.8. 0 means (except for the borders) the targeted amount of walls in the board is 0% of the tiles. 0.8 means (except for the borders) the targeted (but not guaranteed) amount of walls on the board is 80% of the tiles.
     */
    public Game(int rows, int cols, double wallDensity) {
        listener = new AudioListener();
        target = new Target(TARGET_AUDIO, listener);
        this.rows = rows;
        this.cols = cols;
        this.wallDensity = wallDensity;
        start();
    }

    /**
     * Updates this Game by a tick, considering new input.
     * @param c a char corresponding to commands. 'w', 'a', 's', or 'd' represent movement forward, left, back, and right respectively. 'b' displays the current board state. Case must be lowercase.
     * @returns true if this Game has finished (i.e. the player has won), otherwise false
     */
    public boolean tick(char c) {
        int px=0, py=0;
        switch (c) {
            case 'w' -> py=1;
            case 'a' -> px=-1;
            case 's' -> py=-1;
            case 'd' -> px=1;
            case 'b' -> System.out.println(board);
        }
        boolean res = player.tick(px, py);
        turns++;
        target.tick(0, 0);
//        updateMuffled();
//        System.out.println(board);
        return res;
    }

//    private void updateMuffled() {
//        boolean muffled = false;
//        int yDist = player.getPos()[1]-target.getPos()[1];
//        int xDist = player.getPos()[0]-target.getPos()[0];
//        double slope = yDist/(double)xDist;
//        for (int x=player.getPos()[0];x<player.getPos()[0]+xDist;x++) {
//            int y = (int) Math.max(Math.min(Math.floor(x*slope+player.getPos()[1]), board.NUM_ROWS-1), 1);
//            if (board.getPos(x, y)=='x'||board.getPos(x, Math.max(Math.min(board.NUM_ROWS-1,y+1), 1))=='x') {
//                muffled = true;
//                break;
//            }
//            System.out.println(x + " " + y);
//            System.out.println(x + " " + Math.max(Math.min(board.NUM_ROWS-1,y+1), 1));
//        }
//        if (!muffled) {
//            double recipSlope = xDist/(double)yDist;
//            for (int y=player.getPos()[1];y<player.getPos()[1]+yDist;y++) {
//                int x = (int) Math.max(Math.min(Math.floor(y*recipSlope+player.getPos()[0]), board.NUM_COLS-1), 1);
//                if (board.getPos(x, y)=='x'||board.getPos(Math.max(Math.min(board.NUM_COLS-1,x+1), 1), y)=='x') {
//                    muffled = true;
//                    break;
//                }
//                System.out.println(x + " " + y);
//                System.out.println(Math.max(Math.min(board.NUM_COLS-1,x+1), 1) + " " + y);
//            }
//        }
//        target.setMuffled(muffled);
//    }

    /**
     * Stops this Game's sound elements.
     */
    public void stop() {
        target.stop();
    }

    /**
     * Ends this Game instance, disabling this instance and preventing proper future usage. This should be the last method ever called for this Game object.
     */
    public void end() {
        listener.cleanUp();
        target.end();
    }

    /**
     * Starts a new Game (within this same Game instance). (Re)generates this Game instance's board, player starting position, and target starting position.
     */
    public void start() {
        board = new Board(rows, cols, wallDensity);
        player = new Player(listener, board);
        initialPlayerPos=player.getPos();
        turns=0;
        target.start(board);
//        updateMuffled();
    }

    @Override
    public String toString() {
        for (double[] point : player.getVisited())
            board.setPos(point[0], point[1], '•');
        board.setPos(initialPlayerPos[0], initialPlayerPos[1]+1, 'I');
        return board.toString() + "Turns: " + turns;
    }
}
