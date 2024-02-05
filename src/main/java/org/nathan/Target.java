package org.nathan;

public class Target {
    private final AudioSource source;
    private double x, y;
    private boolean isMuffled;
    private Board board;

    public Target(String fileName, AudioListener listener, Board board) {
        source = new AudioSource();
        source.play(listener.getBuffer(fileName));
        source.setLooping(true);
        x=0;
        y=0;
        isMuffled = false;
        this.board = board;
        setRandomPos();
    }

    public void tick(double vx, double vy) {
        board.setPos(x, y, ' ');
        x+=vx;
        y+=vy;
//        source.setVelocity((float) vx, (float) vy, 0);
//        System.out.println("Target pos: (" + x + ", " + y + ")");
        source.setPosition((float) x, (float) y);
        board.setPos(x, y, 'T');
    }

    public void setRandomPos() {
        x=Math.random()*board.NUM_COLS;
        y=Math.random()*board.NUM_ROWS;
        if (board.getPos(x, y)!=' ') setRandomPos();
        else tick(0, 0);
    }

    public void setMuffled(boolean muffled) {
        if (muffled) {
            source.setPitch(-10f);
            source.setVolume(0.5f);
        } else {
            source.setPitch(1);
            source.setVolume(1);
        }
        isMuffled = muffled;
    }

    public boolean isMuffled() {
        return isMuffled;
    }

    public void end() {
        source.delete();
    }
}
