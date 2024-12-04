package OseroApp;

import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        System.out.println("Game Start!");
        Board board = new Board();
        try (Scanner sc = new java.util.Scanner(System.in)) {
            int color = 1;
            int col;
            int row;

            while (true) {
                color = (color == 1) ? 0 : 1;
                System.out.print("Colum > ");
                col = sc.nextInt();
                System.out.print("Row > ");
                row = sc.nextInt();

                board.addCell(row, col, color);
                System.out.println(board);
            }
        }
    }
}