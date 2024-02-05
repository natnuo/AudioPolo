package org.nathan;

public class Player {
    private double x, y;
    private final AudioListener listener;
    private Board board;
    private final static String WALL_EFFECT_PATH = "C:\\Users\\natha\\Coding\\Q1_2024\\MarcoPolo\\src\\main\\java\\org\\nathan\\wall_hit.wav";
    private final static String WIN_EFFECT_PATH = "C:\\Users\\natha\\Coding\\Q1_2024\\MarcoPolo\\src\\main\\java\\org\\nathan\\win.wav";

    public Player(AudioListener listener, Board board) {
        x=0;
        y=0;
        this.listener = listener;
        this.board = board;
        setRandomPos();
    }

    public void setRandomPos() {
        x=Math.random()*board.NUM_COLS;
        y=Math.random()*board.NUM_ROWS;
        if (board.getPos(x, y)!=' ') setRandomPos();
        else tick(0, 0);
    }

    public int[] getPos() {
        return new int[]{(int) x, (int) y};
    }

    public boolean tick(double vx, double vy) {
        board.setPos(x, y, ' ');
        x = Math.min(Math.max(0, x+vx), board.NUM_COLS);
        y = Math.min(Math.max(0, y+vy), board.NUM_ROWS);
        if (board.getPos(x,y)=='x') {
            AudioSource s = new AudioSource();
            s.setPosition((float)x, (float)y);
            s.setVolume(0.42f);
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
//        System.out.println("Player pos: (" + x + ", " + y + ")");
        return false;
    }
}
