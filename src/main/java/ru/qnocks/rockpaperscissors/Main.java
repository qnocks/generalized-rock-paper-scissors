package ru.qnocks.rockpaperscissors;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        if (!validateArgs(args)) {
            System.out.println("Input moves must be odd, greater then 1 and doesn't contain duplicates\n" +
                    "Like so: Rock Paper Scissors");
            return;
        }

        var scanner = new Scanner(System.in);
        var hmacGenerator = new HmacGenerator();
        var game = new Game(args);

        String compMove = game.makeMove();
        System.out.println("HMAC:\n" + hmacGenerator.getHmac(compMove));

        game.menu();
        String input = scanner.nextLine();

        boolean isPlayed = game.play(input);
        if (isPlayed) {
            System.out.println("HMAC key: " + hmacGenerator.getKey());
        }
    }

    public static boolean validateArgs(String[] args) {
        if (args.length == 1 || args.length % 2 == 0)
            return false;
        return new HashSet<>(Arrays.asList(args)).size() == args.length;
    }
}