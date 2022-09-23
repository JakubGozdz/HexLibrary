package pl.redbyte.Hex;

import java.util.ArrayList;

public class FractionalHex {
    /**
     * Sometimes we'll end up with a floating-point cube coordinate, and we'll want to know which hex it should be in.
     * This comes up in line drawing and pixel to hex. For those operations we need fractional hex coordinates. It looks
     * like the Hex class, but uses {@code double} instead of {@code int}
     */
    public final double q;
    public final double r;
    public final double s;


    /**
     * FractionalHex constructor.
     *
     * @param q Cube coordinate
     * @param r Cube coordinate
     * @param s Cube coordinate
     * @return {@code FractionalHex} object
     * @throws IllegalArgumentException when q+r+s is not equal to 0.
     */
    public FractionalHex(double q, double r, double s) {
        this.q = q;
        this.r = r;
        this.s = s;
        if (Math.round(q + r + s) != 0) throw new IllegalArgumentException("q + r + s must be 0");
    }

    /**
     * Rounding method turns a fractional hex coordinate into the nearest integer hex coordinate.
     * <p>
     * Sometimes we'll end up with a floating-point cube coordinate, and we'll want to know which hex it should be in.
     * This comes up in line drawing and pixel to hex. Converting a floating point value to an integer value is called
     * rounding so I call this algorithm {@code hexRound}
     * <p>
     * Just as with integer cube coordinates, fractionalHex.q + fractionalHex.r + fractionalHex.s = 0 with fractional
     * (floating point) cube coordinates. We can round each component to the nearest integer, qi = (int)
     * (Math.round(q)); ri = (int) (Math.round(r)); si = (int) (Math.round(s));. However, after rounding we do not have
     * a guarantee that q + r + s = 0. We do have a way to correct the problem: reset the component with the largest
     * change back to what the constraint q + r + s = 0 requires. For example, if the r-change Math.abs(ri - r) is
     * larger than Math.abs(qi - q) and Math.abs(si - s), then we reset r = -q-s. This guarantees that q + r + s = 0.
     *
     * @return the closest {@code Hex} to this {@code FractionalHex}
     */
    public Hex hexRound() {
        int qi = (int) (Math.round(q));
        int ri = (int) (Math.round(r));
        int si = (int) (Math.round(s));
        double q_diff = Math.abs(qi - q);
        double r_diff = Math.abs(ri - r);
        double s_diff = Math.abs(si - s);
        if (q_diff > r_diff && q_diff > s_diff) {
            qi = -ri - si;
        } else if (r_diff > s_diff) {
            ri = -qi - si;
        } else {
            si = -qi - ri;
        }
        return new Hex(qi, ri, si);
    }


    /**
     * To draw startHex line from one hex to another we use linear interpolation for line drawing. Evenly sample the
     * line at N+1 points, and figure out which hexes those samples are in.
     * <p>
     * First we calculate N to be the hex distance between the endpoints.
     * <p>
     * Then evenly sample N+1 points between point A and point B. Using linear interpolation, each point will be A + (B
     * - A) * 1.0/N * i, for values of i from 0 to N, inclusive. This results in floating point coordinates.
     * <p>
     * Convert each sample point (float) back into startHex hex (int) using {@code hexRound} method.
     * <p>
     * There are times when {@code hexLerp} will return startHex point that's exactly on the side between two hexes.
     * Then {@code hexRound} pushes it one way or the other. The lines look better if it's always pushed in the same
     * direction. So, we do this by adding an "epsilon" hex Cube(1e-6, 2e-6, -3e-6) to both of the endpoints before
     * startHex the loop. This will "nudge" the line in one direction to avoid landing on side boundaries.
     *
     * @param startHex startHex Hex
     * @param endHex   endHex Hex
     * @return an array of Hex that interpolates the line between two hexagons
     */
    static public ArrayList<Hex> hexLinedraw(Hex startHex, Hex endHex) {
        int N = startHex.distance(endHex);
        FractionalHex startHexNudged = new FractionalHex(startHex.q + 1e-06, startHex.r + 1e-06, startHex.s - 2e-06);
        FractionalHex endHexNudged = new FractionalHex(endHex.q + 1e-06, endHex.r + 1e-06, endHex.s - 2e-06);
        ArrayList<Hex> results = new ArrayList<Hex>() {{
        }};
        double step = 1.0 / Math.max(N, 1);
        for (int i = 0; i <= N; i++) {
            results.add(startHexNudged.hexLerp(endHexNudged, step * i).hexRound());
        }
        return results;
    }

    /**
     * Intermediate method for {@code hexLinedraw}
     * <p>
     * Then evenly sample N+1 points between point A and point B. Using linear interpolation, each point will be A + (B
     * - A) * 1.0/N * i, for values of i from 0 to N, inclusive. This results in floating point coordinates. Then find
     * the closest FractionalHex and return it to the main method.
     *
     * @param endHex endHex
     * @return next Fractional Hex that interpolates the line between two hexagons
     */
    public FractionalHex hexLerp(FractionalHex endHex, double lineFraction) {
        return new FractionalHex(q * (1.0 - lineFraction) + endHex.q * lineFraction, r * (1.0 - lineFraction) + endHex.r * lineFraction, s * (1.0 - lineFraction) + endHex.s * lineFraction);
    }

}
