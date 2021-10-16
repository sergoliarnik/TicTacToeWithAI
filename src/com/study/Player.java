package com.study;

import java.util.Scanner;

public class Player implements Players {
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private char symb;

    public char getSymb() {
        return symb;
    }


    Player(String name, char symb) {
        this.name = name;
        this.symb = symb;
    }


    public void makeTurn(Board board) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(name + ": ");
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        x--;
        y--;
        if (x < 0 || x > board.getMatrix().size() || y < 0 || y > board.getMatrix().size() || board.getMatrix().get(x).get(y) != '_') {
            System.out.println("Wrong input");
            makeTurn(board);
        } else {
            board.getMatrix().get(x).set(y, symb);
        }
        board.incrementOcuppiedPlace();

    }


    public void setSymb(char symb) {
        this.symb = symb;
    }
}
