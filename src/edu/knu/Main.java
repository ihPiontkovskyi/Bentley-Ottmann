package edu.knu;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        double rangeMin = 100;
        double rangeMax = 700;

        ArrayList<Segment> data = new ArrayList<>();
        for(int i = 0; i < 100; i++) {
            Point first = new Point(rand(rangeMin, rangeMax), rand(rangeMin, rangeMax));
            Point second = new Point(rand(rangeMin, rangeMax), rand(rangeMin, rangeMax));
            data.add(new Segment(first, second));
        }

        BentleyOttmann test = new BentleyOttmann(data);

        long t1 = System.currentTimeMillis();
        test.findIntersections();
        long t2 = System.currentTimeMillis();

       // test.printIntersections();
        ArrayList<Point> intersections = test.getIntersections();

        new GUI(data, intersections);

        System.out.println("number of intersections: " + intersections.size());
        System.out.println("runtime: " + (t2 - t1) + " ms");
    }

    private static double rand(double rangeMin, double rangeMax) {
        Random r = new Random();
        return rangeMin + (r.nextDouble() * (rangeMax - rangeMin));
    }

}
