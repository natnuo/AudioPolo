package org.nathan;

public class Game {
    private Board board;
    private Player player;
    private int[] initialPlayerPos;
    private Target target;
    private AudioListener listener;
    private int turns;
    // TODO: CREATE SET OF VISITED TILES FOR LOG AFTER GAME END

    private static final String TARGET_AUDIO = "C:\\Users\\natha\\Coding\\Q1_2024\\MarcoPolo\\src\\main\\java\\org\\nathan\\bounce.wav";


    public Game() {
        listener = new AudioListener();
        target = new Target(TARGET_AUDIO, listener);
        start();
    }

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

        boolean muffled = false;
        int xDist = player.getPos()[0]-target.getPos()[0];
        double slope = (player.getPos()[1]-target.getPos()[1])/(double)xDist;
        for (int x=player.getPos()[0];x<player.getPos()[0]+xDist;x++) {
            int y = (int) Math.max(Math.min(Math.floor(x*slope+player.getPos()[1]), board.NUM_ROWS-1), board.NUM_COLS-1);
            if (board.getPos(x, y)=='x'||board.getPos(x, Math.min(board.NUM_ROWS-1,y+1))=='x') {
                muffled = true;
                break;
            }
        }
        target.setMuffled(muffled);
//        System.out.println(board);
        return res;
    }

    public void stop() {
        target.stop();
    }

    public void end() {
        listener.cleanUp();
        target.end();
    }

    public void start() {
        board = new Board(8, 8, 0.1);
        player = new Player(listener, board);
        initialPlayerPos=player.getPos();
        turns=0;
        target.start(board);
    }

    public String toString() {
        board.setPos(initialPlayerPos[0], initialPlayerPos[1]+1, 'I');
        return board.toString();
    }
}
