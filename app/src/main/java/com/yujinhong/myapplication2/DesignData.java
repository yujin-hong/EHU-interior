package com.yujinhong.myapplication2;
import android.graphics.drawable.Drawable;

public class DesignData {
    private Drawable icon;
    private String username;
    private String title;
    private String address;
    private String date;
    private String detail;

    public DesignData(){}

    public DesignData(Drawable icon, String username, String title, String address, String date, String detail){
        this.icon = icon;
        this.username = username;
        this.title = title;
        this.address = address;
        this.date = date;
        this.detail = detail;
    }

    public Drawable getIcon() { return icon;}

    public String getUsername(){ return username; }

    public String getTitle(){
        return title;
    }

    public String getAddress(){
        return address;
    }

    public String getDate(){
        return date;
    }

    public String getDetail() { return detail; }

    public void setIcon(Drawable draw) {icon = draw;}

    public void setUsername(String user) { username = user;}

    public void setTitle(String tit) { title = tit; }

    public void setAddress(String add) { address = add; }

    public void setDate(String dat) { date = dat; }

    public void setDetail(String det) { detail = det;}
}