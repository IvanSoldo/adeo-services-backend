package com.adeo.services.controller.room;

import com.adeo.services.entity.Room;

import java.util.List;
import java.util.stream.Collectors;

public class RoomResponseList {

    private final List<RoomResponse> rooms;

    private RoomResponseList(List<RoomResponse> rooms) {
        this.rooms = rooms;
    }

    public List<RoomResponse> getRooms() {
        return rooms;
    }

    public static RoomResponseList from(List<Room> rooms) {
        List<RoomResponse> roomResponses = rooms.stream().map(RoomResponse::from).collect(Collectors.toList());
        return new RoomResponseList(roomResponses);
    }

}
