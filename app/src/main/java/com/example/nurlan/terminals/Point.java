package com.example.nurlan.terminals;

/**
 * Created by Nurlan on 13.06.2016.
 */
public class Point {
    int point_id;
    String point_name;
    Double point_lat;
    Double point_longt;


    public Point(){
    }

    public Point(int point_id, String point_name, Double point_lat, Double point_longt) {
        this.point_id = point_id;
        this.point_name = point_name;
        this.point_lat = point_lat;
        this.point_longt = point_longt;
    }

    public Point(String point_name, Double point_lat, Double point_longt) {
        this.point_name = point_name;
        this.point_lat = point_lat;
        this.point_longt = point_longt;
    }

    public int getPoint_id() {
        return point_id;
    }

    public void setPoint_id(int point_id) {
        this.point_id = point_id;
    }

    public String getPoint_name() {
        return point_name;
    }

    public void setPoint_name(String point_name) {
        this.point_name = point_name;
    }

    public Double getPoint_lat() {
        return point_lat;
    }

    public void setPoint_lat(Double point_lat) {
        this.point_lat = point_lat;
    }

    public Double getPoint_longt() {
        return point_longt;
    }

    public void setPoint_longt(Double point_longt) {
        this.point_longt = point_longt;
    }

    @Override
    public String toString() {
        return "Point{" +
                "point_id=" + point_id +
                ", point_name='" + point_name + '\'' +
                ", point_lat=" + point_lat +
                ", point_longt=" + point_longt +
                '}';
    }
}