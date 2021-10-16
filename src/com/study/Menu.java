package com.study;


abstract public class Menu {

    public static void printMainMenu(String nameOfUser) {
        System.out.println(nameOfUser + ", welcome to TicTacToeV1");
        System.out.println("1. Single play");
        System.out.println("2. Play with friend");
        System.out.println("3. Options");
        System.out.println("4. Leaderboard");
        System.out.println("5. Exit");

    }

    public static void printResult(String res) {
        for (int i = 0; i < res.length() + 2; i++) {
            System.out.print('-');
        }
        System.out.println();
        System.out.println('|' + res + '|');

        for (int i = 0; i < res.length() + 2; i++) {
            System.out.print('-');
        }
        System.out.println();
    }

    public static void printOptionMenu() {
        System.out.println("1. Change my name");
        System.out.println("2. Change bot name");
        System.out.println("3. Change bot difficult");
        System.out.println("4. Change board size");
        System.out.println("5. Change who makes first turn");
        System.out.println("6. Back");

    }

    public static void printDifficultMenu() {
        System.out.println("Choose computer difficult:");
        System.out.println("1. Easy");
        System.out.println("2. Medium");
        System.out.println("3. Hard");
        System.out.println("4. Impossible");
        System.out.println("5. Back");
    }

    public static void printFirstTurn() {
        System.out.println("Choose who makes first turn:");
        System.out.println("1. I");
        System.out.println("2. Opponent");
        System.out.println("3. Back");
    }


}


