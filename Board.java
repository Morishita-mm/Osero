package OseroApp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Board {
    static final String LINE_SEP = System.getProperty("line.separator");

    private int[][] board = new int[8][8];
    private String[][] visBoard = new String[8][8];
    private int whiteNum = 0;
    private int blackNum = 0;

    private Position[][][] reverseList = new Position[8][8][27];
    private int[][] revCnts = new int[8][8];

    public Board() {
        this.addCell(3, 3, 1);
        this.addCell(3, 4, -1);
        this.addCell(4, 3, -1);
        this.addCell(4, 4, 1);
        this.checkReverse(-1);
        this.updateVisual();
        this.setNums();
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
        if (board[col][row] != 0) {
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
        if (this.revCnts[col][row] > 0) {
            Position[] posList = this.reverseList[col][row];
            for (int i = 0; i < revCnts[col][row]; i++) {
                reverse(posList[i].getRow(), posList[i].getCol());
            }
            addCell(row, col, color);
            return true;
        }
        System.err.println("ひっくり返せないところには置けません");
        return false;
    }

    public void updateVisual() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (revCnts[i][j] <= 0) {
                    this.visBoard[i][j] = null;
                } else {
                    this.visBoard[i][j] = "✅";
                }

                switch (board[i][j]) {
                    case 1:
                        this.visBoard[i][j] = "⚫️";
                        break;
                    case -1:
                        this.visBoard[i][j] = "⚪︎";
                }

            }
        }
    }

    public void checkReverse(int color) {
        int[][] mvs = {
                { 0, 1 }, { 1, 1 }, { 1, 0 }, { 0, -1 }, { -1, -1 }, { -1, 0 }, { 1, -1 }, { -1, 1 } // 移動方向
        };

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int revCnt = 0; // そのますにおいた時にひっくり返せるコマの数
                for (int[] mv : mvs) {
                    int newCol = i, newRow = j;
                    ArrayList<Position> revList = new ArrayList<>();
                    while (true) {
                        newCol += mv[0];
                        newRow += mv[1];
                        if (newCol < 0 || newCol >= 8 || newRow < 0 || newRow >= 8 || board[newCol][newRow] == 0) {
                            break;
                        }
                        if (this.board[newCol][newRow] + color == 0) {
                            revList.add(new Position(newCol, newRow));
                        }
                        if (this.board[newCol][newRow] == color) {
                            for (Position pos : revList) {
                                this.reverseList[i][j][revCnt] = pos;
                                revCnt++;
                            }
                            break;
                        }
                    }
                }
                this.revCnts[i][j] = revCnt;
            }
        }
    }

    // それぞれの色のコマの数を数え上げる
    public void setNums() {
        // nums[ blackNum, whiteNum]
        int[] nums = { 0, 0 };
        for (int[] rows : this.board) {
            for (int row : rows) {
                if (row == 1) {
                    nums[0]++;
                } else if (row == -1) {
                    nums[1]++;
                }
            }
        }
        this.blackNum = nums[0];
        this.whiteNum = nums[1];
    }

    public boolean isGameEnd() {
        if (this.whiteNum == 0 || this.blackNum == 0 || this.blackNum + this.whiteNum == 64) {
            return true;
        }
        return false;
    }

    public int checkWinner() {
        if (this.blackNum == this.whiteNum) {
            return 0;
        }
        return (this.blackNum > this.whiteNum) ? 1 : -1;
    }

    public void showWinner() {
        int winner = checkWinner();
        switch (winner) {
            case 0:
                System.out.println("Draw");
                break;
            case 1:
                System.out.println("⚫️ WIN!!!");
                break;
            case -1:
                System.out.println("⚪︎ WIN!!!");
                break;
            default:
                System.out.println("Error");
                break;
        }
        System.out.printf("Result: ⚪︎ %d : %d ⚫️\n", whiteNum, blackNum);
    }

    public int getBlackNum() {
        return blackNum;
    }

    public int getWhiteNum() {
        return whiteNum;
    }

}