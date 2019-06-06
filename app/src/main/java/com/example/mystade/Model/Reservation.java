package com.example.mystade.Model;

public class Reservation {
     String PlaceId ;
    String description ;
    String date ;
    String id ;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Reservation(String placeId, String description, String date, String id) {
        PlaceId = placeId;
        this.description = description;
        this.date = date;
        this.id = id;
    }

    public String getPlaceId() {
        return PlaceId;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public void setPlaceId(String placeId) {
        PlaceId = placeId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
