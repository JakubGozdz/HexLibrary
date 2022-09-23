package pl.redbyte.Hex;

import java.util.ArrayList;

public class Hex {

    /**
     * Cube coordinates derived from x,y,z cartesian coordinates, and use three axes q,r,s 120° apart, where q+r+s = 0.
     */
    public final int q;
    public final int r;
    public final int s;


    /**
     * Cube coordinates constructor. Cube coordinates are derived from x,y,z cartesian coordinates, and use three axes
     * q,r,s 120° apart, where q+r+s must be 0.
     *
     * @param q Cube coordinate
     * @param r Cube coordinate
     * @param s Cube coordinate
     * @return {@code Hex} object
     * @throws IllegalArgumentException when q+r+s is not equal to 0.
     */
    public Hex(int q, int r, int s) {
        this.q = q;
        this.r = r;
        this.s = s;
        if (q + r + s != 0) throw new IllegalArgumentException("q + r + s must be 0");
    }


    /**
     * Axial coordinate's constructor. Axial coordinates have two axes q,r that are 120° apart, and don't store the s
     * coordinate. Since there is a constraint q+r+s = 0, it can be calculated (s = -q-r) when it's needed. The library
     * provides a constructor for axial coordinates, but still store them as a cube coordinates.
     *
     * @param q Axial coordinate
     * @param r Axial coordinate
     * @return {@code Hex} object
     */
    public Hex(int q, int r) {
        this.q = q;
        this.r = r;
        this.s = -q - r;
    }


    /**
     * Compares this object to the specified object. The result is {@code true} if and only if the argument is not
     * {@code null} and is a {@code Hex} object that contains the same {@code q}, {@code r}, {@code s} values as this
     * object.
     *
     * @param obj the object to compare with.
     * @return {@code true} if the objects are the same; {@code false} otherwise.
     */
    public boolean equals(Object obj) {
        if (obj instanceof Hex) {
            return q == ((Hex) obj).q && r == ((Hex) obj).r && s == ((Hex) obj).s;
        }
        return false;
    }


    /**
     * Returns a {@code String} object representing this {@code Hex}.
     *
     * @return a string representation of the object.
     */
    public String toString() {
        return "Hex(q: " + q + ", r: " + r + ", s: " + s + ")";
    }


    /**
     * Since cube coordinates come from 3d cartesian coordinates, I automatically get things like addition, subtraction,
     * multiplication, and division. For example, you can have Hex(2, 0, -2) that represents two steps southeast, and
     * add that to location Hex(3, -5, 2) the obvious way: Hex(3 + 2, -5 + 0, 2 + -2). With other coordinate systems
     * like offset coordinates, it is not possible in such a simple way.
     *
     * @param addend the {@code Hex} that is added to this object to give a sum
     * @return the sum of both {@code Hex}
     */
    public Hex add(Hex addend) {
        return new Hex(q + addend.q, r + addend.r, s + addend.s);
    }


    /**
     * Since cube coordinates come from 3d cartesian coordinates, I automatically get things like addition, subtraction,
     * multiplication, and division. For example, you can have Hex(2, 0, -2) that represents two steps southeast, and
     * subtract that from location Hex(3, -5, 2) the obvious way: Hex(3 - 2, -5 - 0, 2 - -2) to get a Hex two steps in
     * opposite direction. With other coordinate systems like offset coordinates, it is not possible in such a simple
     * way.
     *
     * @param subtrahend the {@code Hex} that is substracted from this object
     * @return the difference of both {@code Hex}
     */
    public Hex subtract(Hex subtrahend) {
        return new Hex(q - subtrahend.q, r - subtrahend.r, s - subtrahend.s);
    }


    /**
     * Returns a Hex that is {@code scale} times farther from {@code Hex(0,0,0)} than this object.
     *
     * @param scale the scale
     * @return the {@code Hex} with coordinates multiplied by {@code scale} value.
     */
    public Hex scale(int scale) {
        return new Hex(q * scale, r * scale, s * scale);
    }


    /**
     * In the library there are two one-step rotation functions, but which is “left” and which is “right” depends on
     * your map orientation. You may have to swap these.
     *
     * @return rotated {@code Hex}
     */
    public Hex rotateLeft() {
        return new Hex(-s, -q, -r);
    }


    /**
     * In the library there are two one-step rotation functions, but which is “left” and which is “right” depends on
     * your map orientation. You may have to swap these.
     *
     * @return rotated {@code Hex}
     */
    public Hex rotateRight() {
        return new Hex(-r, -s, -q);
    }


    /**
     * The distance between {@code Hex(0,0,0)} and this object.
     *
     * @return distance between {@code Hex(0,0,0)} and this object.
     */
    public int length() {
        return (int) ((Math.abs(q) + Math.abs(r) + Math.abs(s)) / 2);
    }


    /**
     * The distance between two hexes is the length of the line between them.
     *
     * @param destination destination {@code Hex}
     * @return distance between this {@code Hex} and the destination {@code Hex}
     */
    public int distance(Hex destination) {
        return subtract(destination).length();
    }


    /**
     * Moving one space in hex coordinates involves changing one of the 3 cube coordinates by +1 and changing another
     * one by -1 (the sum must remain 0). There are 3 possible coordinates to change by +1, and 2 remaining that could
     * be changed by -1. This results in 6 possible changes. Each corresponds to one of the hexagonal directions. The
     * simplest and fastest approach is to precompute the permutations and put them into a table of Cube(dq, dr, ds)
     */
    static public ArrayList<Hex> directions = new ArrayList<Hex>() {{
        add(new Hex(1, 0, -1));
        add(new Hex(1, -1, 0));
        add(new Hex(0, -1, 1));
        add(new Hex(-1, 0, 1));
        add(new Hex(-1, 1, 0));
        add(new Hex(0, 1, -1));
    }};


    /**
     * Returns a neighbor {@code Hex} in specified direction
     *
     * @param direction direction index [0..5] of the direction vector stored in precomputed array
     * @return neighbor {@code Hex}
     */
    public Hex neighbor(int direction) {
        return add(Hex.directions.get(direction));
    }


    /**
     * Moving to a “diagonal” space in hex coordinates changes one of the 3 cube coordinates by ±2 and the other two by
     * ∓1 (the sum must remain 0). This results in 6 possible changes. Each corresponds to one of the diagonal
     * directions. The simplest and fastest approach is to precompute the permutations and put them into a table of
     * Cube(dq, dr, ds)
     */
    static public ArrayList<Hex> diagonals = new ArrayList<Hex>() {{
        add(new Hex(2, -1, -1));
        add(new Hex(1, -2, 1));
        add(new Hex(-1, -1, 2));
        add(new Hex(-2, 1, 1));
        add(new Hex(-1, 2, -1));
        add(new Hex(1, 1, -2));
    }};


    /**
     * Returns a diagonal neighbor {@code Hex} in specified direction
     *
     * @param direction direction index [0..5] of the direction vector stored in precomputed array
     * @return diagonal neighbor {@code Hex}
     */
    public Hex diagonalNeighbor(int direction) {
        return add(Hex.diagonals.get(direction));
    }
}