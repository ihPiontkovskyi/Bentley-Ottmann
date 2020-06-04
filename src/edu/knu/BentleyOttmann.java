package edu.knu;

import java.util.*;

public class BentleyOttmann {

    private final Queue<Event> q;
    private final NavigableSet<Segment> t;
    private final ArrayList<Point> x;

    BentleyOttmann(ArrayList<Segment> data) {
        this.q = new PriorityQueue<>();
        this.t = new TreeSet<>();
        this.x = new ArrayList<>();
        for(Segment s : data) {
            this.q.add(new Event(s.first(), s, 0));
            this.q.add(new Event(s.second(), s, 1));
        }
    }

    public void findIntersections() {
        while(!this.q.isEmpty()) {
            Event e = this.q.poll();
            double L = e.getValue();
            switch(e.getType()) {
            case 0:
                for(Segment s : e.getSegments()) {
                    this.recalculate(L);
                    this.t.add(s);
                    if(this.t.lower(s) != null) {
                        Segment r = this.t.lower(s);
                        this.reportIntersection(r, s, L);
                    }
                    if(this.t.higher(s) != null) {
                        Segment t = this.t.higher(s);
                        this.reportIntersection(t, s, L);
                    }
                    if(this.t.lower(s) != null && this.t.higher(s) != null) {
                        Segment r = this.t.lower(s);
                        Segment t = this.t.higher(s);
                        this.removeFuture(r, t);
                    }
                }
                break;
            case 1:
                for(Segment s : e.getSegments()) {
                    if(this.t.lower(s) != null && this.t.higher(s) != null) {
                        Segment r = this.t.lower(s);
                        Segment t = this.t.higher(s);
                        this.reportIntersection(r, t, L);
                    }
                    this.t.remove(s);
                }
                break;
            case 2:
                Segment segFirst = e.getSegments().get(0);
                Segment segSecond = e.getSegments().get(1);
                this.swap(segFirst, segSecond);
                if(segFirst.getValue() < segSecond.getValue()) {
                    if(this.t.higher(segFirst) != null) {
                        Segment t = this.t.higher(segFirst);
                        this.reportIntersection(t, segFirst, L);
                        this.removeFuture(t, segSecond);
                    }
                    if(this.t.lower(segSecond) != null) {
                        Segment r = this.t.lower(segSecond);
                        this.reportIntersection(r, segSecond, L);
                        this.removeFuture(r, segFirst);
                    }
                } else {
                    if(this.t.higher(segSecond) != null) {
                        Segment t = this.t.higher(segSecond);
                        this.reportIntersection(t, segSecond, L);
                        this.removeFuture(t, segFirst);
                    }
                    if(this.t.lower(segFirst) != null) {
                        Segment r = this.t.lower(segFirst);
                        this.reportIntersection(r, segFirst, L);
                        this.removeFuture(r, segSecond);
                    }
                }
                this.x.add(e.getPoint());
                break;
            }
        }
    }

    private void reportIntersection(Segment segFirst, Segment segSecond, double L) {
        double x1 = segFirst.first().getX();
        double y1 = segFirst.first().getY();
        double x2 = segFirst.second().getX();
        double y2 = segFirst.second().getY();
        double x3 = segSecond.first().getX();
        double y3 = segSecond.first().getY();
        double x4 = segSecond.second().getX();
        double y4 = segSecond.second().getY();
        double r = (x2 - x1) * (y4 - y3) - (y2 - y1) * (x4 - x3);
        if(r != 0) {
            double t = ((x3 - x1) * (y4 - y3) - (y3 - y1) * (x4 - x3)) / r;
            double u = ((x3 - x1) * (y2 - y1) - (y3 - y1) * (x2 - x1)) / r;
            if(t >= 0 && t <= 1 && u >= 0 && u <= 1) {
                double xC = x1 + t * (x2 - x1);
                double yC = y1 + t * (y2 - y1);
                if(xC > L) {
                    this.q.add(new Event(new Point(xC, yC), new ArrayList<>(Arrays.asList(segFirst, segSecond)), 2));
                }
            }
        }
    }

    private void removeFuture(Segment segFirst, Segment segSecond) {
        for(Event e : this.q) {
            if(e.getType() == 2) {
                if((e.getSegments().get(0) == segFirst && e.getSegments().get(1) == segSecond) || (e.getSegments().get(0) == segSecond && e.getSegments().get(1) == segFirst)) {
                    this.q.remove(e);
                    return;
                }
            }
        }
    }

    private void swap(Segment s_1, Segment s_2) {
        this.t.remove(s_1);
        this.t.remove(s_2);
        double value = s_1.getValue();
        s_1.setValue(s_2.getValue());
        s_2.setValue(value);
        this.t.add(s_1);
        this.t.add(s_2);
    }

    private void recalculate(double L) {
        for (Segment segment : this.t) {
            segment.calculateValue(L);
        }
    }

    public void printIntersections() {
        for(Point p : this.x) {
            System.out.println("(" + p.getX() + ", " + p.getY() + ")");
        }
    }

    public ArrayList<Point> getIntersections() {
        return this.x;
    }

}