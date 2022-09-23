package pl.redbyte.Hex;

public class Orientation {

    /**
     * Hexagons are 6-sided polygons. Regular hexagons have all the sides the same length. The typical orientations for
     * hex grids are vertical columns (flat topped) and horizontal rows (pointy topped).
     * <p>
     * The difference between the two orientations is a rotation, and that causes the angles to change: flat topped
     * angles are 0°, 60°, 120°, 180°, 240°, 300° and pointy topped angles are 30°, 90°, 150°, 210°, 270°, 330°.
     * <p>
     * Note that the library use the y axis pointing down (angles increase clockwise); you may have to make some
     * adjustments if your y axis points up (angles increase counterclockwise).
     * <p>
     * There would be multiple versions of methods translating the cube coordinates to cartesian coordinates, and
     * conversely (one for each orientation). The code would be essentially the same except the numbers would be
     * different, so for this implementation the numbers has been put into the Orientation class.
     */
    public final double f0;
    public final double f1;
    public final double f2;
    public final double f3;
    public final double b0;
    public final double b1;
    public final double b2;
    public final double b3;
    public final double start_angle;

    public Orientation(double f0, double f1, double f2, double f3, double b0, double b1, double b2, double b3, double start_angle) {
        this.f0 = f0;
        this.f1 = f1;
        this.f2 = f2;
        this.f3 = f3;
        this.b0 = b0;
        this.b1 = b1;
        this.b2 = b2;
        this.b3 = b3;
        this.start_angle = start_angle;
    }
}
