package OseroApp;

public class main {
    public static void main(String[] args) {
        System.out.println("Game Start!");
        Board board = new Board();
        boolean isBlack = true;

        for (int i = 0; i < 64; i++) {
            if ((i % 2) == 0) {
                isBlack = true;
            } else {
                isBlack = false;
            }
        }
        System.out.println(board);
    }
}