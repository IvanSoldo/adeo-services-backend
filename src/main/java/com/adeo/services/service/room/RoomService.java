package com.adeo.services.service.room;

import com.adeo.services.entity.Room;
import java.util.List;

public interface RoomService {

  List<Room> getAll();

  String save(Room room);

  void update(Room room);

  void delete(String id);

  Room getById(String id);
}
