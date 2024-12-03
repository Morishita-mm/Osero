package OseroApp;

public class Dist {
    private boolean isBlack;
    private String visual;
    private int h;
    private int w;

    public Dist(boolean isBlack, int h, int w) {
        setIsBlack(isBlack);
        setVisual();
        setH(h);
        setW(w);
    }

    public void setIsBlack(boolean isBlack) {
        this.isBlack = isBlack;
    }

    public boolean getIsBlack() {
        return this.isBlack;
    }

    public void setVisual() {
        if (isBlack) {
            this.visual = "⚫️";
        } else {
            this.visual = "⚪︎";
        }
    }

    public String getVisual() {
        return visual;
    }

    public boolean reverse() {
        return !this.isBlack;
    }

    public void setH(int h) {
        this.h = h;
    }

    public void setW(int w) {
        this.w = w;
    }
}