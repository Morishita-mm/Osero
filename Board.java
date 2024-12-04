package OseroApp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Board {
    static final String LINE_SEP = System.getProperty("line.separator");

    private int[][] board = new int[8][8];
    private String[][] visBoard = new String[8][8];

    public Board() {
        this.addCell(3, 3, 1);
        this.addCell(3, 4, 0);
        this.addCell(4, 3, 0);
        this.addCell(4, 4, 1);
    }

    public void addCell(int row, int col, int color) {
        // 盤内かどうかの判定
        if (col < 0 || col >= 8 || row < 0 || row >= 8) {
            System.err.println("選択された位置が盤外です");
            return;
        }

        // 駒が置かれていないかどうかの判定
        if (visBoard[col][row] != null) {
            System.err.println("すでに駒が置かれています");
            return;
        }

        this.board[col][row] = color;
        this.visBoard[col][row] = (color == 1) ? "⚪︎" : "⚫️";
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (String[] vb : this.visBoard) {
            sb.append("ー".repeat(12)).append(LINE_SEP);
            String[] replacedVb = Arrays.stream(vb)
                    .map(s -> s == null ? "　" : s)
                    .toArray(String[]::new);
            sb.append(String.join("|", replacedVb)).append(LINE_SEP);
        }
        return sb.toString();
    }
}
