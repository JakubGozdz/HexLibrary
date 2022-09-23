package pl.redbyte.Hex;

public class OffsetCoord {

    /**
     * We use the names q and r for cube/axial coordinates, and col and row for offset coordinates.
     */
    public final int col;
    public final int row;


    /**
     * There are four offset types: odd-r, even-r, odd-q, even-q. The “r” types are used with pointy top hexagons and
     * the “q” types are used with flat top. Whether it’s even or odd can be encoded as an offset direction +1 or -1.
     * For pointy top, the offset direction tells us whether to slide alternate rows right or left. For flat top, the
     * offset direction tells us whether to slide alternate columns up or down.
     */
    static public int EVEN = 1;
    static public int ODD = -1;


    /**
     * Offset Coordinates constructor.
     *
     * @param col column index
     * @param row row index
     * @return offset coordinates
     */
    public OffsetCoord(int col, int row) {
        this.col = col;
        this.row = row;
    }


    /**
     * Convert flat top hexagon's coordinates (cube coordinates) to offset coordinates
     *
     * @param offset offset type can be encoded as an offset direction +1 or -1. For pointy top, the offset direction
     *               tells us whether to slide alternate rows right or left. For flat top, the offset direction tells us
     *               whether to slide alternate columns up or down
     * @param hex    hexagon that stores coordinates in cube notation
     * @return offset coordinates
     * @throws IllegalArgumentException when offset != - 1 && offset != +1.
     */
    static public OffsetCoord flatTopOffsetFromCube(int offset, Hex hex) {
        int col = hex.q;
        int row = hex.r + (int) ((hex.q + offset * (hex.q & 1)) / 2);
        if (offset != OffsetCoord.EVEN && offset != OffsetCoord.ODD) {
            throw new IllegalArgumentException("offset must be EVEN (+1) or ODD (-1)");
        }
        return new OffsetCoord(col, row);
    }


    /**
     * Convetr flat top hexagon's offset coordinates to {@code Hex} (that holds cube coordinates)
     *
     * @param offset offset type can be encoded as an offset direction +1 or -1. For pointy top, the offset direction
     *               tells us whether to slide alternate rows right or left. For flat top, the offset direction tells us
     *               whether to slide alternate columns up or down
     * @param coords    offset coordinates
     * @return {@code Hex} object
     * @throws IllegalArgumentException when offset != - 1 && offset != +1.
     */
    static public Hex flatTopOffsetToCube(int offset, OffsetCoord coords) {
        int q = coords.col;
        int r = coords.row - (int) ((coords.col + offset * (coords.col & 1)) / 2);
        int s = -q - r;
        if (offset != OffsetCoord.EVEN && offset != OffsetCoord.ODD) {
            throw new IllegalArgumentException("offset must be EVEN (+1) or ODD (-1)");
        }
        return new Hex(q, r, s);
    }


    /**
     * Convert pointy top hexagon's coordinates (cube coordinates) to offset coordinates
     *
     * @param offset offset type can be encoded as an offset direction +1 or -1. For pointy top, the offset direction
     *               tells us whether to slide alternate rows right or left. For flat top, the offset direction tells us
     *               whether to slide alternate columns up or down
     * @param hex    hexagon that stores coordinates in cube notation
     * @return offset coordinates
     * @throws IllegalArgumentException when offset != - 1 && offset != +1.
     */
    static public OffsetCoord pointyTopOffsetFromCube(int offset, Hex hex) {
        int col = hex.q + (int) ((hex.r + offset * (hex.r & 1)) / 2);
        int row = hex.r;
        if (offset != OffsetCoord.EVEN && offset != OffsetCoord.ODD) {
            throw new IllegalArgumentException("offset must be EVEN (+1) or ODD (-1)");
        }
        return new OffsetCoord(col, row);
    }


    /**
     * Convert pointy top hexagon's offset coordinates to {@code Hex}
     *
     * @param offset offset type can be encoded as an offset direction +1 or -1. For pointy top, the offset direction
     *               tells us whether to slide alternate rows right or left. For flat top, the offset direction tells us
     *               whether to slide alternate columns up or down
     * @param coords    hexagon that stores coordinates in cube notation
     * @return {@code Hex} object
     * @throws IllegalArgumentException when offset != - 1 && offset != +1.
     */
    static public Hex pointyTopOffsetToCube(int offset, OffsetCoord coords) {
        int q = coords.col - (int) ((coords.row + offset * (coords.row & 1)) / 2);
        int r = coords.row;
        int s = -q - r;
        if (offset != OffsetCoord.EVEN && offset != OffsetCoord.ODD) {
            throw new IllegalArgumentException("offset must be EVEN (+1) or ODD (-1)");
        }
        return new Hex(q, r, s);
    }
}
