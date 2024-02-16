package org.nathan;

import java.io.IOException;
import java.util.Scanner;

/**
 * Runs functions relating to the Marco Polo Audio Game.
 * @author (Nathan Tao)
 * @version (2/16/2024)
 */
public class Main {
    /**
     * Runs the program loop, reads from standard input and prints to standard output
     * COMMANDS:
     * -- OUTSIDE OF GAME --
     * q + enter: quit (end program)
     * b + enter: print last game board
     * [any other char] + enter: start game
     * -- IN GAME --
     * {w/a/s/d} + enter: move character
     * b + enter: show board
     */
    private static void repl() {
        String cmd = "";
        Scanner s = new Scanner(System.in);
        Game g = new Game(8, 8, 0.1);
        while (!cmd.equals("q") && !cmd.equals("quit")) {
            if (cmd.equals("b") || cmd.equals("board")) {
                System.out.println(g);
            } else {
                g.start();
                char c = ' ';
                do {
                    String n = s.nextLine();
                    c=n.length()>0?n.charAt(0):' ';
                } while (!g.tick(c));
                g.stop();
            }
            cmd = s.nextLine();
        }
        g.end();
    }

    public static void main(String[] args) {
        System.out.println("Marco Polo v. 1.0.0");
        repl();
    }
}
