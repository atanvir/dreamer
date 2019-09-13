package com.boushra.Model;

public class StorePoints {
    private String points;
    private int point_icon;
    private String point_detail;


    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public int getPoint_icon() {
        return point_icon;
    }

    public void setPoint_icon(int point_icon) {
        this.point_icon = point_icon;
    }

    public String getPoint_detail() {
        return point_detail;
    }

    public void setPoint_detail(String point_detail) {
        this.point_detail = point_detail;
    }

    public StorePoints(String points, int point_icon, String point_detail) {
        this.points = points;
        this.point_icon = point_icon;
        this.point_detail = point_detail;
    }
}
