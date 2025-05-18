package org.example.model.entity;

public class Movie {
    private int id;
    private String name;
    private int seatCount;

    public Movie(int id, String name, int seatCount) {
        this.id = id;
        this.name = name;
        this.seatCount = seatCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
