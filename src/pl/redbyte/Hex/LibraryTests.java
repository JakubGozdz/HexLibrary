package pl.redbyte.Hex;

import java.util.ArrayList;


public class LibraryTests {

    static private void equalHex(String name, Hex a, Hex b)
    {
        if (!(a.q == b.q && a.s == b.s && a.r == b.r))
        {
            LibraryTests.complain(name);
        } else {
            reportSuccess(new Object(){}.getClass().getEnclosingMethod().getName());
        }
    }


    static private void equalOffsetcoord(String name, OffsetCoord a, OffsetCoord b)
    {
        if (!(a.col == b.col && a.row == b.row))
        {
            LibraryTests.complain(name);
        } else {
            reportSuccess(new Object(){}.getClass().getEnclosingMethod().getName());
        }
    }


    static private void equalDoubledcoord(String name, DoubledCoord a, DoubledCoord b)
    {
        if (!(a.col == b.col && a.row == b.row))
        {
            LibraryTests.complain(name);
        } else {
            reportSuccess(new Object(){}.getClass().getEnclosingMethod().getName());
        }
    }


    static private void equalInt(String name, int a, int b)
    {
        if (!(a == b))
        {
            LibraryTests.complain(name);
        } else {
            reportSuccess(new Object(){}.getClass().getEnclosingMethod().getName());
        }
    }


    static private void equalHexArray(String name, ArrayList<Hex> a, ArrayList<Hex> b)
    {
        LibraryTests.equalInt(name, a.size(), b.size());
        for (int i = 0; i < a.size(); i++)
        {
            LibraryTests.equalHex(name, a.get(i), b.get(i));
        }
    }


    static private void testHexArithmetic()
    {
        LibraryTests.equalHex("hex_add", new Hex(4, -10, 6), new Hex(1, -3, 2).add(new Hex(3, -7, 4)));
        LibraryTests.equalHex("hex_subtract", new Hex(-2, 4, -2), new Hex(1, -3, 2).subtract(new Hex(3, -7, 4)));
    }


    static private void testHexDirection()
    {
        LibraryTests.equalHex("hex_direction", new Hex(0, -1, 1), Hex.directions.get(2));
    }


    static private void testHexNeighbor()
    {
        LibraryTests.equalHex("hex_neighbor", new Hex(1, -3, 2), new Hex(1, -2, 1).neighbor(2));
    }


    static private void testHexDiagonal()
    {
        LibraryTests.equalHex("hex_diagonal", new Hex(-1, -1, 2), new Hex(1, -2, 1).diagonalNeighbor(3));
    }


    static private void testHexDistance()
    {
        LibraryTests.equalInt("hex_distance", 7, new Hex(3, -7, 4).distance(new Hex(0, 0, 0)));
    }


    static private void testHexRotateRight()
    {
        LibraryTests.equalHex("hex_rotate_right", new Hex(1, -3, 2).rotateRight(), new Hex(3, -2, -1));
    }


    static private void testHexRotateLeft()
    {
        LibraryTests.equalHex("hex_rotate_left", new Hex(1, -3, 2).rotateLeft(), new Hex(-2, -1, 3));
    }


    static private void testHexRound()
    {
        FractionalHex a = new FractionalHex(0.0, 0.0, 0.0);
        FractionalHex b = new FractionalHex(1.0, -1.0, 0.0);
        FractionalHex c = new FractionalHex(0.0, -1.0, 1.0);
        LibraryTests.equalHex("hex_round 1", new Hex(5, -10, 5), new FractionalHex(0.0, 0.0, 0.0).hexLerp(new FractionalHex(10.0, -20.0, 10.0), 0.5).hexRound());
        LibraryTests.equalHex("hex_round 2", a.hexRound(), a.hexLerp(b, 0.499).hexRound());
        LibraryTests.equalHex("hex_round 3", b.hexRound(), a.hexLerp(b, 0.501).hexRound());
        LibraryTests.equalHex("hex_round 4", a.hexRound(), new FractionalHex(a.q * 0.4 + b.q * 0.3 + c.q * 0.3, a.r * 0.4 + b.r * 0.3 + c.r * 0.3, a.s * 0.4 + b.s * 0.3 + c.s * 0.3).hexRound());
        LibraryTests.equalHex("hex_round 5", c.hexRound(), new FractionalHex(a.q * 0.3 + b.q * 0.3 + c.q * 0.4, a.r * 0.3 + b.r * 0.3 + c.r * 0.4, a.s * 0.3 + b.s * 0.3 + c.s * 0.4).hexRound());
    }


    static private void testHexLinedraw()
    {
        LibraryTests.equalHexArray("hex_linedraw", new ArrayList<>(){{add(new Hex(0, 0, 0)); add(new Hex(0, -1, 1)); add(new Hex(0, -2, 2)); add(new Hex(1, -3, 2)); add(new Hex(1, -4, 3)); add(new Hex(1, -5, 4));}}, FractionalHex.hexLinedraw(new Hex(0, 0, 0), new Hex(1, -5, 4)));
    }


    static private void testLayout()
    {
        Hex h = new Hex(3, 4, -7);
        Layout flat = new Layout(Layout.flat, new Point(10.0, 15.0), new Point(35.0, 71.0));
        LibraryTests.equalHex("layout", h, flat.pixelToHex(flat.hexToPixel(h)));
        Layout pointy = new Layout(Layout.pointy, new Point(10.0, 15.0), new Point(35.0, 71.0));
        LibraryTests.equalHex("layout", h, pointy.pixelToHex(pointy.hexToPixel(h)));
    }


    static private void testOffsetRoundtrip()
    {
        Hex a = new Hex(3, 4, -7);
        OffsetCoord b = new OffsetCoord(1, -3);
        LibraryTests.equalHex("conversion_roundtrip even-q", a, OffsetCoord.flatTopOffsetToCube(OffsetCoord.EVEN, OffsetCoord.flatTopOffsetFromCube(OffsetCoord.EVEN, a)));
        LibraryTests.equalOffsetcoord("conversion_roundtrip even-q", b, OffsetCoord.flatTopOffsetFromCube(OffsetCoord.EVEN, OffsetCoord.flatTopOffsetToCube(OffsetCoord.EVEN, b)));
        LibraryTests.equalHex("conversion_roundtrip odd-q", a, OffsetCoord.flatTopOffsetToCube(OffsetCoord.ODD, OffsetCoord.flatTopOffsetFromCube(OffsetCoord.ODD, a)));
        LibraryTests.equalOffsetcoord("conversion_roundtrip odd-q", b, OffsetCoord.flatTopOffsetFromCube(OffsetCoord.ODD, OffsetCoord.flatTopOffsetToCube(OffsetCoord.ODD, b)));
        LibraryTests.equalHex("conversion_roundtrip even-r", a, OffsetCoord.pointyTopOffsetToCube(OffsetCoord.EVEN, OffsetCoord.pointyTopOffsetFromCube(OffsetCoord.EVEN, a)));
        LibraryTests.equalOffsetcoord("conversion_roundtrip even-r", b, OffsetCoord.pointyTopOffsetFromCube(OffsetCoord.EVEN, OffsetCoord.pointyTopOffsetToCube(OffsetCoord.EVEN, b)));
        LibraryTests.equalHex("conversion_roundtrip odd-r", a, OffsetCoord.pointyTopOffsetToCube(OffsetCoord.ODD, OffsetCoord.pointyTopOffsetFromCube(OffsetCoord.ODD, a)));
        LibraryTests.equalOffsetcoord("conversion_roundtrip odd-r", b, OffsetCoord.pointyTopOffsetFromCube(OffsetCoord.ODD, OffsetCoord.pointyTopOffsetToCube(OffsetCoord.ODD, b)));
    }


    static private void testOffsetFromCube()
    {
        LibraryTests.equalOffsetcoord("offset_from_cube even-q", new OffsetCoord(1, 3), OffsetCoord.flatTopOffsetFromCube(OffsetCoord.EVEN, new Hex(1, 2, -3)));
        LibraryTests.equalOffsetcoord("offset_from_cube odd-q", new OffsetCoord(1, 2), OffsetCoord.flatTopOffsetFromCube(OffsetCoord.ODD, new Hex(1, 2, -3)));
    }


    static private void testOffsetToCube()
    {
        LibraryTests.equalHex("offset_to_cube even-", new Hex(1, 2, -3), OffsetCoord.flatTopOffsetToCube(OffsetCoord.EVEN, new OffsetCoord(1, 3)));
        LibraryTests.equalHex("offset_to_cube odd-q", new Hex(1, 2, -3), OffsetCoord.flatTopOffsetToCube(OffsetCoord.ODD, new OffsetCoord(1, 2)));
    }


    static private void testDoubledRoundtrip()
    {
        Hex a = new Hex(3, 4, -7);
        DoubledCoord b = new DoubledCoord(1, -3);
        LibraryTests.equalHex("conversion_roundtrip doubled-q", a, DoubledCoord.flatTopDoubledToCube(DoubledCoord.flatTopDoubledFromCube(a)));
        LibraryTests.equalDoubledcoord("conversion_roundtrip doubled-q", b, DoubledCoord.flatTopDoubledFromCube(DoubledCoord.flatTopDoubledToCube(b)));
        LibraryTests.equalHex("conversion_roundtrip doubled-r", a, DoubledCoord.pointyTopDoubledToCube(DoubledCoord.pointyTopDoubledFromCube(a)));
        LibraryTests.equalDoubledcoord("conversion_roundtrip doubled-r", b, DoubledCoord.pointyTopDoubledFromCube(DoubledCoord.pointyTopDoubledToCube(b)));
    }


    static private void testDoubledFromCube()
    {
        LibraryTests.equalDoubledcoord("doubled_from_cube doubled-q", new DoubledCoord(1, 5), DoubledCoord.flatTopDoubledFromCube(new Hex(1, 2, -3)));
        LibraryTests.equalDoubledcoord("doubled_from_cube doubled-r", new DoubledCoord(4, 2), DoubledCoord.pointyTopDoubledFromCube(new Hex(1, 2, -3)));
    }


    static private void testDoubledToCube()
    {
        LibraryTests.equalHex("doubled_to_cube doubled-q", new Hex(1, 2, -3), DoubledCoord.flatTopDoubledToCube(new DoubledCoord(1, 5)));
        LibraryTests.equalHex("doubled_to_cube doubled-r", new Hex(1, 2, -3), DoubledCoord.pointyTopDoubledToCube(new DoubledCoord(4, 2)));
    }


    static public void run()
    {
        LibraryTests.testHexArithmetic();
        LibraryTests.testHexDirection();
        LibraryTests.testHexNeighbor();
        LibraryTests.testHexDiagonal();
        LibraryTests.testHexDistance();
        LibraryTests.testHexRotateRight();
        LibraryTests.testHexRotateLeft();
        LibraryTests.testHexRound();
        LibraryTests.testHexLinedraw();
        LibraryTests.testLayout();
        LibraryTests.testOffsetRoundtrip();
        LibraryTests.testOffsetFromCube();
        LibraryTests.testOffsetToCube();
        LibraryTests.testDoubledRoundtrip();
        LibraryTests.testDoubledFromCube();
        LibraryTests.testDoubledToCube();
    }


    static private void complain(String name)
    {
        System.out.println("FAIL " + name);
    }

    static private void reportSuccess(String name)
    {
        System.out.println(name + " - OK");
    }
}
