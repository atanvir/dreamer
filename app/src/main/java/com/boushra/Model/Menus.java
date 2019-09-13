package com.boushra.Model;

public class Menus {
    private String menu_name;
    private int menu_icon;

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public int getMenu_icon() {
        return menu_icon;
    }

    public void setMenu_icon(int menu_icon) {
        this.menu_icon = menu_icon;
    }

    public Menus(String menu_name, int menu_icon) {
        this.menu_name = menu_name;
        this.menu_icon=menu_icon;
    }

    public Menus(String menu_name) {
        this.menu_name = menu_name;
    }
}
