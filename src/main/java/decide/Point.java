package decide;

public class Point {

    public final static int QUADRANT_1 = 1;
    public final static int QUADRANT_2 = 2;
    public final static int QUADRANT_3 = 3;
    public final static int QUADRANT_4 = 4;

    private final double x;
    private final double y;

    /**
    * Class constructor, specifying the x and y  coordinates.
    *
    * @param x a x coordinate
    * @param y a y coordinate
    */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
    * Prints the x and y coordinates of this point. 
    */
    public void printPoint() {
        System.out.println("(" + this.x + "," + this.y + ")");
    }

    /**
    * Returns this points x coordinate
    *
    * @return the x coordinate 
    */
    public double getX() {
        return this.x;
    }

    /**
    * Returns this points y coordinate
    *
    * @return the y coordinate 
    */
    public double getY() {
        return this.y;
    }

    /**
    * Returns which quadrant the point lies in.
    *
    * @return the quadrant number.
    */
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

    /**
    * Returns the euclidean distance from this point to point b.
    *
    * @param b a Point object 
    * @return the distance between this point and point b.
    */
    public double distanceTo(Point b) {
        return Math.hypot(this.getX() - b.getX(), this.getY() - b.getY());
    }
}
