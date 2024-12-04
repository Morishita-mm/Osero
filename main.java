package OseroApp;

import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        System.out.println("Game Start!");
        Board board = new Board();
        System.out.println(board);
        try (Scanner sc = new java.util.Scanner(System.in)) {
            int color = -1, col, row;

            while (true) {
                System.out.println((color == 1) ? "⚫️ Turn" : "⚪️ Turn");
                System.out.printf("⚪︎ %d : %d ⚫️\n", board.getWhiteNum(), board.getBlackNum());
                System.out.print("Colum > ");
                col = sc.nextInt();
                System.out.print("Row > ");
                row = sc.nextInt();

                if (board.putCell(row, col, color)) {
                    color = (color == 1) ? -1 : 1;
                    // board.reverse(row, col);
                    board.checkReverse(color);
                    board.updateVisual();
                    System.out.println(board);
                    if (board.isGameEnd()) {
                        board.showWinner();
                        break;
                    }
                }
            }
        }
    }
}