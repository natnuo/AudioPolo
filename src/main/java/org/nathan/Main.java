package org.nathan;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static void repl() {
        String cmd = "";
        Scanner s = new Scanner(System.in);
        Game g = new Game();
        while (!cmd.equals("q") && !cmd.equals("quit")) {
            if (cmd.equals("b") || cmd.equals("board")) {
                System.out.println(g);
            } else {
                g = new Game();
                char c = ' ';
                do {
                    String n = s.nextLine();
                    c=n.length()>0?n.charAt(0):' ';
                } while (!g.tick(c));
                g.end();
            }
            cmd = s.nextLine();
        }
    }

    public static void main(String[] args) {
        System.out.println("Marco Polo v. 1.0.0");
        repl();
    }
}
