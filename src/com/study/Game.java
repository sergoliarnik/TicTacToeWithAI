package com.study;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Game {

    private static Map<String, Integer> unsortedLeaderboard;

    public static int getDifficult() {
        return difficult;
    }

    public static void setDifficult(int difficult) {

        Game.difficult = difficult;
    }

    private static void inputLeaderboard() {
        unsortedLeaderboard = new HashMap<>();
        try (FileReader fileReader = new FileReader("result.txt");
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String s = bufferedReader.readLine();
            while (s != null) {
                List<String> tmp = Arrays.asList(s.split(": "));
                unsortedLeaderboard.put(tmp.get(0), Integer.parseInt(tmp.get(1)));
                s = bufferedReader.readLine();
            }
        } catch (IOException e) {
            System.out.println("Problem with inputting file");
        }

    }

    private static int difficult = 70;
    private static Player mainPlayer;
    private static final Scanner scanner = new Scanner(System.in);

    public static void startGame() {
        inputLeaderboard();
        System.out.println("What's your name?");
        mainPlayer = new Player(scanner.next(), 'X');
        while (true) {
            Menu.printMainMenu(mainPlayer.getName());
            int choice = Utility.inputIntInRange(1, 5);
            processChoice(choice);

        }
    }

    private static void play(Players secondPlayer) {
        Board board = new Board();
        int i = 0;
        while (!Board.isSomeoneWin(board.getMatrix())) {
            board.printBoard();
            if ((i % 2 == 0 && mainPlayer.getSymb() == 'X') || (i % 2 == 1 && mainPlayer.getSymb() == 'O')) {

                mainPlayer.makeTurn(board);
            } else {
                secondPlayer.makeTurn(board);
            }
            if (i == 8) {

                Menu.printResult("Draw");
                return;
            }

            i++;

        }
        i--;
        board.printBoard();
        if ((i % 2 == 0 && mainPlayer.getSymb() == 'X') || (i % 2 == 1 && mainPlayer.getSymb() == 'O')) {
            unsortedLeaderboard.put(mainPlayer.getName(),
                    unsortedLeaderboard.containsKey(mainPlayer.getName())?unsortedLeaderboard.get(mainPlayer.getName()) + 1 : 1);
            Menu.printResult(mainPlayer.getName() + " win");
        } else {
            unsortedLeaderboard.put(secondPlayer.getName(),
                    unsortedLeaderboard.containsKey(secondPlayer.getName())?unsortedLeaderboard.get(secondPlayer.getName()) + 1 : 1);
            Menu.printResult(secondPlayer.getName() + " win");
        }
        saveLeaderboard();
    }

    public static void processChoice(int choice) {
        switch (choice) {
            case 1:

                play(new Bot(mainPlayer.getSymb() == 'X' ? 'O' : 'X'));
                break;
            case 2:
                System.out.println("Enter opponent's name");
                play(new Player(scanner.next(), mainPlayer.getSymb() == 'X' ? 'O' : 'X'));
                break;
            case 3:
                processOptionMenu();
                break;
            case 4:
                printLeaderboard();
                break;
            case 5:
                System.out.println("Game was successfully closed");
                System.exit(0);
        }
    }

    private static void processOptionMenu() {
        while (true) {
            Menu.printOptionMenu();
            int choice = Utility.inputIntInRange(1, 6);
            switch (choice) {
                case 1:
                    System.out.println("Old name: " + mainPlayer.getName());
                    System.out.print("New name: ");
                    mainPlayer.setName(scanner.next());
                    break;
                case 2:
                    System.out.println("Old computer name: " + Bot.getsName());
                    System.out.print("New computer name: ");
                    Bot.setName(scanner.next());
                    break;
                case 3:
                    processDifficult();
                    break;
                case 4:
                    System.out.println("Current size: " + Board.getMatrixSize() + "x" + Board.getMatrixSize());
                    while (true) {
                        System.out.print("New size: ");
                        String choiceSize = scanner.next();
                        if (isLegalSize(choiceSize)) {
                            int tmp = createLegalSize(choiceSize);
                            if (tmp == 2 || tmp == 3) {
                                Board.setMatrixSize(tmp);
                                break;
                            } else if (tmp > 3) {
                                System.out.println("Single player mode works so slow for 4x4 and more size.\n" +
                                        "You sure?(Yes/No)");
                                String answ;
                                while (true) {
                                    answ = scanner.next();
                                    if ("Yes".equals(answ) || "No".equals(answ)) {
                                        break;
                                    }
                                }
                                if ("Yes".equals(answ)) {
                                    Board.setMatrixSize(tmp);
                                }
                                break;
                            }

                        }

                    }
                    break;
                case 5:
                    boolean readyFirstTurnChange = true;
                    while (readyFirstTurnChange) {
                        Menu.printFirstTurn();
                        System.out.println("Current: " + ((mainPlayer.getSymb() == 'X') ? "I" : "Opponent"));
                        int choiceFirstTurn = Utility.inputIntInRange(1, 3);
                        switch (choiceFirstTurn) {
                            case 1:
                                mainPlayer.setSymb('X');
                                break;
                            case 2:
                                mainPlayer.setSymb('O');
                                break;
                            case 3:
                                readyFirstTurnChange = false;
                                break;
                        }
                    }
                    break;
                case 6:
                    return;
            }
        }
    }

    private static int createLegalSize(String choiceSize) {
        Pattern patternForNumber = Pattern.compile("[0-9]+");
        Matcher matcherForNumber = patternForNumber.matcher(choiceSize);
        List<String> strForNumber = new ArrayList<>();
        while (matcherForNumber.find()) {
            strForNumber.add(matcherForNumber.group());
        }
        return Integer.parseInt(strForNumber.get(0));
    }

    private static boolean isLegalSize(String choiceSize) {
        Pattern pattern = Pattern.compile("[0-9]+x[0-9]+");
        Matcher matcher = pattern.matcher(choiceSize);
        List<String> str = new ArrayList<>();
        while (matcher.find()) {
            str.add(matcher.group());
        }
        Pattern patternForNumber = Pattern.compile("[0-9]+");
        Matcher matcherForNumber = patternForNumber.matcher(choiceSize);
        List<String> strForNumber = new ArrayList<>();
        while (matcherForNumber.find()) {
            strForNumber.add(matcherForNumber.group());
        }
        return str.size() == 1 && (Objects.equals(strForNumber.get(0), strForNumber.get(1)));
    }

    private static void processDifficult() {
        while (true) {
            Menu.printDifficultMenu();
            System.out.println("Current difficult: " + ((difficult == 70) ? "Easy" : (difficult == 80) ? "Medium" : (difficult == 90) ? "Hard" : (difficult == 100) ? "Impossible" : "UNKNOWN DIFFICULT"));
            int choiceDif = Utility.inputIntInRange(1, 5);
            switch (choiceDif) {
                case 1:
                    setDifficult(70);
                    break;
                case 2:
                    setDifficult(80);
                    break;
                case 3:
                    setDifficult(90);
                    break;
                case 4:
                    setDifficult(100);
                    break;
                case 5:
                    return;
            }
        }
    }

    public static void saveLeaderboard() {
        List<Pair> sortedLeaderboard = new ArrayList<>();
        for (var i : unsortedLeaderboard.keySet()) {
            sortedLeaderboard.add(new Pair(i, unsortedLeaderboard.get(i)));
        }
        sortedLeaderboard.sort((Pair a, Pair b) -> (a.b < b.b)? 1 : (a.b > b.b)? -1 : 0);

        try(FileWriter fileWriter = new FileWriter("result.txt")){
            for(var i : sortedLeaderboard){
                fileWriter.append(i.a + ": " + i.b + '\n');
            }

        }catch (IOException e){
            System.out.println("Problem with writing to file");
        }


    }

    private static void printLeaderboard() {
        List<Pair> sortedLeaderboard = new ArrayList<>();
        for (var i : unsortedLeaderboard.keySet()) {
            sortedLeaderboard.add(new Pair(i, unsortedLeaderboard.get(i)));
        }
        sortedLeaderboard.sort((Pair a, Pair b) -> (a.b < b.b)? 1 : (a.b > b.b)? -1 : 0);
        for(var i : sortedLeaderboard){
            System.out.println(i.a + ": " + i.b);
        }
    }

}
