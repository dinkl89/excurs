package com.kleshch.excurs;

import com.google.android.gms.maps.model.LatLng;

public class Point {

    private String name;
    private String shortInfo;
    private String address;
    private String question;
    private String answer;
    private String story;
    private String image;
    private int pointId;
    private LatLng coordinates;

    public Point(int pointId, String name, String shortInfo, String address, String image, LatLng latLng, String story, String question, String answer){
        this.pointId = pointId;
        this.name = name;
        this.shortInfo = shortInfo;
        this.address = address;
        this.image = image;
        this.coordinates = latLng;
        this.story = story;
        this.question = question;
        this.answer = answer;
    }

    public LatLng getCoordinates(){
        return coordinates;
    }

    public String getName(){
        return name;
    }

    public String getShortInfo() {
        return shortInfo;
    }

    public String getStory() {
        return story;
    }

    public String getImage() {
        return image;
    }

    public int getPointId() {
        return pointId;
    }

    public String getAddress() {
        return address;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}
