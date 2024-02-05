package org.nathan;

public class Game {
    private Board board;
    private Player player;
    private int[] initialPlayerPos;
    private Target target;
    AudioListener listener;
    private int turns;

    private static final String TARGET_AUDIO = "C:\\Users\\natha\\Coding\\Q1_2024\\MarcoPolo\\src\\main\\java\\org\\nathan\\bounce.wav";


    public Game() {
        board = new Board(8, 8, 0.1);
        listener = new AudioListener();
        player = new Player(listener, board);
        initialPlayerPos=player.getPos();
        target = new Target(TARGET_AUDIO, listener, board);
        turns = 0;
    }

    public boolean tick(char c) {
        int px=0, py=0;
        switch (c) {
            case 'w' -> py=1;
            case 'a' -> px=-1;
            case 's' -> py=-1;
            case 'd' -> px=1;
        }
        boolean res = player.tick(px, py);
        turns++;
        target.tick(0, 0);
        if (c=='u') target.setMuffled(!target.isMuffled());
        System.out.println(board);
        return res;
    }

    public void end() {
        target.end();
        listener.cleanUp();
    }

    public String toString() {
        board.setPos(initialPlayerPos[0], initialPlayerPos[1], 'I');
        return board.toString();
    }
}
