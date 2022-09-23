package pl.redbyte.Hex;

public class DoubledCoord {

    /**
     * The names q and r are used for cube/axial coordinates, and col and row for doubled coordinates.
     */
    public final int col;
    public final int row;


    /**
     * Doubled Coordinates constructor.
     *
     * @param col column index
     * @param row row index
     * @return doubled coordinates
     */
    public DoubledCoord(int col, int row)
    {
        this.col = col;
        this.row = row;
    }


    /**
     * Convert flat top Hex coordinates (cube coordinates) to doubled coordinates
     *
     * @param hex    hexagon that stores coordinates in cube notation
     * @return doubled coordinates
     */
    static public DoubledCoord flatTopDoubledFromCube(Hex hex)
    {
        int col = hex.q;
        int row = 2 * hex.r + hex.q;
        return new DoubledCoord(col, row);
    }

    /**
     * Convert flat top hexagon's doubled coordinates to {@code Hex} (that holds cube coordinates)
     *
     * @param coords    doubled coordinates
     * @return {@code Hex} object
     */
    static public Hex flatTopDoubledToCube(DoubledCoord coords)
    {
        int q = coords.col;
        int r = (int)((coords.row - coords.col) / 2);
        int s = -q - r;
        return new Hex(q, r, s);
    }


    /**
     * Convert pointy top Hex coordinates (cube coordinates) to doubled coordinates
     *
     * @param hex    hexagon that stores coordinates in cube notation
     * @return doubled coordinates
     */
    static public DoubledCoord pointyTopDoubledFromCube(Hex hex)
    {
        int col = 2 * hex.q + hex.r;
        int row = hex.r;
        return new DoubledCoord(col, row);
    }


    /**
     * Convert pointy top hexagon's doubled coordinates to {@code Hex} (that holds cube coordinates)
     *
     * @param coords    doubled coordinates
     * @return {@code Hex} object
     */
    static public Hex pointyTopDoubledToCube(DoubledCoord coords)
    {
        int q = (int)((coords.col - coords.row) / 2);
        int r = coords.row;
        int s = -q - r;
        return new Hex(q, r, s);
    }

}
