package com.study;

import java.util.LinkedList;
import java.util.List;

public class Board {
    @Override
    public String toString() {
        return "Board{" +
                "matrix=" + matrix +
                ", board=" + board +
                ", occupiedPlace=" + occupiedPlace +
                '}';
    }

    private static int matrixSize = 3;
    private List<List<Character>> matrix;
    private List<List<Character>> board;
    private int occupiedPlace = 0;

    public void incrementOcuppiedPlace() {
        occupiedPlace++;
    }

    public static int getMatrixSize() {
        return matrixSize;
    }

    public static void setMatrixSize(int matrixSize) {
        Board.matrixSize = matrixSize;
    }

    public List<List<Character>> getMatrix() {
        return matrix;
    }

    public Board() {
        matrix = createEmptyMatrix();
        board = creatBoard();
    }

    private List<List<Character>> createEmptyMatrix() {
        List<List<Character>> mtr = new LinkedList<>();
        for (int i = 0; i < matrixSize; i++) {
            mtr.add(new LinkedList<>());
            for (int j = 0; j < matrixSize; j++) {
                mtr.get(i).add('_');
            }
        }
        return mtr;
    }

    private List<List<Character>> creatBoard() {
        List<List<Character>> brd = new LinkedList<>();
        for (int i = 0; i < matrix.size() + 2; i++) {
            brd.add(new LinkedList<>());
            for (int j = 0; j < 2 * matrix.size() + 3; j++) {
                if (i == 0 || i == matrix.size() + 1) brd.get(i).add('-');
                else if (j == 0 || j == 2 * matrix.size() + 2) brd.get(i).add('|');
                else if (j % 2 == 1) brd.get(i).add(' ');
                else brd.get(i).add(matrix.get(i - 1).get(j / 2 - 1));
            }
        }
        return brd;
    }

    public void printBoard() {
        board = creatBoard();
        for (var i : board) {
            for (var j : i) {
                System.out.print(j);
            }
            System.out.println();
        }
    }

    public static boolean isSomeoneWin(List<List<Character>> matrix) {

        if (checkWinHorizontally(matrix) || checkWinVertically(matrix) || checkWinDiagonals(matrix)) {

            return true;
        }
        return false;
    }


    public static boolean checkDraw(List<List<Character>> matrix) {
        for (var i : matrix) {
            for (var j : i) {
                if (j == '_') return false;
            }
        }
        return true;
    }

    private static boolean checkWinHorizontally(List<List<Character>> matrix) {
        for (int i = 0; i < matrix.size(); i++) {
            boolean check = true;
            for (int j = 1; j < matrix.size(); j++) {

                if (matrix.get(i).get(j - 1) == '_' || matrix.get(i).get(j) != matrix.get(i).get(j - 1)) {
                    check = false;
                    break;
                }
            }
            if (check) return true;
        }
        return false;
    }

    private static boolean checkWinVertically(List<List<Character>> matrix) {
        for (int i = 0; i < matrix.size(); i++) {
            boolean check = true;
            for (int j = 1; j < matrix.size(); j++) {
                if (matrix.get(j - 1).get(i) == '_' || matrix.get(j).get(i) != matrix.get(j - 1).get(i)) {
                    check = false;
                    break;
                }
            }
            if (check) return true;
        }
        return false;
    }

    private static boolean checkWinDiagonals(List<List<Character>> matrix) {
        boolean check = true;
        for (int i = 1; i < matrix.size(); i++) {
            if (matrix.get(i - 1).get(i - 1) == '_' || matrix.get(i).get(i) != matrix.get(i - 1).get(i - 1)) {
                check = false;
                break;
            }
        }
        boolean check1 = true;
        for (int i = 1; i < matrix.size(); i++) {
            if (matrix.get(i - 1).get(matrix.size() - i) == '_' ||
                    matrix.get(i).get(matrix.size() - i - 1) != matrix.get(i - 1).get(matrix.size() - i)) {
                check1 = false;
                break;
            }
        }
        return check || check1;
    }


}
