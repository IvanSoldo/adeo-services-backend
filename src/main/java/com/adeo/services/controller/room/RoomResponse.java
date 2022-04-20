package com.adeo.services.controller.room;

import com.adeo.services.entity.Room;

public class RoomResponse {

    private final String id;
    private final String name;
    private final String number;

    private RoomResponse(String id, String name, String number) {
        this.id = id;
        this.name = name;
        this.number = number;
    }

    public static RoomResponse from(Room room) {
        return new RoomResponse(room.getUuid(), room.getName(), room.getNumber());
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return this.number;
    }

}
