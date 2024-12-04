package OseroApp;

public class Position {
    private int col, row;

    public Position(int col, int row) {
        this.setCol(col);
        this.setRow(row);
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setRow(int row) {
        this.row = row;
    }
}
