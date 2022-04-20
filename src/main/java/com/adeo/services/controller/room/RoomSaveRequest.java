package com.adeo.services.controller.room;

import com.adeo.services.entity.Room;

import javax.validation.constraints.NotNull;

public class RoomSaveRequest {

    @NotNull
    private String name;
    @NotNull
    private String number;

    public RoomSaveRequest(){
    }

    public Room from() {
        Room room = new Room();
        room.setName(this.name);
        room.setNumber(this.number);
        return room;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
