package ru.qnocks.rockpaperscissors;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class Game {

    private int compMove;

    private final Map<Integer, String> movesMap = new HashMap<>();

    private final RpsAsciiTable table = new RpsAsciiTable(movesMap, this::calcWin);

    public Game(String[] moves) {
        for (int i = 0; i < moves.length; i++) {
            this.movesMap.put(i, moves[i]);
        }
    }

    public boolean play(String input) {
        if (input.equals("0")) {
            return false;
        }
        if (input.equals("?")) {
            help();
            return false;
        }
        try {
            process(Integer.parseInt(input));
            return true;
        } catch (NumberFormatException e) {
            menu();
            return false;
        }
    }

    public String makeMove() {
        compMove = new SecureRandom().nextInt(movesMap.size());
        return movesMap.get(compMove);
    }

    public void menu() {
        System.out.println("Available moves:");
        movesMap.forEach((i, m) -> System.out.println(++i + ". " + m));
        System.out.println("0. Exit");
        System.out.println("?  Help");
    }

    public void help() {
        table.render();
    }

    private void process(int userMove) {
        int result = calcWin(userMove - 1, compMove);
        if (result > 0) {
            System.out.println("You win!");
        } else if (result < 0) {
            System.out.println("You lose!");
        } else {
            System.out.println("Draw!");
        }
        System.out.println("Your move: " + movesMap.get(userMove - 1));
        System.out.println("Computer move: " + movesMap.get(compMove));
    }

    private int calcWin(int userMove, int compMove) {
        int size = movesMap.size();
        if (userMove == compMove) {
            return 0;
        }
        if (((compMove - userMove + size) % size) <= size / 2) {
            return -1;
        }
        return 1;
    }
}
