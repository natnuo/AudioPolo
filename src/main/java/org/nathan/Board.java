package org.nathan;

import java.util.HashSet;
import java.util.Set;

public class Board {
    private char[][] board;

    public final int NUM_ROWS;
    public final int NUM_COLS;

    /**
     * precondition: (rows-2)*(cols-2)>=1+(number of targets)
     * @param rows
     * @param cols
     * @param wallDensity
     */
    public Board(int rows, int cols, double wallDensity) {
        board = generate(rows, cols, wallDensity);
        NUM_ROWS = rows;
        NUM_COLS = cols;
    }

    private char[][] generate(int rows, int cols, double wallDensity) {
        wallDensity = Math.max(Math.min(wallDensity, 0.8), 0);
        int totalOpens = 0;
        int firstOpenX=-1,firstOpenY=-1;
        char[][] b = new char[rows][cols];
        // generate random walls
        for (int y=1;y<rows;y++) {
            for (int x=1;x<cols;x++) {
                if (Math.random() <= wallDensity) {
                    b[y][x] = 'x';
                } else {
                    b[y][x] = ' ';
                    if (totalOpens==0) {
                        firstOpenX=x;
                        firstOpenY=y;
                    }
                    totalOpens++;
                }
            }
        }
        // generate outside border
        for (int y=0;y<rows;y++) {
            if (b[y][0]==' ') totalOpens--;
            if (b[y][cols-1]==' ') totalOpens--;
            b[y][0] = 'x';
            b[y][cols-1] = 'x';
        }
        for (int x=0;x<cols;x++) {
            if (b[0][x]==' ') totalOpens--;
            if (b[rows-1][x]==' ') totalOpens--;
            b[0][x] = 'x';
            b[rows-1][x] = 'x';
        }
        // ensure all open spaces are reachable
        if (getReachable(firstOpenX, firstOpenY, b).size() == totalOpens) return b;
        return generate(rows, cols, wallDensity);  // regenerate if not all open spaces are reachable
    }

    private Set<Integer> getReachable(int originX, int originY, char[][] b) {
        return getReachable(originX, originY, b, new HashSet<>());
    }

    private Set<Integer> getReachable(int originX, int originY, char[][] b, Set<Integer> context) {
        context.add(originX*b.length+originY);
        int[][] diffs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        for (int[] d : diffs) {
            int newX = originX + d[0];
            int newY = originY + d[1];
            if ((newY<b.length-1&&newY>=1)&&(newX<b[0].length-1&&newX>=1)&&!context.contains(newX*b.length+newY)&&b[newY][newX]==' ') {
                context=getReachable(newX, newY, b, context);
            }
        }
        return context;
    }

    public char getPos(double x, double y) {
        return board[(int)(board.length-y-1)][(int)x];
    }

    public void setPos(double x, double y, char v) {
        board[(int)(board.length-y-1)][(int)x]=v;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (char[] row : board) {
            for (char v : row) {
                s.append(v).append(" ");
            }
            s.append("\n");
        }
        return s.toString();
    }
}
