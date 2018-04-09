package com.example.princ.inclass09;

import java.util.ArrayList;

public class Trip {

    ArrayList<Point> points=new ArrayList<>();
    String title;

    public static class Point{
        double latitude;
        double longitude;

        @Override
        public String toString() {
            return "Point{" +
                    "latitude='" + latitude + '\'' +
                    ", longitude='" + longitude + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Trip{" +
                "points=" + points +
                ", title='" + title + '\'' +
                '}';
    }
}
