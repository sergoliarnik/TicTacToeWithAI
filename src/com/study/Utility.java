package com.study;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

abstract public class Utility {
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static int inputIntInRange(int a, int b) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String tmpChoice = scanner.next();
            if (isInteger(tmpChoice) && a <= Integer.parseInt(tmpChoice) && Integer.parseInt(tmpChoice) <= b) {
                return Integer.parseInt(tmpChoice);

            }
            System.out.println("Wrong input");
        }
    }

    public static List<List<Character>> cloneMatrix(List<List<Character>> matrix) {
        List<List<Character>> mtr = new ArrayList<>();
        for (int i = 0; i < matrix.size(); i++) {
            mtr.add(new ArrayList<>());
            for (int j = 0; j < matrix.size(); j++) {
                mtr.get(i).add(matrix.get(i).get(j));
            }
        }
        return mtr;
    }
}
