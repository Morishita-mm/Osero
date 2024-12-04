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
        this.addCell(3, 4, -1);
        this.addCell(4, 3, -1);
        this.addCell(4, 4, 1);
    }

    // コマを置く
    public void addCell(int row, int col, int color) {
        this.board[col][row] = color;
        this.visBoard[col][row] = (color == 1) ? "⚫️" : "⚪︎";
    }

    // コマを置けるか判定
    public boolean canPutCell(int row, int col) {
        // 盤内かどうかの判定
        if (col < 0 || col >= 8 || row < 0 || row >= 8) {
            System.err.println("選択された位置が盤外です");
            return false;
        }

        // 駒が置かれていないかどうかの判定
        if (visBoard[col][row] != null) {
            System.err.println("すでに駒が置かれています");
            return false;
        }
        return true;
    }

    // コマをひっくり返す
    public void reverse(int row, int col) {
        // 駒が置かれていなければひっくり返さない
        if (board[col][row] == 0) {
            return;
        }
        this.board[col][row] = (this.board[col][row] == 1) ? -1 : 1;
        this.visBoard[col][row] = (this.board[col][row] == 1) ? "⚫️" : "⚪︎";
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(" |０|１|２|３|４|５|６|７").append(LINE_SEP);
        for (int i = 0; i < this.visBoard.length; i++) {
            String[] vb = visBoard[i];
            sb.append("ー".repeat(13)).append(LINE_SEP);
            String[] replacedVb = Arrays.stream(vb)
                    .map(s -> s == null ? "　" : s)
                    .toArray(String[]::new);
            sb.append(Integer.toString(i).toUpperCase() + "|").append(String.join("|", replacedVb)).append(LINE_SEP);
        }
        return sb.toString();
    }

    // コマを置く（ひっくり返すところも含める）
    public boolean putCell(int row, int col, int color) {
        if (!canPutCell(row, col)) {
            return false;
        }

        int[][] mvs = {
                { 0, 1 }, { 1, 1 }, { 1, 0 }, { 0, -1 }, { -1, -1 }, { -1, 0 }, { 1, -1 }, { -1, 1 } // 移動方向
        };
        boolean canReversed = false;

        for (int[] mv : mvs) {
            int newCol = col, newRow = row;
            ArrayList<Position> reverseList = new ArrayList<>();
            while (true) {
                newCol += mv[0];
                newRow += mv[1];

                if (newCol < 0 || newCol >= 8 || newRow < 0 || newRow >= 8) {
                    break;
                }
                if (this.board[newCol][newRow] + color == 0) {
                    reverseList.add(new Position(newCol, newRow));
                }
                if (this.board[newCol][newRow] == color) {
                    if (reverseList.size() > 0) {
                        canReversed = true;
                    }
                    for (Position pos : reverseList) {
                        this.reverse(pos.getRow(), pos.getCol());
                    }
                    break;
                }
            }
        }
        if (!canReversed) {
            System.err.println("コマをひっくり返せないためコマをおけません");
            return false;
        } else {
            this.addCell(row, col, color);
            return true;
        }
    }
}