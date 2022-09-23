package pl.redbyte.Hex;

import java.util.ArrayList;

public class Layout {

    /**
     * In order to convert between hex coordinates and screen coordinates we define an Orientation helper class
     * <p>
     * There’s a pointy top layout and a flat top hex layout. The conversion uses a matrix as well as the inverse of the
     * matrix, so we need a way to store those. Also, for drawing the corners, pointy top starts at 30° and flat top
     * starts at 0°, so we need a place to store that too.
     * <p>
     * Library define an Orientation helper class to store these: the 2×2 forward matrix, the 2×2 inverse matrix, and
     * the starting angle
     */
    public final Orientation orientation;


    /**
     * There are only two orientations, so we make a constants for them
     */
    static public Orientation pointy = new Orientation(
            Math.sqrt(3.0),
            Math.sqrt(3.0) / 2.0,
            0.0,
            3.0 / 2.0,
            Math.sqrt(3.0) / 3.0,
            -1.0 / 3.0,
            0.0,
            2.0 / 3.0,
            0.5);
    static public Orientation flat = new Orientation(
            3.0 / 2.0,
            0.0,
            Math.sqrt(3.0) / 2.0, Math.sqrt(3.0),
            2.0 / 3.0,
            0.0,
            -1.0 / 3.0,
            Math.sqrt(3.0) / 3.0,
            0.0);


    /**
     * Separate x size and y size. That allows two things:
     * <p>
     * 1. Allows stretching and squashing the hexagon to match whatever size pixel art you have. Note that size.x and
     * size.y are not the width and height of the hexagons.
     * <p>
     * 2. Allows using a negative value for the y size to flip the y axis.
     */
    public final Point size;


    /**
     * By default hexagon (0, 0, 0) is centered at x=0,y=0, but in general, there may be a need to center it anywhere.
     * In such a case just add the center (layout.origin) to the result.
     */
    public final Point origin;

    /**
     * Layout constructor.
     *
     * @param orientation object that stores 2×2 forward translation matrix, the 2×2 inverse translation matrix, and the
     *                    starting angle.
     * @param size        Separate x size and y size. That allows to stretching and squashing the hexagon to match
     *                    whatever size pixel art you have. Note that size.x and size.y are not the width and height of
     *                    the hexagons. Allows using a negative value for the y size to flip the y axis.
     * @param origin      orgin coordinates of the (0,0,0) hexagon
     * @return {@code Layout} object
     */
    public Layout(Orientation orientation, Point size, Point origin) {
        this.orientation = orientation;
        this.size = size;
        this.origin = origin;
    }


    /**
     * Returns center point of the given hexagon.
     *
     * @param hexagon the Hex object
     * @return {@code Point} object that represents center point of the hexagon
     */
    public Point hexToPixel(Hex hexagon) {
        Orientation M = orientation;
        double x = (M.f0 * hexagon.q + M.f1 * hexagon.r) * size.x;
        double y = (M.f2 * hexagon.q + M.f3 * hexagon.r) * size.y;
        return new Point(x + origin.x, y + origin.y);
    }

    /**
     * Returns hexagon that contains given point.
     *
     * @param point the point on the grid
     * @return {@code Hex} that contains given point.
     */
    public Hex pixelToHex(Point point) {
        FractionalHex fractionalHex = pixelToFractionalHex(point);
        return fractionalHex.hexRound();
    }

    /**
     * Returns fractional hexagon that contains given point.
     *
     * @param point the point on the grid
     * @return {@code FractionalHex} that contains given point.
     */
    public FractionalHex pixelToFractionalHex(Point point) {
        Orientation M = orientation;
        Point pt = new Point((point.x - origin.x) / size.x, (point.y - origin.y) / size.y);
        double q = M.b0 * pt.x + M.b1 * pt.y;
        double r = M.b2 * pt.x + M.b3 * pt.y;
        return new FractionalHex(q, r, -q - r);
    }

    /**
     * Returns array of hexagon corner's coordinates.
     *
     * @param hex the hexagon
     * @return list of hexagon corner's coordinates.
     */
    public ArrayList<Point> polygonCorners(Hex hex) {
        ArrayList<Point> corners = new ArrayList<Point>() {{
        }};
        Point center = hexToPixel(hex);
        for (int i = 0; i < 6; i++) {
            Point offset = hexCornerOffset(i);
            corners.add(new Point(center.x + offset.x, center.y + offset.y));
        }
        return corners;
    }

    /**
     * Helper method for {@code polygonCorners} return particular corner
     *
     * @param corner corner index
     * @return hexagon corner coordinates.
     */
    public Point hexCornerOffset(int corner) {
        Orientation M = orientation;
        double angle = 2.0 * Math.PI * (M.start_angle - corner) / 6.0;
        return new Point(size.x * Math.cos(angle), size.y * Math.sin(angle));
    }

}
