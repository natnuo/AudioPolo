package org.nathan;

/**
 * Contains functionality for a target that plays audio in the Marco Polo Audio Game.
 * @author (Nathan Tao)
 * @version (2/16/2024)
 */
public class Target {
    private final AudioSource source;
    private double x, y;
    private boolean isMuffled;
    private Board board;
    private int buffer;

    /**
     * Creates a new Target instance which plays a .wav audio of the specified file.
     * @param fileName a String representing the file path to a .wav audio file that this Target instance will play.
     * @param listener the AudioListener associated with the player which will be "listening" to this Target's AudioSource.
     */
    public Target(String fileName, AudioListener listener) {
        source = new AudioSource();
        buffer = listener.getBuffer(fileName);
        source.setLooping(true);
    }

    /**
     * Runs one tick for this Target instance.
     * @param vx the change in x (relative to the board) position for this Target instance over this tick
     * @param vy the change in y (relative to the board) position for this Target instance over this tick
     */
    public void tick(double vx, double vy) {
        board.setPos(x, y, ' ');
        x+=vx;
        y+=vy;
//        source.setVelocity((float) vx, (float) vy, 0);
//        System.out.println("Target pos: (" + x + ", " + y + ")");
        source.setPosition((float) x, (float) y);
        board.setPos(x, y, 'T');
    }

    /**
     * Sets the position of the Target instance to a random empty tile.
     */
    public void setRandomPos() {
        x=Math.random()*board.NUM_COLS;
        y=Math.random()*board.NUM_ROWS;
        if (board.getPos(x, y)!=' ') setRandomPos();
        else tick(0, 0);
    }

    /**
     * Returns the position of this Target instance.
     * @returns an array of two integers [x, y] representing this Player's x and y coordinates.
     */
    public int[] getPos() {
        return new int[]{(int) x, (int) y};
    }

    /**
     * Sets the quality of this Target instance's Audio Source's output between muffled or clear.
     * @param muffled whether to set the quality of this Target instance's Audio Source's output to muffled (true), or to clear (false)
     */
    public void setMuffled(boolean muffled) {
        if (muffled) {
            source.setPitch(-50f);
            source.setVolume(1f);
        } else {
            source.setPitch(1);
            source.setVolume(1);
        }
        isMuffled = muffled;
    }

    /**
     * Returns whether the quality of this Target instance's Audio Source is muffled.
     * @return true if this Target instance's Audio Source is muffled, false otherwise
     */
    public boolean isMuffled() {
        return isMuffled;
    }

    /**
     * Runs start functionality for the Target instance, including choosing a pseudorandom start position and starting to play from the AudioSource.
     * @param board a Board instance representing the Board of the Game this Target instance is associated with.
     */
    public void start(Board board) {
        x=0;
        y=0;
        isMuffled = false;
        this.board = board;
        setRandomPos();
        source.play(buffer);
    }

    /**
     * Stops sound from this Target.
     */
    public void stop() {
        source.stop();
    }

    /**
     * Ends this Target instance, disabling this instance and preventing proper future usage. This should be the last method ever called for this Target object.
     */
    public void end() {
//        System.out.println("LOG: TARGET SOURCE DELETING");
        source.stop();
        source.delete();
    }
}
