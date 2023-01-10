package tictactoe;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class Move {
    private int index;
    private int score;

    public Move(int index, int score) {
        this.index = index;
        this.score = score;
    }

    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
}

class Game {
    private int round;
    private char[][] table;
    private String playingX;
    private String playingO;

    public Game(String playingX, String playingO) {
        this.round = 0;
        this.table = new char[][] {
                {' ',' ',' '},
                {' ',' ',' '},
                {' ',' ',' '}
        };
        this.playingX = playingX;
        this.playingO = playingO;
    }

    private String endGame(char[][] table, int round) {
        if (table[0][0] == 'X' && table[0][0] == table[0][1] && table[0][1] == table[0][2] ||
                table[1][0] == 'X' && table[1][0] == table[1][1] && table[1][1] == table[1][2] ||
                table[2][0] == 'X' && table[2][0] == table[2][1] && table[2][1] == table[2][2] ||
                table[0][0] == 'X' && table[0][0] == table[1][0] && table[1][0] == table[2][0] ||
                table[0][1] == 'X' && table[0][1] == table[1][1] && table[1][1] == table[2][1] ||
                table[0][2] == 'X' && table[0][2] == table[1][2] && table[1][2] == table[2][2] ||
                table[0][0] == 'X' && table[0][0] == table[1][1] && table[1][1] == table[2][2] ||
                table[0][2] == 'X' && table[0][2] == table[1][1] && table[1][1] == table[2][0])
            return "X wins";
        if (table[0][0] == 'O' && table[0][0] == table[0][1] && table[0][1] == table[0][2] ||
                table[1][0] == 'O' && table[1][0] == table[1][1] && table[1][1] == table[1][2] ||
                table[2][0] == 'O' && table[2][0] == table[2][1] && table[2][1] == table[2][2] ||
                table[0][0] == 'O' && table[0][0] == table[1][0] && table[1][0] == table[2][0] ||
                table[0][1] == 'O' && table[0][1] == table[1][1] && table[1][1] == table[2][1] ||
                table[0][2] == 'O' && table[0][2] == table[1][2] && table[1][2] == table[2][2] ||
                table[0][0] == 'O' && table[0][0] == table[1][1] && table[1][1] == table[2][2] ||
                table[0][2] == 'O' && table[0][2] == table[1][1] && table[1][1] == table[2][0])
            return "O wins";
        if (round == 9)
            return "Draw";
        return "Game not finished";
    }
    private ArrayList<Integer> emptyIndexes(char[][] newTable) {
        ArrayList<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (newTable[i][j] == ' ') {
                    indexes.add(3 * i + j + 1);
                }
            }
        }
        return indexes;
    }
    private Move minimax(char[][] newTable, int newRound, char whoPlays) {
        if (endGame(newTable, newRound).equals("X wins") && whoPlays == 'X' ||
                endGame(newTable, newRound).equals("O wins") && whoPlays == 'O') {
            //score = 10
            return new Move(-1, 10);
        }
        if (endGame(newTable, newRound).equals("X wins") && whoPlays == 'O' ||
                endGame(newTable, newRound).equals("O wins") && whoPlays == 'X') {
            //score = -10
            return new Move(-1, -10);
        }
        if (endGame(newTable, newRound).equals("Draw")) {
            //score = 0
            return new Move(-1, 0);
        }

        ArrayList<Integer> availableSpots = emptyIndexes(newTable);

        ArrayList<Move> moves = new ArrayList<>();
        for (int i = 0; i < availableSpots.size(); i++) {
            int index = availableSpots.get(i);
            newTable[(index - 1) / 3][(index - 1) % 3] = newRound % 2 == 0 ? 'X' : 'O';
            int score = minimax(newTable, newRound + 1, whoPlays).getScore();
            newTable[(index - 1) / 3][(index - 1) % 3] = ' ';
            moves.add(new Move(index, score));
        }

        int bestMove = -1;
        if (newRound % 2 == 0 && whoPlays == 'X' || newRound % 2 != 0 && whoPlays == 'O') {
            int bestScore = -100;
            for (int i = 0; i < moves.size(); i++) {
                if (moves.get(i).getScore() > bestScore) {
                    bestScore = moves.get(i).getScore();
                    bestMove = i;
                }
            }
        } else {
            int bestScore = 100;
            for (int i = 0; i < moves.size(); i++) {
                if (moves.get(i).getScore() < bestScore) {
                    bestScore = moves.get(i).getScore();
                    bestMove = i;
                }
            }
        }
        return moves.get(bestMove);
    }

    private void easyLevel(char whoPlays) {
        Random random = new Random();
        int x = random.nextInt(1,4);
        int y = random.nextInt(1, 4);
        while (table[x - 1][y - 1] != ' ') {
            x = random.nextInt(1, 4);
            y = random.nextInt(1, 4);
        }
        table[x - 1][y - 1] = whoPlays;
        round++;
    }
    private void mediumLevel(char whoPlays) {
        int x;
        int y;
        for (int i = 0; i < 3; i++) {
            x = i;
            y = -1;
            int nrX = 0;
            int nrO = 0;
            for (int j = 0; j < 3; j++) {
                if (table[i][j] == 'X') {
                    nrX++;
                } else if (table[i][j] == 'O') {
                    nrO++;
                } else {
                    y = j;
                }
            }
            if (nrX == 2 && nrO == 0 || nrX == 0 && nrO == 2) {
                table[x][y] = whoPlays;
                round++;
                return;
            }
        }
        for (int j = 0; j < 3; j++) {
            x = -1;
            y = j;
            int nrX = 0;
            int nrO = 0;
            for (int i = 0; i < 3; i++) {
                if (table[i][j] == 'X') {
                    nrX++;
                } else if (table[i][j] == 'O') {
                    nrO++;
                } else {
                    x = i;
                }
            }
            if (nrX == 2 && nrO == 0 || nrX == 0 && nrO == 2) {
                table[x][y] = whoPlays;
                round++;
                return;
            }
        }
        x = -1;
        y = -1;
        int nrX = 0;
        int nrO = 0;
        for (int i = 0; i < 3; i++) {
            if (table[i][i] == 'X') {
                nrX++;
            } else if (table[i][i] == 'O') {
                nrO++;
            } else {
                x = i;
                y = i;
            }
        }
        if (nrX == 2 && nrO == 0 || nrX == 0 && nrO == 2) {
            table[x][y] = whoPlays;
            round++;
            return;
        }
        x = -1;
        y = -1;
        nrX = 0;
        nrO = 0;
        for (int i = 0; i < 3; i++) {
            if (table[i][2 - i] == 'X') {
                nrX++;
            } else if (table[i][2 - i] == 'O') {
                nrO++;
            } else {
                x = i;
                y = 2 - i;
            }
        }
        if (nrX == 2 && nrO == 0 || nrX == 0 && nrO == 2) {
            table[x][y] = whoPlays;
            round++;
            return;
        }
        easyLevel(whoPlays);
    }
    private void hardLevel(char whoPlays) {
        int nextMove = minimax(table, round, whoPlays).getIndex();
        table[(nextMove - 1) / 3][(nextMove - 1) % 3] = whoPlays;
        round++;
    }

    private void computerMove(char whoPlays, String difficulty) {
        System.out.println("Making move level \"" + difficulty + "\"");
        switch (difficulty) {
            case "easy" -> easyLevel(whoPlays);
            case "medium" -> mediumLevel(whoPlays);
            case "hard" -> hardLevel(whoPlays);
        }
    }
    private void playerMove(char whoPlays) {
        int x, y;
        while (true) {
            System.out.println("Enter the coordinates: > ");
            try {
                x = Integer.parseInt(Main.scanner.next());
                y = Integer.parseInt(Main.scanner.next());
            } catch (NumberFormatException e) {
                System.out.println("You should enter numbers!");
                continue;
            }
            if (!(x >= 1 && x <= 3 && y >= 1 && y <= 3)) {
                System.out.println("Coordinates should be from 1 to 3!");
            } else if (table[x - 1][y - 1] != ' ') {
                System.out.println("This cell is occupied! Choose another one!");
            } else {
                table[x - 1][y - 1] = whoPlays;
                round++;
                break;
            }
        }
    }

    private void printTable() {
        System.out.println("---------\n" +
                "| " + table[0][0] + " " + table[0][1] + " " + table[0][2] + " |\n" +
                "| " + table[1][0] + " " + table[1][1] + " " + table[1][2] + " |\n" +
                "| " + table[2][0] + " " + table[2][1] + " " + table[2][2] + " |\n" +
                "---------");
    }

    public void startGame() {
        printTable();
        while (endGame(table, round).equals("Game not finished")) {
            if (round % 2 == 0) {
                if (playingX.equals("user")) {
                    playerMove('X');
                } else {
                    computerMove('X', playingX);
                }
            } else {
                if (playingO.equals("user")) {
                    playerMove('O');
                } else {
                    computerMove('O', playingO);
                }
            }
            printTable();
        }
        System.out.println(endGame(table, round));
    }
}

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    public static void menu() {
        String command;
        do {
            System.out.println("Input command: > ");
            String[] input = scanner.nextLine().split(" ");
            command = input[0];
            if (command.equals("start") && input.length == 3) {
                if ((input[1].equals("user") || input[1].equals("easy") || input[1].equals("medium") || input[1].equals("hard")) &&
                        (input[2].equals("user") || input[2].equals("easy") || input[2].equals("medium") || input[2].equals("hard"))) {
                    Game game = new Game(input[1], input[2]);
                    game.startGame();
                } else {
                    System.out.println("Bad parameters!");
                }
            } else if (!command.equals("exit")){
                System.out.println("Bad parameters!");
            }
        } while (!command.equals("exit"));
    }
    public static void main(String[] args) {
        menu();
    }
}