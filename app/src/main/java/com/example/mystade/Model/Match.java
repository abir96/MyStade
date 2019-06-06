package com.example.mystade.Model;

public class Match {
    String description ;
    String date ;
    String id ;

    public Match(String description , String date , String id) {
        this.description = description;
        this.date = date;
        this.id = id ;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }
}
