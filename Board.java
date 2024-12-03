package OseroApp;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Board {
    private ArrayList<Dist> distList = new ArrayList<>();

    public Board() {
        this.distList.add(new Dist(true, 3, 3));
        this.distList.add(new Dist(false, 3, 4));
        this.distList.add(new Dist(false, 4, 3));
        this.distList.add(new Dist(true, 4, 4));
    }

    public String toString() {
        return "";
    }
}
