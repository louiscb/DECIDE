package decide;

public class Point {

    public final static int QUADRANT_1 = 1;
    public final static int QUADRANT_2 = 2;
    public final static int QUADRANT_3 = 3;
    public final static int QUADRANT_4 = 4;

    private final double x;
    private final double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void printPoint() {
        System.out.println("(" + this.x + "," + this.y + ")");
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public int getQuadrantNumber() {
        if (this.x >= 0 && this.y >= 0)
            return QUADRANT_1;
        if (this.x < 0 && this.y >= 0)
            return QUADRANT_2;
        if (this.x <= 0 && this.y < 0)
            return QUADRANT_3;

        // if this.x < 0 && this.y >= 0
        return QUADRANT_4;
    }

    public double distanceTo(Point b) {
        return Math.hypot(this.getX() - b.getX(), this.getY() - b.getY());
    }
}
