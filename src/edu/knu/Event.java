package edu.knu;

import java.util.*;

public class Event implements Comparable<Event> {

    private final Point point;
    private final ArrayList<Segment> segments;
    private final double value;
    private final int type;

    Event(Point p, Segment s, int type) {
        this.point = p;
        this.segments = new ArrayList<>(Collections.singletonList(s));
        this.value = p.getX();
        this.type = type;
    }

    Event(Point p, ArrayList<Segment> s, int type) {
        this.point = p;
        this.segments = s;
        this.value = p.getX();
        this.type = type;
    }

    public Point getPoint() {
        return this.point;
    }

    public ArrayList<Segment> getSegments() {
        return this.segments;
    }

    public int getType() {
        return this.type;
    }

    public double getValue() {
        return this.value;
    }

    @Override
    public int compareTo(Event event) {
        return Double.compare(this.getValue(), event.getValue());
    }
}
