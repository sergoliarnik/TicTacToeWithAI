package com.study;

import java.util.*;

public class Bot implements Players {
    private static String name = "Computer";
    private char symb;
    private Random random = new Random(new Date().getTime());

    public String getName() {
        return name;
    }

    public static String getsName() {
        return name;
    }

    Bot(char symb) {
        this.symb = symb;
    }

    public static void setName(String name) {
        Bot.name = name;
    }

    public void makeTurn(Board board) {

        List<Integer> coords = computerThink(symb, board);
        System.out.println(name + ": " + (coords.get(0) + 1) + " " + (coords.get(1) + 1));
        board.getMatrix().get(coords.get(0)).set(coords.get(1), symb);
        board.incrementOcuppiedPlace();
    }

    private List<Integer> computerThink(char symb, Board board) {
        List<Integer> bestMove = new ArrayList<>();
        int mxScore = -99;
        for (int i = 0; i < board.getMatrix().size(); i++) {
            for (int j = 0; j < board.getMatrix().size(); j++) {
                if (board.getMatrix().get(i).get(j) == '_') {
                    var cloneMtr = Utility.cloneMatrix(board.getMatrix());
                    cloneMtr.get(i).set(j, symb);
                    int score = minimax(cloneMtr, 0, symb);
                    if (score >= mxScore) {
                        mxScore = score;
                        bestMove = Arrays.asList(i, j);
                    }
                }
            }
        }
        return bestMove;
    }

    private int minimax(List<List<Character>> matrix, int depth, Character symb) {
        boolean stateOfWinning = Board.isSomeoneWin(matrix);
        boolean isDraw = Board.checkDraw(matrix);
        if (stateOfWinning) {
            if (depth % 2 == 0) {

                return 1;
            } else {

                return -1;
            }
        } else if (isDraw) {

            return 0;
        }
        int mxScore = -100000;
        int mnScore = 100000;
        symb = (symb == 'O') ? 'X' : 'O';
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.size(); j++) {
                if (matrix.get(i).get(j) == '_') {
                    var cloneMtr = Utility.cloneMatrix(matrix);
                    cloneMtr.get(i).set(j, symb);
                    int score = minimax(cloneMtr, depth + 1, symb);
                    if (depth % 2 == 1) {


                        if (mxScore != Math.max(mxScore, score)) {
                            if (random.nextInt(10) < Game.getDifficult() / 10) {
                                mxScore = Math.max(mxScore, score);
                            }
                        }

                    } else {
                        if (mnScore != Math.min(mnScore, score)) {
                            if (random.nextInt(10) < Game.getDifficult() / 10) {
                                mnScore = Math.min(mnScore, score);
                            }
                        }
                    }
                }
            }
        }
        int res = depth % 2 == 1 ? mxScore : mnScore;

        return res;
    }
}
