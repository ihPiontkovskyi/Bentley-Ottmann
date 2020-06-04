package edu.knu;

public class Segment implements Comparable<Segment>{

    private final Point first;
    private final Point second;
    double value;

    Segment(Point first, Point second) {
        this.first = first;
        this.second = second;
        this.calculateValue(this.first().getX());
    }

    public Point first() {
        if(first.getX() <= second.getX()) {
            return first;
        } else {
            return second;
        }
    }

    public Point second() {
        if(first.getX() <= second.getX()) {
            return second;
        } else {
            return first;
        }
    }

    public void calculateValue(double value) {
        double x1 = this.first().getX();
        double x2 = this.second().getX();
        double y1 = this.first().getY();
        double y2 = this.second().getY();
        this.value = y1 + (((y2 - y1) / (x2 - x1)) * (value - x1));
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getValue() {
        return this.value;
    }

    @Override
    public int compareTo(Segment segment) {
        return Double.compare(segment.getValue(), this.getValue());
    }
}
